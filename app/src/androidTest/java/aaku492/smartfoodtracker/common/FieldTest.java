package aaku492.smartfoodtracker.common;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;

import static android.view.View.GONE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FieldTest extends BaseScreenshotTest {

    @Test
    public void testRenderedStates() {
        // Need to run on main thread, because the view inflation will render the text, which
        // will animate the hint to move up.
        // Animations can only happen on Looper threads
        final View[] view = new View[1];
        runOnMainSync(new Runnable() {
            @SuppressLint("InflateParams")
            @Override
            public void run() {
                view[0] = getLayoutInflater().inflate(R.layout.test_field, null, false);
            }
        });

        Field basicField = (Field) view[0].findViewById(R.id.test_basic_field);
        Field hintField = (Field) view[0].findViewById(R.id.test_empty_field_with_hint);
        Field textField = (Field) view[0].findViewById(R.id.test_field_with_text);
        Field textHintField = (Field) view[0].findViewById(R.id.test_field_with_text_and_hint);

        assertEquals("", basicField.getText());
        assertNull(basicField.getHint());
        assertEquals("", hintField.getText());
        assertEquals("A hint", hintField.getHint());
        assertNull(textField.getHint());
        assertEquals("Some Text", textField.getText());
        assertEquals("A hint", textHintField.getHint());
        assertEquals("Some Text", textHintField.getText());

        takeScreenshot(view[0]);
    }

    @Test
    public void testTextChangedListener() {
        // Need to run on main thread, because the view inflation will render the text, which
        // will animate the hint to move up.
        // Animations can only happen on Looper threads

        final Field[] field = new Field[1];
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field[0] = new Field(getContext());
                field[0].setHint("A hint");
            }
        });

        final String newText = "Some text";

        final int[] numCalls = {0};

        field[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                assertEquals(0, numCalls[0]++);
                assertEquals(0, start);
                assertEquals(0, count);
                assertEquals(newText.length(), after);
                assertEquals(0, s.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                assertEquals(2, numCalls[0]++);
                assertEquals(newText.length(), s.length());
                assertEquals(0, start);
                assertEquals(0, before);
                assertEquals(newText.length(), count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                assertEquals(4, numCalls[0]++);
                assertEquals(newText, s.toString());
            }
        });

        field[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                assertEquals(1, numCalls[0]++);
                assertEquals(0, start);
                assertEquals(0, count);
                assertEquals(newText.length(), after);
                assertEquals(0, s.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                assertEquals(3, numCalls[0]++);
                assertEquals(newText.length(), s.length());
                assertEquals(0, start);
                assertEquals(0, before);
                assertEquals(newText.length(), count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                assertEquals(5, numCalls[0]++);
                assertEquals(newText, s.toString());
            }
        });

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field[0].setText(newText);
            }
        });
        assertEquals(6, numCalls[0]);

        field[0].clearTextChangedListeners();
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field[0].setText(newText + " updated");
            }
        });
        assertEquals(6, numCalls[0]);

        takeScreenshot(field[0]);
    }

    @Test
    public void testValidator() {
        final View[] view = new View[1];
        runOnMainSync(new Runnable() {
            @SuppressLint("InflateParams")
            @Override
            public void run() {
                view[0] = getLayoutInflater().inflate(R.layout.test_field, null, false);
            }
        });
        // Only need 2 fields
        final Field firstField = (Field) view[0].findViewById(R.id.test_basic_field);
        final Field secondField = (Field) view[0].findViewById(R.id.test_empty_field_with_hint);
        view[0].findViewById(R.id.test_field_with_text).setVisibility(GONE);
        view[0].findViewById(R.id.test_field_with_text_and_hint).setVisibility(GONE);


        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstField.setText("Some text");
                firstField.setHint("A hint");
                secondField.setText("Some other text");
                secondField.setHint("Another hint");
                firstField.validate(new Field.TextValidator() {
                    @Override
                    public boolean isValid(CharSequence text) {
                        return false;
                    }
                }, "Test error message");
                secondField.validate(new Field.TextValidator() {
                    @Override
                    public boolean isValid(CharSequence text) {
                        return true;
                    }
                }, "This shouldn't show up");
            }
        });

        takeScreenshot(view[0]);
    }

    // Can't test suggestions in an automated way, because they don't show up in the screenshots for some reason.
    // This might be due to the implementation of the testing library, because the suggestions show up in a different layer.
    // The testing library might not include that in the images.
}
