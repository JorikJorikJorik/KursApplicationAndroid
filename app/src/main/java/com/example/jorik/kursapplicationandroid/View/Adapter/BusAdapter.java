package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.databinding.ItemBusListBinding;

import java.util.List;

/**
 * Created by jorik on 17.05.16.
 */
public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private List<BusDTO> mBusDTOList;
    private Context mContext;

    public BusAdapter( Context context, List<BusDTO> list){
        mContext = context;
        mBusDTOList = list;
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemBusListBinding itemBusListBinding = ItemBusListBinding.inflate(inflater, parent, false);
        return new BusViewHolder(itemBusListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        BusDTO bus = mBusDTOList.get(position);
        holder.mItemBusListBinding.setBus(bus);
    }

    @Override
    public int getItemCount() {
        return mBusDTOList.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {

        ItemBusListBinding mItemBusListBinding;

        public BusViewHolder(View view){
            super(view);
            mItemBusListBinding = DataBindingUtil.bind(view);

        }

    }
}
