package com.dayum.dayum_diet.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NicknameService {

    @Autowired
    private MemberRepository memberRepository;
    private static final String[] ADJECTIVES = {"순한", "묘한", "예쁜", "작은", "멋진", "강한", "화난", "분한", "고운"};
    private static final String[] NOUNS = {"고양이", "강아지", "고구마", "오렌지", "바나나", "호랑이", "아저씨", "아가씨"};

    private Random random = new Random();

    // 랜덤 닉네임 생성
    public String generateRandomNickname() {
        // 형용사 선택
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];

        // 명사 선택
        String noun = NOUNS[random.nextInt(NOUNS.length)];

        // 0부터 999 사이의 랜덤 숫자 생성
        int randomNumber = random.nextInt(1000); // 0~999

        // 조합하여 닉네임 생성
        return String.format("%s%s%d", adjective, noun, randomNumber);
    }

    // 닉네임 중복 체크
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    // 닉네임 수정
    public void updateNickname(String email, String newNickname) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (checkNicknameDuplicate(newNickname)) {
            throw new CustomException("닉네임이 이미 사용 중입니다.", HttpStatus.CONFLICT);
        }

        Member updatedMember = member.toBuilder() // 빌더로 변환
                .nickname(newNickname) // 새 닉네임 설정
                .build(); // 새 Member 객체 생성
        memberRepository.save(updatedMember);
    }
}
