package org.example.shopping.chat;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 내 채팅방 조회
    @GetMapping("/chatRoom/me")
    public String findMyChatRoom(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ChatResponse.ChatRoom myChatRoom = chatService.findMyChatRoom(sessionUser.getId());

        List<ChatResponse.ChatList> chatList = myChatRoom.getChatList().stream()
                        .map(chat -> new ChatResponse.ChatList(
                                chat.getSender().name(),
                                chat.getMessage(),
                                chat.getSender() == SenderRole.USER
                        ))
                        .toList();

        model.addAttribute("chatList", chatList);
        model.addAttribute("chatRoomId", myChatRoom.getChatRoomId());
        return "user/mypage-myChat";
    }

    @PostMapping("/chatRoom/delete")
    public String deleteChatRoom(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        chatService.deleteChatRoom(sessionUser.getId());
        return "redirect:/user/update";
    }

    // 메세지 수신
    @MessageMapping("/chat/message")
    public void receiveMessage(Map<String, Object> payload) {
        String message = (String) payload.get("message");
        Long chatRoomId = ((Number) payload.get("chatRoomId")).longValue();
        String senderStr = (String) payload.get("sender");
        SenderRole sender = SenderRole.valueOf(senderStr);

        chatService.saveAndBroadcast(message, chatRoomId, sender);
    }

}
