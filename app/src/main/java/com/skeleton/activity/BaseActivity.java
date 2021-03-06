package com.skeleton.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.skeleton.BuildConfig;
import com.skeleton.R;
import com.skeleton.constant.ApiKeyConstant;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.util.Util;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.dialog.BookingRequestDialog;
import com.skeleton.util.dialog.CustomAlertDialog;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.skeleton.util.Util.TEN;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Amit Sharma +++++++++++
 * +++++++++++++++++++++++++++++++
 */
public abstract class BaseActivity extends AppCompatActivity implements AppConstant, ApiKeyConstant, View.OnClickListener {
    private static final int OVERLAY_TEXT_SIZE_INT = 15;
    private static final String BOOKING_DIALOG = "booking";
    private String overlayText;
    private BookingRequestDialog bookingRequestDialog;
    /**
     * Receiver To handle Location When App is in Foreground state
     */
    private BroadcastReceiver notificationReceiver;

    /**
     * To handle Dialog, {@link #showNotificationDialog}
     */
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                showNotificationDialog(intent.getExtras());
            }
        };

        // setting flavor type in common Data
        if (!BuildConfig.IS_UNIVERSAL) {
            CommonData.setFlavorType(BuildConfig.FLAVOR);
        }
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver, new IntentFilter(NOTIFICATION_RECEIVED));
        addWaterMark();
    }

    /**
     * Show Booking Dialog
     */
    public void showBookingDialog() {
        if (bookingRequestDialog == null) {
            bookingRequestDialog = BookingRequestDialog.getInstance();
        }
        bookingRequestDialog.show(getSupportFragmentManager(), BOOKING_DIALOG);
    }

    /**
     * add water mark
     */
    public void addWaterMark() {
        overlayText = BuildConfig.APP_NAME + "_"
                + CommonData.getApplicationType() + "_v" + BuildConfig.VERSION_CODE;
        drawWaterMark();
    }


    /**
     * Draw Code Version On the Every Screen Of the APP
     */
    public void drawWaterMark() {
        if (BuildConfig.WATER_MARK) {
            DrawOnTop mDraw = new DrawOnTop(this);
            addContentView(mDraw, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            mDraw.bringToFront();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        View view = getCurrentFocus();
        try {
            boolean ret = super.dispatchTouchEvent(event);

            if (view != null && view instanceof EditText) {
                View w = getCurrentFocus();
                int[] scrcoords = new int[2];
                assert w != null;
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP
                        && (x < w.getLeft() || x >= w.getRight()
                        || y < w.getTop() || y > w.getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * {@link #mDialog}
     *
     * @param mBundle notification bundle
     */
    public void showNotificationDialog(final Bundle mBundle) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = new CustomAlertDialog.Builder(BaseActivity.this)
                .setMessage(mBundle.getString(MESSAGE))
                .setPositiveButton(R.string.text_ok, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                    @Override
                    public void onClick() {

                    }
                })
                .show();
    }


    /**
     * @param toolBar toolbar
     */
    public void setToolBar(final Toolbar toolBar) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");
    }


    /**
     * @param materialEditText parameter material edit text
     */
    public void clearFields(final MaterialEditText... materialEditText) {
        for (MaterialEditText editText : materialEditText) {
            editText.setText("");
        }
    }


    /**
     * Class to Draw the Version Code
     */
    class DrawOnTop extends View {
        private Paint paintText;
        private Rect bounds;

        /**
         * @param activity Constructor with context of activity
         */
        public DrawOnTop(final Context activity) {
            super(activity);
            paintText = new Paint();
            bounds = new Rect();
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            invalidate();
            // put your drawing commands here
            paintText.setColor(Color.GRAY);
            paintText.setTextSize(Util.dpToPx(BaseActivity.this, OVERLAY_TEXT_SIZE_INT));
            paintText.getTextBounds(overlayText, 0, overlayText.length(), bounds);
            canvas.drawText(overlayText,
                    getWidth() - (bounds.width() + TEN),
                    this.getHeight() - OVERLAY_TEXT_SIZE_INT,
                    paintText);

        }


    }
}
