package com.skeleton.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.skeleton.R;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Click labs +++++++++++
 * +++++++++++++++++++++++++++++++
 */
public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatButton btnLocation, btnCallUs, btnMail;
    private Toolbar toolbar;
    private TextView tvTitle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
    }

    /**
     * initialization
     */
    private void init() {
        tvTitle = (TextView) findViewById(R.id.toolbar_title);
        tvTitle.setText(R.string.toolbar_title_contact_us);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        btnLocation = (AppCompatButton) findViewById(R.id.btn_location);
        btnCallUs = (AppCompatButton) findViewById(R.id.btn_phone_number);
        btnMail = (AppCompatButton) findViewById(R.id.btn_message);
        btnLocation.setOnClickListener(this);
        btnCallUs.setOnClickListener(this);
        btnMail.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_location:
                break;
            case R.id.btn_phone_number:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(CLICK_LABS_PHONE_NUMBER));
                startActivity(intent);
                break;
            case R.id.btn_message:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse(CLICK_LABS_EMAIL));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{CLICK_LABS_EMAIL});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, CLICK_LABS_SUPPORT);
                startActivity(sendIntent);
                break;
            default:
                break;
        }
    }

}
