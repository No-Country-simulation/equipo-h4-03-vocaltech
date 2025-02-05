package com.vocaltech.api.service;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatAnalysisService {

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatAnalysisService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String analyzeTranscription(String transcription) {
        // Crear un mensaje del sistema
        SystemMessage systemMessage = new SystemMessage("Actúa como un analista experto en lenguaje natural y comunicación interpersonal e institucional.");
        // Crear un mensaje del usuario
        UserMessage userMessage = new UserMessage(
                "Analiza este texto y realiza un diagnóstico sobre las problemásticas que se plantean y " +
                        "que soluciones les puedes recomendar: " + transcription);

        // Construir el prompt de chat
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        // Realizar la llamada al modelo de chat
        ChatResponse response = chatModel.call(prompt);

        // Devolver el contenido de la respuesta
        return response.getResults().get(0).getOutput().toString();
    }
}
