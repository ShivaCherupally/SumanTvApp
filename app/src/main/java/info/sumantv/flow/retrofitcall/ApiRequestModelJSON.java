package info.sumantv.flow.retrofitcall;

import android.content.Context;

import com.google.gson.JsonElement;

import retrofit2.Call;

public class ApiRequestModelJSON {
    public Context context;
    public Call<JsonElement> call;
    public String condition;
    public Boolean showProgress;
    public IApiListener iApiListener;
    public Boolean showError;

    public ApiRequestModelJSON(){}

    public ApiRequestModelJSON setModel(Context context, Call<JsonElement> call, String condition, Boolean showProgress, IApiListener iApiListener,Boolean showError){
        ApiRequestModelJSON apiRequestModel = new ApiRequestModelJSON();
        apiRequestModel.context = context;
        apiRequestModel.call = call;
        apiRequestModel.condition = condition;
        apiRequestModel.showProgress = showProgress;
        apiRequestModel.iApiListener = iApiListener;
        apiRequestModel.showError = showError;
        return apiRequestModel;
    }
}
