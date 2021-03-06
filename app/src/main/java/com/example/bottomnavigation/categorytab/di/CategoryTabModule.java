package com.example.bottomnavigation.categorytab.di;

import com.example.bottomnavigation.ApiService;
import com.example.bottomnavigation.categorytab.CategoryViewModelFactory;
import com.example.bottomnavigation.data.datasource.remote.CategoryRemoteDataSource;

public class CategoryTabModule {
    public static CategoryRemoteDataSource provideCategorySource(ApiService apiService){
        return new CategoryRemoteDataSource(apiService);
    }

    public static CategoryViewModelFactory provideCategoryViewModelFactory(CategoryRemoteDataSource categoryRemoteDataSource){
        return new CategoryViewModelFactory(categoryRemoteDataSource);
    }
}
