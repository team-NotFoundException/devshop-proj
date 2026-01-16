package org.example.shopping.chat;

import lombok.RequiredArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.chatRoom.ChatRoom;
import org.example.shopping.chatRoom.ChatRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private ChatRoom createChatRoom(Long userId) {

        Long ADMIN_ID = 1L;
        return ChatRoom.builder()
                .userId(userId)
                .adminId(ADMIN_ID)
                .build();
    }

    public ChatResponse.ChatRoomDTO findMyChatRoom(Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByUserId(userId)
                .orElseGet(() -> chatRoomRepository.save(createChatRoom(userId)));
        List<Chat> chatList = chatRepository.findAllByChatRoomId(chatRoom.getId());
        Long chatRoomId = chatRoom.getId();

        return new ChatResponse.ChatRoomDTO(chatRoomId, chatList);
    }

    @Transactional
    public void deleteChatRoom(Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        chatRepository.deleteAllByChatRoomId(chatRoom.getId());

        chatRoomRepository.delete(chatRoom);
    }

    public void saveAndBroadcast(String message, Long chatRoomId, SenderRole sender) {
        Chat chat = Chat.builder()
                .message(message)
                .chatRoomId(chatRoomId)
                .sender(sender)
                .build();

        chatRepository.save(chat);
        String formattedMessage = sender + ":" + message;

        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatRoomId, formattedMessage);
    }


    public List<ChatResponse.ChatRoomListDTO> findAll() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();

        return chatRoomList.stream()
                .map(ChatResponse.ChatRoomListDTO::new)
                .toList();
    }

    public ChatResponse.ChatRoomDTO enterChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new Exception404("채팅방을 찾을 수 없습니다."));

        List<Chat> chatList = chatRepository.findAllByChatRoomId(chatRoomId);

        return new ChatResponse.ChatRoomDTO(chatRoomId, chatList);
    }
}
