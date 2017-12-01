package com.sz.quxin.addresschosedialog.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sz.quxin.addresschosedialog.R;
import com.sz.quxin.addresschosedialog.entry.AddressBean;
import com.sz.quxin.addresschosedialog.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class AddressDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {


    private Activity                mActivity;
    private ListView                listView;
    private LinearLayout            layout_tab;
    private TextView                textViewProvince;
    private TextView                textViewCity;
    private TextView                textViewCounty;
    private View                    indicator;
    private AddressBean             mCityBean;
    private OnEndSelectItemListener listener;

    private String province;
    private String mCity;
    private String area;
    private String areaId;//区ID
    //data
    private List<AddressBean.DataBean>                                     data               = new ArrayList<>();//省的集合
    private List<AddressBean.DataBean.CitiesBean>                          cities             = new ArrayList<>();//市一级的集合
    private List<AddressBean.DataBean.CitiesBean.AreasBean>                areas              = new ArrayList<>();//区一级的集合
    private List<AddressBean.DataBean.CitiesBean.AreasBean.SmallAreasBean> smallAreasBeanList = new ArrayList<>();//小区一级的集合


    //用来记录编辑你点击的是省,市,区哪个条目
    private int provinceIndex = -1;
    private int cityIndex     = -1;
    private int countyIndex   = -1;

    //标记现在是点击的是第几个条目
    private int currentIndex = -1;

    private static final int PROVINCEFLAG = 1;
    private static final int CITYFLAG     = 2;
    private static final int COUNTYFLAG   = 3;

    //标记显示现在应该显示是哪个 是省还是市,还是区
    private int currentFlag = 1;//默认值应该为-1

    //用来存储选择的省市区;
    private String curProvince;
    private String curCity;
    private String curArea;
    private String curAreaId;
    private AddressAdapter adapter;
    private Context        mContext;
    private  ImageView iv_colse;
    private ProgressBar progressBar;

    public AddressDialog(Activity context, AddressBean c,String province,String city,String area, OnEndSelectItemListener l) {
        super(context, R.style.bottom_dialog);
        mActivity = context;
        this.mCityBean = c;
        this.listener = l;
        mContext = context;
        curProvince=province;
        curCity=city;
        curArea=area;

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
        indicator = findViewById(R.id.indicator); //指示器
        layout_tab = (LinearLayout) findViewById(R.id.layout_tab);
        textViewProvince = (TextView) findViewById(R.id.textViewProvince);//省份
        textViewCity = (TextView) findViewById(R.id.textViewCity);//城市
        textViewCounty = (TextView) findViewById(R.id.textViewCounty);//区 乡镇
        iv_colse= (ImageView) findViewById(R.id.iv_colse);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
    }

    //初始化数据
    private void initData() {
        if (mCityBean != null && mCityBean.getData() != null) {
            data = mCityBean.getData();
            //用来状态回显
            if (TextUtils.isEmpty(curProvince)||TextUtils.isEmpty(curCity)||TextUtils.isEmpty(curArea)){

                textViewProvince.setText("请选择");
                textViewProvince.setVisibility(View.VISIBLE);
                adapter = new AddressAdapter(mActivity, data);
                progressBar.setVisibility(View.GONE);
                listView.setAdapter(adapter);
            }else{
                curProvince=curProvince.trim();
                curCity=curCity.trim();
                curArea=curArea.trim();

                if (data==null ||data.size()==0 ){
                    return;
                }
                //需要显示回显的状态
                for (int i = 0; i < data.size(); i++) {
                    AddressBean.DataBean dataBean = data.get(i);
                    String province = dataBean.getProvince();

                    if(curProvince.equals(province)){
                        provinceIndex = i;
                        currentIndex=i;
                        curProvince = dataBean.getProvince();
                        textViewProvince.setText(curProvince);
                        textViewProvince.setVisibility(View.VISIBLE);
                              cities = dataBean.getCities();

                        if (cities==null ||cities.size()==0 ){
                            return;
                        }
                        for (int j = 0; j <cities.size(); j++) {
                            AddressBean.DataBean.CitiesBean citiesBean = cities.get(j);

                            String city = citiesBean.getCity();

                            if (curCity.equals(city)) {
                                currentIndex=j;
                                cityIndex = j;

                                textViewCity.setText(curCity);
                                textViewCity.setVisibility(View.VISIBLE);
                                areas = citiesBean.getAreas();
                                if (areas == null || areas.size() == 0) {
                                    return;
                                }
                                //显示区
                                currentFlag=COUNTYFLAG;
                                for (int k = 0; k < areas.size(); k++) {
                                    AddressBean.DataBean.CitiesBean.AreasBean areasBean = areas.get(k);
                                    String area = areasBean.getArea();
                                    if (curArea.equals(area)){
                                       countyIndex=k;
                                        currentIndex=k;
                                        textViewCounty.setText(curArea);
                                        textViewCounty.setVisibility(View.VISIBLE);
                                        adapter = new AddressAdapter(mActivity, data);
                                        progressBar.setVisibility(View.GONE);
                                        listView.setAdapter(adapter);

                                    }

                                }
                            }




                        }
                    }
                }

            }

        }else{
            ToastUtil.show(mContext,"请求回来的地址为空!");
        }


    }

    //初始化事件
    protected void initEvent() {
        listView.setOnItemClickListener(this);
        textViewProvince.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewCounty.setOnClickListener(this);
        iv_colse.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentIndex=position;
        switch (currentFlag) {
            case PROVINCEFLAG:
                AddressBean.DataBean item = (AddressBean.DataBean) adapter.getItem(position);
                provinceIndex = position;
                curProvince = item.getProvince();
                textViewProvince.setText(curProvince);
                textViewCity.setVisibility(View.VISIBLE);
                cities=item.getCities();
                currentFlag=CITYFLAG;


                break;
            case CITYFLAG:
                AddressBean.DataBean.CitiesBean citiesBean = (AddressBean.DataBean.CitiesBean) adapter.getItem(position);
                curCity = citiesBean.getCity();
                cityIndex = position;
                textViewCity.setText(curCity);
                textViewCounty.setVisibility(View.VISIBLE);
                areas=citiesBean.getAreas();
                currentFlag=COUNTYFLAG;

                break;
            case COUNTYFLAG:
                AddressBean.DataBean.CitiesBean.AreasBean areasBean = (AddressBean.DataBean.CitiesBean.AreasBean) adapter.getItem(position);
                countyIndex = position;
                curArea = areasBean.getArea();
                textViewCounty.setText(curArea);
                curAreaId=areasBean.getAreaid();
                //回调数据到MainActivity
                listener.getItemListStr(curProvince,curCity,curArea,curAreaId,areasBean);
                dismiss();
                break;
            default:
                ToastUtil.show(mActivity, "程序逻辑有问题:currentFlag==" + currentFlag);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewProvince://省
                currentFlag = PROVINCEFLAG;
                textViewCity.setVisibility(View.GONE);
                textViewCounty.setVisibility(View.GONE);
                break;
            case R.id.textViewCity://市
                currentFlag = CITYFLAG;
                textViewCounty.setVisibility(View.GONE);
                break;
            case R.id.textViewCounty://区
                currentFlag = COUNTYFLAG;

                break;
            case R.id.iv_colse:

                dismiss();
                break;
            default:
                break;
        }
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnEndSelectItemListener {

        public void getItemListStr(String province, String city, String area, String areaId, AddressBean.DataBean.CitiesBean.AreasBean mSmallArea);
    }

    private class AddressAdapter extends BaseAdapter {


        private Activity                   mActivity;
        private List<AddressBean.DataBean> mData;
        public AddressAdapter(Activity activity, List<AddressBean.DataBean> data) {

            mActivity = activity;
            mData = data;
        }

        @Override
        public int getCount() {
            if (mCityBean != null) {
                switch (currentFlag) {
                    case PROVINCEFLAG:
                        data = mCityBean.getData();
                        if (AddressDialog.this.data != null) {
                            if (AddressDialog.this.data.size() > 0) {
                                return AddressDialog.this.data.size();
                            } else {
                                ToastUtil.show(mContext, "服务器,省的集合为0");
                            }
                        } else {
                            ToastUtil.show(mContext, "服务器,省的集合为空");
                        }
                        break;
                    case CITYFLAG:
                        AddressBean.DataBean dataBean = data.get(currentIndex);
                        if (dataBean != null) {
                            cities = dataBean.getCities();
                            if (cities != null) {
                                if (cities.size() > 0) {
                                    return cities.size();
                                } else {
                                    ToastUtil.show(mContext, "服务器,市的集合为0");
                                }
                            } else {
                                ToastUtil.show(mContext, "服务器,市的集合为空");
                            }
                        } else {
                            ToastUtil.show(mContext, "服务器,城市bean为空");
                        }

                        break;
                    case COUNTYFLAG:
                        AddressBean.DataBean.CitiesBean citiesBean = cities.get(currentIndex);
                        if (citiesBean != null) {
                            areas = citiesBean.getAreas();
                            if (areas != null) {
                                if (areas.size() > 0) {
                                    return areas.size();
                                } else {
                                    ToastUtil.show(mContext, "服务器,区的集合为0");
                                }
                            } else {
                                ToastUtil.show(mContext, "地区bean为空");
                            }
                        } else {
                            ToastUtil.show(mContext, "服务器,城市bean为空");
                        }
                        break;
                    default:
                        ToastUtil.show(mActivity, "程序逻辑有问题getCount():currentFlag==" + currentFlag);
                        break;
                }
            } else {
                ToastUtil.show(mContext, "服务器,地址bean为空");
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mCityBean != null) {
                switch (currentFlag) {
                    case PROVINCEFLAG://省
                        if (mData != null) {
                            if (mData.size() > position) {
                                return mData.get(position);
                            } else {
                                ToastUtil.show(mContext, "getItem()程序出问题");
                            }
                        } else {
                            ToastUtil.show(mContext, "服务器,省的集合为空");
                        }

                        break;
                    case CITYFLAG://市
                        if (cities != null) {
                            if (cities != null) {
                                if (cities.size() > position) {
                                    return cities.get(position);
                                } else {
                                    ToastUtil.show(mContext, "程序出问题");
                                }
                            } else {
                                ToastUtil.show(mContext, "服务器,市的集合为空");
                            }

                        } else {
                            ToastUtil.show(mContext, "服务器,市的集合为空");
                        }
                        break;
                    case COUNTYFLAG://区
                        if (areas != null) {
                            if (areas != null) {
                                if (areas.size() > position) {
                                    return areas.get(position);
                                } else {
                                    ToastUtil.show(mContext, "程序出问题");
                                }
                            } else {
                                ToastUtil.show(mContext, "服务器,区的集合为空");
                            }

                        } else {
                            ToastUtil.show(mContext, "服务器,区的集合为空");
                        }
                        break;
                    default:
                        ToastUtil.show(mActivity, "程序逻辑有问题:currentFlag==" + currentFlag);
                        break;
                }

            } else {
                ToastUtil.show(mContext, "服务器,地址bean为空");
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sel_address, parent, false);
                holder.item_address_tv = (TextView) convertView.findViewById(R.id.item_address_tv);
                holder.item_address_img2 = (ImageView) convertView.findViewById(R.id.item_address_img2);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /********************设置数据 start: **************************/
            switch (currentFlag) {
                case PROVINCEFLAG://省
                    AddressBean.DataBean dataBean = mData.get(position);
                    String province = dataBean.getProvince();
                    holder.item_address_tv.setText(province);
                    break;
                case CITYFLAG://市
                    AddressBean.DataBean.CitiesBean citiesBean = cities.get(position);
                    holder.item_address_tv.setText(citiesBean.getCity());
                    break;
                case COUNTYFLAG://区
                    AddressBean.DataBean.CitiesBean.AreasBean areasBean = areas.get(position);
                    holder.item_address_tv.setText(areasBean.getArea());
                    break;
                default:
                    ToastUtil.show(mActivity, "getView 程序逻辑有问题:currentFlag==" + currentFlag);
                    break;
            }

            /********************设置数据 END**************************/


            /********************设置选中标记 start: **************************/
            switch (currentFlag) {
                case PROVINCEFLAG://省
                    if (provinceIndex == position) {
                        holder.item_address_img2.setVisibility(View.VISIBLE);
                    } else {
                        holder.item_address_img2.setVisibility(View.GONE);
                    }
                    break;
                case CITYFLAG://市
                    if (cityIndex == position) {
                        holder.item_address_img2.setVisibility(View.VISIBLE);
                    } else {
                        holder.item_address_img2.setVisibility(View.GONE);
                    }
                    break;
                case COUNTYFLAG://区
                    if (countyIndex == position) {
                        holder.item_address_img2.setVisibility(View.VISIBLE);
                    } else {
                        holder.item_address_img2.setVisibility(View.GONE);
                    }
                    break;
                default:
                    ToastUtil.show(mActivity, "getView 程序逻辑有问题:currentFlag==" + currentFlag);
                    break;
            }

            /********************设置选中标记 END*****************************/

            return convertView;
        }

        public class ViewHolder {

            private ImageView item_address_img2;
            private TextView  item_address_tv;

        }
    }

}
