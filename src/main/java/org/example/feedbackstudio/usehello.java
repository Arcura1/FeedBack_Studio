package org.example.feedbackstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class usehello {


 // Her bir endpoint için ayrı ayrı ekleyebilirsiniz
    @PostMapping
    public String createUsera(@RequestBody String user) {
        return "oray";
    }


    @PostMapping("/user")
    public List<String> createUser(@RequestBody String user) {
        return new ArrayList<>();
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }


    @PostMapping("/get-id")
    public Long getIdFromJson(@RequestBody testDto myData) {
        return myData.getId();
    }
}

