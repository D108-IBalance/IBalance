package com.ssafy.ibalance.notification.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;

@Configuration
@PropertySource("classpath:firebase.properties")
@Slf4j
public class FirebaseCredentialInfo {

    @Value("${firebase.type}")
    private String type;

    @Value("${firebase.project.id}")
    private String projectId;

    @Value("${firebase.private.key.id}")
    private String privateKeyId;

    @Value("${firebase.private.key}")
    private String privateKey;

    @Value("${firebase.client.email}")
    private String clientEmail;

    @Value("${firebase.client.id}")
    private String clientId;

    @Value("${firebase.auth.uri}")
    private String authUri;

    @Value("${firebase.token.uri}")
    private String tokenUri;

    @Value("${firebase.auth.provider.x509_cert_url}")
    private String providerCertUrl;

    @Value("${firebase.client.x509_cert_url}")
    private String clientCertUrl;

    @Value("${firebase.universe.domain}")
    private String universeDomain;

    @Qualifier("firebaseInfoStream")
    @Bean
    public InputStream firebaseInfoStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = getJsonGenerator(outputStream);

        writeOnJson(jsonGenerator);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private JsonGenerator getJsonGenerator(OutputStream out) {
        try {
            new JsonFactory().createGenerator(out, JsonEncoding.UTF8);
        } catch (IOException e) {
            log.warn("Json Generator 만드는 것 실패");
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }

    private void writeOnJson(JsonGenerator json) {
        try {
            json.writeStartObject();
            json.writeStringField("type", type);
            json.writeStringField("project_id", projectId);
            json.writeStringField("private_key_id", privateKeyId);
            json.writeStringField("private_key", privateKey);
            json.writeStringField("client_email", clientEmail);
            json.writeStringField("client_id", clientId);
            json.writeStringField("auth_uri", authUri);
            json.writeStringField("token_uri", tokenUri);
            json.writeStringField("auth_provider_x509_cert_url", providerCertUrl);
            json.writeStringField("client_x509_cert_url", clientCertUrl);
            json.writeStringField("universe_domain", universeDomain);
            json.writeEndObject();
            json.close();
        } catch (IOException e) {
            log.warn("Json 기입 도중 IOException 발생");
            throw new RuntimeException(e.getMessage());
        }
    }
}
