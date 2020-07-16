package com.hzm.demo_security.utils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class TimeUtils {
    public static Date plus(LocalDateTime dateTime, long amountToAdd, ChronoUnit unit) {
        LocalDateTime timeAfterPlus = dateTime.plus(amountToAdd, unit);
        return localDateTimeToDate(timeAfterPlus);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
