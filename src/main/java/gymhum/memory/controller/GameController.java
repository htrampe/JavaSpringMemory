package gymhum.memory.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gymhum.memory.model.Player;

@Controller
public class GameController {
    
    @GetMapping("/game/startgame")
    public String startGame(@RequestParam(name="player1", required = true) int player1, @RequestParam(name="player2", required = true) int player2, Model model){
        System.out.println("CREATE NEW GAME!");
        return "game.html";
    }

    @PostMapping("/game/games")
    public String viewGames(RedirectAttributes redirectAttributes, @RequestParam MultiValueMap body, Model model) throws NumberFormatException, SQLException{
        DatabaseController db = new DatabaseController();
        Player player1 = db.getPlayer(Integer.parseInt(body.getFirst("player1").toString()));
        Player player2 = db.getPlayer(Integer.parseInt(body.getFirst("player2").toString()));
        
        System.out.println();
        if(player1 != null && player2 != null){
            if(Integer.parseInt(body.getFirst("player1_key").toString()) == player1.getKey() && Integer.parseInt(body.getFirst("player2_key").toString()) == player2.getKey() && player1.getId() != player2.getId()){
                
                model.addAttribute("player1", player1);
                model.addAttribute("player2", player2);

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
