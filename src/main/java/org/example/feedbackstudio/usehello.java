package org.example.feedbackstudio;

import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class usehello {

    @Autowired
    MessageSender MessageSender;

    @PostMapping("/oray")
    public String Hello(@RequestBody PdfUploadQueryModel oray) {
        System.out.println("Received query: " + oray);
        return "done";
    }



    @GetMapping("/hello")
    public String sayHello() {

        Jedis jedis = new Jedis();
        jedis.set("events/city/rome", "32,15,223,828");
        String cachedResponse = jedis.get("events/city/rome");
//        redisService.save(key, value, 10); // 10 dakika süreyle saklar
        return cachedResponse;
//
//        return "Hello, World!";
    }


    @PostMapping("/get-id")
    public Long getIdFromJson(@RequestBody testDto myData) {
        return myData.getId();
    }
}

