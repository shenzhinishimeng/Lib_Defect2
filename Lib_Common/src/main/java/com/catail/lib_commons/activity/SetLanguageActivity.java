package com.catail.lib_commons.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.catail.lib_commons.R;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.utils.Logger;
import com.finddreams.languagelib.LanguageType;
import com.finddreams.languagelib.MultiLanguageUtil;

/**
 * 设置语言页面
 */
public class SetLanguageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_english;
    private ImageView iv_followsystem;
    private ImageView iv_simplified_chinese;
    private ImageView iv_traditional_chinese;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Logger.e("------------------------------");
            Logger.e("handleMessage====");
            Logger.e("------------------------------");
            Intent intent = new Intent(SetLanguageActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    };


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_set_language;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(R.string.swith_language);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        RelativeLayout rl_followsytem = findViewById(R.id.rl_followsytem);
        RelativeLayout rl_simplified_chinese = findViewById(R.id.rl_simplified_chinese);
        RelativeLayout rl_traditional_chinese = findViewById(R.id.rl_traditional_chinese);
        RelativeLayout rl_english = findViewById(R.id.rl_english);
        iv_followsystem = findViewById(R.id.iv_followsystem);
        iv_english = findViewById(R.id.iv_english);
        iv_simplified_chinese = findViewById(R.id.iv_simplified_chinese);
        iv_traditional_chinese = findViewById(R.id.iv_traditional_chinese);
        rl_followsytem.setOnClickListener(this);
        rl_simplified_chinese.setOnClickListener(this);
        rl_traditional_chinese.setOnClickListener(this);
        rl_english.setOnClickListener(this);
        int savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (savedLanguageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            setFollowSytemVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            setTraditionalVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_EN) {
            setEnglishVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            setSimplifiedVisible();
        } else {
            setSimplifiedVisible();
        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int selectedLanguage = 0;
        if (id == R.id.title_bar_left_menu) {
            SetLanguageActivity.this.finish();
        } else if (id == R.id.rl_followsytem) {
            setFollowSytemVisible();
            selectedLanguage = LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        } else if (id == R.id.rl_simplified_chinese) {
            setSimplifiedVisible();
            selectedLanguage = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
        } else if (id == R.id.rl_traditional_chinese) {
            setTraditionalVisible();
            selectedLanguage = LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
        } else if (id == R.id.rl_english) {
            setEnglishVisible();
            selectedLanguage = LanguageType.LANGUAGE_EN;
        }
        Logger.e("------------------------------");
        Logger.e("selectedLanguage====" + selectedLanguage);
        Logger.e("------------------------------");
        MultiLanguageUtil.getInstance().updateLanguage(selectedLanguage);

//        handler.sendEmptyMessageDelayed(0, 200);
        Intent intent = new Intent(SetLanguageActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (selectedLanguage == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            System.exit(0);
        }
    }

    private void setSimplifiedVisible() {
        iv_followsystem.setVisibility(View.GONE);
        iv_english.setVisibility(View.GONE);
        iv_simplified_chinese.setVisibility(View.VISIBLE);
        iv_traditional_chinese.setVisibility(View.GONE);
    }

    private void setEnglishVisible() {
        iv_followsystem.setVisibility(View.GONE);
        iv_english.setVisibility(View.VISIBLE);
        iv_simplified_chinese.setVisibility(View.GONE);
        iv_traditional_chinese.setVisibility(View.GONE);
    }

    private void setTraditionalVisible() {
        iv_followsystem.setVisibility(View.GONE);
        iv_english.setVisibility(View.GONE);
        iv_simplified_chinese.setVisibility(View.GONE);
        iv_traditional_chinese.setVisibility(View.VISIBLE);
    }

    private void setFollowSytemVisible() {
        iv_followsystem.setVisibility(View.VISIBLE);
        iv_english.setVisibility(View.GONE);
        iv_simplified_chinese.setVisibility(View.GONE);
        iv_traditional_chinese.setVisibility(View.GONE);
    }
}
