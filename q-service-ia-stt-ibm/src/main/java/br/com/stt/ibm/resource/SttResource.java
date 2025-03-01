package br.com.stt.ibm.resource;

import br.com.stt.ibm.dto.requests.TranscricaoRequest;
import br.com.stt.ibm.dto.responses.TranscricaoResponse;
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
        byte[] audioBytes = Base64.getDecoder().decode(request.base64Audio());

        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);

        String texto = sttService.transcreverAudio(bais, request.contentType(), request.model());

        return new TranscricaoResponse(texto);
    }
}