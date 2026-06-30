package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        user.setRole("JOB_SEEKER");
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
public String showLoginForm() {
    return "login";
}

@PostMapping("/login")
public String loginUser(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

    Optional<User> userOpt = userService.findByEmail(email);

    if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
        session.setAttribute("loggedInUser", userOpt.get());
        return "redirect:/dashboard";
    } else {
        model.addAttribute("error", "Invalid email or password!");
        return "login";
    }
}


@GetMapping("/dashboard")
public String showDashboard(HttpSession session, Model model) {

    User loggedInUser = (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {
        return "redirect:/login";
    }

    model.addAttribute("user", loggedInUser);
    return "dashboard";
}


}