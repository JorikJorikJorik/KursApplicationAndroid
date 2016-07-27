package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.DataFriendsDTO;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
import com.example.jorik.kursapplicationandroid.databinding.ItemPhotoListBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;
import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.LIST_PHOTO;

/**
 * Created by jorik on 27.05.16.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context mContext;
    private List<DataFriendsDTO> mPhotoDTOList;

    public PhotoAdapter(Context mContext, List<DataFriendsDTO> list) {
        this.mContext = mContext;
        mPhotoDTOList = list;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemPhotoListBinding mItemPhotoListBinding = ItemPhotoListBinding.inflate(inflater, parent, false);
        return new PhotoViewHolder(mItemPhotoListBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return mPhotoDTOList.size();
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        DataFriendsDTO mPhotoDTO = mPhotoDTOList.get(position);
        holder.mItemPhotoListBinding.setFriends(mPhotoDTO);
        holder.mItemPhotoListBinding.cardPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putStringArrayListExtra(LIST_PHOTO, getOnlyPhotoFromFriendsData(mPhotoDTOList));
            intent.putExtra(CHOOSE_ITEM_ID, position);
            mContext.startActivity(intent);
        });
    }

    private ArrayList<String> getOnlyPhotoFromFriendsData(List<DataFriendsDTO> photoDTOList) {
        ArrayList<String> data = new ArrayList<>();
        for (DataFriendsDTO item : photoDTOList) {
            data.add(item.getPicture().getDataPictureDTO().getUrl());
        }
        return data;
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ItemPhotoListBinding mItemPhotoListBinding;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            mItemPhotoListBinding = DataBindingUtil.bind(itemView);
        }
    }
}
