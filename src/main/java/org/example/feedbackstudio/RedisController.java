package org.example.feedbackstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@CrossOrigin(origins = "*")
public class RedisController {

    @Autowired
    public RedisService redisService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value, 10); // 10 dakika süreyle saklar
        return "Veri kaydedildi.";
    }

    @GetMapping("/find")
    public Object find(@RequestParam String key) {
        return redisService.find(key);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        redisService.delete(key);
        return "Veri silindi.";
    }
}
