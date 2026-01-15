package org.example.shopping.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatRoomId(Long chatRoomId);

    void deleteAllByChatRoomId(Long id);
}
