package simple.myboard.myprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTest {

    @Test
    public void dateTest() throws ParseException {
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date result1 = formatter.parse("2023-09-20 14:08:22");
        Date result2 = formatter.parse("2023-09-20 14:08:22");
        Date result3 = formatter.parse("2023-09-20 14:08:23");
        Assertions.assertEquals(result1, result2);
        Assertions.assertNotEquals(result1, result3);

    }

}
