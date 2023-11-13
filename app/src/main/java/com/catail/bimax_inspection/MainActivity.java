package com.catail.bimax_inspection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.catail.bimax_defect2.f_defect2.activity.Defect2ApplyActivity;
import com.catail.bimax_defect2.f_defect2.activity.Defect2StatisticsHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button bt_open_defect = findViewById(R.id.bt_open_defect);
        bt_open_defect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Defect2StatisticsHomeActivity.class);
                startActivity(intent);
            }
        });
    }


}