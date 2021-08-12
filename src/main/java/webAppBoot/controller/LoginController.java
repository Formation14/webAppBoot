package webAppBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import webAppBoot.models.User;
import webAppBoot.service.RoleService;
import webAppBoot.service.UserService;

import java.security.Principal;

@Controller
public class LoginController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(value = "/login")
    public String loginPage() {
        return "/login";
    }

    @RequestMapping("/creat")
    public String creatDefaultUsers() {
        userService.creatDefaultUser();
        return "redirect:/login";
    }

    @GetMapping(value = "/list")
    public String index(Model model, Principal principal) {
        model.addAttribute("autUser", userService.getUserByName(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/list";
    }

    @GetMapping
    public String redirectToListPage() {
        return "redirect:/list";
    }

    @GetMapping("/user")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }

    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
//        List<User> list = userService.getAllUsers();
//        model.addAttribute("allUsers", list);
//        model.addAttribute("addUser", new User());
//        model.addAttribute("allRoles", roleService.getAllRoles());
        return "list";
    }
}
