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
import com.example.jorik.kursapplicationandroid.Model.Enum.ConditionClickItemAdapter;
import com.example.jorik.kursapplicationandroid.Model.Enum.ResponseData;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.POJO.BusModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateBusDataActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by jorik on 17.05.16.
 */
public class BusViewModel extends BaseViewModel {

    private static final String TAG = BusViewModel.class.getSimpleName();
    private static final int OK_RESPONSE = 200;

    private Context mContext;

    private List<BusDTO> listBusDTO;
    private ResponseData mResponseData;

    private BusService mBusService;
    private Subscription mSubscriptionGetAllBus;
    private Subscription mSubscriptionDeleteBus;

    private BusModel mBusModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public BusViewModel(Context mContext, SwipeRefreshLayout mSwipeRefresh, Rights rights) {
        this.mContext = mContext;
        this.mSwipeRefresh = mSwipeRefresh;
        mBusModel = new BusModel();
        listBusDTO = new ArrayList<>();
        mBusService = RestClient.getServiceInterface(BusService.class);
        setRightsBus(rights);
        setVisibleFABBus(View.INVISIBLE);

    }

    @Bindable
    public boolean getVisibleProgressBus() {
        return mBusModel.getVisibleProgress();
    }

    public void setVisibleProgressBus(boolean visibleProgress) {
        mBusModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressBus);
    }

    @Bindable
    public int getVisibleFABBus() {
        return mBusModel.getVisibleFAB();
    }

    public void setVisibleFABBus(int visibleFAB) {
        mBusModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABBus);
    }

    @Bindable
    public String getErrorStringBus() {
        return mBusModel.getErrorString();
    }

    public void setErrorStringBus(String errorString) {
        mBusModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringBus);
    }

    @Bindable
    public BusAdapter getBusAdapter() {
        return mBusModel.getBusAdapter();
    }

    public void setBusAdapter(BusAdapter BusAdapter) {
        mBusModel.setBusAdapter(BusAdapter);
        notifyPropertyChanged(BR.busAdapter);
    }

    @Bindable
    public Rights getRightsBus() {
        return mBusModel.getRights();
    }

    public void setRightsBus(Rights rights) {
        mBusModel.setRights(rights);
        notifyPropertyChanged(BR.rightsBus);
    }

    @Bindable
    public boolean getCompleteRequestBus() {
        return mBusModel.getCompleteRequest();
    }

    public void setCompleteRequestBus(boolean completeRequest) {
        mBusModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestBus);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDataBus() {

        mResponseData = ResponseData.ERROR;

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressBus(true);
        }
        if (listBusDTO != null) {
            listBusDTO.clear();
        }

        Observable<List<BusDTO>> BusDTOObservable = mBusService.getAllBuses();
        mSubscriptionGetAllBus = BusDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getBusId(), item2.getBusId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<BusDTO>() {
                    @Override
                    public void onCompleted() {
                        mResponseData = ResponseData.DATA;
                        hideProgressAndRefresh(mResponseData);
                        if (listBusDTO.size() == 0) {
                            mResponseData = ResponseData.EMPTY;
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        }
                        setBusAdapter(new BusAdapter(mContext, listBusDTO, ConditionClickItemAdapter.LIST));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        errorAction(e);
                    }

                    @Override
                    public void onNext(BusDTO BusDTO) {
                        listBusDTO.add(BusDTO);
                    }
                });

    }

    public void deleteBus(int id) {

        Observable<Integer> responseBusObservable = mBusService.deleteBus(listBusDTO.get(id).getBusId());
        mSubscriptionDeleteBus = responseBusObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        if (listBusDTO.size() == 0) {
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
                        deleteToast(integer == OK_RESPONSE ? mContext.getString(R.string.success_delete_bus) : mContext.getString(R.string.error_delete_bus) + integer.toString());
                    }
                });

    }

    public void unsubscribeRequest() {
        if (mSubscriptionGetAllBus != null)
            mSubscriptionGetAllBus.unsubscribe();
        if (mSubscriptionDeleteBus != null)
            mSubscriptionDeleteBus.unsubscribe();
    }

    private void deleteToast(String response) {
        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressAndRefresh(ResponseData mResponseData) {

        boolean completed = mResponseData.equals(ResponseData.DATA) || mResponseData.equals(ResponseData.EMPTY);

        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressBus(false);
        setCompleteRequestBus(mResponseData.equals(ResponseData.DATA));
        setVisibleFABBus(completed ? getRightsBus() == Rights.CHANGE ? View.VISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    private void errorAction(Throwable e){
        hideProgressAndRefresh(mResponseData);
        setErrorStringBus(e.toString() + mContext.getString(R.string.tap_for_refresh));
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateBusDataActivity.class));
    }
}