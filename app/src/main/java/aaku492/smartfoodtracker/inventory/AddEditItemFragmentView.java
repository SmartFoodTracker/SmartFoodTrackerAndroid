package aaku492.smartfoodtracker.inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragmentView extends LinearLayout {
    @BindView(R.id.item_quantity_units)
    protected AppCompatSpinner units;

    @BindView(R.id.item_expiry_date_wrapper)
    protected TextInputLayout expiryDateWrapper;

    @BindView(R.id.item_title)
    protected TextInputEditText title;

    @BindView(R.id.item_quantity)
    protected TextInputEditText quantity;

    @BindView(R.id.item_expiry_date)
    protected TextInputEditText expiryDate;

    private ProgressDialog dialog = null;

    public AddEditItemFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static AddEditItemFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_add_edit_item, inflater, container);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.item_quantity_units_options,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(adapter);

        expiryDateWrapper.setHint(getContext().getString(R.string.item_expiry_date_formatter, getContext().getString(R.string.date_format)));
    }

    private void renderTitle(@Nullable String title) {
        this.title.setText(title == null ? "" : title);
    }

    private void renderQuantity(@Nullable Double quantity) {
        this.quantity.setText(quantity == null ? "0.0" : quantity.toString());
    }

    private void renderExpiryDate(@Nullable Date date) {
        this.expiryDate.setText(date == null ?
                "" :
                new SimpleDateFormat(getContext().getString(R.string.date_format), Locale.getDefault()).format(date));
    }

    private void renderUnits(@Nullable String units) {
        this.units.setSelection(units == null ?
                        0 :
                        Arrays.asList(getContext().getResources().getStringArray(R.array.item_quantity_units_options)).indexOf(units),
                false);

    }

    public void render(@NonNull final InventoryItem item) {
        renderTitle(item.getTitle());
        renderQuantity(item.getQuantity());
        renderExpiryDate(item.getExpiryDate());
        renderUnits(item.getUnits());

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTitle(s.toString());
            }
        });

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    item.setQuantity(0.0);
                } else {
                    try {
                        item.setQuantity(Double.parseDouble(s.toString()));
                    } catch (NumberFormatException e) {
                        // Inconsistent format, wait for completion
                    }
                }
            }
        });

        units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item.setUnits(getContext().getResources().getStringArray(R.array.item_quantity_units_options)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        expiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    item.setExpiryDate(null);
                } else {
                    try {
                        item.setExpiryDate(new SimpleDateFormat(getContext().getString(R.string.date_format), Locale.getDefault()).parse(s.toString()));
                    } catch (ParseException e) {
                        // Inconsistent format, wait for completion
                    }
                }
            }
        });
    }

    public void setLoading(boolean isLoading) {
        if (isLoading && dialog == null) {
            dialog = ViewUtils.createAndShowProgressDialog(getContext());
        } else if (!isLoading && dialog != null) {
            dialog.hide();
            dialog = null;
        }

    }

    public void showMessage(String message) {
        ViewUtils.showMessage(message, this);
    }
}
