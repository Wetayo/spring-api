package wetayo.wetayoapi.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public String postlogin(){
        return "login";
    }

    @GetMapping
    public String getlogin(){
        return "login";
    }
}
