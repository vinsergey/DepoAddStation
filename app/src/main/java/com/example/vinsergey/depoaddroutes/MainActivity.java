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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private String[] typeTransport = {"tram", "trolley", "bus"};
    private String[] directionTransport = {"forward", "backward"};
    private String currentType, currentDirection;
    private static final String WORK = "work";
    private static final String HOLI = "holi";
    //private static final String MY_LOG = "MyLog";
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
//        stationsWorkday.add("6:00");
//        stationsWorkday.add("6:12");
//        stationsWorkday.add("6:18");
        stationsHoliday = new ArrayList<>();
//        stationsHoliday.add("6:30");
//        stationsHoliday.add("6:40");
//        stationsHoliday.add("6:50");

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
        adapterWorkday = new RecyclerViewAdapter(this, WORK);
        binding.recyclerViewWorkday.setAdapter(adapterWorkday);

        binding.recyclerViewHoliday.setLayoutManager(new LinearLayoutManager(this));
        adapterHoliday = new RecyclerViewAdapter(this, HOLI);
        binding.recyclerViewHoliday.setAdapter(adapterHoliday);

        binding.btnAddTimeWorkday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = binding.timeStationWorkday.getText().toString();
                stationsWorkday.add(station);
                //Log.d(MY_LOG, String.valueOf(dataWorkday.size()));
                adapterWorkday.setData(stationsWorkday);
                binding.recyclerViewWorkday.smoothScrollToPosition(adapterWorkday.getItemCount() - 1);
            }
        });

        binding.btnAddTimeHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = binding.timeStationHoliday.getText().toString();
                stationsHoliday.add(station);
                //Log.d(MY_LOG, String.valueOf(dataWorkday.size()));
                adapterHoliday.setData(stationsHoliday);
                binding.recyclerViewHoliday.smoothScrollToPosition(adapterHoliday.getItemCount() - 1);
            }
        });

        binding.btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeRoute();
            }
        });
    }

    private void writeRoute() {
        Route route = new Route();
        String nameRoute = binding.nameRoute.getText().toString();
        String id_station = binding.idStation.getText().toString();
        route.nameStation = binding.nameStation.getText().toString();
        route.workday = stationsWorkday;
        route.holiday = stationsHoliday;

        database.child(currentType).child(nameRoute).child(currentDirection).child(id_station).setValue(route);
    }

    @Override
    public void onClick(View v) {
        RecyclerViewAdapter.Test test = (RecyclerViewAdapter.Test) v.getTag();
//        Integer test = (Integer) v.getTag(14);
//        String test2 = (String) v.getTag(13);
//        Log.d(MY_LOG, String.valueOf(test));
        //Log.d(MY_LOG, String.valueOf(test.position + " " + test.name));

        if (test.name.equals(WORK)) {
            stationsWorkday.remove(test.position);
            adapterWorkday.setData(stationsWorkday);
        }
        if (test.name.equals(HOLI)) {
            stationsHoliday.remove(test.position);
            adapterHoliday.setData(stationsHoliday);
        }
    }
}
