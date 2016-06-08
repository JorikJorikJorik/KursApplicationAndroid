package com.example.jorik.kursapplicationandroid.ViewModel.Create;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Network.DTO.RepairDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.RepairService;
import com.example.jorik.kursapplicationandroid.View.Fragment.Create.CreateRepairDataActivityFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 01.06.16.
 */

public class CreateRepairViewModel extends BaseViewModel {

    private Context mContext;
    private RepairDTO mRepairDTO;
    private Fragment mFragment;

    private boolean enableSendButton;

    private Subscription mSubscriptionRequest;

    private ResponseCallback mCallback;


    public CreateRepairViewModel(Context context, Fragment fragment) {
        mContext = context;
        mRepairDTO = new RepairDTO();
        mFragment = fragment;
        setEnableSendButton(true);
    }

    @Bindable
    public String getCondition() {
        return mRepairDTO.getCondition();
    }

    public void setCondition(String condition) {
        mRepairDTO.setCondition(condition);
        notifyPropertyChanged(BR.condition);
    }

    @Bindable
    public boolean isEnableSendButton() {
        return enableSendButton;
    }

    public void setEnableSendButton(boolean enableSendButton) {
        this.enableSendButton = enableSendButton;
        notifyPropertyChanged(BR.enableSendButton);
    }

    public void sendRequestByCreate(){
        if(mFragment instanceof CreateRepairDataActivityFragment)
            mCallback = (ResponseCallback) mFragment;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mRepairDTO.setTime(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mRepairDTO.setBusId(null);


        RepairService repairService = RestClient.getServiceInterface(RepairService.class);
        Observable<Integer> requestObservable = repairService.createRepairList(mRepairDTO,  ApplicationDataBase.getInstance().getSelectDataBase().getNumberDriver());
        mSubscriptionRequest = requestObservable
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

    public void unsubscribe() {
        if (mSubscriptionRequest != null)
            mSubscriptionRequest.unsubscribe();
    }


}
