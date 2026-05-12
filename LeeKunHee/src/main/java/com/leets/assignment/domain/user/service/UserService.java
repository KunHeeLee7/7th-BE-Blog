package com.leets.assignment.domain.user.service;

import com.leets.assignment.domain.user.entity.User;
import com.leets.assignment.domain.user.exception.UserException;
import com.leets.assignment.domain.user.exception.code.UserErrorCode;
import com.leets.assignment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    public void validateEmailNotDuplicated(String email) {
        if (existsByEmail(email)) {
            throw new UserException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    public void validateNicknameNotDuplicated(String nickname) {
        if (existsByNickname(nickname)) {
            throw new UserException(UserErrorCode.NICKNAME_ALREADY_EXISTS);
        }
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}
