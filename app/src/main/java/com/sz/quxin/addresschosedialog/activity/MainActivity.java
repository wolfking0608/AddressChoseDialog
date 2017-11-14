package com.sz.quxin.addresschosedialog.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sz.quxin.addresschosedialog.R;
import com.sz.quxin.addresschosedialog.entry.AddressBean;
import com.sz.quxin.addresschosedialog.utils.Constant;
import com.sz.quxin.addresschosedialog.utils.ToastUtil;
import com.sz.quxin.addresschosedialog.view.AddressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    String VOLLEY_TAG = this.getClass().getName();//没有包名+类名,唯一性
    private String loadUrl = "http://www2.hxinside.com:9998/uc_r2/user/getAreas?terminal=android&version=1.01";
    @BindView(R.id.btn)
    Button mBtn;
    private AddressBean   item;
    private Context       context;
    private StringRequest mRequest;
    private RequestQueue  mQueue;
    private boolean       finishFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        init();
    }

    protected void init() {
        initView();
        initData();
        initEvent();
    }

    //初始化视图
    protected void initView() {

    }

    //初始化数据
    private void initData() {
        requestDate();
    }

    //初始化事件
    protected void initEvent() {
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (finishFlag) {
            AddressDialog dialog = new AddressDialog(this, item, new AddressDialog.OnEndSelectItemListener() {
                @Override
                public void getItemListStr(String province, String city, String area, String areaId, List<AddressBean.DataBean.CitiesBean.AreasBean.SmallAreasBean> mSmallArea) {

                }
            });
            dialog.show();
        } else {
            ToastUtil.show(context, "数据还在请求中!");
        }

    }

    private void requestDate() {
        /*****************************************start: volleyString请求*************************************/
        //前提:  1.添加volley_lib.jar, 2.0 添加网络权限
        String VOLLEY_TAG = this.getClass().getName();//没有包名+类名,唯一性
        mRequest = new StringRequest(loadUrl, new Response.Listener<String>() {
            //成功的回调 在主线程
            @Override
            public void onResponse(String result) {
                Log.e(TAG, "onResponse: result=====" + result);
                if (result != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = 100;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = 200;
                    msg.obj = "暂时无法获取数据";
                    handler.sendMessage(msg);
                }

            }
        }, new Response.ErrorListener() {

            //失败的回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message msg = handler.obtainMessage();
                msg.what = 300;
                msg.obj = "回调失败";
                handler.sendMessage(msg);

            }
        });
        //第1.1部分 get请求设置标签方便以后统一管理
        mRequest.setTag(VOLLEY_TAG);

        //第二步 创建请求队列
        mQueue = Volley.newRequestQueue(context);
        //第三步:  把请求添加到请求队列
        mQueue.add(mRequest);
        /*****************************************END*************************************/}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //这个工作中的标准写法
        mQueue.cancelAll(VOLLEY_TAG);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String result = (String) msg.obj;
                    item = Constant.getPerson(result, AddressBean.class);
                    ToastUtil.show(context, "获取成功!");
                    finishFlag = true;
                    break;
                case 200:
                    ToastUtil.show(context, "暂无数据");
                    break;
                case 300:
                    ToastUtil.show(context, "回调失败");
                    break;
                default:
                    break;
            }
        }
    };
}








