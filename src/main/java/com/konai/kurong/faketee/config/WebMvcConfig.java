package com.konai.kurong.faketee.config;

import com.konai.kurong.faketee.auth.LoginUserArgumentResolver;
import com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor;
import com.konai.kurong.faketee.employee.utils.EmployeeHandlerMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final EmployeeHandlerMethodArgumentResolver employeeHandlerMethodArgumentResolver;
    private final EmpAuthInterceptor empAuthInterceptor;

    /**
     * 정적 리소스 파일 읽어드리기 위해 핸들러 설정
     * 리소스파일 불러올때 경로, 해당 경로로 불렀을때 리소스를 찾을 위치 설정
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    /**
     * (@LoginUser)어노테이션 작동을 위해 커스텀한 LoginUserArgumentResolver 등록
     * @param resolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(loginUserArgumentResolver);
        resolvers.add(employeeHandlerMethodArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(empAuthInterceptor);
    }

    @Bean
    public RequestContextListener requestContextListener(){

        return new RequestContextListener();
    }
}
