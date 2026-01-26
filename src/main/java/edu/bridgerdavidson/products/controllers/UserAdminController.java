package edu.bridgerdavidson.products.controllers;

import edu.bridgerdavidson.products.data.UsersRepository;
import edu.bridgerdavidson.products.models.Role;
import edu.bridgerdavidson.products.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    public UsersRepository usersRepository;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", usersRepository.findAll());
        return "userAdmin";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        UserEntity user = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/users/edit")
    public String editUser(@RequestParam int id, @RequestParam String username, @RequestParam String email) {
        UserEntity user = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setUsername(username);
        user.setEmail(email);
        usersRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        UserEntity user = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("user", user);
        return "confirmDeleteUser";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam int id)
    {
        UserEntity user = usersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        usersRepository.delete(user);
        return "redirect:/admin/users";
    }
}
