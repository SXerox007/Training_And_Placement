package com.skeleton.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skeleton.R;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Amit  labs +++++++++++
 * +++++++++++++++++++++++++++++++
 */
public class DrawerMenuFragment extends BaseFragment {

    private static final int INDICATOR_INDEX = 1;
    private DrawerMenuClickListener mListener;
    private SwitchCompat switchDutyOn;
    private TextView tvNotificationCount;
    private ContentFrameLayout aboutus, aboutdev, support, contactus, flTutorials;
    private ContentFrameLayout flSupport, flReferEarn, flLogout;
    private ViewGroup lastClicked;

    /**
     *
     */
    public DrawerMenuFragment() {
        // Required empty public constructor
    }

    /**
     * @return DrawerMenuFragment instance
     */
    public static DrawerMenuFragment newInstance() {
        DrawerMenuFragment fragment = new DrawerMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_menu_fragment, container, false);
        init(view);
        return view;
    }

    private void init(final View view) {
        aboutus = (ContentFrameLayout) view.findViewById(R.id.aboutus);
        aboutdev = (ContentFrameLayout) view.findViewById(R.id.aboutdev);
        support = (ContentFrameLayout) view.findViewById(R.id.support);
        contactus = (ContentFrameLayout) view.findViewById(R.id.contactus);
        flLogout = (ContentFrameLayout) view.findViewById(R.id.fl_logout);
        aboutus.setOnClickListener(this);
        aboutdev.setOnClickListener(this);
        support.setOnClickListener(this);
        contactus.setOnClickListener(this);

    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof DrawerMenuClickListener) {
            mListener = (DrawerMenuClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DrawerMenuClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(final View v) {
        mListener.menuClicked(v.getId());
        addIndicator(v);
    }

    private void addIndicator(final View viewGroup) {

        if (lastClicked != null) {
            lastClicked.removeViewAt(INDICATOR_INDEX);
        }

        if (viewGroup instanceof ViewGroup) {
            View view = new View(getActivity());
            view.setBackgroundResource(R.color.white);
            ContentFrameLayout.LayoutParams layoutParams = new ContentFrameLayout.LayoutParams(getActivity().getResources()
                    .getDimensionPixelSize(R.dimen.nav_menu_bar_width)
                    , getActivity().getResources()
                    .getDimensionPixelSize(R.dimen.nav_menu_bar_height));
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            ((ViewGroup) viewGroup).addView(view, INDICATOR_INDEX, layoutParams);
            lastClicked = (ViewGroup) viewGroup;
        }

    }

    /**
     *
     */
    public interface DrawerMenuClickListener {

        /**
         * @param id view id
         */
        void menuClicked(int id);
    }
}
