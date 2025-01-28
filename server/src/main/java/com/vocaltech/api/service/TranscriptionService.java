package com.vocaltech.api.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class TranscriptionService {


    private final OpenAiAudioTranscriptionModel transcriptionModel;

    @Autowired
    public TranscriptionService(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    public String transcribeAudio(Resource audioFile) {
        try {

            if (audioFile == null || !audioFile.exists()) {
                throw new IllegalArgumentException("Invalid audio file provided");
            }
            // Crear solicitud de transcripción usando configuraciones por defecto
            AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile);

            // Llamar al modelo de transcripción
            AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);

            // Devolver el texto transcrito
            return response.getResults().get(0).getOutput();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
