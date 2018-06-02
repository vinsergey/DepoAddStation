package com.example.vinsergey.depoaddroutes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.example.vinsergey.depoaddroutes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String[] typeTransport = {"tram", "trolley", "bus"};
    private String[] directionTransport = {"forward", "backward"};
    private String[] dayOfWeek = {"workday", "holiday"};
    private String currentType, currentDirection;
    private static final String MY_LOG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeTransport);
        ArrayAdapter<String> directionTransportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directionTransport);

        binding.type.setAdapter(typeAdapter);
        binding.type.setSelection(0);
        binding.type.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentType = parent.getSelectedItem().toString();
                Log.d(MY_LOG, currentType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.direction.setAdapter(directionTransportAdapter);
        binding.direction.setSelection(0);
        binding.direction.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentDirection = parent.getSelectedItem().toString();
                Log.d(MY_LOG, currentDirection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
