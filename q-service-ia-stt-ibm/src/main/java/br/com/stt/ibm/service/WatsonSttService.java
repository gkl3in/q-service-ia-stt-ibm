package br.com.stt.ibm.service;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;

@ApplicationScoped
@RegisterForReflection(targets = { 
    SpeechRecognitionResults.class 
})
public class WatsonSttService {

    private final SpeechToText speechToText;

    @Inject
    public WatsonSttService(
        @ConfigProperty(name = "watson.stt.api-key") String apiKey,
        @ConfigProperty(name = "watson.stt.url") String url
    ) {
        Authenticator authenticator = new IamAuthenticator(apiKey);
        this.speechToText = new SpeechToText(authenticator);
        this.speechToText.setServiceUrl(url);
    }

    public String transcreverAudio(InputStream audioStream, String contentType, String model) {
        try {
            RecognizeOptions.Builder builder = new RecognizeOptions.Builder()
                    .audio(audioStream)
                    .contentType(contentType)
                    .model(model != null ? model : "pt-BR_BroadbandModel")
                    .maxAlternatives(1)
                    .smartFormatting(true);

            RecognizeOptions options = builder.build();

            SpeechRecognitionResults results = speechToText.recognize(options).execute().getResult();

            if (results.getResults() != null && !results.getResults().isEmpty()) {
                return results
                        .getResults()
                        .get(0)
                        .getAlternatives()
                        .get(0)
                        .getTranscript();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}