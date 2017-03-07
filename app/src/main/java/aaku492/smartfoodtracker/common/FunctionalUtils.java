package aaku492.smartfoodtracker.common;

import android.support.v4.util.Pair;

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

    public static <T> List<T> filter(Iterable<T> in, Predicate<T> predicate) {
        ArrayList<T> out = new ArrayList<>();
        for (T i: in) {
            if (predicate.test(i)) {
                out.add(i);
            }
        }
        return out;
    }

    public static <T> List<Pair<Integer, T>> enumerate(Iterable<T> in) {
        return map(in, new Mapper<T, Pair<Integer, T>>() {
            private int i = 0;
            @Override
            public Pair<Integer, T> map(T in) {
                return new Pair<>(i++, in);
            }
        });
    }

    public interface Mapper<T1, T2> {
        T2 map(T1 in);
    }

    public interface Predicate<T> {
        boolean test(T in);
    }
}
