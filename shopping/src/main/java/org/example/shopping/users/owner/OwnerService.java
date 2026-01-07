package org.example.shopping.users.owner;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.utils.ValidationGroups;
import org.example.shopping.users.User;
import org.example.shopping.users.dto.UserRequest;
import org.example.shopping.users.enums.OwnerStatus;
import org.example.shopping.users.enums.RoleType;
import org.example.shopping.users.owner.dto.OwnerRequest;
import org.example.shopping.users.user.UserRepository;
import org.example.shopping.users.user.UserRole;
import org.example.shopping.users.user.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final OwnerRepository ownerRepository;

    public User login(UserRequest.@Valid LoginDTO loginDTO) {
        User userEntity = userRepository
                .findByUsername(loginDTO.getUsername())
                .orElse(null);

        if (userEntity == null) {
            throw new Exception400("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 검증
//        if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
//            throw new Exception400("아이디 또는 비밀번호가 일치하지 않습니다.");
//        }

        return userEntity;
    }

    @Transactional
    public User ownerSignUp(@Valid OwnerRequest.OwnerSignUpDTO ownerSignUpDTO) {
        if (userRepository.findByUsername(ownerSignUpDTO.getUsername()).isPresent()) {
            throw new Exception400("이미 있는 이름입니다.");
        }
        User user = ownerSignUpDTO.toEntity();
        user.setPassword(passwordEncoder.encode(ownerSignUpDTO.getPassword()));
        userRepository.save(user);

        UserRole userRole = new UserRole(user, RoleType.OWNER);
        userRoleRepository.save(userRole);

        Owner newOwner = Owner.builder()
                .user(user)
                .name(ownerSignUpDTO.getOwnerName())
                .status(OwnerStatus.WAIT)
                .build();

        ownerRepository.save(newOwner);

        return user;
    }
}
