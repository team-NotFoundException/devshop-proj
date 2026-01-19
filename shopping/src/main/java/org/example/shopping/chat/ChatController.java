package org.example.shopping.chat;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.example.shopping.users.enums.RoleType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
        ChatResponse.ChatRoomDTO myChatRoom = chatService.findMyChatRoom(sessionUser.getId());

        String myRole = sessionUser.getRole().getRole().toString();

        List<ChatResponse.ChatListDTO> chatList = myChatRoom.getChatList().stream()
                        .map(chat -> new ChatResponse.ChatListDTO(
                                chat.getSender().name(),
                                chat.getMessage(),
                                chat.getSender().toString().equals(myRole)
                        ))
                        .toList();

        model.addAttribute("chatList", chatList);
        model.addAttribute("chatRoomId", myChatRoom.getChatRoomId());
        model.addAttribute("myRole", myRole);
        return "chat/index";
    }

    @PostMapping("/chatRoom/delete")
    public String deleteChatRoom(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        chatService.deleteChatRoom(sessionUser.getId());
        return "redirect:/";
    }

    // 메세지 수신
    @MessageMapping("/chat/message")
    public void receiveMessage(Map<String, Object> payload, SimpMessageHeaderAccessor accessor) {

        User sessionUser = (User) accessor.getSessionAttributes().get("sessionUser");

        String message = (String) payload.get("message");
        Long chatRoomId = ((Number) payload.get("chatRoomId")).longValue();

        SenderRole sender = sessionUser.getRole().getRole().equals(RoleType.ADMIN) ? SenderRole.ADMIN : SenderRole.OWNER;
        chatService.saveAndBroadcast(message, chatRoomId, sender);
    }

}
