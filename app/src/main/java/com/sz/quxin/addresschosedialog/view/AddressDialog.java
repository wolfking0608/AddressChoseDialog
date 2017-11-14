package com.sz.quxin.addresschosedialog.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
    private TextView                textViewStreet;
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
    private int currentFlag = -1;

    //用来存储选择的省市区;
    private String curProvince;
    private String curCity;
    private String curArea;
    private String curAreaId;
    private AddressAdapter adapter;
    private Context        mContext;

    public AddressDialog(Activity context) {
        super(context, R.style.bottom_dialog);
        mActivity = context;
    }

    public AddressDialog(Activity context, AddressBean c, OnEndSelectItemListener l) {
        super(context, R.style.bottom_dialog);
        mActivity = context;
        this.mCityBean = c;
        this.listener = l;
        mContext = context;
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
        textViewStreet = (TextView) findViewById(R.id.textViewStreet);//街道
    }

    //初始化数据
    private void initData() {
        if (mCityBean != null && mCityBean.getData() != null) {
            adapter = new AddressAdapter(mActivity, mCityBean.getData());
        }

        listView.setAdapter(adapter);
    }

    //初始化事件
    protected void initEvent() {
        listView.setOnItemClickListener(this);
        textViewProvince.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewCounty.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (currentFlag) {
            case PROVINCEFLAG:
                AddressBean.DataBean item = (AddressBean.DataBean) adapter.getItem(position);
                provinceIndex = position;
                curProvince = item.getProvince();
                break;
            case CITYFLAG:
                AddressBean.DataBean.CitiesBean citiesBean = (AddressBean.DataBean.CitiesBean) adapter.getItem(position);
                curCity = citiesBean.getCity();
                cityIndex = position;
                break;
            case COUNTYFLAG:
                AddressBean.DataBean.CitiesBean.AreasBean areasBean = (AddressBean.DataBean.CitiesBean.AreasBean) adapter.getItem(position);
                countyIndex = position;
                curArea = areasBean.getArea();
                break;
            default:
                ToastUtil.show(mActivity, "程序逻辑有问题:currentFlag==" + currentFlag);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewProvince://省
                currentFlag = PROVINCEFLAG;
                break;
            case R.id.textViewCity://市
                currentFlag = CITYFLAG;
                break;
            case R.id.textViewCounty://区
                currentFlag = COUNTYFLAG;
                listener.getItemListStr(curProvince,curCity,curArea,curAreaId,smallAreasBeanList);
                break;
            default:
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public interface OnEndSelectItemListener {

        public void getItemListStr(String province, String city, String area, String areaId, List<AddressBean.DataBean.CitiesBean.AreasBean.SmallAreasBean> mSmallArea);
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
                        ToastUtil.show(mActivity, "程序逻辑有问题:currentFlag==" + currentFlag);
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
                        if (data != null) {
                            if (data.size() > position) {
                                return data.get(position);
                            } else {
                                ToastUtil.show(mContext, "程序出问题");
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
                    AddressBean.DataBean dataBean = data.get(position);
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
