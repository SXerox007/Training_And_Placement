package com.skeleton.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skeleton.R;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Click labs +++++++++++
 * +++++++++++++++++++++++++++++++
 **/
public class ReferAndEarnFragment extends BaseFragment implements View.OnClickListener {

    private static final String TEXT_TYPE = "text/plain";
    private AppCompatButton btnShareViaWhatsApp;
    private TextView tvSharePromoOthers;

    /**
     *Creating Instance of Fragment
     * @return Instance of Fragment
     */
    public static ReferAndEarnFragment newInstance() {
        return new ReferAndEarnFragment();
    }

    /**
     *
     * @param inflater View to be inflate into the Fragment
     * @param container container of views
     * @param savedInstanceState savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refer_and_earn_fragment, container, false);
        return view;
    }

    /**
     *
     * @param view View Created
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    /**
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * @param view
     */
    private void init(final View view) {
        tvSharePromoOthers = (TextView) view.findViewById(R.id.tv_share_refal_via_others);
        btnShareViaWhatsApp = (AppCompatButton) view.findViewById(R.id.btn_share_refral_whats_app);
        tvSharePromoOthers.setOnClickListener(this);
        btnShareViaWhatsApp.setOnClickListener(this);
    }

    /**
     *
     * @param v view of listener
     */
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_share_refal_via_others:
                shareReferralCode();
                break;
            case R.id.btn_share_refral_whats_app:
                shareViaWhatsApp();
                break;
            default:
                break;
        }
    }

    /**
     * share the referral code with all others
     */
    private void shareReferralCode() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, REFERAL_CODE);
        sendIntent.setType(TEXT_TYPE);
        startActivity(Intent.createChooser(sendIntent, REFER_CODE_SEND));
    }


    /**
     * share Referral code Via Whats App
     */
    private void shareViaWhatsApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, REFERAL_CODE);
        sendIntent.setType(TEXT_TYPE);
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }
}
