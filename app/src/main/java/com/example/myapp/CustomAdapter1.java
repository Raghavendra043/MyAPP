package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.Holder> {
    private List<Reminder> itemList;
    Reminder temprem;
    // String event, time;
    //i_price;
    //  String amount;
    public CustomAdapter1( List<Reminder> item){
        this.itemList = item;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item1,viewGroup,false);

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter1.Holder holder, int i) {
        temprem = itemList.get(i);
        holder.textView.setText(temprem.getEventName());
        holder.textView1.setText(temprem.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textView, textView1;


        public Holder(View view) {
            super(view);

            textView = view.findViewById(R.id.ev_name);
            textView1 = view.findViewById(R.id.time);

        }

    }

/*    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getI_price() {
        return i_price;
    }

    public void setI_price(String i_price) {
        this.i_price = i_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
