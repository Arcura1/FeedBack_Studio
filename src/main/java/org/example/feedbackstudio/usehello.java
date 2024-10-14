package org.example.feedbackstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class usehello {






    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }


    @PostMapping("/get-id")
    public Long getIdFromJson(@RequestBody testDto myData) {
        return myData.getId();
    }
}

