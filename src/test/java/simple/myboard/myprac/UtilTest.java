package simple.myboard.myprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UtilTest {

    @Test
    public void dateTest() throws ParseException {
        // MySql 에서 던져주는 date 패턴
        String pattern = "yyyy-MM-dd hh:mm:ss";
        // Date 자료형으로 변환하기
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date result1 = formatter.parse("2023-09-20 14:08:22");
        Date result2 = formatter.parse("2023-09-20 14:08:22");
        Date result3 = formatter.parse("2023-09-20 14:08:23");
        // 동등성 비교해 보기
        Assertions.assertEquals(result1, result2);
        Assertions.assertNotEquals(result1, result3);

    }

    @Test
    public void localDateTest() {
        // 날짜 정보만 가지고 생성
        LocalDate date1 = LocalDate.of(2023, 9, 17);
        LocalDate date2 = LocalDate.of(2023, 10, 26);
        LocalDate date3 = LocalDate.of(2023, 11, 5);
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);
        System.out.println("date3 = " + date3);

        // 날짜에 시분초 정보까지도 포함
        LocalDateTime time1 = LocalDateTime.of(2023, 9, 17, 3, 26, 57);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 26, 14, 43, 36);
        LocalDateTime time3 = LocalDateTime.of(2023, 11, 5, 23, 8, 14);
        System.out.println("time1 = " + time1);
        System.out.println("time2 = " + time2);
        System.out.println("time3 = " + time3);

        // LocalDateTime 을 날짜만 가지고 비교하고 싶을 때
        LocalDateTime dayTime = time1.truncatedTo(ChronoUnit.DAYS);
        System.out.println("dayTime = " + dayTime);
        // LocalDateTime 을 시간까지만 비교하고 싶을 때
        LocalDateTime hourTime = time1.truncatedTo(ChronoUnit.HOURS);
        System.out.println("hourTime = " + hourTime);
        // LocalDateTime 을 분까지만 비교하고 싶을 때
        LocalDateTime minTime = time1.truncatedTo(ChronoUnit.MINUTES);
        System.out.println("minTime = " + minTime);
        // LocalDateTime 을 초까지만 비교하고 싶을 때
        LocalDateTime secTime = time1.truncatedTo(ChronoUnit.SECONDS);
        System.out.println("secTime = " + secTime);

        // LocalDateTime 을 LocalDate 로 변환
        LocalDate timeToDate = time1.toLocalDate();
        System.out.println("timeToDate = " + timeToDate);
        
        // LocalDateTime 과 LocalDate 비교하기
        Assertions.assertEquals(time1.toLocalDate(), date1);
    }

    @Test
    public void LocalDateTimeForamtterTest() {
        // 변환 전 시간 문자열
        String targetTime = "2023-10-06 14:26:56";
        // 변환
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time1 = LocalDateTime.parse(targetTime, formatter1);
        // 변환 후 시간 LocalDateTime
        System.out.println("##### time1 = " + time1);
        //
        //
        // pattern 에서 시간을 지정할 떄 hh 랑 HH 랑 다름.
        // hh 해두면 1-12 사이의 숫자를 입력하고 am 인지 pm 인지 따로 지정해줘야 함.
        // HH 는 밀리터리 시간으로 1-24 사이 숫자를 입력할 수 있음 
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        Assertions.assertThrows(DateTimeParseException.class, ()->{LocalDateTime.parse(targetTime, formatter2);});
        


    }


}
