package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.CurrentUser;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
//@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private User currentUser;
    private  UserRepository userRepository;



    public UserController(UserService userService) {

        this.userService = userService;
    }

//    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
//    public String login() {
//        return "login";
//    }

    @GetMapping("/create-user")
    @ResponseBody
    public String createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        return "admin";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }

    @ModelAttribute("user")
    public User getUser(){
        return currentUser;

    }



        @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Long id, Model model, HttpSession session) {
        User user = id == null ? new User() : userRepository.findById(id).get();
        model.addAttribute("user", user);

        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String saveForm(@Valid User user, BindingResult result, Long id, HttpSession session, CurrentUser currentUser) {
        if (result.hasErrors()) {
            return "login";
        }
//        session.setAttribute("cu", currentUser);
//        session.setAttribute("user", currentUser.getUser());
        userRepository.save(user);
        return "redirect:/";
    }

//
//
//    @RequestMapping("/all")
//    @ResponseBody
//    public List<User> showAllUsers(){
//        return userRepository.findAll();
//    }



//    @ModelAttribute("users")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }



}
