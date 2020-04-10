package com.duanndz.core.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created By ngdduan@gmail.com at 4/10/20 11:49 AM
 */
public class DateTimeExample {

    public static void main(String[] args) {
        // Convert from UTC to ZonedDateTime.
        System.out.println("------------------------------------------");
        System.out.println("Convert from UTC Instant to ZonedDateTime.");
        Instant utc = Instant.now();
        System.out.println(utc.toString());
        // America/Los_Angeles
        ZonedDateTime caDateTime = utc.atZone(ZoneId.of("America/Los_Angeles"));
        System.out.println("ZonedDateTime : " + caDateTime.toString());
        System.out.println("OffSet : " + caDateTime.getOffset());
        System.out.println("Format ZonedDateTime: " + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(caDateTime));
    }

}
