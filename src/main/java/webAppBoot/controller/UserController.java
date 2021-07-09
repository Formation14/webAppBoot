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
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/list";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/list";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/list";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user")User user,
                         @RequestParam("chooseRole") String[] chooseRole) {

        userService.chooseRole(user,chooseRole);
        userService.updateUser(chooseRole,user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/creat")
    public String creatDefaultUsers(ModelMap model) {
        userService.creatDefaultUser();
        return "redirect:/admin";
    }

    @GetMapping("/user/show")
    public String showUserByIdForUser(Principal principal, ModelMap model) {
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "user/showUser";
    }
}
