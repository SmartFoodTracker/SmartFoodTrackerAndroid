package aaku492.smartfoodtracker.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aaku492.smartfoodtracker.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-31.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Field extends RelativeLayout {

    @BindView(R.id.edit_text)
    protected TextInputEditText editText;

    @BindView(R.id.error_field)
    protected TextView errorView;

    @BindView(R.id.text_input_layout)
    protected TextInputLayout layout;

    public Field(Context context) {
        super(context);
        init(context);
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(attrs);
    }

    public Field(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        initAttrs(attrs);
    }

    @SuppressWarnings("ResourceType")
    private void initAttrs(AttributeSet attrs) {
        int[] set = {
                android.R.attr.hint,
                android.R.attr.inputType,
                android.R.attr.text
        };
        TypedArray a = getContext().obtainStyledAttributes(attrs, set);

        String hint = a.getString(0);
        int inputType = a.getInt(1, InputType.TYPE_NULL);
        String text = a.getString(2);
        editText.setInputType(inputType);
        setHint(hint);
        setText(text);

        a.recycle();
    }

    private void init(Context context) {
        View.inflate(context, R.layout.component_field, this);
        ButterKnife.bind(this);
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public void setHint(CharSequence hint) {
        layout.setHint(hint);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}
