package by.it_academy.lesson17;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Maxim Tereshchenko
 */
class DateTimeApi {

    public static void main(String[] args) {
        localDate();
        localTime();
        localDateTime();
        zonedDateTime();
        instant();
        clock();
        period();
        duration();
        format();
    }

    private static void format() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "'Era 'G\n" +
                        "'Year 'yyyy\n" +
                        "'Day of year 'D\n" +
                        "'Month of year 'MMMM\n" +
                        "'Day of month 'd\n" +
                        "'Day of week 'E\n" +
                        "'Hour of day 'H\n" +
                        "'Minute of hour 'm\n" +
                        "'Second of minute 's\n" +
                        "'Time zone id 'VV\n" +
                        "'Zone offset 'X"
        );

        System.out.println(formatter.format(ZonedDateTime.now()));
    }

    private static void duration() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finalDateTime = now.plus(Period.ofDays(5));

        System.out.println("Duration.between(now,finalDateTime).getSeconds() = " + Duration.between(now, finalDateTime).getSeconds());
        System.out.println("ChronoUnit.SECONDS.between(now,finalDateTime) = " + ChronoUnit.SECONDS.between(now, finalDateTime));
        System.out.println();
    }

    private static void period() {
        LocalDate now = LocalDate.now();
        LocalDate finalDate = now.plus(Period.ofDays(5));

        System.out.println("Period.between(now, finalDate).getDays() = " + Period.between(now, finalDate).getDays());
        System.out.println("ChronoUnit.DAYS.between(now, finalDate) = " + ChronoUnit.DAYS.between(now, finalDate));
        System.out.println();
    }

    private static void clock() {
        Clock defaultClock = Clock.systemDefaultZone();
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.of("+3"));

        System.out.println("defaultClock = " + defaultClock);
        System.out.println("fixedClock = " + fixedClock);

        System.out.println("LocalDateTime.now(defaultClock) = " + LocalDateTime.now(defaultClock));
        System.out.println("LocalDateTime.now(fixedClock) = " + LocalDateTime.now(fixedClock));
        System.out.println("LocalDateTime.now(defaultClock) = " + LocalDateTime.now(defaultClock));
        System.out.println("LocalDateTime.now(fixedClock) = " + LocalDateTime.now(fixedClock));
        System.out.println();
    }

    private static void instant() {
        Instant now = Instant.now();
        Instant oneSecondAhead = now.plusSeconds(1);

        System.out.println("now = " + now);
        System.out.println("oneSecondAhead = " + oneSecondAhead);

        System.out.println("now.getEpochSecond() = " + now.getEpochSecond());
        System.out.println("now.getNano() = " + now.getNano());

        System.out.println("now.isBefore(oneSecondAhead) = " + now.isBefore(oneSecondAhead));
        System.out.println("oneSecondAhead.isAfter(now) = " + oneSecondAhead.isAfter(now));
        System.out.println("now.isBefore(now) = " + now.isBefore(now));
        System.out.println("now.compareTo(now) = " + now.compareTo(now));
        System.out.println("now.compareTo(oneSecondAhead) = " + now.compareTo(oneSecondAhead));
        System.out.println();
    }

    private static void zonedDateTime() {
        ZonedDateTime now = ZonedDateTime.now();

        System.out.println("now = " + now);

        System.out.println("now.getOffset() = " + now.getOffset());
        System.out.println("now.getZone() = " + now.getZone());

        System.out.println("ZoneOffset.UTC = " + ZoneOffset.UTC);
        System.out.println("ZonedDateTime.of(LocalDateTime.now(),ZoneOffset.UTC) = " + ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        System.out.println();
    }

    private static void localDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);

        System.out.println("now = " + now);
        System.out.println("tomorrow = " + tomorrow);

        System.out.println("now.getHour() = " + now.getHour());
        System.out.println("now.getMinute() = " + now.getMinute());
        System.out.println("now.getSecond() = " + now.getSecond());
        System.out.println("now.getNano() = " + now.getNano());

        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());
        System.out.println("now.getDayOfWeek() = " + now.getDayOfWeek());
        System.out.println("now.getDayOfYear() = " + now.getDayOfYear());
        System.out.println("now.getMonth() = " + now.getMonth());
        System.out.println("now.getYear() = " + now.getYear());

        System.out.println("now.isBefore(tomorrow) = " + now.isBefore(tomorrow));
        System.out.println("tomorrow.isAfter(now) = " + tomorrow.isAfter(now));
        System.out.println("now.isBefore(now) = " + now.isBefore(now));
        System.out.println("now.compareTo(now) = " + now.compareTo(now));
        System.out.println("now.compareTo(tomorrow) = " + now.compareTo(tomorrow));

        System.out.println("now.toLocalDate() = " + now.toLocalDate());
        System.out.println("now.toLocalTime() = " + now.toLocalTime());
        System.out.println();
    }

    private static void localTime() {
        LocalTime now = LocalTime.now();
        LocalTime oneSecondAhead = now.plusSeconds(1);

        System.out.println("now = " + now);
        System.out.println("oneSecondAhead = " + oneSecondAhead);

        System.out.println("now.getHour() = " + now.getHour());
        System.out.println("now.getMinute() = " + now.getMinute());
        System.out.println("now.getSecond() = " + now.getSecond());
        System.out.println("now.getNano() = " + now.getNano());

        System.out.println("now.isBefore(oneSecondAhead) = " + now.isBefore(oneSecondAhead));
        System.out.println("oneSecondAhead.isAfter(now) = " + oneSecondAhead.isAfter(now));
        System.out.println("now.isBefore(now) = " + now.isBefore(now));
        System.out.println("now.compareTo(now) = " + now.compareTo(now));
        System.out.println("now.compareTo(oneSecondAhead) = " + now.compareTo(oneSecondAhead));
        System.out.println();
    }

    private static void localDate() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);

        System.out.println("now = " + now);
        System.out.println("tomorrow = " + tomorrow);

        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());
        System.out.println("now.getDayOfWeek() = " + now.getDayOfWeek());
        System.out.println("now.getDayOfYear() = " + now.getDayOfYear());
        System.out.println("now.getMonth() = " + now.getMonth());
        System.out.println("now.getYear() = " + now.getYear());

        System.out.println("now.isBefore(tomorrow) = " + now.isBefore(tomorrow));
        System.out.println("tomorrow.isAfter(now) = " + tomorrow.isAfter(now));
        System.out.println("now.isBefore(now) = " + now.isBefore(now));
        System.out.println("now.compareTo(now) = " + now.compareTo(now));
        System.out.println("now.compareTo(tomorrow) = " + now.compareTo(tomorrow));
        System.out.println();
    }
}
