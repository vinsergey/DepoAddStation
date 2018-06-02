package com.example.vinsergey.depoaddroutes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.example.vinsergey.depoaddroutes.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String[] typeTransport = {"tram", "trolley", "bus"};
    private String[] directionTransport = {"forward", "backward"};
    private String[] dayOfWeek = {"workday", "holiday"};
    private String currentType, currentDirection;
    private static final String MY_LOG = "MyLog";
    private RecyclerViewAdapter adapterWorkday, adapterHoliday;
    private List<String> stationsWorkday, stationsHoliday;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeTransport);
        ArrayAdapter<String> directionTransportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directionTransport);
        database = FirebaseDatabase.getInstance().getReference().child("routes");

        stationsWorkday = new ArrayList<>();
        stationsHoliday = new ArrayList<>();

        binding.type.setAdapter(typeAdapter);
        binding.type.setSelection(0);
        binding.type.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentType = parent.getSelectedItem().toString();
                //Log.d(MY_LOG, currentType);
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
                //Log.d(MY_LOG, currentDirection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.recyclerViewWorkday.setLayoutManager(new LinearLayoutManager(this));
        adapterWorkday = new RecyclerViewAdapter();
        binding.recyclerViewWorkday.setAdapter(adapterWorkday);

        binding.recyclerViewHoliday.setLayoutManager(new LinearLayoutManager(this));
        adapterHoliday = new RecyclerViewAdapter();
        binding.recyclerViewHoliday.setAdapter(adapterHoliday);

        binding.btnAddTimeWorkday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = binding.timeStationWorkday.getText().toString();
                stationsWorkday.add(station);
                //Log.d(MY_LOG, String.valueOf(dataWorkday.size()));
                adapterWorkday.setData(stationsWorkday);
            }
        });

        binding.btnAddTimeHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = binding.timeStationHoliday.getText().toString();
                stationsHoliday.add(station);
                //Log.d(MY_LOG, String.valueOf(dataWorkday.size()));
                adapterHoliday.setData(stationsHoliday);
            }
        });

        binding.btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeRoute();
                //writeTest();
            }
        });
    }

    private void writeRoute() {
        String id = binding.idRoute.getText().toString();
        Route route = new Route();
        route.name_route = binding.nameRoute.getText().toString();
        route.type = currentType;
        route.direction.name_station = binding.nameStation.getText().toString();
        route.direction.workday = stationsWorkday;
        route.direction.holiday = stationsHoliday;

        database.child(id).child(currentDirection).setValue(route);
    }

    private void writeTest() {
        List<String> list = new ArrayList<>();
        list.add("Sergey");
        list.add("Olya");
        list.add("Vasya");
        database.setValue(list);
    }
}
