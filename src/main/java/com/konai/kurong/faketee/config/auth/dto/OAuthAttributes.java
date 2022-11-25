package com.konai.kurong.faketee.config.auth.dto;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.account.util.Type;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
 *
 */
@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private Type type;

    /**
     * OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야함
     * @param registrationID
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    public static OAuthAttributes of(String registrationID, String userNameAttributeName, Map<String, Object> attributes){

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .type(Type.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * User 엔티티 생성
     * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
     * 가입할 때의 기본 권한을 USER로 주기 위해서 role 빌더값에 role.user
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성
     * @return
     */
    public User toEntity(){

        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .type(type)
                .build();
    }
}
