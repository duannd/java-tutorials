package com.duanndz.core.datetime;

import java.time.*;

/**
 * Created By ngdduan@gmail.com at 4/11/20 11:44 PM
 */
public class ZoneOffsetExample {

    public static void main(String[] args) {
        Instant utc = Instant.now();
        System.out.println("UTC time: " + utc);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("zonedDateTime now: " + zonedDateTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime now: " + localDateTime);

        ZoneId vnTimezone = ZoneOffset.of("+07:00");

        ZonedDateTime vnTime = ZonedDateTime.now(vnTimezone);
        System.out.println("ZonedDateTime at vnTimezone: " + vnTime);
        System.out.println("UTC to VN time: " + utc.atZone(vnTimezone));
    }
}
