package org.springproject.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springproject.springproject.model.Error;
import org.springproject.springproject.model.User;
import org.springproject.springproject.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@Slf4j
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showAddUser(ModelMap modelMap, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticationUser){
        boolean isUserLogged = Objects.nonNull(authenticationUser);
        modelMap.addAttribute("isUserLogged", isUserLogged);
        if (isUserLogged) {
            boolean isAuthorizedUserAdminOrManager = authenticationUser.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
            modelMap.addAttribute("isAuthorizedUserAdminOrManager", isAuthorizedUserAdminOrManager);
        }
        modelMap.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") User user, final Errors errors,ModelMap modelMap){
        modelMap.addAttribute("isUserLogged", false);
        modelMap.addAttribute("isAuthorizedUserAdminOrManager", false);
        if (errors.hasErrors()){
            return "register";
        }
        User createdUser = userService.createNewUser(user);

        if (Objects.isNull(createdUser)){
            modelMap.addAttribute("userExistsError","Can't create new user, because that username or email already exist.");
            return "register";
        }
//        else {
//            modelMap.addAttribute("userExistsError", null);
//        }

        return "redirect:/login";

    }

}
