package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.databinding.ItemDriverListBinding;

import java.util.List;

/**
 * Created by jorik on 18.05.16.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private Context mContext;
    private List<DriverDTO> mDriverDTOs;

    public  DriverAdapter(Context mContext, List<DriverDTO> list){
        this.mContext = mContext;
        mDriverDTOs = list;
    }

    @Override
    public DriverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemDriverListBinding mItemDriverListBinding = ItemDriverListBinding.inflate(inflater, parent, false);
        return new DriverViewHolder(mItemDriverListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(DriverViewHolder holder, int position) {
        DriverDTO mDriverDto = mDriverDTOs.get(position);
        holder.mItemDriverListBinding.setDriver(mDriverDto);
    }

    @Override
    public int getItemCount() {
        return mDriverDTOs.size();
    }

    public class DriverViewHolder extends RecyclerView.ViewHolder {

        ItemDriverListBinding mItemDriverListBinding;

        public DriverViewHolder(View itemView) {
            super(itemView);
            mItemDriverListBinding = DataBindingUtil.bind(itemView);
        }
    }
}

