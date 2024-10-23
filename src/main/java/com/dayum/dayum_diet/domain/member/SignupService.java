package com.dayum.dayum_diet.domain.member;

import com.dayum.dayum_diet.domain.token.TokenProvider;
import com.dayum.dayum_diet.domain.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class SignupService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenService tokenService;

    // 회원가입 처리 메서드
    public Map<String, String> signup(Member newMember) {
        // 닉네임 중복 체크
        if (memberRepository.findByNickname(newMember.getNickname()).isPresent()) {
            throw new CustomException("닉네임이 중복되었습니다.", HttpStatus.CONFLICT);
        }

        // 회원 저장
        Member savedMember = memberRepository.save(newMember);

        // 토큰 생성
        String accessToken = tokenProvider.generateAccessToken(savedMember.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(savedMember.getEmail());

        // Redis에 토큰 저장
        tokenService.storeAccessToken("ACCESS_TOKEN_" + savedMember.getEmail(), accessToken);
        tokenService.storeRefreshToken("REFRESH_TOKEN_" + savedMember.getEmail(), refreshToken);

        // 반환할 데이터 준비
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }
}
