package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.View.Fragment.CreateBusActivityFragment;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 22.05.16.
 */

public class CreateBusViewModel extends BaseObservable {

    private Context mContext;
    private BusDTO mBusDTO;
    private String errorValidationModel;
    private String errorValidationNumber;
    private Observable<Boolean> isNumber;
    private Observable<Boolean> isModel;
    private boolean enableSendButton;
    private Subscription mSubscriptionNumber;
    private Subscription mSubscriptionModel;
    private Subscription mSubscriptionRequest;
    private Fragment mFragment;
    private CreateBusCallback mCallback;


    public CreateBusViewModel(Context context, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        mBusDTO = new BusDTO();
    }

    @Bindable
    public String getNumber() {
        if (mBusDTO.getNumber() != null)
            return Integer.toString(mBusDTO.getNumber());
        return "";
    }

    public void setNumber(String number) {
        validationNumber(number);
        notifyPropertyChanged(com.example.jorik.kursapplicationandroid.BR.number);
    }

    @Bindable
    public String getModel() {
        return mBusDTO.getModel();
    }

    public void setModel(String model) {
        validationModel(model);
        notifyPropertyChanged(com.example.jorik.kursapplicationandroid.BR.model);

    }

    @Bindable
    public String getCondition() {
        return mBusDTO.getCondition();
    }

    public void setCondition(String condition) {
        mBusDTO.setCondition(condition);
        notifyPropertyChanged(com.example.jorik.kursapplicationandroid.BR.condition);
    }

    @Bindable
    public String getErrorValidationNumber() {
        return errorValidationNumber;
    }

    public void setErrorValidationNumber(String errorValidation) {
        this.errorValidationNumber = errorValidation;
        notifyPropertyChanged(BR.errorValidationNumber);
    }

    @Bindable
    public String getErrorValidationModel() {
        return errorValidationModel;
    }

    public void setErrorValidationModel(String errorValidationModel) {
        this.errorValidationModel = errorValidationModel;
        notifyPropertyChanged(BR.errorValidationModel);
    }

    @Bindable
    public boolean getEnableSendButton() {
        return enableSendButton;
    }

    public void setEnableSendButton(boolean enableSendButton) {
        this.enableSendButton = enableSendButton;
        notifyPropertyChanged(BR.enableSendButton);
    }

    private void validationNumber(String numberS) {
        Integer number = null;
        try {
            number = Integer.parseInt(numberS);
        } catch (Exception e) {
            mBusDTO.setNumber(null);
        }
        Observable<Integer> validatorNumber = Observable.just(number)
                .map(num -> {
                    setErrorValidationNumber(num == null ? "Enter number" : null);
                    return num;
                })
                .filter(num -> Integer.toString(num).length() < 4)
                .filter(num -> num > 0);


        mSubscriptionNumber = validatorNumber.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
                mBusDTO.setNumber(integer);
            }
        });

        isNumber = Observable.just(number).map(num -> !(num == null));
        enableSendButton();
    }

    private void validationModel(String modelString) {

        Observable<String> validatorModel = Observable.just(modelString)
                .filter(model -> model.length() < 25)
                .map(model -> {
                    setErrorValidationModel(model.length() < 4 ? "Small amount letter" : null);
                    return model;
                });

        mSubscriptionModel = validatorModel.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationModel("Error validation");
            }

            @Override
            public void onNext(String s) {
                mBusDTO.setModel(s);
            }
        });

        isModel = validatorModel.map(model -> model.length() > 3 && model.length() < 25);
        enableSendButton();
    }

    public void sendRequestByCreate() {
        if(mFragment instanceof CreateBusActivityFragment)
            mCallback = (CreateBusActivityFragment) mFragment;
        BusService busService = RestClient.getServiceInterface(BusService.class);
        Observable<Integer> request = busService.createBus(mBusDTO);
        mSubscriptionRequest = request
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
                    public void onNext(Integer response) {
                        mCallback.responseFromServer(response);
                    }
                });
    }


    public void unsubscribe() {
        if (mSubscriptionRequest != null)
            mSubscriptionRequest.unsubscribe();
        if (mSubscriptionNumber != null)
            mSubscriptionNumber.unsubscribe();
        if (mSubscriptionModel != null)
            mSubscriptionModel.unsubscribe();
    }

    private void enableSendButton() {
        if (isNumber != null && isModel != null) {
            Observable<Boolean> enableSend = Observable.combineLatest(isNumber, isModel, (number, model) -> number && model);
            enableSend.subscribe(this::setEnableSendButton);
        }
    }

    public interface CreateBusCallback {
        void responseFromServer(Integer response);
    }



}
