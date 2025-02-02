package com.jdsolutions.microservices_llm_security;

import com.jdsolutions.microservices_llm_security.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/llm")
public class AppController {

    @Autowired
    private MistralAiChatModel chatModel;

    @Autowired
    private ChatService chatService;


    @GetMapping("/")
    public String welcomeMessage(){
        return "Welcome !!!";
    }

    @PostMapping("/ai/generateMessage")
    public ResponseEntity<String> queryResponse(@RequestBody UserSearch userSearch, HttpServletRequest request) {

        Map<String, String> chatResponse = chatService.getResponse(userSearch);
        return ResponseEntity.ok().body(chatResponse.get("generation"));

    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }
}
