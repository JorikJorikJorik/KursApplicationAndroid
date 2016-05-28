package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.POJO.BusModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServerRequestAllData;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.DriverService;
import com.example.jorik.kursapplicationandroid.View.Activity.CreateDataActivity;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
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
public class BusViewModel extends BaseObservable implements ServerRequestAllData {

    private static final String TAG = BusViewModel.class.getSimpleName();

    private Context mContext;
    private List<BusDTO> listBusDTO;
    private Subscription mSubscriptionGetAll;
    private Subscription mSubscriptionDelete;
    private BusModel mBusModel;
    private SwipeRefreshLayout mSwipeRefresh;
    private BusService mBusService;

    public BusViewModel(Context context, SwipeRefreshLayout swipeRefresh, Rights rights) {
        mContext = context;
        mSwipeRefresh = swipeRefresh;
        mBusModel = new BusModel();
        listBusDTO = new ArrayList<>();
        mBusService = RestClient.getServiceInterface(BusService.class);
        setRights(rights);
    }

    @Bindable
    public int getVisibleRecyclerLayout() {
        return mBusModel.getVisibleRecyclerLayout();
    }

    public void setVisibleRecyclerLayout(int visibleRefresh) {
        mBusModel.setVisibleRecyclerLayout(visibleRefresh);
        notifyPropertyChanged(BR.visibleRecyclerLayout);

    }

    @Bindable
    public int getVisibleError() {
        return mBusModel.getVisibleError();
    }

    public void setVisibleError(int visibleError) {
        mBusModel.setVisibleError(visibleError);
        notifyPropertyChanged(BR.visibleError);

    }

    @Bindable
    public int getVisibleProgress() {
        return mBusModel.getVisibleProgress();
    }

    public void setVisibleProgress(int visibleProgress) {
        mBusModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgress);
    }

    @Bindable
    public int getVisibleFAB() {
        return mBusModel.getVisibleFAB();
    }

    public void setVisibleFAB(int visibleFAB) {
        mBusModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFAB);
    }

    @Bindable
    public String getErrorString() {
        return mBusModel.getErrorString();
    }

    public void setErrorString(String errorString) {
        mBusModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorString);
    }

    @Bindable
    public BusAdapter getBusAdapter() {
        return mBusModel.getBusAdapter();
    }

    public void setBusAdapter(BusAdapter busAdapter) {
        mBusModel.setBusAdapter(busAdapter);
        notifyPropertyChanged(BR.busAdapter);
    }

    @Bindable
    public Rights getRights() {
        return mBusModel.getRights();
    }

    public void setRights(Rights rights) {
        mBusModel.setRights(rights);
        notifyPropertyChanged(BR.rights);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void getAllDataRequest() {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgress(View.VISIBLE);
        }
        setVisibleError(View.INVISIBLE);

        if (listBusDTO != null)
            listBusDTO.clear();

        Observable<List<BusDTO>> busDTOObservable = mBusService.getAllBuses();
        mSubscriptionGetAll = busDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Long.compare(item1.getBusId(), item2.getBusId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<BusDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        setBusAdapter(new BusAdapter(mContext, listBusDTO));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        hideProgressAndRefresh(false);
                        setErrorString(e.toString() + "\nTap on the text");
                    }

                    @Override
                    public void onNext(BusDTO busDTO) {
                        listBusDTO.add(busDTO);
                    }
                });

    }

    @Override
    public void deleteItemRequest(int id) {
        Observable<Integer> responseObservable = mBusService.deleteBus(listBusDTO.get(id).getBusId());
        mSubscriptionDelete = responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Toast.makeText(mContext, integer == 200 ? "Success delete item" : "Error delete item : " + integer.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void unsubscribeRequest() {
        if (mSubscriptionGetAll != null)
            mSubscriptionGetAll.unsubscribe();
        if (mSubscriptionDelete != null)
            mSubscriptionDelete.unsubscribe();

    }

    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgress(View.INVISIBLE);
        setVisibleError(completed ? View.INVISIBLE : View.VISIBLE);
        setVisibleRecyclerLayout(completed ? View.VISIBLE : View.INVISIBLE);
        setVisibleFAB(completed ? getRights() == Rights.CHANGE ?  View.VISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateDataActivity.class));
    }

}