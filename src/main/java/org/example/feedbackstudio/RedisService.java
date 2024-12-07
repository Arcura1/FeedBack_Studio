package org.example.feedbackstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    // Veri oluşturma veya güncelleme
    public void save(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    // Veri okuma
    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Veri silme
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // Tüm anahtarları listeleme (isteğe bağlı)
    public Set<String> findAllKeys() {
        return redisTemplate.keys("*");
    }
}
