package com.dayum.dayum_diet.domain.member;


import com.dayum.dayum_diet.domain.token.TokenProvider;
import com.dayum.dayum_diet.domain.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private NicknameService nicknameService;

    @Autowired
    private TokenProvider tokenProvider; // TokenProvider 주입
    @Autowired
    private MemberRepository memberRepository; // MemberRepository 주입

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // 가입된 사용자 확인
        Member member = memberService.findOrCreateMember(email);

        if (member != null) {
            // 가입된 사용자라면 AccessToken과 RefreshToken 생성
            String accessToken = tokenProvider.generateAccessToken(member.getEmail());
            String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());

            // Redis에 저장
            tokenService.storeAccessToken("ACCESS_TOKEN_" + member.getEmail(), accessToken);
            tokenService.storeRefreshToken("REFRESH_TOKEN_" + member.getEmail(), refreshToken);

            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } else {
            // 가입되지 않은 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원가입 필요");
        }
    }

    // 로그아웃 메서드
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // Authorization 헤더에서 "Bearer " 부분 제거
        String accessToken = token.replace("Bearer ", "");

        // Redis에서 Access Token 삭제
        tokenService.deleteToken("ACCESS_TOKEN_" + accessToken);

        // 로그아웃 성공 응답
        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> request) {
        // 프론트엔드에서 전달된 값 받기
        String nickname = (String) request.get("nickname");
        String introduction = (String) request.get("introduction");
        String profileImageUrl = (String) request.get("profileImageUrl"); // 프로필 사진 URL

        // 새로운 멤버 객체 생성
        Member newMember = Member.builder()
                .nickname(nickname) // 닉네임은 이미 중복체크된 값
                .introduction(introduction)
                .profileImageUrl(profileImageUrl) // 프로필 사진
                .build();

        // 회원가입 서비스 호출
        Map<String, String> tokens = signupService.signup(newMember);

        // AccessToken과 RefreshToken 반환
        return ResponseEntity.ok(tokens);
    }

    // 회원 탈퇴 메서드
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader("Authorization") String token) {
        // Authorization 헤더에서 "Bearer " 부분 제거
        String accessToken = token.replace("Bearer ", "");

        // 사용자 이메일 추출
        String email = tokenProvider.extractEmailFromToken(accessToken);

        // 회원 삭제
        memberService.deleteMember(email);

        // Redis에서 Access Token 및 Refresh Token 삭제
        tokenService.deleteToken("ACCESS_TOKEN_" + accessToken);
        tokenService.deleteToken("REFRESH_TOKEN_" + email);

        // 탈퇴 성공 응답
        return ResponseEntity.ok().body("회원 탈퇴 성공");
    }


    @PutMapping("/nickname/set")
    public ResponseEntity<?> updateNickname(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newNickname = request.get("newNickname");

        nicknameService.updateNickname(email, newNickname);
        return ResponseEntity.ok(Map.of("message", "닉네임이 성공적으로 업데이트되었습니다."));
    }
    // 회원가입 시 랜덤 닉네임 생성
    @PostMapping("/nickname/random")
    public ResponseEntity<?> generateNickname() {
        String randomNickname = nicknameService.generateRandomNickname();
        return ResponseEntity.ok(Map.of("nickname", randomNickname));
    }

    // 닉네임 중복 체크 API
    @PostMapping("/nickname/check")
    public ResponseEntity<?> checkNickname(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        boolean isDuplicate = nicknameService.checkNicknameDuplicate(nickname);

        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "닉네임이 이미 사용 중입니다."));
        } else {
            return ResponseEntity.ok(Map.of("message", "사용 가능한 닉네임입니다."));
        }
    }

    // Refresh Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Refresh Token 유효성 확인
        if (tokenProvider.validateToken(refreshToken)) {
            String email = tokenProvider.extractEmailFromToken(refreshToken);
            Member member = memberService.findOrCreateMember(email);

            // 새 Access Token 발급
            String newAccessToken = tokenProvider.generateAccessToken(email);
            tokenService.storeAccessToken("ACCESS_TOKEN_" + email, newAccessToken);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 유효하지 않습니다.");
        }
    }

    // 한 줄 소개 업데이트
    @PutMapping("/introduction")
    public ResponseEntity<String> updateIntroduction(@RequestBody Map<String, String> request,
                                                     @RequestHeader("Authorization") String token) {
        String introduction = request.get("introduction");

        // Access Token에서 이메일 추출
        String accessToken = token.replace("Bearer ", "");
        String email = tokenProvider.extractEmailFromToken(accessToken);

        // 한 줄 소개 길이 체크
        if (introduction.length() > 100) {
            return ResponseEntity.badRequest().body("소개는 100자 이하로 작성해야 합니다.");
        }

        // 한 줄 소개 업데이트
        memberService.updateIntroduction(email, introduction);

        return ResponseEntity.ok("한 줄 소개가 성공적으로 업데이트되었습니다.");
    }
    // 프로필 사진 업데이트
    @PutMapping("/profile-image")
    public ResponseEntity<?> updateProfileImage(@RequestBody Map<String, Object> request,
                                                @RequestHeader("Authorization") String token) {
        String profileImageUrl = (String) request.get("profileImageUrl");
        String providedProfile = (String) request.get("providedProfile"); // "background,character" 형식
        boolean useProvided = (boolean) request.getOrDefault("useProvided", false);

        // Authorization 헤더에서 이메일 추출
        String accessToken = token.replace("Bearer ", "");
        String email = tokenProvider.extractEmailFromToken(accessToken);

        // 프로필 이미지 업데이트 서비스 호출
        memberService.updateProfileImage(email, profileImageUrl, providedProfile, useProvided);

        return ResponseEntity.ok().body("프로필 사진이 성공적으로 업데이트되었습니다.");
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
