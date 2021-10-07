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
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        Context context = this;
        initView();
        TextView nameTextView = findViewById(R.id.NameTextView);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        // nameTextView.setText(name);
    }

    private void initView() {
        inputEditText = findViewById(R.id.inputEditText);
        Button sendButton = findViewById(R.id.sendButton);
        ListView listView2 = findViewById(R.id.listView2);
        listView2.setDivider(null);
        simpleAdapter = new SimpleAdapter(this, list, R.layout.item_talking, new String[]{"message"}, new int[]{R.id.talkTextView});
        listView2.setAdapter(simpleAdapter);
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        sendMessage();
        handler.sendEmptyMessage(0);
    }

    public void sendMessage() {
        String message = inputEditText.getText().toString();
        if (message.length() > 0) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", message);
            list.add(map);
            inputEditText.setText("");
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            simpleAdapter.notifyDataSetChanged();
        }
    };
}
