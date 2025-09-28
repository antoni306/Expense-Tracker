package com.example.demo.Controller;

import com.example.demo.Services.MyUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginRegisterController {

    @Autowired
    private MyUserDetailsService userService;
    @GetMapping("/index")
    public String loadMainPage(HttpSession session){
        var username = (String)session.getAttribute("username");
        var password = (String) session.getAttribute("password");
        var user = userService.getUser(username);
        if(user != null && user.getPassword().equals(password)){
            return "index";
        }else{
            System.out.println("wyjebało od razu");
            System.out.println(session.getAttribute("username"));
            return "redirect:/login";
        }
    }
    @GetMapping()
    public String initialLoad(){
        return "login";
    }

    // 1) GET /login — pokazuje formularz (statyczny albo z templates/login.html)
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // szuka login.html w src/main/resources/templates
    }

    @PostMapping("/check-credentials")
    public String logIn(@RequestParam String username, @RequestParam String password, HttpSession session,RedirectAttributes ra){
        var user=userService.getUser(username);
        if(user!=null && password.equals(user.getPassword())){
            System.out.println("udalo sie");
            session.setAttribute("username",username);
            session.setAttribute("password",password);
           return "redirect:/index";
        }else{
            ra.addAttribute("error","wrong credentials");
            return "redirect:/login";
        }
    }

    @GetMapping("/create-account")
    public String registerPage() {
        return "register";
    }
    @PostMapping("/register")
    public String createAccount(@RequestParam String email,@RequestParam String username,@RequestParam String password){
        if(userService.usernameTaken(username)){
            return "register";
        }
        if (userService.emailTaken(email)) {
            return "register";
        }

        userService.register(username,email,password);
        return "redirect:/login";
    }
}

