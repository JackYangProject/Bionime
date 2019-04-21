package com.bionime.bionimedemo.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bionime.bionimedemo.R;


public class CommonDialog extends Dialog {

    private ImageView imageIv ;
    private TextView titleTv ;
    private TextView sitenameTv,countyTv,aqiTv,statusTv,pollutantTv;
    private Button negtiveBn ,positiveBn;
    private View columnLineView ;
    public CommonDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    /**
     * 都是内容数据
     */
    private String sitename,county,aqi,status,pollutant;
    private String title;
    private String positive,negtive ;
    private int imageResId = -1 ;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }else {
            titleTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sitename)) {
            sitenameTv.setText(sitename);
        }
        if (!TextUtils.isEmpty(county)) {
            countyTv.setText(county);
        }
        if (!TextUtils.isEmpty(aqi)) {
            aqiTv.setText(aqi);
        }
        if (!TextUtils.isEmpty(status)) {
            statusTv.setText(status);
        }
        if (!TextUtils.isEmpty(pollutant)) {
            pollutantTv.setText(pollutant);
        }

        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        }else {
            positiveBn.setText("刪除");
        }

        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        }else {
            negtiveBn.setText("取消");
        }

        if (imageResId!=-1){
            imageIv.setImageResource(imageResId);
            imageIv.setVisibility(View.VISIBLE);
        }else {
            imageIv.setVisibility(View.GONE);
        }
        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle){
            columnLineView.setVisibility(View.GONE);
            negtiveBn.setVisibility(View.GONE);
        }else {
            negtiveBn.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = findViewById(R.id.negtive);
        positiveBn = findViewById(R.id.positive);
        titleTv = findViewById(R.id.title);
        sitenameTv = findViewById(R.id.sitename_msg);
        countyTv = findViewById(R.id.county_msg);
        aqiTv = findViewById(R.id.aqi_msg);
        statusTv = findViewById(R.id.status_msg);
        pollutantTv = findViewById(R.id.pollutant_msg);
        imageIv = findViewById(R.id.image);
        columnLineView = findViewById(R.id.column_line);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;
    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }
    public interface OnClickBottomListener{
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();
        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }
    public CommonDialog setSitename(String message) {
        this.sitename = message;
        return this ;
    }
    public CommonDialog setCounty(String message) {
        this.county = message;
        return this ;
    }
    public CommonDialog setAqi(String message) {
        this.aqi = message;
        return this ;
    }
    public CommonDialog setStatus(String message) {
        this.status = message;
        return this ;
    }
    public CommonDialog setPollutant(String message) {
        this.pollutant = message;
        return this ;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this ;
    }

    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this ;
    }

    public String getNegtive() {
        return negtive;
    }

    public CommonDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this ;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public CommonDialog setSingle(boolean single) {
        isSingle = single;
        return this ;
    }

    public CommonDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this ;
    }

}