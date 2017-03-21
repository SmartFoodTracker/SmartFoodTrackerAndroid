package aaku492.smartfoodtracker.common;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static aaku492.smartfoodtracker.common.FunctionalUtils.any;
import static aaku492.smartfoodtracker.common.FunctionalUtils.enumerate;
import static aaku492.smartfoodtracker.common.FunctionalUtils.filter;
import static aaku492.smartfoodtracker.common.FunctionalUtils.indexOf;
import static aaku492.smartfoodtracker.common.FunctionalUtils.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FunctionalUtilsTest {
    private ArrayList<Integer> in;

    @Before
    public void setup() {
        in = new ArrayList<>(5);
        in.add(100);
        in.add(-2);
        in.add(-4);
        in.add(1);
        in.add(0);
    }

    @Test
    public void map_works() {
        List<Integer> mapped = map(in, new FunctionalUtils.Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer in) {
                return in*2;
            }
        });
        assertEquals(in.size(), mapped.size());
        for (int i = 0; i < in.size(); ++i) {
            assertEquals(in.get(i) * 2, mapped.get(i).longValue());
        }
    }

    @Test
    public void map_withEmptyList_works() {
        List<Integer> mapped = map(new ArrayList<Integer>(), new FunctionalUtils.Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer in) {
                throw new IllegalStateException("Mapper should'nt have been called for empty list");
            }
        });
        assertEquals(0, mapped.size());
    }

    @Test
    public void any_withListContainingMatchingValue_returnsTrue() {
        final int[] invocationCount = {0};

        boolean foundNegative = any(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });

        assertTrue(foundNegative);
        assertEquals(2, invocationCount[0]);
    }

    @Test
    public void any_withListNotContainingMatchingValue_returnsFalse() {
        in.set(1, 2);
        in.set(2, 4);
        final int[] invocationCount = {0};

        boolean foundNegative = any(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });

        assertFalse(foundNegative);
        assertEquals(in.size(), invocationCount[0]);
    }

    @Test
    public void filter_works() {
        final int[] invocationCount = {0};

        List<Integer> filtered = filter(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });
        assertEquals(2, filtered.size());
        assertEquals(in.get(1), filtered.get(0));
        assertEquals(in.get(2), filtered.get(1));
        assertEquals(in.size(), invocationCount[0]);
    }

    @Test
    public void enumerate_works() {
        List<Pair<Integer, Integer>> enumerated = enumerate(in);
        assertEquals(in.size(), enumerated.size());
        for (Pair<Integer, Integer> e : enumerated) {
            assertEquals(in.get(e.first), e.second);
        }
    }

    @Test
    public void enumerate_withEmptyList_works() {
        List<Pair<Integer, Integer>> enumerated = enumerate(new ArrayList<Integer>());
        assertEquals(0, enumerated.size());
    }

    @Test
    public void indexOf_containingMatchingValue_returnsIndex() {
        final int[] invocationCount = {0};

        int index = indexOf(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });

        assertEquals(1, index);
        assertEquals(2, invocationCount[0]);


        // Set the first negative number positive, so that the other negative is found
        invocationCount[0] = 0;
        in.set(1, 100);

        index = indexOf(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });

        assertEquals(2, index);
        assertEquals(3, invocationCount[0]);
    }

    @Test
    public void indexOf_notContainingMatchingValue_returnsFalse() {
        in.set(1, 2);
        in.set(2, 4);
        final int[] invocationCount = {0};

        int index = indexOf(in, new FunctionalUtils.Predicate<Integer>() {

            @Override
            public boolean test(Integer in) {
                ++invocationCount[0];
                return in.compareTo(0) < 0;
            }
        });

        assertEquals(-1, index);
        assertEquals(in.size(), invocationCount[0]);
    }
}
