package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.DataFriendsDTO;
import com.example.jorik.kursapplicationandroid.databinding.ItemFriendsListBinding;

import java.util.List;

/**
 * Created by jorik on 18.05.16.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private Context mContext;
    private List<DataFriendsDTO> mDriverDTOs;

    public FriendsAdapter(Context mContext, List<DataFriendsDTO> list) {
        this.mContext = mContext;
        mDriverDTOs = list;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemFriendsListBinding mItemFriendsListBinding = ItemFriendsListBinding.inflate(inflater, parent, false);
        return new FriendsViewHolder(mItemFriendsListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        DataFriendsDTO mDriverDto = mDriverDTOs.get(position);
        holder.mItemFriendsListBinding.setFriends(mDriverDto);
    }

    @Override
    public int getItemCount() {
        return mDriverDTOs.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {

        ItemFriendsListBinding mItemFriendsListBinding;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mItemFriendsListBinding = DataBindingUtil.bind(itemView);
        }
    }

}

