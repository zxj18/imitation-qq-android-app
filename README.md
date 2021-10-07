# 仿QQ安卓APP

@[toc]
写在前面

**由于本人初学阶段，写这篇博客是总结所学的知识点，为后面的进阶打好基础**

**有任何关于代码和表述问题，欢迎评论区指出**



楼主近期在学习关于安卓中`Fragment`和`ListView`中的知识，按照老师的要求模仿一下QQ界面 要求功能

- 有登录界面
  - 密码不对提示密码不对
  - 账号密码任一为空提示用户不能为空
  - 登录成功提示登录成功
  - 可以实现账号密码记住功能
- 有三个界面可以点击底部按钮实现页面的切换
  - 实现按钮选中状态和未选中状态不一样
  - 联系人界面
  - 信息界面
  - 状态界面
- 发送信息功能
  - 点击信息界面中的任意消息可以进入发消息界面
  - 可以实现点击发送按钮将所输入的文字显示在屏幕中



**提示：**本人用的**IDE开发环境**是`Android Studio` **API**是`30`

**目录结构一览**

[![2MmRG6.png](https://img-blog.csdnimg.cn/img_convert/463f3226b97ffd815d6d4c4da5bbeb79.png)](https://imgtu.com/i/2MmRG6)

**res中的文件**

[![2MmfxO.png](https://img-blog.csdnimg.cn/img_convert/92033f404494a051f627cf943902c6ab.png)](https://imgtu.com/i/2MmfxO)

## 1. 登录界面

- 登录界面的布局文件

**activity_main.xml**

所用图标都可以在阿里巴巴图标网找到[iconfont-阿里巴巴矢量图标库](https://www.iconfont.cn/)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:orientation="vertical"
        tools:ignore="UselessParent">
        <TextView
            android:drawableLeft="@drawable/qq"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="50dp"
            android:layout_marginLeft="120dp"
            android:gravity="center"
            android:text="QQ"
            android:textColor="#000000"
            android:textSize="35sp"
            app:drawableStartCompat="@drawable/qq"
            android:drawableStart="@drawable/qq"
            tools:ignore="UseCompatTextViewDrawableXml"
            android:layout_marginStart="120dp">
        </TextView>
        <EditText
            android:id="@+id/userNameEditText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="100dp"
            android:hint="请输入用户名/账号/手机号"
            android:inputType="text"
            android:padding="5dp">
        </EditText>
        <EditText
            android:id="@+id/passwordEditText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="40dp"
            android:hint="请输入密码"
            android:inputType="textPassword"
            android:padding="5dp">
        </EditText>
        <ImageButton
            android:id="@+id/loginButton"
            android:layout_marginTop="60dp"
            android:layout_gravity="center"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:src="@drawable/login"
            android:background="#fff">
        </ImageButton>
    </LinearLayout>

</RelativeLayout>
```

所对应的`MainActivity`

- 其中的判断通过if语句判断用户是否输入和输入正确
- 提示 通过`Toast`的方式提示

```java
package com.czie.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText userNameEditText, passwordEditText;
    private ImageButton loginButton;
    private ShareHelper shareHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        shareHelper=new ShareHelper(context);
        initview();
    }

    private void initview() {
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行登录页面的处理
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.length() > 0) {
                    if (username.equals("zk")) {
                        if (password.length() > 0) {
                            if (password.equals("123")) {
                                // 对账号和密码进行保存
                                shareHelper.save("username",username);
                                shareHelper.save("password",password);
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "密码不正确", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请填写密码",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,"用户名不正确",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"请填写用户名",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userNameEditText.setText(shareHelper.read("username"));
        passwordEditText.setText(shareHelper.read("password"));

    }
}
```



## 2. 记住密码功能

- 实现第一次登录成功后，再次登录会记住账号和密码的功能

这个功能我们用`SharedPreferences`

简单了解下什么是SharedPreferences

- SharedPreferences是Android平台上一个轻量级的存储辅助类，用来保存应用的一些常用配置，它提供了string，set，int，long，float，boolean六种数据类型。最终数据是以xml形式进行存储。在应用中通常做一些简单数据的持久化缓存。

```java
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
```

注意事项：

- 输入值记得提交，editor.commit();
- 在`MainACtivity`中记得在OnStart方法中使用ShareHelper.read传入需要记住的值！

预览一下成果！

![1](https://img-blog.csdnimg.cn/img_convert/0b9cf9673af809c6f33cc361e35825ac.gif)

## 3. Fragment界面跳转

这里有三个Fragment，所对应需要三个fragment布局界面

- 首先要创建3个按钮的切换xml

**message.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/message_on" android:state_selected="true"/>
    <item android:drawable="@drawable/message_off" android:state_selected="false"/>
</selector>
```

**people.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/people_on" android:state_selected="true"/>
    <item android:drawable="@drawable/people_off" android:state_selected="false"/>
</selector>
```

**statue.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/statue_on" android:state_selected="true"/>
    <item android:drawable="@drawable/statue_off" android:state_selected="false"/>
</selector>
```

然后在布局界面中使用

**activity_home.xml**

这个界面的布局可以随意发挥，本人做的比较简单，见谅

- 3个`ImageView`和1个`FrameLayout`
- `ImageView`分别绑定点击事件

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <ImageView
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:src="@drawable/basketball">
        </ImageView>
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:text="Upcoming">
        </TextView>
        
        <TextView
            android:layout_width="50dp"
            android:layout_marginLeft="250dp"
            android:layout_height="match_parent"
            android:text="+  -"
            android:textSize="25sp">
        </TextView>
    </LinearLayout>

    <FrameLayout
        android:layout_marginTop="10dp"
        android:id="@+id/frameLayout"
        android:layout_width="match_parent"
        android:layout_height="550dp">
    </FrameLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:orientation="horizontal">
    <ImageView
        android:id="@+id/messageImageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="60dp"
        android:background="@drawable/message"
        android:layout_weight="1"
        android:layout_marginStart="0dp">
    </ImageView>

    <ImageView
        android:id="@+id/peopleImageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="150dp"
        android:background="@drawable/people"
        android:layout_weight="1"
        android:layout_marginStart="150dp"
        tools:ignore="ContentDescription">
    </ImageView>

        <ImageView
            android:id="@+id/statueImageView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="170dp"
            android:background="@drawable/statue"
            android:layout_weight="1"
            android:layout_marginStart="170dp"
            tools:ignore="ContentDescription">
        </ImageView>
    </LinearLayout>
</LinearLayout>
```

### 3.1 Fragement的界面编写

**item_listview.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@drawable/backgrounds">

    <ListView
        android:id="@+id/messagelistView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>

</LinearLayout>
```

**fragment_message.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@drawable/backgrounds">

    <ListView
        android:id="@+id/messagelistView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>

</LinearLayout>
```

**fragment_people.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="30sp"
        android:textColor="#000000"
        android:text="这是联系人页面"
        tools:ignore="MissingConstraints">
    </TextView>

</LinearLayout>
```

**fragment_statue.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="30sp"
        android:textColor="#000000"
        android:text="这是动态页面"
        tools:ignore="MissingConstraints">
    </TextView>

</LinearLayout>
```

Fragment布局写完就是重点如何在`FrameLayout`中加载

我们还需要创建布局所对应的Fragment

**每个Fragment要指定加载的布局和调用的方法**

> **onCreateView()**：每次创建、绘制该Fragment的View组件时回调该方法，Fragment将会显示该方法返回的View组件。
> **onActivityCreated()**：当Fragment所在的Activity被启动完成后回调该方法。

在这个文件中需要实现

- 点击进入聊天界面
- 可以发送消息显示在屏幕上

我们通过`List`集合中存放`Map`

`Map`中所存放的Key就是所创建的`Names`数组，Value就是`Images`数组

通过创建SimpleAdapter对象来加载`fragment_message`

**讲下这三行关键的代码**

```java
//加载在哪个布局界面
messagelistView= getActivity().findViewById(R.id.messagelistView);
//适配器中的参数
/**
context 上下文
dataList 集合中存放的值
R.layout.item_listview 加载的布局界面
new String[]{"Name","Image"} String数组 
new int[]{R.id.NameTextView,R.id.ImageView} int数组 获取id
*/
        simpleAdapter=new SimpleAdapter(context,dataList,R.layout.item_listview,
                new String[]{"Name","Image"},
                new int[]{R.id.NameTextView,R.id.ImageView});
//加载适配器
        messagelistView.setAdapter(simpleAdapter);
```

一个获取数据的方法`getData()`

```java
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
```

**MessageFragment**

```java
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
```

**PeopleFragment**

```java
package com.czie.qq;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class PeopleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
```

**StatueFragment**

```java
package com.czie.qq;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class StatueFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statue,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
```

最后在`HomeActivity`中使用

了解什么是`FragmentTransaction`

> 使用Fragment时，可以通过用户交互来执行一些动作，比如增加、移除、替换等。
>
> 所有这些改变构成一个集合，这个集合被叫做一个transaction

**HomeActivity**

- 监听`ImgaeView`的点击事件
- 初始化`ImageView`的选中状态为false 通过方法传入参数从而判断哪个`ImageView`被点击 将被点击的状态设为false
- `showFragment(Fragment fragment)`中通过隐藏`Fragment`，点击`ImageView`时再判断显示哪个`Fragment`

```java
package com.czie.qq;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings({"all"})
public class HomeActivity extends Activity implements View.OnClickListener {
    private ImageView messageImageView, peopleImageView, statueImageView;
    private MessageFragment messageFragment;
    private PeopleFragment peopleFragment;
    private StatueFragment statueFragment;
    private final FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initFragment();
        showFragment(statueFragment);
    }

    private void initView() {
        messageImageView = findViewById(R.id.messageImageView);
        peopleImageView = findViewById(R.id.peopleImageView);
        statueImageView = findViewById(R.id.statueImageView);
        messageImageView.setOnClickListener(this);
        peopleImageView.setOnClickListener(this);
        statueImageView.setOnClickListener(this);
    }

    private void initFragment() {
        messageFragment = new MessageFragment();
        peopleFragment = new PeopleFragment();
        statueFragment = new StatueFragment();

        //展示
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameLayout, messageFragment);
        transaction.add(R.id.frameLayout, peopleFragment);
        transaction.add(R.id.frameLayout, statueFragment);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.messageImageView:
                init(messageImageView);
                showFragment(messageFragment);
                break;
            case R.id.peopleImageView:
                init(peopleImageView);
                showFragment(peopleFragment);
                //startActivity(new Intent(HomeActivity.this, ListViewActivity.class));
                break;
            case R.id.statueImageView:
                init(statueImageView);
                showFragment(statueFragment);
                break;
            default:
                break;

        }
    }

    public void init(ImageView imageView) {
        messageImageView.setSelected(false);
        peopleImageView.setSelected(false);
        statueImageView.setSelected(false);
        imageView.setSelected(true);
    }

    //展示指定的Fragment
    //定义当前页面
    private Fragment curFragment = new Fragment();

    public void showFragment(Fragment fragment) {
        //如果当前的fragment与传入的fragment是同一个，呢么将返回
        if (curFragment.equals(fragment)) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(messageFragment);
        transaction.hide(peopleFragment);
        transaction.hide(statueFragment);
        transaction.show(fragment);
        transaction.commit();
    }
}
```

注意事项

- 记得 `transaction`要commit

预览一下成果!

![2](https://img-blog.csdnimg.cn/img_convert/60b4443ca5dee1ca3be3cca6bfa8bf13.gif)

## 4. 聊天界面

**item_talking.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:padding="10dp"
    android:gravity="right">

    <TextView
        android:id="@+id/talkTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="300dp"
        android:text="烤肠"
        android:textSize="25sp"
        android:textColor="#000000"
        android:background="#cef"
        android:layout_marginStart="300dp">
    </TextView>

    <ImageView
        android:id="@+id/ImageView"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:background="@mipmap/kaochang">
    </ImageView>
</LinearLayout>
```

**activity_talk.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textColor="#000000"
            android:text="康小庄"
            android:id="@+id/NameTextView"
            android:background="#44cef6">
        </TextView>
    <ListView
        android:id="@+id/listView2"
        android:layout_width="match_parent"
        android:layout_height="600dp"
        android:layout_weight="1"
        />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal"
            android:layout_gravity="bottom"
            >
            <EditText
                android:id="@+id/inputEditText"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_weight="1">
            </EditText>

            <Button
                android:id="@+id/sendButton"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_weight="3"
                android:text="发送">
            </Button>
        </LinearLayout>
    </LinearLayout>
</RelativeLayout>
```

**TalkActivity**

- 通过`Handler`来实现消息发出去之后 对话框置为`null`
- 通过`simpleAdapter`实现加载到哪个布局界面的id

```java
package com.czie.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalkActivity extends Activity implements View.OnClickListener {
    private EditText inputEditText;
    private SimpleAdapter simpleAdapter;
    List<Map<String,String>> list=new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        Context context = this;
        initView();
        TextView nameTextView = findViewById(R.id.NameTextView);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        nameTextView.setText(name);
    }

    private void initView() {
        inputEditText=findViewById(R.id.inputEditText);
        Button sendButton = findViewById(R.id.sendButton);
        ListView listView2 = findViewById(R.id.listView2);
        listView2.setDivider(null);
        simpleAdapter=new SimpleAdapter(this,list,R.layout.item_talking,new String[]{"message"},new int[]{R.id.talkTextView});
        listView2.setAdapter(simpleAdapter);
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        sendMessage();
        handler.sendEmptyMessage(0);
    }

    public void sendMessage(){
        String message=inputEditText.getText().toString();
        if (message.length()>0){
            HashMap<String, String> map = new HashMap<>();
            map.put("message",message);
            list.add(map);
            inputEditText.setText("");
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            simpleAdapter.notifyDataSetChanged();
        }
    };
}
```



![3](https://img-blog.csdnimg.cn/img_convert/23a42c04426f85991ce4520ba4ae5739.gif)

**总结：**

- 学会了简单的页面跳转功能，提高思考的能力，学会如何用较少代码实现功能
- 还有许多不足仍需努力



**写在最后：**

- 有任何错误欢迎评论区指出，及时改正！
- 有问题可以加我QQ:2247830091，一起讨论