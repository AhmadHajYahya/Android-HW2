package com.ahmad.ahmadhajyahya_hw2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ahmad.ahmadhajyahya_hw2.fragments.Fragment_List;
import com.ahmad.ahmadhajyahya_hw2.fragments.Fragment_Map;
import com.ahmad.ahmadhajyahya_hw2.interfaces.CallBack_List;
import com.google.android.material.textview.MaterialTextView;

public class RecordsActivity extends AppCompatActivity {
    private Fragment_List fragmentList;
    private Fragment_Map fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        fragmentList = new Fragment_List();
        fragmentMap = new Fragment_Map();

        fragmentList.setCallBackList(new CallBack_List() {
            @Override
            public void showLocationInMap(double lat, double lon) {
                fragmentMap.setLocation(lat, lon);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_LAY_top, fragmentList)
                .add(R.id.main_LAY_bottom, fragmentMap)
                .commit();

    }

}