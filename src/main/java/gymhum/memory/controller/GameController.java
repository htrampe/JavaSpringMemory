package gymhum.memory.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import gymhum.memory.model.Game;
import gymhum.memory.model.MemoryCard;
import gymhum.memory.model.Player;

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

    @GetMapping("/game/load")
    public String loadGame(@RequestParam(name="gameId", required = true) int gameId, Model model) throws SQLException{
        DatabaseController db = new DatabaseController();
        Game game = db.getGame(gameId);

        ArrayList<MemoryCard> memorycards = db.getGameMemoryCards(gameId);

        if(memorycards.size() == 0){
            System.out.println("CARDSSET EMPTY - CREATE");
        }
        else {
            model.addAttribute("cards", memorycards);
        }

        model.addAttribute("game", game);

        return "game.html";
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
