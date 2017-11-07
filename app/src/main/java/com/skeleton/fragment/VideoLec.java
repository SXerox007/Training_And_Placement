package com.skeleton.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skeleton.R;
import com.skeleton.activity.YouTube;

/**
 * Created by mcgreen on 7/11/17.
 */

public class VideoLec extends BaseFragment {
    private LinearLayout tut1;
    private Context context;


    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        init(view);
        return view;
    }

    /**
     * init
     * @param view view
     */
    private void init(View view) {
        tut1 = (LinearLayout) view.findViewById(R.id.tut1);
        tut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(context, YouTube.class).putExtra("url","PLAE85DE8440AA6B83")
                        .putExtra("des", getResources().getString(R.string.des)));
            }
        });
    }

    @Override
    public void onAttach(final Context contextt) {
        super.onAttach(context);
        context = contextt;
    }


}
