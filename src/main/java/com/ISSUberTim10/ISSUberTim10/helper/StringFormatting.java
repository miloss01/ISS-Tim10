package com.ISSUberTim10.ISSUberTim10.helper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class StringFormatting {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy.");

}
