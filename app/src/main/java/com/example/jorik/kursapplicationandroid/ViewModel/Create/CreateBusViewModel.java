package com.example.jorik.kursapplicationandroid.ViewModel.Create;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.Network.DTO.BusDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.BusService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Fragment.Create.CreateBusActivityFragment;
import com.example.jorik.kursapplicationandroid.ViewModel.BaseViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 22.05.16.
 */

public class CreateBusViewModel extends BaseViewModel {

    private final static int MIN_LENGTH_MODEL = 3;
    private final static int MAX_LENGTH_MODEL = 25;
    private final static int MAX_LENGTH_NUMBER = 4;
    private final static int MIN_ERROR_LENGTH_LETTER_IN_MODEL = 4;

    private Context mContext;
    private BusDTO mBusDTO;
    private Fragment mFragment;

    private String errorValidationModel;
    private String errorValidationNumber;

    private Observable<Boolean> isNumber;
    private Observable<Boolean> isModel;

    private boolean enableSendButton;

    private Subscription mSubscriptionNumber;
    private Subscription mSubscriptionModel;
    private Subscription mSubscriptionEnable;
    private Subscription mSubscriptionRequest;

    private ResponseCallback mCallback;


    public CreateBusViewModel(Context context, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        mBusDTO = new BusDTO();
    }

    @Bindable
    public String getNumber() {
        if (mBusDTO.getNumber() != null)
            return Integer.toString(mBusDTO.getNumber());
        return mContext.getString(R.string.empty_response);
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

        Observable<Integer> validatorNumber = Observable.just(numberS)
                .filter(num -> num.length() < MAX_LENGTH_NUMBER)
                .map(Integer::parseInt)
                .filter(num -> num > 0);


        mSubscriptionNumber = validatorNumber.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                setErrorValidationNumber(null);
                isNumber = Observable.just(true);
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationNumber(mContext.getString(R.string.enter_number_error));
                mBusDTO.setNumber(null);
                isNumber = Observable.just(false);
            }

            @Override
            public void onNext(Integer integer) {
                mBusDTO.setNumber(integer);
            }
        });

        enableSendButton();
    }

    private void validationModel(String modelString) {

        Observable<String> validatorModel = Observable.just(modelString)
                .filter(model -> model.length() < MAX_LENGTH_MODEL)
                .map(model -> {
                    setErrorValidationModel(model.length() < MIN_ERROR_LENGTH_LETTER_IN_MODEL ? mContext.getString(R.string.small_count_letter) : null);
                    return model;
                });

        mSubscriptionModel = validatorModel.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationModel(mContext.getString(R.string.error_validation));
            }

            @Override
            public void onNext(String s) {
                mBusDTO.setModel(s);
            }
        });

        isModel = validatorModel.map(model -> model.length() > MIN_LENGTH_MODEL && model.length() < MAX_LENGTH_MODEL);
        enableSendButton();
    }

    public void sendRequestByCreate() {
        if (mFragment instanceof CreateBusActivityFragment)
            mCallback = (ResponseCallback) mFragment;

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

    private void enableSendButton() {
        if (isNumber != null && isModel != null) {
            Observable<Boolean> enableSend = Observable.combineLatest(isNumber, isModel, (number, model) -> number && model);
            mSubscriptionEnable = enableSend.subscribe(this::setEnableSendButton);
        }
    }

    public void unsubscribe() {
        if (mSubscriptionRequest != null)
            mSubscriptionRequest.unsubscribe();
        if (mSubscriptionNumber != null)
            mSubscriptionNumber.unsubscribe();
        if (mSubscriptionModel != null)
            mSubscriptionModel.unsubscribe();
        if (mSubscriptionEnable != null)
            mSubscriptionEnable.unsubscribe();
    }

}
