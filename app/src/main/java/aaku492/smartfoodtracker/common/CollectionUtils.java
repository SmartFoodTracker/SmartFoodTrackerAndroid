package aaku492.smartfoodtracker.common;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-27.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class CollectionUtils {
    @SafeVarargs
    public static <T> ArrayList<T> createArrayList(T... items) {
        ArrayList<T> list = new ArrayList<>(items.length);
        list.addAll(Arrays.asList(items));
        return list;
    }
}
