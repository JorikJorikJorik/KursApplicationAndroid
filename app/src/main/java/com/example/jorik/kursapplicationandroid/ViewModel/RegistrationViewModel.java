package com.example.jorik.kursapplicationandroid.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.jorik.kursapplicationandroid.BR;
import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Model.Enum.Rights;
import com.example.jorik.kursapplicationandroid.Model.Enum.Role;
import com.example.jorik.kursapplicationandroid.Model.Enum.StateApplication;
import com.example.jorik.kursapplicationandroid.Model.POJO.RegistrationModel;
import com.example.jorik.kursapplicationandroid.Model.POJO.RegistrationModelView;
import com.example.jorik.kursapplicationandroid.R;
import com.example.jorik.kursapplicationandroid.View.Activity.MainActivity;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by jorik on 05.06.16.
 */

public class RegistrationViewModel extends BaseViewModel {

    private static final int MAX_COUNT_NAME = 15;
    private static final int MAX_COUNT_PASSWORD = 15;
    private static final int MAX_COUNT_EXPERIENCE = 3;
    private static final int MAX_COUNT_SALARY = 6;

    private Context mContext;
    private RegistrationModelView mRegistrationModelView;
    private RegistrationModel mRegistrationModel;

    private Observable<String> passwordObservable;
    private Observable<String> confirmPasswordObservable;
    private Observable<Boolean> isName;
    private Observable<Boolean> isPassword;
    private Observable<Boolean> isConfirmPassword;
    private Observable<Boolean> passwordValidation;
    private Observable<Boolean> isExperience;
    private Observable<Boolean> isSalary;
    private boolean enableRegistrationButton;
    private boolean visibleDriverFields;

    private Subscription mSubscriptionName;
    private Subscription mSubscriptionPassword;
    private Subscription mSubscriptionPasswordValidation;
    private Subscription mSubscriptionConfirmPassword;
    private Subscription mSubscriptionExperience;
    private Subscription mSubscriptionSalary;
    private Subscription mSubscriptionEnable;

    private CheckedTextView mCheckedTextView;

    public RegistrationViewModel(Context context, CheckedTextView checkedTextView) {
        mContext = context;
        mCheckedTextView = checkedTextView;
        mRegistrationModel = new RegistrationModel();
        mRegistrationModelView = new RegistrationModelView();
    }

    @Bindable
    public String getName() {
        return mRegistrationModel.getName();
    }

    public void setName(String name) {
        validationName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPasswordR() {
        return mRegistrationModel.getPassword();
    }

    public void setPasswordR(String password) {
        validationPassword(password);
        notifyPropertyChanged(BR.passwordR);
    }

    @Bindable
    public String getConfirmPasswordR() {
        return mRegistrationModel.getConfirmPassword();
    }

    public void setConfirmPasswordR(String confirmPassword) {
        validationConfirmPassword(confirmPassword);
        notifyPropertyChanged(BR.confirmPasswordR);
    }

    @Bindable
    public String getQualification() {
        return mRegistrationModel.getQualification();
    }

    public void setQualification(String qualification) {
        mRegistrationModel.setQualification(qualification);
        notifyPropertyChanged(BR.qualification);
    }

    @Bindable
    public String getExperience() {
        if (mRegistrationModel.getExperience() != null)
            return Integer.toString(mRegistrationModel.getExperience());
        return "";
    }

    public void setExperience(String experience) {
        validationExperience(experience);
        notifyPropertyChanged(BR.experience);
    }

    @Bindable
    public String getSalary() {
        if (mRegistrationModel.getSalary() != null)
            return Integer.toString(mRegistrationModel.getSalary());
        return "";
    }

    public void setSalary(String salary) {
        validationSalary(salary);
        notifyPropertyChanged(BR.salary);
    }

    @Bindable
    public String getErrorValidationName() {
        return mRegistrationModelView.getErrorValidationName();
    }

    public void setErrorValidationName(String errorValidationName) {
        mRegistrationModelView.setErrorValidationName(errorValidationName);
        notifyPropertyChanged(BR.errorValidationName);
    }

    @Bindable
    public String getErrorValidationPassword() {
        return mRegistrationModelView.getErrorValidationPassword();
    }

    public void setErrorValidationPassword(String errorValidationPassword) {
        mRegistrationModelView.setErrorValidationPassword(errorValidationPassword);
        notifyPropertyChanged(BR.errorValidationPassword);
    }

    @Bindable
    public String getErrorValidationConfirmPassword() {
        return mRegistrationModelView.getErrorValidationConfirmPassword();
    }

    public void setErrorValidationConfirmPassword(String errorValidationConfirmPassword) {
        mRegistrationModelView.setErrorValidationConfirmPassword(errorValidationConfirmPassword);
        notifyPropertyChanged(BR.errorValidationConfirmPassword);
    }

    @Bindable
    public String getErrorValidationExperience() {
        return mRegistrationModelView.getErrorValidationExperience();
    }

    public void setErrorValidationExperience(String errorValidationExperience) {
        mRegistrationModelView.setErrorValidationExperience(errorValidationExperience);
        notifyPropertyChanged(BR.errorValidationExperience);
    }

    @Bindable
    public String getErrorValidationSalary() {
        return mRegistrationModelView.getErrorValidationSalary();
    }

    public void setErrorValidationSalary(String errorValidationSalary) {
        mRegistrationModelView.setErrorValidationSalary(errorValidationSalary);
        notifyPropertyChanged(BR.errorValidationSalary);
    }

    @Bindable
    public boolean getEnableRegistrationButton() {
        return enableRegistrationButton;
    }

    public void setEnableRegistrationButton(boolean enableRegistrationButton) {
        this.enableRegistrationButton = enableRegistrationButton;
        notifyPropertyChanged(BR.enableRegistrationButton);
    }

    @Bindable
    public boolean isVisibleDriverFields() {
        return visibleDriverFields;
    }

    public void setVisibleDriverFields(boolean visibleDriverFields) {
        this.visibleDriverFields = visibleDriverFields;
        notifyPropertyChanged(BR.visibleDriverFields);
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
                mRegistrationModel.setName(s);
            }
        });
        enableSendButton();
    }

    private void validationPassword(String passwordS) {

        passwordObservable = Observable.just(passwordS)
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
                mRegistrationModel.setPassword(s);
            }
        });

        if (mRegistrationModel.getPassword().length() != 0)
            equalsPassword();
        enableSendButton();
    }

    private void validationConfirmPassword(String confirmPasswordS) {

        confirmPasswordObservable = Observable.just(confirmPasswordS)
                .map(String::trim)
                .filter(confirm -> confirm.length() < MAX_COUNT_PASSWORD)
                .map(confirm -> {
                    setErrorValidationConfirmPassword(confirm.length() == 0 ? mContext.getString(R.string.empty_field) : null);
                    isConfirmPassword = Observable.just(confirm.length() != 0);
                    return confirm;
                });

        mSubscriptionConfirmPassword = confirmPasswordObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mRegistrationModel.setConfirmPassword(s);
            }
        });

        if (mRegistrationModel.getConfirmPassword().length() != 0)
            equalsPassword();
        enableSendButton();
    }

    private void validationExperience(String experienceS) {
        Observable<Integer> experienceObservable = Observable.just(experienceS)
                .filter(experience -> experience.length() < MAX_COUNT_EXPERIENCE)
                .map(Integer::parseInt)
                .filter(num -> num > 0);


        mSubscriptionExperience = experienceObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                setErrorValidationExperience(null);
                isExperience = Observable.just(true);
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationExperience(mContext.getString(R.string.enter_number_error));
                mRegistrationModel.setExperience(null);
                isExperience = Observable.just(false);
            }

            @Override
            public void onNext(Integer integer) {
                mRegistrationModel.setExperience(integer);
            }
        });

        enableSendButton();
    }

    private void validationSalary(String salaryS) {
        Observable<Integer> salaryObservable = Observable.just(salaryS)
                .filter(num -> num.length() < MAX_COUNT_SALARY)
                .map(Integer::parseInt)
                .filter(num -> num > 0);


        mSubscriptionSalary = salaryObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                setErrorValidationSalary(null);
                isSalary = Observable.just(true);
            }

            @Override
            public void onError(Throwable e) {
                setErrorValidationSalary(mContext.getString(R.string.enter_number_error));
                mRegistrationModel.setSalary(null);
                isSalary = Observable.just(false);
            }

            @Override
            public void onNext(Integer integer) {
                mRegistrationModel.setSalary(integer);
            }
        });

        enableSendButton();
    }

    private void equalsPassword() {
        if (confirmPasswordObservable != null && passwordObservable != null) {
            passwordValidation = Observable.combineLatest(passwordObservable, confirmPasswordObservable, String::equals);
            mSubscriptionPasswordValidation = passwordValidation.subscribe(validPass -> {
                setErrorValidationConfirmPassword(!validPass ? mContext.getString(R.string.passwor_not_same) : null);
            });
        }
    }

    private void enableSendButton() {
        Observable<Boolean> enableSend = null;

        if (mCheckedTextView.isChecked()) {
                enableSend = Observable.combineLatest(isName, isPassword, isConfirmPassword, passwordValidation, isExperience, isSalary,
                        (name, pass, confirmPass, passValid, experience, salary) -> name && pass && confirmPass && passValid && experience && salary);
        } else {
            enableSend = Observable.combineLatest(isName, isPassword, isConfirmPassword, passwordValidation,
                    (name, pass, confirmPass, passValid) -> name && pass && confirmPass && passValid);
        }

            mSubscriptionEnable = enableSend.subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    setEnableRegistrationButton(false);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    setEnableRegistrationButton(aBoolean);
                }
            });
            equalsPassword();


    }

    public void moveToWorkWithApplication() {
        writeInDataBaseAndMove();
    }

    private void writeInDataBaseAndMove(){
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();
        base.setName(mRegistrationModel.getName());
        base.setRole(!mCheckedTextView.isChecked() ? Role.ADMIN : Role.DRIVER);
        base.setStateApplication(StateApplication.ENTER);
        base.setNumberDriver(4);
        base.save();

        Intent moveToWork = new Intent(mContext, MainActivity.class);
        mContext.startActivity(moveToWork);
    }

    public void checkListenerForVisibleView() {
        setVisibleDriverFields(!mCheckedTextView.isChecked());
        mCheckedTextView.setChecked(!mCheckedTextView.isChecked());
        enableSendButton();
    }


    public void unsubscribe() {

        if (mSubscriptionName != null)
            mSubscriptionName.unsubscribe();
        if (mSubscriptionPassword != null)
            mSubscriptionPassword.unsubscribe();
        if (mSubscriptionPasswordValidation != null)
            mSubscriptionPasswordValidation.unsubscribe();
        if (mSubscriptionConfirmPassword != null)
            mSubscriptionConfirmPassword.unsubscribe();
        if (mSubscriptionExperience != null)
            mSubscriptionExperience.unsubscribe();
        if (mSubscriptionSalary != null)
            mSubscriptionSalary.unsubscribe();
        if (mSubscriptionEnable != null)
            mSubscriptionEnable.unsubscribe();

    }


}
