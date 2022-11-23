package com.konai.kurong.faketee.account.service;

import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.dto.UserResponseDto;
import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.util.exception.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(UserJoinRequestDto requestDto) {

        String rawPassword = requestDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        requestDto.setEncPassword(encPassword);
        requestDto.setRole(Role.USER);
        return userRepository.save(requestDto.toEntity()).getId();
    }

    public UserResponseDto findByEmail(String email) {

        return new UserResponseDto(userRepository.findByEmail(email).orElseThrow(() -> new NoUserFoundException()));
    }

    @Transactional
    public Long updatePassword(Long id, UserUpdateRequestDto requestDto) {

        User user = userRepository.findById(id).orElseThrow(() -> new NoUserFoundException());
        String rawPassword = requestDto.getNewPassword(); // 수정할 비밀번호
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 수정할 비밀번호 암호화
        if(validatePassword(user, requestDto.getOldPassword())){ // 비밀번호 확인이 통과되면
            requestDto.setEncPassword(encPassword); // 비밀번호 수정절차 진행
            user.updatePassword(requestDto);
            return id;
        }else {
            return -1L;
        }
    }

    @Transactional
    public void delete(Long id) {

        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NoUserFoundException()));
    }

    public boolean validatePassword(User user, String password) {

        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}
