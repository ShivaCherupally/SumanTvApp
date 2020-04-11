package info.dkapp.flow.retrofitcall;

import android.content.Context;
import retrofit2.Call;

public class ApiRequestModel {
    public Context context;
    public Call<ApiResponseModel> call;
    public String condition;
    public Boolean showProgress;
    public IApiListener iApiListener;
    public Boolean showError;

    public ApiRequestModel(){}

    public ApiRequestModel setModel(Context context, Call<ApiResponseModel> call, String condition, Boolean showProgress, IApiListener iApiListener,Boolean showError){
        ApiRequestModel apiRequestModel = new ApiRequestModel();
        apiRequestModel.context = context;
        apiRequestModel.call = call;
        apiRequestModel.condition = condition;
        apiRequestModel.showProgress = showProgress;
        apiRequestModel.iApiListener = iApiListener;
        apiRequestModel.showError = showError;
        return apiRequestModel;
    }
}
