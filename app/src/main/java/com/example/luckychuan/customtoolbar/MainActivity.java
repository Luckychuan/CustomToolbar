package com.example.luckychuan.customtoolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomToolbar toolbar = (CustomToolbar) findViewById(R.id.customToolbar);

        toolbar.setOnBackButtonClickListener(new CustomToolbar.OnBackButtonClickListener() {
            @Override
            public void onBackButtonClick() {
                Toast.makeText(MainActivity.this, "点击了backButton", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setOnMenuItemClickListener(new CustomToolbar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int itemId) {
                if(itemId == R.id.item1){
                    Toast.makeText(MainActivity.this, "点击了item1", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
