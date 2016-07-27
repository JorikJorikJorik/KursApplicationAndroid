package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.POJO.DetailsItemModel;
import com.example.jorik.kursapplicationandroid.databinding.ItemDetailsBinding;

import java.util.List;

/**
 * Created by jorik on 20.07.16.
 */

public class DriverDetailsAdapter extends RecyclerView.Adapter<DriverDetailsAdapter.DriverDetailsViewHolder> {

    Context mContext;
    List<DetailsItemModel> mDetailsItemModel;

    public DriverDetailsAdapter(Context context, List<DetailsItemModel> detailsItemModelList){
        mContext = context;
        mDetailsItemModel = detailsItemModelList;
    }

    @Override
    public DriverDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ItemDetailsBinding itemDetailsBinding = ItemDetailsBinding.inflate(layoutInflater, parent, false);
        return new DriverDetailsViewHolder(itemDetailsBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return mDetailsItemModel.size();
    }

    @Override
    public void onBindViewHolder(DriverDetailsViewHolder holder, int position) {
        DetailsItemModel itemModel = mDetailsItemModel.get(position);
        holder.mItemDetailsBinding.setDetailsItem(itemModel);
    }

    public class DriverDetailsViewHolder extends RecyclerView.ViewHolder{

        ItemDetailsBinding mItemDetailsBinding;

        public DriverDetailsViewHolder(View itemView) {
            super(itemView);

            mItemDetailsBinding = DataBindingUtil.bind(itemView);
        }
    }
}
