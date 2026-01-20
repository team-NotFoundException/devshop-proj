package org.example.shopping._core.utils;

import jakarta.validation.GroupSequence;
import org.example.shopping.review.ReviewRequest;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.owner.dto.OwnerRequest;

public interface ValidationGroups {
     interface NotEmptyGroup {};
     interface PatternCheckGroup {};
     interface EmailCheckGroup {};
     interface EtcCheckGroup {};

    // --- 회원가입 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.SignUpDTO.class
    })
     interface SignUpOrderGroup {}

    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            OwnerRequest.OwnerSignUpDTO.class
    })
     interface OwnerSignUpOrderGroup {}

    // --- 로그인 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.LoginDTO.class
    })
     interface LoginOrderGroup {}

    // --- 회원정보 수정 검증 순서 ---
    @GroupSequence({
            NotEmptyGroup.class,
            PatternCheckGroup.class,
            UserRequest.UpdateDTO.class
    })
     interface UpdateOrderGroup {}

    @GroupSequence({
            PatternCheckGroup.class,
            NotEmptyGroup.class,
            ReviewRequest.SaveDTO.class
    })
     interface ReviewOrderGroup {}
}

