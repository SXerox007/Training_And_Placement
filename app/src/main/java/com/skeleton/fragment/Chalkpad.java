package com.skeleton.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skeleton.R;
import com.skeleton.util.customview.ProgressDialog;

/**
 * Created by mcgreen on 7/11/17.
 */

public class Chalkpad extends BaseFragment {
    private Context contetxt;
    WebView wv;

    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chalkpad, container, false);
        wv = (WebView) view.findViewById(R.id.webview);
        wv.setWebViewClient(new WebViewClient());
        ProgressDialog.showProgressDialog(contetxt, "Chalkpad is being loded...");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("http://punjab.chitkara.edu.in");
        putDelay(wv);
        return view;
    }


    /**
     * put delay
     *
     * @param wv web view
     */
    private void putDelay(final WebView wv) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog.dismissProgressDialog();

            }
        }, 5500);
    }

    /**
     * return url
     *
     * @return url
     */
    private String getUrl() {
        return "punjab.chitkara.edu.in";
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        contetxt = context;
    }
}
