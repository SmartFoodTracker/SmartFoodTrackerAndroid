package aaku492.smartfoodtracker.common;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class StringUtils {
    public static String titleCase(String rawString) {
        rawString = rawString.trim();
        if (rawString.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder(rawString.length());
        for (String part : rawString.split("\\s+")) {
            sb.append(part.substring(0, 1).toUpperCase());
            if (part.length() > 1) {
                sb.append(part.substring(1).toLowerCase());
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
