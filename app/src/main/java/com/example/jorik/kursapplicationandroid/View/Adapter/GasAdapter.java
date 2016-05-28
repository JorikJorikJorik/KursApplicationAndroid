package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.GasDTO;
import com.example.jorik.kursapplicationandroid.databinding.ItemGasListBinding;

import java.util.List;

/**
 * Created by jorik on 27.05.16.
 */

public class GasAdapter extends RecyclerView.Adapter<GasAdapter.GasViewHolder> {

    private Context mContext;
    private List<GasDTO> mGasDTOList;

    public GasAdapter(Context mContext,List<GasDTO> list){
        this.mContext = mContext;
        mGasDTOList = list;
    }

    @Override
    public GasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemGasListBinding mItemGasListBinding = ItemGasListBinding.inflate(inflater, parent, false);
        return new GasViewHolder(mItemGasListBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return mGasDTOList.size();
    }

    @Override
    public void onBindViewHolder(GasViewHolder holder, int position) {
        GasDTO mGasDTO = mGasDTOList.get(position);
        holder.mItemGasListBinding.setGas(mGasDTO);

    }

    public class GasViewHolder extends RecyclerView.ViewHolder {

        ItemGasListBinding mItemGasListBinding;

        public GasViewHolder(View itemView) {
            super(itemView);

            mItemGasListBinding = DataBindingUtil.bind(itemView);
        }
    }
}
