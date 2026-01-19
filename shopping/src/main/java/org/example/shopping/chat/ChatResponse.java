package org.example.shopping.chat;

import lombok.Data;

import java.util.List;

public class ChatResponse {

    @Data
    public static class ChatRoomDTO {
        private Long chatRoomId;
        private List<Chat> chatList;

        public ChatRoomDTO(Long chatRoomId, List<Chat> chatList) {
            this.chatRoomId = chatRoomId;
            this.chatList = chatList;
        }
    }

    @Data
    public static class ChatListDTO {
        private String sender;
        private String message;
        private boolean isMe;

        public ChatListDTO(String sender, String message,boolean isMe) {
            this.sender = sender;
            this.message = message;
            this.isMe = isMe;
        }
    }

    @Data
    public static class ChatRoomListDTO {
        private Long chatRoomId;
        private Long userId;

        public ChatRoomListDTO(org.example.shopping.chatRoom.ChatRoom chatRoom) {
            this.chatRoomId = chatRoom.getId();
            this.userId = chatRoom.getUserId();
        }
    }
}
