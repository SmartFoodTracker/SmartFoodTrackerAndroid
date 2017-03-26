package aaku492.smartfoodtracker.common;

import android.annotation.SuppressLint;
import android.view.View;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class ChipTest extends BaseScreenshotTest {
    @Test
    public void testRenderedStates() {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.test_chip, null, false);
        Chip chip1 = (Chip) view.findViewById(R.id.test_chip1);
        Chip chip2 = (Chip) view.findViewById(R.id.test_chip2);
        Chip chip3 = (Chip) view.findViewById(R.id.test_chip3);
        Chip chip4 = (Chip) view.findViewById(R.id.test_chip4);

        chip3.setSelected(true);
        chip4.setSelected(true);

        assertEquals("Small", chip1.getText());
        assertEquals("A really really long string", chip2.getText());
        assertEquals("Small", chip3.getText());
        assertEquals("A really really long string", chip4.getText());

        assertFalse(chip1.isSelected());
        assertFalse(chip2.isSelected());
        assertTrue(chip3.isSelected());
        assertTrue(chip4.isSelected());

        takeScreenshot(view, 300);
    }

    @Test
    public void testSelectionChangedCallbacks() {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.test_chip, null, false);
        Chip chip1 = (Chip) view.findViewById(R.id.test_chip1);
        Chip chip2 = (Chip) view.findViewById(R.id.test_chip2);
        Chip chip3 = (Chip) view.findViewById(R.id.test_chip3);
        Chip chip4 = (Chip) view.findViewById(R.id.test_chip4);

        final Chip[] lastSelectedChip = {null};
        final Boolean[] lastSelection = {null};
        Chip.OnSelectionChangedListener callback = new Chip.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(Chip v, boolean selected) {
                lastSelectedChip[0] = v;
                lastSelection[0] = selected;
            }
        };

        chip1.setOnSelectionChangedListener(callback);
        chip2.setOnSelectionChangedListener(callback);
        chip3.setOnSelectionChangedListener(callback);
        chip4.setOnSelectionChangedListener(callback);


        // Verify initial state
        assertNull(lastSelectedChip[0]);
        assertNull(lastSelection[0]);

        // Assert proper callbacks
        chip3.setSelected(true);
        assertEquals(chip3, lastSelectedChip[0]);
        assertTrue(lastSelection[0]);

        // Second check
        chip4.setSelected(true);
        assertEquals(chip4, lastSelectedChip[0]);
        assertTrue(lastSelection[0]);

        // Flip selection
        chip3.setSelected(false);
        assertEquals(chip3, lastSelectedChip[0]);
        assertFalse(lastSelection[0]);

        // Re-select. Callback shouldn't have been triggered
        chip4.setSelected(true);
        assertEquals(chip3, lastSelectedChip[0]);
        assertFalse(lastSelection[0]);

        takeScreenshot(view, 300);
    }
}
