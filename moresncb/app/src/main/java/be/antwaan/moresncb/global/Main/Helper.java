package be.antwaan.moresncb.global.Main;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {

    public static boolean IntToBool(int i){
        if (i > 0)
            return true;
        return false;
    }

    public static boolean StringToBool(String j){
        int i = Integer.parseInt(String.valueOf(j.charAt(0)));
        return Helper.IntToBool(i);
    }

    public static String RemoveDoubleQuote(String text){
        String newText = text.substring(1, text.length()-1);
        return newText;
    }

    public static String MakeOneDigitTwo(int i){
        if (i > 0 && i < 10)
            return "0" + i;
        return "" + i;
    }

    public static int FromSecondsToMinutes(int i){
        return i / 60;
    }

    public static String getDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        return date.format(formatter);
    }

    public static String DecentDate(String date){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        return LocalDate.of(year, month, day).toString();
    }

    public static String getTime(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return time.format(formatter);
    }


}
