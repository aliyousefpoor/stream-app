package com.example.StreamApp.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.StreamApp.data.datasource.DataSourceListener;
import com.example.StreamApp.data.datasource.local.database.IsLoginListener;
import com.example.StreamApp.data.model.LoginStepOneRequest;
import com.example.StreamApp.data.model.LoginStepOneResponse;
import com.example.StreamApp.data.model.LoginStepTwoRequest;
import com.example.StreamApp.data.model.LoginStepTwoResponse;
import com.example.StreamApp.data.repository.LoginRepository;
import com.example.StreamApp.moretab.SingleLiveEvent;

public class LoginSharedViewModel extends ViewModel {
    private static final String TAG = "LoginSharedViewModel";
    private LoginRepository loginRepository;
    LoginStepOneRequest loginStepOneBody;
    Boolean isLoginUser;

    public LoginSharedViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    private MutableLiveData<LoginStepOneResponse> _loginStepOneLiveData = new MutableLiveData<>();
    public LiveData<LoginStepOneResponse> loginStepOneLiveData = _loginStepOneLiveData;

    private MutableLiveData<LoginStepTwoResponse> _loginStepTwoLiveData = new MutableLiveData<>();
    public LiveData<LoginStepTwoResponse> loginStepTwoLiveData = _loginStepTwoLiveData;

    private SingleLiveEvent<Boolean> _isLogin = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isLogin = _isLogin;

    public void loginStepOne(LoginStepOneRequest loginStepOneRequest) {
        loginStepOneBody = loginStepOneRequest;
        loginRepository.loginStepOne(loginStepOneRequest, new DataSourceListener<LoginStepOneResponse>() {
            @Override
            public void onResponse(LoginStepOneResponse response) {
                _loginStepOneLiveData.setValue(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "onFailure: ");
                _loginStepOneLiveData.setValue(null);
            }
        });
    }

    public void loginStepTwo(LoginStepTwoRequest loginStepTwoRequest) {
        loginRepository.loginStepTwo(loginStepTwoRequest, new DataSourceListener<LoginStepTwoResponse>() {
            @Override
            public void onResponse(LoginStepTwoResponse response) {
                _loginStepTwoLiveData.setValue(response);

            }

            @Override
            public void onFailure(Throwable throwable) {
                _loginStepTwoLiveData.setValue(null);
                throwable.getMessage();
            }
        });
    }


    public void isLogin() {
        loginRepository.isLogin(new IsLoginListener() {
            @Override
            public void isLogin(Boolean isLogin) {
                isLoginUser = isLogin;
                if (isLogin) {
                    _isLogin.postValue(true);

                } else {
                    _isLogin.postValue(false);

                }
            }
        });
    }


}