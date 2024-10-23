package com.dayum.dayum_diet.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 이메일로 회원을 찾음
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }


    // 이메일로 회원을 찾거나 새로 생성
    public Member findOrCreateMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> createMember(email));
    }

    // 새 회원 생성
    private Member createMember(String email) {
        return memberRepository.save(Member.builder()
                .email(email)
                .build());
    }

    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        memberRepository.delete(member); // 회원 삭제
    }
    // 한 줄 소개 업데이트
    public void updateIntroduction(String email, String introduction) {
        // Optional 사용
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // 회원이 존재하는지 확인
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 빌더를 사용하여 새로운 Member 객체 생성
            Member updatedMember = member.toBuilder()
                    .introduction(introduction)
                    .build();

            memberRepository.save(updatedMember); // 변경 사항 저장
        } else {
            throw new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
    public void updateProfileImage(String email, String profileImageUrl, String providedProfile, boolean useProvided) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Member.MemberBuilder memberBuilder = member.toBuilder(); // 빌더로 변환

        // 1. 설정 안 함
        if (profileImageUrl == null && !useProvided) {
            memberBuilder.profileImageUrl(null);
            memberBuilder.background(null);
            memberBuilder.character(null);
        }
        // 2. 어플에서 제공하는 배경과 캐릭터 사용
        else if (useProvided) {
            String[] providedParts = providedProfile.split(",");
            memberBuilder.profileImageUrl(providedParts[0]); // 배경
            memberBuilder.character(providedParts[1]); // 캐릭터
        }
        // 3. 사용자 앨범이나 카메라에서 선택
        else {
            memberBuilder.profileImageUrl(profileImageUrl);
        }

        Member updatedMember = memberBuilder.build(); // 최종 Member 객체 생성
        memberRepository.save(updatedMember); // DB에 저장
    }

}
