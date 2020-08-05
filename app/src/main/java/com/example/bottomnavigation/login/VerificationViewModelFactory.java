package com.example.bottomnavigation.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigation.data.repository.LoginRepository;
import com.example.bottomnavigation.login.VerificationViewModel;

public class VerificationViewModelFactory implements ViewModelProvider.Factory {
    private LoginRepository loginRepository;

    public VerificationViewModelFactory(LoginRepository loginRepository){
        this.loginRepository=loginRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VerificationViewModel.class)){
            return (T) new VerificationViewModel(loginRepository);
        }
    throw new IllegalArgumentException("UnKnown Class");
    }
}