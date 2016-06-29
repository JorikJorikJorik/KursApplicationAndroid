package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;


import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.Model.POJO.SignInModel;
import com.example.jorik.kursapplicationandroid.Network.DTO.AccountDTO;
import com.example.jorik.kursapplicationandroid.Network.RestClient;
import com.example.jorik.kursapplicationandroid.Network.ServiceInterface.AccountService;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;
import com.example.jorik.kursapplicationandroid.BR;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by jorik on 05.06.16.
 */

public class SignInViewModel extends BaseViewModel {

    private static final int MAX_COUNT_NAME = 15;
    private static final int MAX_COUNT_PASSWORD = 15;

    private Context mContext;
    private SignInModel mSignInModel;
    private List<String> mDataList;

    boolean enable;

    private Observable<Boolean> isName;
    private Observable<Boolean> isPassword;

    private Subscription mSubscriptionName;
    private Subscription mSubscriptionPassword;
    private Subscription mSubscriptionEnable;
    private Subscription mSubscriptionPasswordSignIn;

    public SignInViewModel(Context context) {
        mContext = context;
        mSignInModel = new SignInModel();
        mDataList = new ArrayList<>();
    }

    @Bindable
    public String getName() {
        return mSignInModel.getName();
    }

    public void setName(String name) {

        validationName(name);
        notifyPropertyChanged(BR.name);

    }

    @Bindable
    public String getPassword() {
        return mSignInModel.getPassword();
    }

    public void setPassword(String password) {
        validationPassword(password);
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getErrorValidationName() {
        return mSignInModel.getErrorValidationName();
    }

    public void setErrorValidationName(String errorValidationName) {
        mSignInModel.setErrorValidationName(errorValidationName);
        notifyPropertyChanged(BR.errorValidationName);
    }

    @Bindable
    public String getErrorValidationPassword() {
        return mSignInModel.getErrorValidationPassword();
    }

    public void setErrorValidationPassword(String errorValidationPassword) {
        mSignInModel.setErrorValidationPassword(errorValidationPassword);
        notifyPropertyChanged(BR.errorValidationPassword);
    }

    @Bindable
    public boolean getEnableSignInButton() {
        return mSignInModel.getEnableSignInButton();
    }

    public void setEnableSignInButton(boolean enableSignInButton) {
        mSignInModel.setEnableSignInButton(enableSignInButton);
        notifyPropertyChanged(BR.enableSignInButton);
    }

    public boolean isFinishActivity() {
        return mSignInModel.isFinishActivity();
    }

    public void setFinishActivity(boolean finishActivity) {
        mSignInModel.setFinishActivity(finishActivity);
    }

    private void validationName(String nameS) {
        Observable<String> nameObservable = Observable.just(nameS)
                .map(String::trim)
                .filter(name -> name.length() < MAX_COUNT_NAME)
                .map(name -> {
                    setErrorValidationName(name.length() == 0 ? mContext.getString(R.string.empty_field) : null);
                    isName = Observable.just(name.length() != 0);
                    return name;
                });

        mSubscriptionName = nameObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mSignInModel.setName(s);
            }
        });
        enableSendButton();
    }

    private void validationPassword(String passwordS) {

        Observable<String> passwordObservable = Observable.just(passwordS)
                .map(String::trim)
                .filter(password -> password.length() < MAX_COUNT_PASSWORD)
                .map(password -> {
                    setErrorValidationPassword(password.length() == 0 ? mContext.getString(R.string.empty_field) : null);
                    isPassword = Observable.just(password.length() != 0);
                    return password;
                });

        mSubscriptionPassword = passwordObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mSignInModel.setPassword(s);
            }
        });
        enableSendButton();
    }

    private void enableSendButton() {
        Observable<Boolean> enableSend = null;

        enableSend = Observable.combineLatest(isName, isPassword,
                (name, pass) -> name && pass);

        mSubscriptionEnable = enableSend.subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                setEnableSignInButton(false);
            }

            @Override
            public void onNext(Boolean aBoolean) {
                setEnableSignInButton(aBoolean);
            }
        });
    }

    public void moveToWorkWithApplication() {
        createSignIn();
    }

    private void createSignIn() {

        if (mDataList != null) {
            mDataList.clear();
        }

        AccountDTO mAccountDTO = new AccountDTO();
        mAccountDTO.setSecondname(mSignInModel.getName());
        mAccountDTO.setPassword(mSignInModel.getPassword());

        AccountService accountService = RestClient.getServiceInterface(AccountService.class);
        Observable<ArrayList<String>> accountObservable = accountService.createSignIn(mAccountDTO);
        mSubscriptionPasswordSignIn = accountObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (mDataList.size() <= 1)
                            onError(new Throwable(mContext.getString(R.string.incorrect_data)));
                        else writeInDataBaseAndMove();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        mSignInModel.setFinishActivity(false);
                    }

                    @Override
                    public void onNext(String data) {
                        mDataList.add(data);
                    }
                });
    }

    private void writeInDataBaseAndMove() {
        boolean chooseRole = mDataList.get(0).equals(mContext.getString(R.string.dispatcher));
        mSignInModel.setFinishActivity(true);

        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
        base.setName(mSignInModel.getName());
        base.setRole(chooseRole ? Role.ADMIN : Role.DRIVER);
        base.setStateApplication(StateApplication.ENTER);
        base.setNumberDriver(chooseRole ? 0 : Integer.parseInt(mDataList.get(1)));
        base.save();

        Intent moveToWork = new Intent(mContext, MainActivity.class);
        mContext.startActivity(moveToWork);
    }

    public void unsubscribe() {

        if (mSubscriptionName != null)
            mSubscriptionName.unsubscribe();
        if (mSubscriptionPassword != null)
            mSubscriptionPassword.unsubscribe();
        if (mSubscriptionPasswordSignIn != null)
            mSubscriptionPasswordSignIn.unsubscribe();
    }


}
