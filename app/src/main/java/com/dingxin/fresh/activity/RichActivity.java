package com.dingxin.fresh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dingxin.fresh.R;
import com.yanzhenjie.sofia.Sofia;
import com.zzhoujay.richtext.RichText;

public class RichActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_3;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich);
        Sofia.with(this).invasionStatusBar().statusBarBackground(R.color.color_orange_2).statusBarBackgroundAlpha(0);
        img = findViewById(R.id.img);
        tv_3 = findViewById(R.id.tv_3);
        img.setOnClickListener(this);
        RichText.from(getIntent().getStringExtra("rich")).bind(this).into(tv_3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
                finish();
                break;
        }
    }
}
