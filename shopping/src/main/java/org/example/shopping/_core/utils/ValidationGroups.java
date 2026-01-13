package org.example.shopping._core.utils;

import jakarta.validation.GroupSequence;
import org.example.shopping.review.ReviewRequest;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.owner.dto.OwnerRequest;

public interface ValidationGroups {
    public interface NotEmptyGroup {};
    public interface PatternCheckGroup {};
    public interface EmailCheckGroup {};
    public interface EtcCheckGroup {};

    // --- 회원가입 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.SignUpDTO.class
    })
    public interface SignUpOrderGroup {}

    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            OwnerRequest.OwnerSignUpDTO.class
    })
    public interface OwnerSignUpOrderGroup {}

    // --- 로그인 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.LoginDTO.class
    })
    public interface LoginOrderGroup {}

    // --- 회원정보 수정 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.UpdateDTO.class
    })
    public interface UpdateOrderGroup {}

    @GroupSequence({
            PatternCheckGroup.class,
            NotEmptyGroup.class,
            ReviewRequest.SaveDTO.class
    })
    public interface ReviewOrderGroup {}
}
