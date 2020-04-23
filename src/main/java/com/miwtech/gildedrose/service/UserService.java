package com.miwtech.gildedrose.service;

import com.miwtech.gildedrose.entity.UserEntity;
import com.miwtech.gildedrose.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * The UserService performs a lookup of authorized users by name for the purpose of
 * authentication a user during the purchase process.
 */
@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve a user by name.
     * @param name
     * @return
     */
    public UserEntity findByName(String name) {
        return userRepository.findByName(name);
    }
}
