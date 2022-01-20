package com.redis.springredis.repository;

import com.redis.springredis.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private RedisTemplate<String, User> redisTemplate;

    public static final String HASH_KEY = "User";

    public UserRepository(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(User user) {
        redisTemplate.opsForHash().put(HASH_KEY, user.getId(), user);
    }
    
    public List<Object> findAll() {
        System.out.println("hi");
        List<Object> obj = redisTemplate.opsForHash().values(HASH_KEY);
        System.out.println(obj + "hi");
        return obj;
    }

    public User findById(String id) {
        return (User) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public void update(User user) {
        save(user);
        redisTemplate.opsForHash().put(HASH_KEY, user.getId(), user);
    }

    public void delete(String id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
    }
}
