package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.FullGasDTO;
import com.example.jorik.kursapplicationandroid.View.Fragment.GasListFragment;
import com.example.jorik.kursapplicationandroid.databinding.ItemFullGasListBinding;

import java.util.List;

/**
 * Created by jorik on 30.05.16.
 */

public class FullGasAdapter extends RecyclerView.Adapter<FullGasAdapter.FullGasViewHolder> implements GasListFragment.AdapterFragmentCallback {

    private Context mContext;
    private List<FullGasDTO> mFullGasDTOs;

    public FullGasAdapter(Context mContext, List<FullGasDTO> mFullGasDTOs){
        this.mContext = mContext;
        this.mFullGasDTOs = mFullGasDTOs;
    }

    @Override
    public FullGasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemFullGasListBinding itemFullGasListBinding = ItemFullGasListBinding.inflate(inflater, parent, false);
        return new FullGasViewHolder(itemFullGasListBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return mFullGasDTOs.size();
    }

    @Override
    public void onBindViewHolder(FullGasViewHolder holder, int position) {
        FullGasDTO fullGasDTO = mFullGasDTOs.get(position);
        holder.mItemFullGasListBinding.setFullGas(fullGasDTO);

    }

    @Override
    public void deleteItem(int position) {
        mFullGasDTOs.remove(position);
        notifyItemRemoved(position);
    }

    public class FullGasViewHolder extends RecyclerView.ViewHolder {

        ItemFullGasListBinding mItemFullGasListBinding;

        public FullGasViewHolder(View itemView) {
            super(itemView);

            mItemFullGasListBinding = DataBindingUtil.bind(itemView);
        }
    }
}
