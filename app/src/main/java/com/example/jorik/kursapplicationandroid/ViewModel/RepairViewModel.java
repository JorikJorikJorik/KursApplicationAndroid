package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.ResponseData;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateWork;
import com.example.jorik.kursapplicationandroid.Model.POJO.RepairModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.FullRepairDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.RepairService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateRepairDataActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.RepairAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jorik on 29.05.16.
 */

public class RepairViewModel extends BaseViewModel {

    private static final String TAG = RepairViewModel.class.getSimpleName();
    private static final int OK_RESPONSE = 200;

    private Context mContext;

    private List<FullRepairDTO> listRepairDTO;

    private RepairService mRepairService;
    private Subscription mSubscriptionGetAllRepair;
    private Subscription mSubscriptionDeleteRepair;

    private ResponseData mResponseData;
    private RepairModel mRepairModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public RepairViewModel(Context mContext, SwipeRefreshLayout mSwipeRefresh, Rights rights) {
        this.mContext = mContext;
        this.mSwipeRefresh = mSwipeRefresh;
        mRepairModel = new RepairModel();
        listRepairDTO = new ArrayList<>();
        mRepairService = RestClient.getServiceInterface(RepairService.class);
        setVisibleFABRepair(View.GONE);
        setRightsRepair(rights);

    }

    @Bindable
    public boolean getVisibleProgressRepair() {
        return mRepairModel.getVisibleProgress();
    }

    public void setVisibleProgressRepair(boolean visibleProgress) {
        mRepairModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressRepair);
    }

    @Bindable
    public int getVisibleFABRepair() {
        return mRepairModel.getVisibleFAB();
    }

    public void setVisibleFABRepair(int visibleFAB) {
        mRepairModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABRepair);
    }

    @Bindable
    public String getErrorStringRepair() {
        return mRepairModel.getErrorString();
    }

    public void setErrorStringRepair(String errorString) {
        mRepairModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringRepair);
    }

    @Bindable
    public RepairAdapter getRepairAdapter() {
        return mRepairModel.getRepairAdapter();
    }

    public void setRepairAdapter(RepairAdapter RepairAdapter) {
        mRepairModel.setRepairAdapter(RepairAdapter);
        notifyPropertyChanged(BR.repairAdapter);
    }

    @Bindable
    public Rights getRightsRepair() {
        return mRepairModel.getRights();
    }

    public void setRightsRepair(Rights rights) {
        mRepairModel.setRights(rights);
        notifyPropertyChanged(BR.rightsRepair);
    }

    @Bindable
    public boolean getCompleteRequestRepair() {
        return mRepairModel.getCompleteRequest();
    }

    public void setCompleteRequestRepair(boolean completeRequest) {
        mRepairModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestRepair);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDataRepair() {

        mResponseData = ResponseData.ERROR;

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressRepair(true);
        }
        if (listRepairDTO != null) {
            listRepairDTO.clear();
        }

        Observable<List<FullRepairDTO>> repairDTOObservable = getRightsRepair() == Rights.WATCH ? mRepairService.getAllFullRepairList() : mRepairService.getAllFullRepairListForUser(ApplicationDataBase.getInstance().getSelectDataBase().getNumberUser());
        mSubscriptionGetAllRepair = repairDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getRepairDTO().getServiceListId(), item2.getRepairDTO().getServiceListId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<FullRepairDTO>() {
                    @Override
                    public void onCompleted() {
                        mResponseData = ResponseData.DATA;
                        hideProgressAndRefresh(mResponseData);
                        if (listRepairDTO.size() == 0) {
                            mResponseData = ResponseData.EMPTY;
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        }
                        setRepairAdapter(new RepairAdapter(mContext, listRepairDTO));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        errorAction(e);
                    }

                    @Override
                    public void onNext(FullRepairDTO fullRepairDTO) {
                        listRepairDTO.add(fullRepairDTO);
                    }
                });

    }

    public void deleteRepair(int id) {

        Observable<Integer> responseRepairObservable = mRepairService.deleteRepairList(listRepairDTO.get(id).getRepairDTO().getServiceListId());
        mSubscriptionDeleteRepair = responseRepairObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        if (listRepairDTO.size() == 0) {
                            mResponseData = ResponseData.EMPTY;
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        if(mResponseData == ResponseData.EMPTY) {
                            errorAction(e);
                        }
                        deleteToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        deleteToast(integer == OK_RESPONSE ? mContext.getString(R.string.success_delete_repair) : mContext.getString(R.string.error_delete_repair) + integer.toString());
                    }
                });

    }

    public void unsubscribeRequest() {
        if (mSubscriptionGetAllRepair != null)
            mSubscriptionGetAllRepair.unsubscribe();
        if (mSubscriptionDeleteRepair != null)
            mSubscriptionDeleteRepair.unsubscribe();
    }

    private void deleteToast(String response) {
        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressAndRefresh(ResponseData mResponseData) {

        boolean completed = mResponseData.equals(ResponseData.DATA) || mResponseData.equals(ResponseData.EMPTY);

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressRepair(false);
        setCompleteRequestRepair(mResponseData.equals(ResponseData.DATA));
        setVisibleFABRepair(completed ? getRightsRepair() == Rights.CHANGE ? base.getStateWork() == StateWork.WORK_TODAY ? View.VISIBLE : View.INVISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    private void errorAction(Throwable e){
        hideProgressAndRefresh(mResponseData);
        setErrorStringRepair(e.toString() + mContext.getString(R.string.tap_for_refresh));
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateRepairDataActivity.class));
    }
}
