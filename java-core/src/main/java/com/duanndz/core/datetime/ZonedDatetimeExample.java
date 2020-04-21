package com.duanndz.core.datetime;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created By duan.nguyen at 4/21/20 9:43 AM
 */
public class ZonedDatetimeExample {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "UTC");
        ZonedDateTime currentPST = ZonedDateTime.now(ZoneOffset.of("-08:00"));
        System.out.println(currentPST);
        Instant currentPST2UTC = currentPST.toInstant();
        System.out.println(currentPST2UTC);
        Instant currentUTC = Instant.now();
        System.out.println(currentUTC);
        ZonedDateTime timeToPushNotification;
        if (currentPST.getHour() > 8) { // if currentPST > 8 am.
            timeToPushNotification = currentPST.plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
        } else {
            timeToPushNotification = currentPST.withHour(8).withMinute(0).withSecond(0).withNano(0);
        }
        System.out.println(timeToPushNotification);
        Instant utcTime = timeToPushNotification.toInstant();
        System.out.println(utcTime);
        Date dateToPushNotification = new Date(utcTime.toEpochMilli());
        System.out.println(dateToPushNotification);
    }
}
