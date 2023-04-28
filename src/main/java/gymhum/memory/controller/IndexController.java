package gymhum.memory.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gymhum.memory.model.Player;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(@RequestParam(name="error", required = false, defaultValue = "false") boolean error, Model model) throws SQLException{
        DatabaseController db = new DatabaseController();
        db.createTable();
        ArrayList<Player> players = db.getAllPlayers();
        model.addAttribute("players", players);
        model.addAttribute("error", error);
        return "index.html";
    }
}
