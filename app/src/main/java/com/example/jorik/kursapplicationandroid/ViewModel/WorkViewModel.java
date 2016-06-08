package com.example.jorik.kursapplicationandroid.ViewModel;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.BrodcastReciver.NotificationDriverAboutWork;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.KindDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateWork;
import com.example.jorik.kursapplicationandroid.Model.POJO.WorkModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.DTO.FullWorkDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.WorkService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.Create.CreateWorkDataActivity;
import com.example.jorik.kursapplicationandroid.View.Activity.DetailsActivity;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;
import com.example.jorik.kursapplicationandroid.View.Adapter.WorkListAdapter;

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

import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.CHOOSE_ITEM_ID;
import static com.example.jorik.kursapplicationandroid.View.Fragment.DetailsActivityFragment.KIND_DETAILS;

/**
 * Created by jorik on 01.06.16.
 */

public class WorkViewModel extends BaseViewModel implements WorkListAdapter.WorkItemCallback {

    public static final String INFO_FOR_BROADCAST = "bus_info_for_broadcast";
    private static final int TWHO_DAYS = 172800000;
    private static final String TAG = WorkViewModel.class.getSimpleName();
    private static final int OK_RESPONSE = 200;

    private Context mContext;
    private WorkModel mWorkModel;
    private WorkService mWorkService;
    private List<FullWorkDTO> mFullWorkDTOs;
    private BottomSheetBehavior mBottomSheetBehavior;

    private Subscription mSubscriptionWorkGet;
    private Subscription mSubscriptionWorkDelete;
    private Subscription mSubscriptionAlarmManager;

    private SwipeRefreshLayout mSwipeRefresh;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public WorkViewModel(Context context, SwipeRefreshLayout swipeRefresh, Rights mRights, BottomSheetBehavior bottomSheetBehavior) {
        mContext = context;
        mSwipeRefresh = swipeRefresh;
        mWorkModel = new WorkModel();
        mFullWorkDTOs = new ArrayList<>();
        mBottomSheetBehavior = bottomSheetBehavior;
        mWorkService = RestClient.getServiceInterface(WorkService.class);
        setRightsWork(mRights);
        setVisibleFABWork(View.INVISIBLE);
    }

    @Bindable
    public boolean getVisibleProgressWork() {
        return mWorkModel.getVisibleProgress();
    }

    public void setVisibleProgressWork(boolean visibleProgress) {
        mWorkModel.setVisibleProgress(visibleProgress);
        notifyPropertyChanged(BR.visibleProgressWork);
    }

    @Bindable
    public String getErrorStringWork() {
        return mWorkModel.getErrorString();
    }

    public void setErrorStringWork(String errorString) {
        mWorkModel.setErrorString(errorString);
        notifyPropertyChanged(BR.errorStringWork);
    }

    @Bindable
    public Rights getRightsWork() {
        return mWorkModel.getRights();
    }

    public void setRightsWork(Rights rights) {
        mWorkModel.setRights(rights);
        notifyPropertyChanged(BR.rightsWork);
    }

    @Bindable
    public int getVisibleFABWork() {
        return mWorkModel.getVisibleFAB();
    }

    public void setVisibleFABWork(int visibleFAB) {
        mWorkModel.setVisibleFAB(visibleFAB);
        notifyPropertyChanged(BR.visibleFABWork);
    }

    @Bindable
    public boolean getCompleteRequestWork() {
        return mWorkModel.getCompleteRequest();
    }

    public void setCompleteRequestWork(boolean completeRequest) {
        mWorkModel.setCompleteRequest(completeRequest);
        notifyPropertyChanged(BR.completeRequestWork);
    }

    @Bindable
    public WorkListAdapter getFullAdapterWork() {
        return mWorkModel.getFullWorkAdapter();
    }

    public void setFullAdapterWork(WorkListAdapter workAdapter) {
        mWorkModel.setFullWorkAdapter(workAdapter);
        notifyPropertyChanged(BR.fullAdapterWork);
    }

    public int getDriverIdBottomSheet() {
        return mWorkModel.getDriverIdBottomSheet();
    }

    public void setDriverIdBottomSheet(int positionBottomSheet) {
        mWorkModel.setDriverIdBottomSheet(positionBottomSheet);
    }

    public int getBusIdBottomSheet() {
        return mWorkModel.getBusIdBottomSheet();
    }

    public void setBusIdBottomSheet(int positionBottomSheet) {
        mWorkModel.setBusIdBottomSheet(positionBottomSheet);
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void getAllDataWork() {

        if (!mSwipeRefresh.isRefreshing()) {
            setVisibleProgressWork(true);
        }
        if (mFullWorkDTOs != null) {
            mFullWorkDTOs.clear();
        }

        Observable<List<FullWorkDTO>> workDTOObservable = mWorkService.getAllWorkList();
        mSubscriptionWorkGet = workDTOObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .toSortedList((item1, item2) -> Integer.compare(item1.getWorkDTO().getWorkListId(), item2.getWorkDTO().getWorkListId()))
                .flatMap(Observable::from)
                .subscribe(new Subscriber<FullWorkDTO>() {
                    @Override
                    public void onCompleted() {
                        hideProgressAndRefresh(true);
                        if (mFullWorkDTOs.size() == 0)
                            onError(new Throwable(mContext.getString(R.string.no_data)));
                        setFullAdapterWork(new WorkListAdapter(mContext, mFullWorkDTOs, WorkViewModel.this));
                        makeStateWork();
                        makeAlarmManager();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        hideProgressAndRefresh(false);
                        setErrorStringWork(e.toString() + mContext.getString(R.string.tap_for_refresh));
                        makeAlarmManager();
                    }

                    @Override
                    public void onNext(FullWorkDTO fullWorkDTOs) {
                        mFullWorkDTOs.add(fullWorkDTOs);
                    }
                });

    }

    public void deleteWork(int id) {
        Observable<Integer> requestDelete = mWorkService.deleteWorkList(mFullWorkDTOs.get(id).getWorkDTO().getWorkListId());
        mSubscriptionWorkDelete = requestDelete
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

    private void deleteToast(String response) {
        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressAndRefresh(boolean completed) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        setVisibleProgressWork(false);
        setCompleteRequestWork(completed);
        setVisibleFABWork(completed ? getRightsWork() == Rights.CHANGE ? View.VISIBLE : View.INVISIBLE : View.INVISIBLE);
    }

    public void moveToCreateActivity() {
        mContext.startActivity(new Intent(mContext, CreateWorkDataActivity.class));
    }

    public void moveToDriverDetails() {
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra(CHOOSE_ITEM_ID, getDriverIdBottomSheet());
        intent.putExtra(KIND_DETAILS, KindDataBase.DRIVER.getValue());
        mContext.startActivity(intent);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void moveToBusDetails() {
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra(CHOOSE_ITEM_ID, getBusIdBottomSheet());
        intent.putExtra(KIND_DETAILS, KindDataBase.BUS.getValue());
        mContext.startActivity(intent);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void itemAction(int busId, int driverId) {
        setBusIdBottomSheet(busId);
        setDriverIdBottomSheet(driverId);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void makeStateWork() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        ApplicationDataBase dataBase = ApplicationDataBase.getInstance().getSelectDataBase();
        try {
            currentDate = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO :замени id
        for (FullWorkDTO item : mFullWorkDTOs) {
            if (currentDate.equals(item.getWorkDTO().getDateAction()) && item.getDriverDTO().getDriverNumber() ==  ApplicationDataBase.getInstance().getSelectDataBase().getNumberDriver()) {
                dataBase.setStateWork(StateWork.WORK_TODAY);
                dataBase.save();
                break;
            } else {
                dataBase.setStateWork(StateWork.NOT_WORK_TODAY);
                dataBase.save();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void makeAlarmManager() {
        //TODO :замени id
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentMillisecond = currentDate == null ? Calendar.getInstance().getTime().getTime() : currentDate.getTime();
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

            Observable<FullWorkDTO> getDataFinally = Observable.from(mFullWorkDTOs)
                    .filter(item -> item.getWorkDTO().getDateAction().getTime() >= currentMillisecond && item.getDriverDTO().getDriverNumber() ==  ApplicationDataBase.getInstance().getSelectDataBase().getNumberDriver())
                    .toSortedList((item1, item2) -> Long.compare(item1.getWorkDTO().getDateAction().getTime(), item2.getWorkDTO().getDateAction().getTime()))
                    .flatMap(Observable::from)
                    .filter(item -> item.getWorkDTO().getDateAction().getTime() != base.getDateManager())
                    .first();

            getDataFinally.subscribe(
                    item -> {
                        if (base.getRole() == Role.DRIVER)
                            createAlarmManager(item.getWorkDTO().getDateAction().getTime(), item.getBusDTO());
                    },
                    error -> {
                    }
            );
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createAlarmManager(long timeStart, BusDTO busInfo) {

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intentBroadcast = new Intent(mContext, NotificationDriverAboutWork.class);
        intentBroadcast.putExtra(INFO_FOR_BROADCAST, busInfo);
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, intentBroadcast, 0);
        long timeSummaryStart = timeStart + base.getStartTime();

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeSummaryStart, pendingIntent);

    }



    public void unsubscribe() {
        if (mSubscriptionWorkGet != null)
            mSubscriptionWorkGet.unsubscribe();
        if (mSubscriptionWorkDelete != null)
            mSubscriptionWorkDelete.unsubscribe();
        if(mSubscriptionAlarmManager != null)
            mSubscriptionAlarmManager.unsubscribe();
    }
}
