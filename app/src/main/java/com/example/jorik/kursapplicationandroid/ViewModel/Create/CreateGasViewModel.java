package com.example.jorik.kursapplicationandroid.ViewModel.Create;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Network.DTO.GasDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.GasService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.Create.CreateGasDataActivityFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 31.05.16.
 */

public class CreateGasViewModel extends BaseViewModel {

    private final static int MAX_LENGTH_COUNT = 3;
    private final static int MAX_LENGTH_COST = 5;

    private Context mContext;
    private GasDTO mGasDTO;
    private Fragment mFragment;

    private boolean enableSendButton;

    private String errorValidationCount;
    private String errorValidationCost;

    Observable<Boolean> isCount;
    Observable<Boolean> isCost;

    private Subscription mSubscriptionRequest;
    private Subscription mSubscriptionValidationCount;
    private Subscription mSubscriptionValidationCost;
    private Subscription mSubscriptionValidationEnable;

    private ResponseCallback mCallback;


    public CreateGasViewModel(Context context, Fragment fragment) {
        mContext = context;
        mGasDTO = new GasDTO();
        mFragment = fragment;
    }

    @Bindable
    public String getCount() {
        return mGasDTO.getCount() != null ? Integer.toString(mGasDTO.getCount() ) : mContext.getString(R.string.empty_response);
    }

    public void setCount(String count) {
        validationCount(count);
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public String getType() {
        return mGasDTO.getType();
    }

    public void setType(String type) {
        mGasDTO.setType(type);
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getCost() {
        return mGasDTO.getCost() != null ?  Integer.toString(mGasDTO.getCost()) : mContext.getString(R.string.empty_response);
    }

    public void setCost(String cost) {
        validationCost(cost);
        notifyPropertyChanged(BR.cost);
    }

    @Bindable
    public Date getTime() {
        return mGasDTO.getTime();
    }

    public void setTime(Date time) {
        mGasDTO.setTime(time);
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public boolean getEnableSendButton() {
        return enableSendButton;
    }

    public void setEnableSendButton(boolean enableSendButton) {
        this.enableSendButton = enableSendButton;
        notifyPropertyChanged(BR.enableSendButton);
    }

    @Bindable
    public String getErrorValidationCount() {
        return errorValidationCount;
    }

    public void setErrorValidationCount(String errorValidationCount) {
        this.errorValidationCount = errorValidationCount;
        notifyPropertyChanged(BR.errorValidationCount);
    }

    @Bindable
    public String getErrorValidationCost() {
        return errorValidationCost;
    }

    public void setErrorValidationCost(String errorValidationCost) {
        this.errorValidationCost = errorValidationCost;
        notifyPropertyChanged(BR.errorValidationCost);
    }

    private void validationCount(String countS) {

        Observable<Integer> validatorCount = Observable.just(countS)
                .filter(count -> count.length() < MAX_LENGTH_COUNT)
                .map(Integer::parseInt)
                .filter(count -> count > 0);

        mSubscriptionValidationCount = validatorCount.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                setErrorValidationCount(null);
                isCount = Observable.just(true);
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationCount(mContext.getString(R.string.enter_number_error));
                mGasDTO.setCount(null);
                isCount = Observable.just(false);

            }

            @Override
            public void onNext(Integer integer) {
                mGasDTO.setCount(integer);
            }
        });

        enableSendButton();
    }

    private void validationCost(String costS) {

        Observable<Integer> validatorCost = Observable.just(costS)
                .filter(count -> count.length() < MAX_LENGTH_COST)
                .map(Integer::parseInt)
                .filter(count -> count > 0);

        mSubscriptionValidationCost = validatorCost.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                setErrorValidationCost(null);
                isCost = Observable.just(true);
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationCost(mContext.getString(R.string.enter_number_error));
                mGasDTO.setCost(null);
                isCost = Observable.just(false);

            }

            @Override
            public void onNext(Integer integer) {
                mGasDTO.setCost(integer);
            }
        });

        enableSendButton();
    }

    //TODO: смени id
    public void sendRequestByCreate(){
        if(mFragment instanceof CreateGasDataActivityFragment)
            mCallback = (ResponseCallback) mFragment;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mGasDTO.setTime(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        GasService gasService = RestClient.getServiceInterface(GasService.class);
        Observable<Integer> requestObservable = gasService.createGasList(mGasDTO,  ApplicationDataBase.getInstance().getSelectDataBase().getNumberDriver());
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

    public void enableSendButton(){
        if( isCost != null && isCount != null) {
            Observable<Boolean> enable = Observable.combineLatest(isCost, isCount, ( cost, count) -> cost && count);
            mSubscriptionValidationEnable = enable.subscribe(this::setEnableSendButton);
        }
    }

    public void unsubscribe() {
        if(mSubscriptionRequest != null)
            mSubscriptionRequest.unsubscribe();
        if(mSubscriptionValidationEnable != null)
            mSubscriptionValidationEnable.unsubscribe();
        if(mSubscriptionValidationCount != null)
            mSubscriptionValidationCount.unsubscribe();
        if(mSubscriptionValidationCost != null)
            mSubscriptionValidationCost.unsubscribe();

    }
}
