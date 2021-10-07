package com.czie.qq;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFragment extends Fragment {


    private Context context;
    private ListView messagelistView;


    List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
    String [] Names={"一号","二号","三号","四号","五号","六号","七号"};
    int [] Images={R.mipmap.kaochang,R.mipmap.kaojitui,R.mipmap.kaojichi,R.mipmap.kaoqiezi,R.mipmap.kaolajiao,R.mipmap.kaojinzhengu ,R.mipmap.kaonanguabing};

    //创建SimpleAdapter对象
    private SimpleAdapter simpleAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        initView();
    }
    private void initView() {
        messagelistView= getActivity().findViewById(R.id.messagelistView);
        getData();
        simpleAdapter=new SimpleAdapter(context,dataList,R.layout.item_listview,
                new String[]{"Name","Image"},
                new int[]{R.id.NameTextView,R.id.ImageView});
        messagelistView.setAdapter(simpleAdapter);
        //listView的项点击监听事件
        messagelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获取当前项的数据
                String name=dataList.get(i).get("Name").toString();
                Intent intent=new Intent(getActivity(),TalkActivity.class);
                startActivity(new Intent(getActivity(), TalkActivity.class));
                intent.putExtra("name",name);
                Toast.makeText(context,"当前点击的选项名称是"+name,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //list获取数据
    public void getData(){
        //遍历数组中的数据 添加到list中
        for (int i = 0; i < Names.length; i++) {
            //新建map 存放数组中的数据 最后存放在list中
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Name",Names[i]);
            map.put("Image",Images[i]);
            dataList.add(map);
        }
    }
}
