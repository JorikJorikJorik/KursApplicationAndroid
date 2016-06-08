package com.example.jorik.kursapplicationandroid.ViewModel.Create;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.ConditionClickItemAdapter;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateCreateWorkList;
import com.example.jorik.kursapplicationandroid.Model.POJO.CreateWorkModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.DriverDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.WorkDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.DriverService;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.WorkService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.WorkListActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.BusAdapter;
import com.example.jorik.kursapplicationandroid.View.Adapter.DriverAdapter;
import com.example.jorik.kursapplicationandroid.View.Fragment.Create.CreateWorkDataActivityFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 01.06.16.
 */

public class CreateWorkViewModel extends BaseViewModel implements BusAdapter.NextStepBusByCreateWorkCallback, DriverAdapter.NextStepDriverByCreateWorkCallback {

    private Context mContext;
    private CreateWorkModel mCreateWorkModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Fragment mFragment;

    private List<DriverDTO> mDriverDTOs;
    private List<BusDTO> mBusDTOs;

    private WorkDTO mWorkDTO;

    private Subscription mSubscriptionBus;
    private Subscription mSubscriptionDriver;
    private Subscription mSubscriptionResponse;

    private ResponseCallback mCallback;

    public CreateWorkViewModel(Context context, SwipeRefreshLayout swipeRefreshLayout, Fragment fragment) {
        mContext = context;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mFragment = fragment;
        mCreateWorkModel = new CreateWorkModel();
        mDriverDTOs = new ArrayList<>();
        mBusDTOs = new ArrayList<>();
        mWorkDTO = new WorkDTO();
        setVisibleChooseDate(View.INVISIBLE);
        changeViewByCondition(StateCreateWorkList.CHOOSE_DRIVER);
        validationDateChoose(Calendar.getInstance().getTime().getTime(), false);
    }




    @Bindable
    public RecyclerView.Adapter<?> getCreateAdapter() {
        return mCreateWorkModel.getAdapter();
    }

    public void setCreateAdapter(RecyclerView.Adapter<?> createAdapter) {
        mCreateWorkModel.setAdapter(createAdapter);
        notifyPropertyChanged(BR.createAdapter);
    }

    @Bindable
    public String getErrorString() {
        return mCreateWorkModel.getErrorString();
    }

    public void setErrorString(String errorString) {
        mCreateWorkModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorString);
    }

    @Bindable
    public StateCreateWorkList getStateCreateWorkList() {
        return mCreateWorkModel.getStateCreateWorkList();
    }

    public void setStateCreateWorkList(StateCreateWorkList stateCreateWorkList) {
        mCreateWorkModel.setStateCreateWorkList(stateCreateWorkList);
        notifyPropertyChanged(BR.stateCreateWorkList);
    }

    @Bindable
    public boolean getCompleteRequest() {
        return mCreateWorkModel.getCompleteRequest();
    }

    public void setCompleteRequest(boolean completeRequest) {
        mCreateWorkModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequest);
    }

    @Bindable
    public int getVisibleChooseDate() {
        return mCreateWorkModel.getVisibleChooseDate();
    }

    public void setVisibleChooseDate(int visibleFab) {
        mCreateWorkModel.setVisibleChooseDate(visibleFab);
        notifyPropertyChanged(BR.visibleChooseDate);
    }

    @Bindable
    public boolean getVisibleProgress() {
        return mCreateWorkModel.getVisibleProgress();
    }

    public void setVisibleProgress(boolean visibleProgress) {
        mCreateWorkModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgress);
    }

    @Bindable
    public String getTitle() {
        return mCreateWorkModel.getTitle();
    }

    public void setTitle(String title) {
        mCreateWorkModel.setTitle(title);
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public boolean getEnableSendButton() {
        return mCreateWorkModel.getEnableSendButton();
    }

    public void setEnableSendButton(boolean enableSendButton) {
        mCreateWorkModel.setEnableSendButton(enableSendButton);
        notifyPropertyChanged(BR.enableSendButton);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDriverRequest() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            setVisibleProgress(true);
        }
        if (mDriverDTOs != null) {
            mDriverDTOs.clear();
        }

        DriverService driverService = RestClient.getServiceInterface(DriverService.class);
        Observable<List<DriverDTO>> mDriverDTOObservable = driverService.getAllDrivers();
        mSubscriptionDriver = mDriverDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getDriverId(), item2.getDriverId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<DriverDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (mDriverDTOs.size() == 0) onError(new Throwable(mContext.getString(R.string.no_data)));
                        setCreateAdapter(new DriverAdapter(mContext, mDriverDTOs, ConditionClickItemAdapter.CREATE, CreateWorkViewModel.this));
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorString(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(DriverDTO driverDTO) {
                        mDriverDTOs.add(driverDTO);
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getAllBusRequest() {

        if (!mSwipeRefreshLayout.isRefreshing()) {
            setVisibleProgress(true);
        }
        if (mBusDTOs != null) {
            mBusDTOs.clear();
        }

        BusService busService = RestClient.getServiceInterface(BusService.class);
        Observable<List<BusDTO>> mBusDTOObservable = busService.getAllBuses();
        mSubscriptionBus = mBusDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getBusId(), item2.getBusId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<BusDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (mBusDTOs.size() == 0) onError(new Throwable(mContext.getString(R.string.no_data)));
                        setCreateAdapter(new BusAdapter(mContext, mBusDTOs, ConditionClickItemAdapter.CREATE, CreateWorkViewModel.this));
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressAndRefresh(false);
                        setErrorString(e.toString() + mContext.getString(R.string.tap_for_refresh));
                    }

                    @Override
                    public void onNext(BusDTO busDTO) {
                        mBusDTOs.add(busDTO);
                    }
                });
    }

    public void createWorkList() {

        mWorkDTO.setSecondnameDispatcher(ApplicationDataBase.getInstance().getSelectDataBase().getName());
        if (mFragment instanceof CreateWorkDataActivityFragment)
            mCallback = (ResponseCallback) mFragment;

        WorkService workService = RestClient.getServiceInterface(WorkService.class);
        Observable<Integer> mResponseObservable = workService.createWorkList(mWorkDTO);
        mSubscriptionResponse = mResponseObservable
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
                        mCallback.responseFromServer(integer);
                    }
                });

    }

    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        setVisibleProgress(false);
        setCompleteRequest(completed);
    }

    public void chooseRefreshList() {
        switch (getStateCreateWorkList()) {
            case CHOOSE_DRIVER:
                getAllDriverRequest();
                break;
            case CHOOSE_BUS:
                getAllBusRequest();
                break;
            case CHOOSE_DATE:
                break;
        }
    }

    public void validationDateChoose(Long date, boolean choose) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String chooseData = simpleDateFormat.format(new Date(date));
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean resultDate = currentDate != null && date >= currentDate.getTime();
        if (choose)
            Toast.makeText(mContext, resultDate ? "Choose date : " + chooseData : "Error: choose date less then current date", Toast.LENGTH_SHORT).show();
        mWorkDTO.setDateAction(resultDate ? new Date(date) : null);
        setEnableSendButton(resultDate);
    }

    private void changeViewByCondition(StateCreateWorkList mStateCreateWorkList) {
        setStateCreateWorkList(mStateCreateWorkList);
        switch (mStateCreateWorkList) {
            case CHOOSE_DRIVER:
                setTitle(mContext.getString(R.string.choose_driver));
                break;
            case CHOOSE_BUS:
                setTitle(mContext.getString(R.string.choose_bus));
                break;
            case CHOOSE_DATE:
                setTitle(mContext.getString(R.string.choose_date));
                break;
        }
    }

    public void unsubscriber() {
        if (mSubscriptionBus != null)
            mSubscriptionBus.unsubscribe();
        if (mSubscriptionDriver != null)
            mSubscriptionDriver.unsubscribe();
        if (mSubscriptionResponse != null)
            mSubscriptionResponse.unsubscribe();
    }

    @Override
    public void nextStepAfterDriver(int id) {
        mWorkDTO.setDriverId(id);
        getAllBusRequest();
        changeViewByCondition(StateCreateWorkList.CHOOSE_BUS);
    }

    @Override
    public void nextStepAfterBus(int id) {
        mWorkDTO.setBusId(id);
        setErrorString(null);
        setCompleteRequest(false);
        changeViewByCondition(StateCreateWorkList.CHOOSE_DATE);
        setVisibleChooseDate(getStateCreateWorkList() == StateCreateWorkList.CHOOSE_DATE ? View.VISIBLE : View.INVISIBLE);
    }

}
