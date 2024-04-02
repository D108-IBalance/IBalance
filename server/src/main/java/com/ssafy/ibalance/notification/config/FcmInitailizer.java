package com.ssafy.ibalance.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FcmInitailizer {

    private final InputStream firebaseInfoStream;

    public FcmInitailizer(@Qualifier("firebaseInfoStream") InputStream stream) {
        this.firebaseInfoStream = stream;
    }

    @PostConstruct
    public void initialize() {
        try (InputStream is = firebaseInfoStream) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            log.warn("FCM initialize IOException 발생 : {}", e.getMessage());
        }
    }

}
