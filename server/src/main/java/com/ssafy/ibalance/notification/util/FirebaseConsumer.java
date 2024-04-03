package com.ssafy.ibalance.notification.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

@FunctionalInterface
public interface FirebaseConsumer<T extends FirebaseMessaging, E extends FirebaseMessagingException> {
    void accept(T t) throws E;
}
