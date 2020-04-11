package info.dkapp.flow.retrofitcall;

public interface IApiListener {
    void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel);
    void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel);
}
