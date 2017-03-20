package aaku492.smartfoodtracker.about;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AboutFragmentView extends LinearLayout {
    @BindView(R.id.app_description)
    protected TextView appDescription;

    @BindView(R.id.app_sources)
    protected TextView appSources;

    @BindView(R.id.udey_image)
    protected ImageView udeyImage;

    @BindView(R.id.udey_name)
    protected TextView udeyName;

    @BindView(R.id.kyle_image)
    protected ImageView kyleImage;

    @BindView(R.id.kyle_name)
    protected TextView kyleName;

    @BindView(R.id.hoskins_image)
    protected ImageView hoskinsImage;

    @BindView(R.id.hoskins_name)
    protected TextView hoskinsName;

    @BindView(R.id.bradshaw_image)
    protected ImageView bradshawImage;

    @BindView(R.id.bradshaw_name)
    protected TextView bradshawName;

    public AboutFragmentView(Context context) {
        super(context);
    }

    public AboutFragmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static AboutFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_about, inflater, container);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        appDescription.setMovementMethod(LinkMovementMethod.getInstance());
        appSources.setMovementMethod(LinkMovementMethod.getInstance());
        udeyName.setMovementMethod(LinkMovementMethod.getInstance());
        kyleName.setMovementMethod(LinkMovementMethod.getInstance());
        hoskinsName.setMovementMethod(LinkMovementMethod.getInstance());
        bradshawName.setMovementMethod(LinkMovementMethod.getInstance());

        Glide.with(getContext())
                .load(getContext().getString(R.string.udey_image))
                .centerCrop()
                .into(this.udeyImage);

        Glide.with(getContext())
                .load(getContext().getString(R.string.kyle_image))
                .centerCrop()
                .into(this.kyleImage);

        Glide.with(getContext())
                .load(getContext().getString(R.string.hoskins_image))
                .centerCrop()
                .into(this.hoskinsImage);

        Glide.with(getContext())
                .load(getContext().getString(R.string.bradshaw_image))
                .centerCrop()
                .into(this.bradshawImage);
    }
}
