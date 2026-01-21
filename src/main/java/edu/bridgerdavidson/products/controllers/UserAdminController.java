package edu.bridgerdavidson.products.controllers;

import edu.bridgerdavidson.products.data.UsersRepository;
import edu.bridgerdavidson.products.models.Role;
import edu.bridgerdavidson.products.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("user", usersRepository.findById(id));
        return "editUser";
    }

    @PostMapping("/users/edit")
    public String editUser(@RequestParam String username, @RequestParam String email) {
        UserEntity user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setUsername(username);
        user.setEmail(email);
        usersRepository.save(user);
        return "redirect:/admin/userAdmin";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        Optional<UserEntity> user = usersRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("username", user.get().getUsername());
            model.addAttribute("email", user.get().getEmail());
        }
        return "confirmDeleteUser";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam String username)
    {
        UserEntity user = usersRepository.findByUsername(username);
        if (user != null) {
            usersRepository.delete(user);
        }
        return "redirect:/admin/userAdmin";
    }
}
