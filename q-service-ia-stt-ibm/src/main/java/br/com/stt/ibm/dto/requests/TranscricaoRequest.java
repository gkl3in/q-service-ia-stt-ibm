package br.com.stt.ibm.dto.requests;

public record TranscricaoRequest(
    String base64Audio,
    String contentType, // ex "audio/wav"
    String model       // ex "pt-BR_BroadbandModel"
) {}