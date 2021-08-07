package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    // 일반 로그인 테스트(UserDetails 타입,PrincipalDetails도 가능(implement 했으므로))
//    @GetMapping("/test/login")
//    public @ResponseBody String testLogin(Authentication authentication,
//                                          @AuthenticationPrincipal PrincipalDetails userDetails){// DI(의존성 주입)
//        System.out.println("/test/login===================");
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("authentication: " + principalDetails.getUser());
//
//        System.out.println("userDetails: " + userDetails.getUser());
//        return "세션 정보 확인하기";
//    }
//
//    // 구글 로그인 테스트(OAuth2User 타입)
//    @GetMapping("/test/oauth/login")
//    public @ResponseBody String testOAuthLogin(Authentication authentication,
//                                               @AuthenticationPrincipal OAuth2User oauth){// DI(의존성 주입)
//        System.out.println("/test/oauth/login===================");
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("authentication: " + oauth2User.getAttributes());
//
//        System.out.println("oaut2User: " + oauth.getAttributes());
//        return "OAuth세션 정보 확인하기";
//    }

    @GetMapping({"/",""})
    public String index(){
        // 템플릿 엔진 : 머스테치 => 기본 폴더 src/main/resources/
        // 뷰 리졸버 설정 : templates(prefix), .mustache(suffix) => 생략 가능
        return "index";
    }
    
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails: " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // 스프링 시큐리티가 해당 주소를 채간다 - SecurityConfig 파일 생성 후 작동 안함.
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        userRepository.save(user);// 패스워드가 암호화 되어야지 시큐리티 로그인이 가능함.
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인 정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }

}
