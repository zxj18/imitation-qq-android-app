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
