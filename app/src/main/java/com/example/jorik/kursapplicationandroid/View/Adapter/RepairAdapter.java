package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBinder;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.RepairDTO;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
import com.example.jorik.kursapplicationandroid.databinding.ItemBusListBinding;
import com.example.jorik.kursapplicationandroid.databinding.ItemRepairListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorik on 27.05.16.
 */

public class RepairAdapter  extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {

    Context mContext;
    List<RepairDTO> mRepairDTOList;

    public RepairAdapter(Context mContext, List<RepairDTO> list){
        this.mContext = mContext;
        mRepairDTOList = list;
    }

    @Override
    public RepairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemRepairListBinding binding = ItemRepairListBinding.inflate(inflater, parent, false);
        return new RepairViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RepairViewHolder holder, int position) {
        RepairDTO itemRepair = mRepairDTOList.get(position);
        holder.mItemRepairListBinding.setRepair(itemRepair);
    }

    @Override
    public int getItemCount() {
        return mRepairDTOList.size();
    }

    class RepairViewHolder extends RecyclerView.ViewHolder {

        ItemRepairListBinding mItemRepairListBinding;

        public RepairViewHolder(View itemView) {
            super(itemView);
            mItemRepairListBinding = DataBindingUtil.bind(itemView);
        }
    }
}
