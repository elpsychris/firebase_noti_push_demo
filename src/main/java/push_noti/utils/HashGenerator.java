package push_noti.utils;

import java.util.Date;

public class HashGenerator {
    public static String  generateHash() {
        return new Date().getTime() + "";
    }
}
