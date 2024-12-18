package history.traveler.springbootdeveloper.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/login")
    public String login(){
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}
