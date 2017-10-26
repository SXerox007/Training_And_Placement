package com.skeleton.activity;

import android.os.Bundle;

import com.skeleton.R;
import com.skeleton.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();

        setHome();
    }

    /**
     * set home fragment
     */
    private void setHome() {
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll, homeFragment);
        fragmentTransaction.commit();


    }
}
