package com.sz.quxin.addresschosedialog.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.quxin.addresschosedialog.R;
import com.sz.quxin.addresschosedialog.entry.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class AddressDialog extends Dialog {


    private Activity mActivity;
    private ListView listView;
    private LinearLayout layout_tab;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private TextView textViewStreet;
    private View indicator;

    public AddressDialog(Activity context) {
        super(context, R.style.bottom_dialog);
        mActivity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_selector);
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (metric.widthPixels);
        Window window = getWindow();
        window.setAttributes(p);
        window.setGravity(Gravity.BOTTOM);
        //window.setWindowAnimations(R.style.Umengstyle);
        setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        init();
    }

    protected void init() {
        initView();
        initData();
        initEvent();
    }

    //初始化视图
    protected void initView() {
        listView = (ListView) findViewById(R.id.listView);
        this.indicator = findViewById(R.id.indicator); //指示器
        this.layout_tab = (LinearLayout) findViewById(R.id.layout_tab);
        this.textViewProvince = (TextView) findViewById(R.id.textViewProvince);//省份
        this.textViewCity = (TextView) findViewById(R.id.textViewCity);//城市
        this.textViewCounty = (TextView) findViewById(R.id.textViewCounty);//区 乡镇
        this.textViewStreet = (TextView) findViewById(R.id.textViewStreet);//街道
    }

    //初始化数据
    private void initData() {

    }

    //初始化事件
    protected void initEvent() {

    }
    public interface OnEndSelectItemListener {
        public void getItemListStr(String province, String city, String area, String areaId,
                                   List<AddressBean.DataBean.CitiesBean.AreasBean.SmallAreasBean> mSmallArea);
    }

}
