package org.example.shopping.chat;

import lombok.Data;

import java.util.List;

public class ChatResponse {

    @Data
    public static class ChatRoom {
        private Long chatRoomId;
        private List<Chat> chatList;

        public ChatRoom(Long chatRoomId, List<Chat> chatList) {
            this.chatRoomId = chatRoomId;
            this.chatList = chatList;
        }
    }

    @Data
    public static class ChatList {
        private String sender;
        private String message;
        private boolean isMe;

        public ChatList(String sender, String message,boolean isMe) {
            this.sender = sender;
            this.message = message;
            this.isMe = isMe;
        }
    }
}
