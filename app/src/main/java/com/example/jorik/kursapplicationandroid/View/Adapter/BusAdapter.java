package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
import com.example.jorik.kursapplicationandroid.View.Fragment.BusListFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment;
import com.example.jorik.kursapplicationandroid.databinding.ItemBusListBinding;

import java.util.List;

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.*;
import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;

/**
 * Created by jorik on 17.05.16.
 */
public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> implements BusListFragment.BusFragmentCallback {

    private Context mContext;
    private List<BusDTO> mBusDTOList;

    public BusAdapter(Context mContext, List<BusDTO> list){
        this.mContext = mContext;
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
        holder.mItemBusListBinding.busCardView.setOnClickListener(v ->{
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(CHOOSE_ITEM_ID, bus.getBusId());
            mContext.startActivity(intent);
        });
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

    @Override
    public void deleteItem(int position) {
        mBusDTOList.remove(position);
        notifyItemRemoved(position);
    }


}
