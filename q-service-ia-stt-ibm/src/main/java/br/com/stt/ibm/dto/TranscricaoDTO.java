package br.com.stt.ibm.dto;

public class TranscricaoDTO {
    private String base64Audio;
    private String contentType; // ex "audio/wav"
    private String model;       // ex "pt-BR_BroadbandModel"

    public String getBase64Audio() {
        return base64Audio;
    }
    public void setBase64Audio(String base64Audio) {
        this.base64Audio = base64Audio;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
