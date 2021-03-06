package com.example.pavel.loadmore.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.model.LaunchModel.Payload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pavel on 25.02.18.
 */

public class PayloadAdapter extends RecyclerView.Adapter<PayloadAdapter.customViewHolder>{
    ArrayList<Payload> payloads;
    Context context;

    public PayloadAdapter(ArrayList<Payload> payloads, Context context) {
        this.payloads = payloads;
        this.context = context;
    }

    @Override
    public customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_payload,parent,false);
        return new customViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PayloadAdapter.customViewHolder holder, int position) {
        if (payloads.get(position).getPayloadId()!=null){
            holder.payloadId.setText("id: "+payloads.get(position).getPayloadId());
        }
        else{
            holder.payloadId.setText("id: "+R.string.not_found);
        }

        if (payloads.get(position).getPayloadType()!=null){
            holder.type.setText("type: "+payloads.get(position).getPayloadType());
        }
        else{
            holder.type.setText("type: "+R.string.not_found);
        }
        if(payloads.get(position).getOrbit()!=null){
            holder.orbit.setText("orbit: "+payloads.get(position).getOrbit());
        }
        else{
            holder.orbit.setText("orbit: "+R.string.not_found);
        }
    }

    @Override
    public int getItemCount() {
        return payloads.size();
    }
    public class customViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.payloadId) TextView payloadId;
        @BindView(R.id.payloadType) TextView type;
        @BindView(R.id.payloadOrbit) TextView orbit;

        public customViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
