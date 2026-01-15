package org.example.shopping.chat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "chat_tb")
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chatRoomId;

    @Enumerated(EnumType.STRING)
    private SenderRole sender;

    @Column(nullable = false)
    private String message;

    private LocalDateTime createdAt;

    @Builder
    public Chat(Long id, Long chatRoomId, SenderRole sender, String message, LocalDateTime createdAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }
}