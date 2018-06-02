package org.vap.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vap.webapp.model.User;
import org.vap.webapp.service.user.AuthException;
import org.vap.webapp.service.user.UserAuthenticationService;

import java.util.List;

/**
 * @author Vahe Pezeshkian
 */
@RestController
public class UserController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping("/user/manage")
    public Response<String> manage(@RequestParam String action) {

        switch (action.toLowerCase()) {
            case "clear":
                userAuthenticationService.clear();
                return new Response<>("DONE", true, "All users deleted");

            default:
                return new Response<>("UNKNOWN", false, "Unrecognized action");
        }
    }

    @GetMapping("/user/all")
    public Response<List<User>> all() {
        List<User> users = userAuthenticationService.getAll();
        return new Response<>(users, true, String.format("All users: %s", users.size()));
    }

    @GetMapping("/user/register")
    public Response<User> register(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userAuthenticationService.register(username, password);
            return new Response<>(user, true, "OK");
        } catch (Exception e) {
            return new Response<>(e);
        }
    }

    @GetMapping("/user/login")
    public Response<User> login(@RequestParam String username, @RequestParam String password) {
        User user;
        try {
            user = userAuthenticationService.login(username, password);
        } catch (AuthException e) {
            return new Response<>(e);
        }

        return new Response<>(user, true, "User logged in successfully");
    }
}
