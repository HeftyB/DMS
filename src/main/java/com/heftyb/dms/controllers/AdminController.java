package com.heftyb.dms.controllers;

import com.heftyb.dms.users.User;
import com.heftyb.dms.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    public AdminController(final UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/")
//    public ModelAndView adminHome(HttpServletRequest request, ModelMap model, Principal principal) {
//        User u = userService.findUserByUsername(principal.getName());
//        model.addAttribute("user", u);
//        return new ModelAndView("administrator", model);
//    }

    @GetMapping("/")
    public String adminHome(HttpServletRequest request, Model model, Principal principal) {
        User u = userService.findUserByUsername(principal.getName());
        model.addAttribute("user1", u);

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "administrator";
    }
}
