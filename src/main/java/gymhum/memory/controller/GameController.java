package gymhum.memory.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import gymhum.memory.model.Game;
import gymhum.memory.model.MemoryCard;
import gymhum.memory.model.Player;

import java.util.concurrent.ThreadLocalRandom;

import javax.xml.crypto.Data;

import org.json.JSONObject;
import org.json.JSONTokener;

@Controller
public class GameController {
    
    @GetMapping("/game/startgame")
    public RedirectView startGame(RedirectAttributes redirectAttributes, @RequestParam(name="player1", required = true) int player1, @RequestParam(name="player2", required = true) int player2, Model model) throws SQLException{
        
        Game game = new Game(player1, player2);
        
        DatabaseController db = new DatabaseController();
        int newGameId = db.saveNewGame(game, player1, player2);
        redirectAttributes.addAttribute("gameId", newGameId);
        return new RedirectView("/game/load");
    }

    @GetMapping("/game/load/resc")
    public RedirectView resetMemoryCards(RedirectAttributes redirectAttributes, @RequestParam(name="gameid", required = true, defaultValue = "0") int gameid) throws SQLException{
        
        DatabaseController db = new DatabaseController();
        db.resetMemoryCardsInGame(gameid);
        
        redirectAttributes.addAttribute("gameId", gameid);
        return new RedirectView("/game/load");
    }

    @GetMapping("/game/load")
    public String loadGame(@RequestParam(name="gameId", required = true) int gameId, Model model) throws SQLException, IOException {
        DatabaseController db = new DatabaseController();
        Game game = db.getGame(gameId);

        ArrayList<MemoryCard> memorycards = db.getGameMemoryCards(gameId);
        
        // No Memorycards created - create new set for given game and save to database
        if(memorycards.size() == 0){

            // Loading Images
            int randomPicPage = ThreadLocalRandom.current().nextInt(1, 100);
            URLConnection connection = new URL("https://api.pexels.com/v1/curated?per_page=63&page=" + randomPicPage).openConnection();
            connection.setRequestProperty("Authorization", "O4wyLlJNjXgpFxcJPkqyGrToonSTkQFFvjRw4Nj4J3jgUhhoUxNhnJsu");
            //Get Response  
            InputStream is = connection.getInputStream();
            JSONObject jsonObject = new JSONObject(new JSONTokener(is));

            for(int i = 0; i <= 27; i++){
                // Generate pair key
                int pairKey = ThreadLocalRandom.current().nextInt(1000000, 9999999);
                // generate Pic-URL
                JSONObject pic = (JSONObject) jsonObject.getJSONArray("photos").get(i);
                String picUrl = pic.getJSONObject("src").getString("medium");

                // Card 1
                int newId = db.saveNewMemorCard(picUrl, "", "0", "" + pairKey, "" + gameId);
                memorycards.add(new MemoryCard(gameId, pairKey, picUrl, 0, newId, 0));

                // Card 2 - same pic
                newId = db.saveNewMemorCard(picUrl, "", "0", "" + pairKey, "" + gameId);
                memorycards.add(new MemoryCard(gameId, pairKey, picUrl, 0, newId, 0));
            }

            // Shuffle the array list
            Collections.shuffle(memorycards);

            // save new Slot in Card
            for(int i = 0; i <= 53; i++){
                memorycards.get(i).setSlot(i);
            }
            model.addAttribute("cards", memorycards);
        }
        else {
            model.addAttribute("cards", memorycards);
        }
        
        // Count visible Cards in game
        int visibleCardCount = 0;
        for(int i = 0; i < memorycards.size(); i++){
            if(memorycards.get(i).getStatus() == 1){
                visibleCardCount = visibleCardCount + 1;
            }
        }
        model.addAttribute("visibleCardCount", visibleCardCount);

        model.addAttribute("game", game);
        model.addAttribute("gameid", gameId);
        return "game.html";
    }

    /*
     * MemoryCard update ONLY
     * 
     * Main gameplay-function in loadGame
     */
    @GetMapping("/game/uc/")
    public RedirectView updateMemoryCard(RedirectAttributes redirectAttributes, @RequestParam(name="cardid", required = true, defaultValue = "0") int cardid, @RequestParam(name="gameid", required = true, defaultValue = "0") int gameid) throws SQLException{
        
        DatabaseController db = new DatabaseController();
        db.updateMemoryCard(cardid, gameid);

        redirectAttributes.addAttribute("gameId", gameid);
        return new RedirectView("/game/load");
    }

    @GetMapping("/game/del")
    public RedirectView removeGame(@RequestParam(name="id", required = true, defaultValue = "0") int id){
        DatabaseController db = new DatabaseController();
        try {
            db.removeGame(id);
        } catch (SQLException e) {
            System.out.println("ERROR while removing game");
            System.out.println(e);
        }
        return new RedirectView("/");
    }

    @PostMapping("/game/games")
    public String viewGames(RedirectAttributes redirectAttributes, @RequestParam MultiValueMap body, Model model) throws NumberFormatException, SQLException, ParseException{
        DatabaseController db = new DatabaseController();
        Player player1 = db.getPlayer(Integer.parseInt(body.getFirst("player1").toString()));
        Player player2 = db.getPlayer(Integer.parseInt(body.getFirst("player2").toString()));
        
        System.out.println();
        if(player1 != null && player2 != null){
            if(Integer.parseInt(body.getFirst("player1_key").toString()) == player1.getKey() && Integer.parseInt(body.getFirst("player2_key").toString()) == player2.getKey() && player1.getId() != player2.getId()){
                
                model.addAttribute("player1", player1);
                model.addAttribute("player2", player2);

                model.addAttribute("games", db.getGamesByPlayerIds(player1.getId(), player2.getId()));

                return "games.html";
            }
            else{
                redirectAttributes.addAttribute("error", "true");
                return "redirect:/";
            }
        }
        else{
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/";
        }
       
    }
}
