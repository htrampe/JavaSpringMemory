package gymhum.memory.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import gymhum.memory.model.Player;


@Controller
public class PlayerController {
    
    ArrayList<Player> players = new ArrayList<Player>();

    @GetMapping("/player")
    public String managePlayer(Model model) throws SQLException, IOException {
        DatabaseController db = new DatabaseController();
        model.addAttribute("players", db.getAllPlayers());
        return "player.html";
    }

    @GetMapping("/player/add")
    public String addPlayer() {
        return "player_add.html";
    }

    @GetMapping("/player/del")
    public RedirectView removePlayer(@RequestParam(name="id", required = true, defaultValue = "0") int id){
        DatabaseController db = new DatabaseController();
        try {
            db.removePlayer(id);
        } catch (SQLException e) {
            System.out.println("ERROR while removing player");
            System.out.println(e);
        }
        return new RedirectView("/player");
    }
    
    // Reading data from Request and turn it into Player-Object
    @PostMapping(path="/player/add/do")
    public RedirectView addPlayerDo(@RequestParam MultiValueMap body){

        //Read Data Values from body-Object from Post-Body-Request
        Player player = new Player(body.getFirst("playername").toString(), Integer.parseInt(body.getFirst("key").toString()));
        
        try {
            DatabaseController db = new DatabaseController();
            db.addPlayer(player);
        }
        catch(Exception e){
            System.out.println("Error! Player data not valid or parsing went wrong :( !");
            System.out.println(e);
        }

        // redirect to player-manage-page
        return new RedirectView("/player");
    }
}
