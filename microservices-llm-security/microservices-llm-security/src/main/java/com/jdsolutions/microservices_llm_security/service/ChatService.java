package com.jdsolutions.microservices_llm_security.service;

import com.jdsolutions.microservices_llm_security.UserSearch;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ChatService {
    @Autowired
    private MistralAiChatModel chatModel;

    public Map<String, String> getResponse(UserSearch userSearch) {

        return Map.of("generation", this.chatModel.call(userSearch.getQueryString()));
    }
}
