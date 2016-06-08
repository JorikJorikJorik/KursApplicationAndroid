package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Model.Enum.ConditionClickItemAdapter;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.POJO.DriverModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.DriverService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateBusDataActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;

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

public class DriverViewModel extends BaseObservable {

    private static final String TAG = DriverViewModel.class.getSimpleName();
    private static final int OK_RESPONSE = 200;

    private Context mContext;

    private List<DriverDTO> listDriverDTO;

    private DriverService mDriverService;
    private Subscription mSubscriptionGetAllDriver;
    private Subscription mSubscriptionDeleteDriver;

    private DriverModel mDriverModel;
    private SwipeRefreshLayout mSwipeRefresh;

    public DriverViewModel(Context mContext, SwipeRefreshLayout mSwipeRefresh, Rights rights) {
        this.mContext = mContext;
        this.mSwipeRefresh = mSwipeRefresh;
        mDriverModel = new DriverModel();
        listDriverDTO = new ArrayList<>();
        mDriverService = RestClient.getServiceInterface(DriverService.class);
        setRightsDriver(rights);
        setVisibleFABDriver(View.INVISIBLE);

    }

    @Bindable
    public boolean getVisibleProgressDriver() {
        return mDriverModel.getVisibleProgress();
    }

    public void setVisibleProgressDriver(boolean visibleProgress) {
        mDriverModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressDriver);
    }

    @Bindable
    public int getVisibleFABDriver() {
        return mDriverModel.getVisibleFAB();
    }

    public void setVisibleFABDriver(int visibleFAB) {
        mDriverModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABDriver);
    }

    @Bindable
    public String getErrorStringDriver() {
        return mDriverModel.getErrorString();
    }

    public void setErrorStringDriver(String errorString) {
        mDriverModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringDriver);
    }

    @Bindable
    public DriverAdapter getDriverAdapter() {
        return mDriverModel.getDriverAdapter();
    }

    public void setDriverAdapter(DriverAdapter driverAdapter) {
        mDriverModel.setDriverAdapter(driverAdapter);
        notifyPropertyChanged(BR.driverAdapter);
    }

    @Bindable
    public Rights getRightsDriver() {
        return mDriverModel.getRights();
    }

    public void setRightsDriver(Rights rights) {
        mDriverModel.setRights(rights);
        notifyPropertyChanged(BR.rightsDriver);
    }

    @Bindable
    public boolean getCompleteRequestDriver() {
        return mDriverModel.getCompleteRequest();
    }

    public void setCompleteRequestDriver(boolean completeRequest) {
        mDriverModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestDriver);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDataDriver() {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressDriver(true);
        }
        if (listDriverDTO != null) {
            listDriverDTO.clear();
        }

        Observable<List<DriverDTO>> driverDTOObservable = mDriverService.getAllDrivers();
        mSubscriptionGetAllDriver = driverDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getDriverId(), item2.getDriverId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<DriverDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (listDriverDTO.size() == 0)
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        setDriverAdapter(new DriverAdapter(mContext, listDriverDTO, ConditionClickItemAdapter.LIST));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        hideProgressAndRefresh(false);
                        setErrorStringDriver(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(DriverDTO driverDTO) {
                        listDriverDTO.add(driverDTO);
                    }
                });

    }

    public void deleteDriver(int id) {

        Observable<Integer> responseDriverObservable = mDriverService.deleteDriver(listDriverDTO.get(id).getDriverId());
        mSubscriptionDeleteDriver = responseDriverObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        deleteToast(e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        deleteToast(integer == OK_RESPONSE ? mContext.getString(R.string.success_delete_driver) : mContext.getString(R.string.error_delete_driver) + integer.toString());
                    }
                });

    }

    public void unsubscribeRequest() {
        if (mSubscriptionGetAllDriver != null)
            mSubscriptionGetAllDriver.unsubscribe();
        if (mSubscriptionDeleteDriver != null)
            mSubscriptionDeleteDriver.unsubscribe();
    }

    private void deleteToast(String response) {
        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressDriver(false);
        setCompleteRequestDriver(completed);
        setVisibleFABDriver(completed ? getRightsDriver() == Rights.CHANGE ? View.VISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateBusDataActivity.class));
    }

}
