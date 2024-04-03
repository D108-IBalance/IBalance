package com.ssafy.ibalance.notification.service;

import com.google.firebase.messaging.*;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.notification.dto.NotifyTargetDto;
import com.ssafy.ibalance.notification.dto.response.FcmResponse;
import com.ssafy.ibalance.notification.entity.FcmToken;
import com.ssafy.ibalance.notification.repository.FcmTokenRedisRepository;
import com.ssafy.ibalance.notification.util.FirebaseConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    @Value("${default.img.logo}")
    private String logo;
    private final FcmTokenRedisRepository fcmTokenRedisRepository;
    private final GrowthRepository growthRepository;
    private final DietRepository dietRepository;
    private final String topic = "iBalance";

    public FcmResponse saveFcmToken(Member member, String token) {
        fcmTokenRedisRepository.save(FcmToken.builder()
                .memberId(member.getId())
                .fcmToken(token)
                .build());

        sendByFirebase(firebase -> firebase.subscribeToTopic(List.of(token), topic), "Topic 구독");

        return FcmResponse.builder()
                .memberId(member.getId())
                .token(token)
                .build();
    }

    @Scheduled(cron = "0 5 17 * * *")
    public void sendCheckDiet() {
        Message message = Message.builder()
                .setNotification(firebaseNotification("오늘의 식단을 확인해 보세요!😋"))
                .setTopic(topic)
                .build();

        sendByFirebase(firebase -> firebase.send(message), "식단 확인");
    }

    @Scheduled(cron = "30 5 17 * * *")
    public void sendReview() {
        List<Integer> memberIdList = dietRepository.getNotifyTargetList();

        sendFirebaseMultiMessage(memberIdList,
                "오늘 식단은 어떠셨나요?\n리뷰를 남겨주세요!🧡", "리뷰 작성");
    }

    @Scheduled(cron = "0 6 17 * * *")
    public void sendUpdate() {
        List<Integer> memberIdList = growthRepository.getNotifyTargetList().stream()
                .map(NotifyTargetDto::getId)
                .toList();

        sendFirebaseMultiMessage(memberIdList,
                "정보를 업데이트 한 지 일주일이 지났어요!\n" +
                        "그 동안 얼마나 자랐는지 확인 해볼까요?👉👉", "정보 업데이트");
    }

    private void sendFirebaseMultiMessage(List<Integer> memberIdList, String messageBody, String alarmName) {
        if(!memberIdList.isEmpty()) {
            List<FcmToken> fcmTokens = (List<FcmToken>) fcmTokenRedisRepository.findAllById(memberIdList);

            List<String> tokens = fcmTokens.stream()
                    .map(FcmToken::getFcmToken)
                    .toList();

            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(firebaseNotification(messageBody))
                    .addAllTokens(tokens)
                    .build();

            sendByFirebase(firebase -> firebase.sendEachForMulticast(message), alarmName);
        }
    }

    private Notification firebaseNotification(String body) {
        return Notification.builder()
                .setTitle(topic)
                .setBody(body)
                .setImage(logo)
                .build();
    }

    private void sendByFirebase(FirebaseConsumer<FirebaseMessaging, FirebaseMessagingException> consumer,
                                String alarmName) {

        try {
            consumer.accept(FirebaseMessaging.getInstance());
        } catch (FirebaseMessagingException e) {
            log.warn("{} 알림 보내기 실패 : {}", alarmName, e.getMessage());
        }
    }
}
