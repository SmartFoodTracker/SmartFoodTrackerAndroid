package aaku492.smartfoodtracker.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aaku492.smartfoodtracker.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-06.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Chip extends RelativeLayout {
    @BindView(R.id.chip_text)
    protected TextView chipText;
    private OnSelectionChangedListener onSelectionChangedListener = null;

    public Chip(Context context) {
        super(context);
        init(context);
    }

    public Chip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(attrs);
    }

    public Chip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        initAttrs(attrs);
    }

    private void init(final Context context) {
        View.inflate(context, R.layout.component_chip, this);
        this.setClickable(true);
        ButterKnife.bind(this);
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(!isSelected());
            }
        });
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        throw new UnsupportedOperationException("Please use setOnSelectionChangedListener instead.");
    }

    public void setOnSelectionChangedListener(@Nullable OnSelectionChangedListener onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }

    @Override
    public void setSelected(boolean selected) {
        if (isSelected() == selected) {
            return;
        }
        super.setSelected(selected);
        if (onSelectionChangedListener != null) {
            onSelectionChangedListener.onSelectionChanged(Chip.this, isSelected());
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Chip);
        String text = a.getString(R.styleable.Chip_android_text);
        setText(text);
        a.recycle();
    }

    public void setText(CharSequence text) {
        this.chipText.setText(text);
    }

    public CharSequence getText() {
        return chipText.getText();
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(Chip v, boolean selected);
    }
}
