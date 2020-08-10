package com.example.bottomnavigation.data.remote;

import com.example.bottomnavigation.ApiService;
import com.example.bottomnavigation.data.datasource.DataSourceListener;
import com.example.bottomnavigation.data.datasource.UserRemoteDataSource;
import com.example.bottomnavigation.data.model.RemoteUser;
import com.example.bottomnavigation.data.model.ProfileUpdate;
import com.example.bottomnavigation.data.model.UpdateProfileBody;
import com.example.bottomnavigation.data.model.UpdateResponseBody;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {
    private static final String TAG = "UserRemoteDataSource";
    private ApiService apiService;

    public UserRemoteDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void updateProfile(ProfileUpdate profileUpdate, final DataSourceListener<UpdateResponseBody> dataSourceListener) {
        UpdateProfileBody updateProfileBody = new UpdateProfileBody(profileUpdate.getNickname(),
                profileUpdate.getDate_of_birth(), profileUpdate.getGender());
        apiService.update("Token " + profileUpdate.getToken(), updateProfileBody).enqueue(new Callback<UpdateResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponseBody> call, @NotNull Response<UpdateResponseBody> response) {
                dataSourceListener.onResponse(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponseBody> call, @NotNull Throwable t) {
                dataSourceListener.onFailure(t);
            }
        });
    }

    @Override
    public void getProfile(String token, final DataSourceListener<RemoteUser> dataSourceListener) {
        apiService.getUser("Token " + token).enqueue(new Callback<RemoteUser>() {
            @Override
            public void onResponse(@NotNull Call<RemoteUser> call, @NotNull Response<RemoteUser> response) {
                dataSourceListener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<RemoteUser> call, @NotNull Throwable t) {
                dataSourceListener.onFailure(t);
            }
        });
    }

    @Override
    public void updateImage(File file, final DataSourceListener<UpdateResponseBody> dataSourceListener) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        apiService.updateImage(requestImage).enqueue(new Callback<UpdateResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<UpdateResponseBody> call, @NotNull Response<UpdateResponseBody> response) {
                dataSourceListener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<UpdateResponseBody> call, @NotNull Throwable t) {
                dataSourceListener.onFailure(t);
            }
        });
    }

}