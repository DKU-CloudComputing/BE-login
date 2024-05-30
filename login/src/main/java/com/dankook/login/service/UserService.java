package com.dankook.login.service;

import com.dankook.login.dto.AddUserRequest;
import com.dankook.login.entity.User;
import com.dankook.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(
                        User
                                .builder()
                                .email(dto.getEmail())
                                .password(encoder.encode(dto.getPassword()))
                                .nickname(dto.getNickname())
                                .build()
                )
                .getId();
    }
    public Long save(User dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(dto).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }
}
