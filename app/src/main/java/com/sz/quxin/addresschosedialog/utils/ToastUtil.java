package com.sz.quxin.addresschosedialog.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast t;
	public static void show(Context context, String msg){
		if(t == null){
			t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}else{
			t.setText(msg);
			t.setDuration(Toast.LENGTH_SHORT);
		}
		t.show();
	}
}
