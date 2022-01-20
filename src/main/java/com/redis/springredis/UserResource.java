package com.redis.springredis;

import com.redis.springredis.entity.User;
import com.redis.springredis.repository.UserRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("redis/user")
public class UserResource {

    private UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add/{id}/{name}")
    @Cacheable(key = "#id", value = "User")
    public User add(@PathVariable ("id") final String id, @PathVariable ("name") final String name) {
    userRepository.save(new User(id, name, 20000L));
    return userRepository.findById(id);
    }

    @PutMapping("/update/{id}/{name}")
    @CachePut(key = "#id", value = "User")
    public User update(@PathVariable ("id") final String id, @PathVariable ("name") final String name) {
        userRepository.save(new User(id, name, 1000L));
        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public List<Object> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "User")
    public User findById(@PathVariable ("id") String id) {
        return userRepository.findById(id);
    }
}
