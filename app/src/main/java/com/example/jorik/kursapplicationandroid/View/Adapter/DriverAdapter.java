package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Model.Enum.ConditionClickItemAdapter;
import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
import com.example.jorik.kursapplicationandroid.View.Fragment.BusListFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.DriverListFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.Create.CreateWorkViewModel;
import com.example.jorik.kursapplicationandroid.databinding.ItemDriverListBinding;

import java.util.List;

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;
import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.KIND_DETAILS;

/**
 * Created by jorik on 18.05.16.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> implements DriverListFragment.AdapterFragmentCallback {

    private Context mContext;
    private List<DriverDTO> mDriverDTOs;
    private ConditionClickItemAdapter mConditionClickItemAdapter;
    private NextStepDriverByCreateWorkCallback mCallback;
    private CreateWorkViewModel mCreateWorkViewModel;

    public DriverAdapter(Context mContext, List<DriverDTO> list, ConditionClickItemAdapter conditionClickItemAdapter){
        this.mContext = mContext;
        mDriverDTOs = list;
        mConditionClickItemAdapter = conditionClickItemAdapter;
    }

    public  DriverAdapter(Context mContext, List<DriverDTO> list, ConditionClickItemAdapter conditionClickItemAdapter, CreateWorkViewModel mViewModel){
        this.mContext = mContext;
        mDriverDTOs = list;
        mConditionClickItemAdapter = conditionClickItemAdapter;
        mCreateWorkViewModel = mViewModel;
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
        holder.mItemDriverListBinding.driverCardView.setOnClickListener(v -> {
            chooseWorkWithItemList(mDriverDto.getDriverId());
        });
    }

    @Override
    public int getItemCount() {
        return mDriverDTOs.size();
    }

    @Override
    public void deleteItem(int position) {
        mDriverDTOs.remove(position);
        notifyItemRemoved(position);
    }

    public class DriverViewHolder extends RecyclerView.ViewHolder {

        ItemDriverListBinding mItemDriverListBinding;

        public DriverViewHolder(View itemView) {
            super(itemView);
            mItemDriverListBinding = DataBindingUtil.bind(itemView);
        }
    }

    private void chooseWorkWithItemList(Integer driverId){
        switch (mConditionClickItemAdapter){
            case DETAILS:
                break;

            case LIST: moveToDetails(driverId);
                break;

            case CREATE:
                mCallback = (NextStepDriverByCreateWorkCallback) mCreateWorkViewModel;
                mCallback.nextStepAfterDriver(driverId);
                break;
        }
    }

    private void moveToDetails(int driverId){
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra(CHOOSE_ITEM_ID, driverId);
        intent.putExtra(KIND_DETAILS, KindDataBase.DRIVER.getValue());
        mContext.startActivity(intent);
    }

    public interface NextStepDriverByCreateWorkCallback {
        void nextStepAfterDriver(int id);
    }

}

