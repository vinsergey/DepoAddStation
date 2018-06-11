package com.example.vinsergey.depoaddroutes;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> data = Collections.emptyList();
    private View.OnClickListener listener;
    private String name;

    RecyclerViewAdapter(View.OnClickListener listener, String name) {
        this.listener = listener;
        this.name = name;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Test test = new Test();
        test.name = name;
        test.position = position;

        //      holder.itemView.setTag(13, name);
        holder.time.setText(data.get(position));
        holder.itemView.setOnClickListener(listener);
// /       holder.itemView.setTag(14, position);'
        holder.itemView.setTag(test);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_item);
        }
    }

    class Test {
        int position;
        String name;
    }
}
