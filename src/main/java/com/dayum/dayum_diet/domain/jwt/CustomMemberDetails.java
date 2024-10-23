package com.dayum.dayum_diet.domain.jwt;

import com.dayum.dayum_diet.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomMemberDetails implements UserDetails {

    private final Member member; // Member 엔티티를 참조

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 빈 권한 목록 반환 (역할 구분 없음)
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호가 없을 경우 null
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 이메일을 사용자 이름으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (항상 활성 상태)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (항상 잠금 해제)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 (항상 활성 상태)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 (항상 활성화)
    }

    // 추가: Member 객체에 대한 접근자 메서드
    public Member getMember() {
        return this.member;
    }
}
