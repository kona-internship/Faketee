package com.konai.kurong.faketee.auth;

import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인시 실행
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SessionUser user = new SessionUser(userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다.")));
        if(user != null){

            return new PrincipalDetails(user);
        }
        else throw new UsernameNotFoundException("존재하지 않는 Email 입니다.");
    }
}
