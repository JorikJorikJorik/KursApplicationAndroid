package com.example.jorik.kursapplicationandroid.View.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorik.kursapplicationandroid.Network.DTO.FullWorkDTO;
import com.example.jorik.kursapplicationandroid.View.Activity.WorkListActivity;
import com.example.jorik.kursapplicationandroid.View.Fragment.Create.CreateWorkDataActivityFragment;
import com.example.jorik.kursapplicationandroid.View.Fragment.WorkListFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.Create.CreateWorkViewModel;
import com.example.jorik.kursapplicationandroid.ViewModel.WorkViewModel;
import com.example.jorik.kursapplicationandroid.databinding.ItemWorkListBinding;

import java.util.List;

/**
 * Created by jorik on 01.06.16.
 */

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.WorkListViewHolder> implements WorkListFragment.AdapterFragmentCallback {

    private Context mContext;
    private List<FullWorkDTO> mFullWorkDTOs;
    private WorkItemCallback mCallback;
    private WorkViewModel mWorkViewModel;

    public WorkListAdapter(Context context, List<FullWorkDTO> fullWorkDTOs, WorkViewModel viewModel) {
        mContext = context;
        mFullWorkDTOs = fullWorkDTOs;
        mWorkViewModel = viewModel;
    }

    @Override
    public WorkListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemWorkListBinding itemWorkListBinding = ItemWorkListBinding.inflate(inflater, parent, false);
        return new WorkListViewHolder(itemWorkListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(WorkListViewHolder holder, int position) {
        FullWorkDTO fullWorkDTO = mFullWorkDTOs.get(position);
        holder.mItemWorkListBinding.setFullWork(fullWorkDTO);

        holder.mItemWorkListBinding.workCard.setOnClickListener(v -> {
            mCallback = (WorkItemCallback) mWorkViewModel;
            mCallback.itemAction(mFullWorkDTOs.get(position).getWorkDTO().getBusId(), mFullWorkDTOs.get(position).getWorkDTO().getDriverId());});
    }

    @Override
    public int getItemCount() {
        return mFullWorkDTOs.size();
    }

    @Override
    public void deleteItem(int position) {
        mFullWorkDTOs.remove(position);
        notifyItemRemoved(position);
    }

    public class WorkListViewHolder extends RecyclerView.ViewHolder {

        private ItemWorkListBinding mItemWorkListBinding;

        public WorkListViewHolder(View itemView) {
            super(itemView);

            mItemWorkListBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface WorkItemCallback{
        public void itemAction(int busId, int driverId);
    }

}
