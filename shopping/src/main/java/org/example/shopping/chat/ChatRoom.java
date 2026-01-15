package org.example.shopping.chat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shopping._core.utils.BaseTimeEntity;

@NoArgsConstructor
@Getter
@Table(name = "chat_room_tb")
@Entity
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long adminId;

    @Builder
    public ChatRoom(Long id, Long userId, Long adminId) {
        this.id = id;
        this.userId = userId;
        this.adminId = adminId;
    }

 }
