package aaku492.smartfoodtracker.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FunctionalUtils {
    public static <T1, T2> List<T2> map(Iterable<T1> in, Mapper<T1, T2> mapper) {
        ArrayList<T2> out = new ArrayList<>();
        for (T1 i : in) {
            out.add(mapper.map(i));
        }
        return out;
    }

    public static <T> boolean any(Iterable<T> in, Predicate<T> predicate) {
        for (T i : in) {
            if (predicate.test(i)) {
                return true;
            }
        }
        return false;
    }

    public interface Mapper<T1, T2> {
        T2 map(T1 in);
    }

    public interface Predicate<T> {
        boolean test(T in);
    }
}
