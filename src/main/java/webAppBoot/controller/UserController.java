package webAppBoot.controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webAppBoot.models.User;
import webAppBoot.service.RoleService;
import webAppBoot.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("addUser",new User());
        return "admin/list";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.addUser(user);
        return "redirect:/admin";
    }
    
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("addUser") User user,
                         @PathVariable("id") Long id,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.updateUser(id,user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/creat")
    public String creatDefaultUsers() {
        userService.creatDefaultUser();
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.getUserByName(principal);
        model.addAttribute("user", user);
        return "user/showUser";
    }
}
