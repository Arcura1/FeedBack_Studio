package org.example.feedbackstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class usehello {

    @Autowired
    private userRepository userRepository;
    @PostMapping
    public User createUsera(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PostMapping("/user")
    public List<User> createUser(@RequestBody User user) {
        return userRepository.findAll();
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!tunaacar";
    }

}
