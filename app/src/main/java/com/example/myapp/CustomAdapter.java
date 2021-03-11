package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.net.URI;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    List<String> itemList;
    ImageView image;
    String name, i_price;
    String amount;

    URI imageuri;

    public CustomAdapter( List<String> item){
        this.itemList = item;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item,viewGroup,false);

        Holder holder = new Holder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.Holder holder, int i) {
        holder.textname.setText(itemList.get(i));


    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }







    public class Holder extends RecyclerView.ViewHolder {
        TextView textname;
        EditText price;
        ElegantNumberButton textamount;

        public Holder(View view) {
            super(view);
            textname = view.findViewById(R.id.tv_name);
            name = textname.getText().toString();




            price = (EditText)itemView.findViewById(R.id.et_price);
            i_price=  price.getText().toString();

            textamount =(ElegantNumberButton)itemView.findViewById(R.id.amount);
            amount= textamount.getNumber();





        }


    }


    public String getAmount() {
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
    }
}
