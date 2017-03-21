package aaku492.smartfoodtracker.common;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class StringUtils {

    public static String titleCase(String rawString) {
        final char LOWER_TO_UPPER_ASCII_DIFF = 'a' - 'A';

        StringBuilder stringBuilder = new StringBuilder(rawString.length());

        boolean firstLetter = true;
        for (char c : rawString.toCharArray()) {
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                stringBuilder.append(c);
                firstLetter = true;
            } else if (firstLetter) {
                if (c >= 'a' && c <= 'z') {
                    c = (char) (c - LOWER_TO_UPPER_ASCII_DIFF);
                }
                stringBuilder.append(c);
                firstLetter = false;
            } else {
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + LOWER_TO_UPPER_ASCII_DIFF);
                }
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
