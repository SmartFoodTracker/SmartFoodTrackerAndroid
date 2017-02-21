package aaku492.smartfoodtracker.common;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-21.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Debouncer {
    private final long continuousDelayMilliseconds;
    private final Runnable action;
    private Thread thread = null;
    private final Object lock = new Object();

    public Debouncer(Runnable action, long continuousDelayMilliseconds) {
        this.action = action;
        this.continuousDelayMilliseconds = continuousDelayMilliseconds;
    }

    public void start() {
        if (thread != null) {
            throw new IllegalStateException("Cannot call start twice");
        }

        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(continuousDelayMilliseconds);
                        synchronized (lock) {
                            if (Thread.interrupted()) {
                                // possible if thread.sleep finished while bounce() had the lock,
                                // but before it called interrupt(). That way, the exception won't
                                // be thrown
                                continue;
                            }
                            action.run();
                            thread = null;
                            break;
                        }

                    } catch (InterruptedException e) {
                        // do nothing and go back to sleep
                    }
                }
            }
        });
        thread.start();
    }

    public void bounce() {
        synchronized (lock) {
            if (thread == null) {
                throw new IllegalStateException("Cannot bounce before starting.");
            }
            thread.interrupt();
        }
    }

    public boolean finished() {
        return thread == null;
    }
}
