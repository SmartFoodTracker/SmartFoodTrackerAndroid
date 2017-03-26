package aaku492.smartfoodtracker.inventory;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.junit.Test;

import java.text.ParseException;

import aaku492.smartfoodtracker.BaseScreenshotTest;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragmentViewTest extends BaseScreenshotTest {

    @Test
    public void testRenderEditItem() {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(getLayoutInflater(), null, null);
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    view.render(getTestInventoryItems().get(0));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ViewHelpers.setupView(view)
                .setExactWidthDp(300)
                .layout();

        Screenshot.snap(view)
                .record();
    }

    @Test
    public void testRenderNewItem() {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(getLayoutInflater(), null, null);
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.render(new InventoryItem(null, null, 1.00, InventoryItem.Unit.values()[0], null, null));

                // Hack to fix a timing issue with the animations.
                // See: https://github.com/facebook/screenshot-tests-for-android/issues/60
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ViewHelpers.setupView(view)
                .setExactWidthDp(300)
                .layout();

        Screenshot.snap(view)
                .record();
    }
}
