package br.com.stt.ibm.resource;

import br.com.stt.ibm.service.WatsonSttService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@Path("/api/stt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SttResource {

    @Inject
    WatsonSttService sttService;

    @POST
    @Path("/transcrever")
    public TranscricaoResponse transcrever(TranscricaoRequest request) {
        // Decodifica o Base64 -> byte[]
        byte[] audioBytes = Base64.getDecoder().decode(request.base64Audio());

        // Converte em InputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);

        // Chama Watson
        String texto = sttService.transcreverAudio(bais, request.contentType(), request.model());

        // Retorna a resposta em JSON
        return new TranscricaoResponse(texto);
    }

    // DTO de entrada
    public static record TranscricaoRequest(
        String base64Audio,
        String contentType, // ex "audio/wav"
        String model       // ex "pt-BR_BroadbandModel"
    ) {}

    public static record TranscricaoResponse(String texto) {}
}