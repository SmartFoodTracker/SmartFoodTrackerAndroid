package aaku492.smartfoodtracker.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class DebouncerTest {
    private static final long TEST_DURATION_MS = 500;
    private static final double ERROR_MARGIN = 1.05;

    private final long[] actionFinishedTime = {-1};
    private Debouncer debouncer;

    @Before
    public void setup() {
        actionFinishedTime[0] = -1;
        debouncer = new Debouncer(new Runnable() {
            @Override
            public void run() {
                actionFinishedTime[0] = System.currentTimeMillis();
            }
        }, TEST_DURATION_MS);
    }

    @Test
    public void start_completesActionAfterTimeout() throws InterruptedException {
        long start = System.currentTimeMillis();
        debouncer.start();
        assertFalse(debouncer.finished());

        // Wait for sufficient time to make sure that there are no race conditions under correct behaviour
        Thread.sleep(2*TEST_DURATION_MS);

        assertTrue(debouncer.finished());
        long duration = actionFinishedTime[0] - start;
        assertTrue(duration > 0 && duration < TEST_DURATION_MS * ERROR_MARGIN);
    }

    @Test
    public void bounce_delaysActionByDelayAmount() throws InterruptedException {
        long start = System.currentTimeMillis();
        debouncer.start();

        // Runnable queued up to run after TEST_DURATION_MS

        final int numLoops = 4;
        final int bounceFactor = 2;

        for (int i = 0; i < numLoops; ++i) {
            assertFalse(debouncer.finished());
            // Don't let the runnable run for 2 * TEST_DURATION_MS
            Thread.sleep(TEST_DURATION_MS/bounceFactor);
            debouncer.bounce();
        }
        assertFalse(debouncer.finished());
        // Finally let it complete after TEST_DURATION_MS
        // So total completion time == 3 * TEST_DURATION_MS
        Thread.sleep(2*TEST_DURATION_MS);
        assertTrue(debouncer.finished());
        long duration = actionFinishedTime[0] - start;
        assertTrue(duration > 0 && duration < TEST_DURATION_MS * (numLoops/bounceFactor + 1) * ERROR_MARGIN);
    }
}
