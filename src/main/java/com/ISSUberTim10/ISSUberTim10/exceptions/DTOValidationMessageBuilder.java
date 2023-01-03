package com.ISSUberTim10.ISSUberTim10.exceptions;

public class DTOValidationMessageBuilder {

    public static String maxLengthMessage(String fieldName, Integer length) {
        return "Field (" + fieldName + ") cannot be longer than " + length.toString() + " characters!";
    }

    public static String maxAndMinLengthMessage(String fieldName, Integer min, Integer max) {
        return "Field (" + fieldName + ") cannot be shorter than " + min.toString() + " or longer than " + max.toString() + " characters!";
    }

    public static String requiredMessage(String fieldName) {
        return "Field (" + fieldName + ") is required!";
    }

}
