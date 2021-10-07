package com.czie.qq;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareHelper {
    //两个功能 保存，读取
    Context context;

    public ShareHelper() {
    }

    public ShareHelper(Context context) {
        this.context = context;
    }

    //保存
    public void save(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("iot1921", Context.MODE_PRIVATE);
        //创建一个输入值
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //读取数据
    public String read(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("iot1921", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
