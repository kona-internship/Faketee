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

    /**
     * 회원가입
     * 입력받은 비밀번호를 암호화하여 저장하고 repo에 save
     * @param requestDto: 회원가입시 사용자로부터 입력받은 값을 가지는 dto
     * @return
     */
    @Transactional
    public Long join(UserJoinRequestDto requestDto) {

        String rawPassword = requestDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        requestDto.setEncPassword(encPassword);
        requestDto.setRole(Role.USER);

        User user = requestDto.toEntity();

        return userRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 이메일로 사용자 READ
     * @param email: READ할 사용자 이메일
     * @return 이메일에 해당하는 사용자 response dto
     */
    public UserResponseDto findByEmail(String email) {

        return new UserResponseDto(userRepository.findByEmail(email).orElseThrow(() -> new NoUserFoundException()));
    }

    /**
     * 비밀번호 변경
     * 유저로부터 입력받은 비밀번호(requestDto)를 암호화하고 기존 비밀번호와 일치하지 않으면 updatePassword 진행
     * 기존 암호와 동일하면 return -1L
     * @param id: user pk
     * @param requestDto: user update request dto
     * @return update user pk
     */
    @Transactional
    public Long updatePassword(Long id, UserUpdateRequestDto requestDto) {

        User user = userRepository.findById(id).orElseThrow(() -> new NoUserFoundException());
        String rawPassword = requestDto.getNewPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        if(validatePassword(user, requestDto.getOldPassword())){
            requestDto.setEncPassword(encPassword);
            user.updatePassword(requestDto);
            return id;
        }else {
            return -1L;
        }
    }

    /**
     * 유저 삭제
     * @param id: user pk
     */
    @Transactional
    public void delete(Long id) {

        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NoUserFoundException()));
    }

    /**
     * 비밀번호 검수
     * 입력받은 비밀번호가 기존 비밀번호와 일치하는지 확인
     * @param user: 검수할 사용자
     * @param password: 입력받은 비밀번호
     * @return
     */
    public boolean validatePassword(User user, String password) {

        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    /**
     * EMAIL 중복 검수
     * 회원가입할 이메일이 기존에 존재하는지 확인
     * @param email: 회원가입할 EMAIL
     * @return 가입 가능하면 true, 불가능하면 false
     */
    public boolean validateEmail(String email) {

        return userRepository.findByEmail(email).orElse(null) == null? true : false;
    }
}