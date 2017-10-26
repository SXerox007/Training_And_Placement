package com.skeleton.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.adapter.RamanAdapter;
import com.skeleton.database.CommonData;
import com.skeleton.model.raman.DataObj;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.StringUtil;
import com.skeleton.util.customview.MaterialEditText;

public class GetSetTaskActivity extends BaseActivity {

    RamanAdapter ramanAdapter;
    private TextView tvHeading;
    private MaterialEditText tvId, name;
    private EditText task;
    private LinearLayout llScreen1, llScreen2;
    private String date, id;
    private boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_set_task);
        init();
        getData();
    }

    private void getData() {
        date = getIntent().getStringExtra("a");
        isNew = getIntent().getBooleanExtra("b", false);
        id = getIntent().getStringExtra("C");
        if (isNew) {
            llScreen1.setVisibility(View.GONE);
            llScreen2.setVisibility(View.VISIBLE);
            tvHeading.setText("Welcome");
            tvId.setText(id);
        } else {
            DataObj dataObj = CommonData.getData();
            tvHeading.setText(StringUtil.toCamelCase(dataObj.getData().get(0).getName()));
            ramanAdapter.setData(dataObj.getData());
        }

    }

    private void init() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvRaman);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        llScreen1 = (LinearLayout) findViewById(R.id.llScreen1);
        llScreen2 = (LinearLayout) findViewById(R.id.llScreen2);
        tvId = (MaterialEditText) findViewById(R.id.id);
        name = (MaterialEditText) findViewById(R.id.name);
        task = (EditText) findViewById(R.id.task);
        /*
         * detailed Options Adapter
         */
        ramanAdapter = new RamanAdapter(this);
        recyclerView.setAdapter(ramanAdapter);
        final LinearLayoutManager linearLayoutManagerNormal = new LinearLayoutManager(GetSetTaskActivity.this);
        recyclerView.setLayoutManager(linearLayoutManagerNormal);
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.save:
                addTaskApiHit();
                break;
            default:
        }
    }

    /**
     * Api hit to add task for the selected date
     */
    private void addTaskApiHit() {
        CommonParams params = new CommonParams.Builder()
                .add("startDate", date)
                .add("name", name.getText().toString())
                .add("text", task.getText().toString())
                .add("uniqueId", tvId.getText().toString()).build();
        RestClient.getApiInterface().addData("en", params.getMap())
                .enqueue(new ResponseResolver<CommonResponse>(this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        DataObj dataObj = commonResponse.toResponseModel(DataObj.class);
//                        tvHeading.setText(StringUtil.toCamelCase(dataObj.getData().get(0).getName()));
                        ramanAdapter.setData(dataObj.getData());
                        llScreen1.setVisibility(View.VISIBLE);
                        llScreen2.setVisibility(View.GONE);

                    }

                    @Override
                    public void failure(final APIError error) {

                    }
                });
    }
}
