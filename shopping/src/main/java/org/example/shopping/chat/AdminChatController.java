package org.example.shopping.chat;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminChatController {
    private final ChatService chatService;

    // 어드민 채팅창 리스트
    @GetMapping("/admin/chat/list")
    public String chatList(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        List<ChatResponse.ChatRoomListDTO> chatRoomList = chatService.findAll();

        model.addAttribute("chatRoomList", chatRoomList);

        return "/chat/list";
    }

    @GetMapping("/admin/chat/{chatRoomId}")
    public String getChatRoom(@PathVariable Long chatRoomId, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        String myRole;

        if (sessionUser.getRole().getRole().toString().equals("OWNER")) {
            myRole = "USER";
        } else {
            myRole = sessionUser.getRole().getRole().toString();
        }

        ChatResponse.ChatRoomDTO chatRoom = chatService.enterChatRoom(chatRoomId);

        List<ChatResponse.ChatListDTO> chatList = chatRoom.getChatList().stream()
                .map(chat -> new ChatResponse.ChatListDTO(
                        chat.getSender().name(),
                        chat.getMessage(),
                        chat.getSender().toString().equals(myRole)
                ))
                .toList();

        model.addAttribute("chatList", chatList);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("myRole", myRole);

        return "/chat/index";
    }
}
