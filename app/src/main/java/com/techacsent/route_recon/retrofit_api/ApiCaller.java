package com.techacsent.route_recon.retrofit_api;

import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;

import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.BadgeCountResponse;
import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.CategoryCreateResponse;
import com.techacsent.route_recon.model.CategoryListResponse;
import com.techacsent.route_recon.model.CategoryWiseChecklistResponse;
import com.techacsent.route_recon.model.ChecklistCreateResponse;
import com.techacsent.route_recon.model.CreateAccountResponse;
import com.techacsent.route_recon.model.CreateChecklistResponse;
import com.techacsent.route_recon.model.CreateContactSuccessResponse;
import com.techacsent.route_recon.model.CreateGroupResponse;
import com.techacsent.route_recon.model.CreateTripChecklistResponse;
import com.techacsent.route_recon.model.CustomChecklistResponse;
import com.techacsent.route_recon.model.EditContactResponse;
import com.techacsent.route_recon.model.LandmarkCreateResponseV2;
import com.techacsent.route_recon.model.deleteHazardImage.DeleteHazardImageResponse;
import com.techacsent.route_recon.model.deletehazardpin.DeleteHazardPinResponse;
import com.techacsent.route_recon.model.edithazrdpin.EditHazardPinModel;
import com.techacsent.route_recon.model.edithazrdpin.EditHazardPinResponse;
import com.techacsent.route_recon.model.GetTripLocationModel;
import com.techacsent.route_recon.model.GroupMemberResponse;
import com.techacsent.route_recon.model.LandmarkCreateResponse;
import com.techacsent.route_recon.model.MarkerDetailResponse;
import com.techacsent.route_recon.model.MyTripDescriptionModel;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.model.PackageResponse;
import com.techacsent.route_recon.model.PaymentResponse;
import com.techacsent.route_recon.model.polygonimageuploadresponse.PolygonImageUploadRespone;
import com.techacsent.route_recon.model.PolygonPinDetailsResponse;
import com.techacsent.route_recon.model.PolygonPinIImageUploadModel;
import com.techacsent.route_recon.model.PostCrimeReportModel;
import com.techacsent.route_recon.model.PostCrimeReportResponse;
import com.techacsent.route_recon.model.ReportInRangeResponse;
import com.techacsent.route_recon.model.ReportListResponse;
import com.techacsent.route_recon.model.SecurityChecklistResponse;
import com.techacsent.route_recon.model.SharingTripLocationResponse;
import com.techacsent.route_recon.model.ShortTripResponse;
import com.techacsent.route_recon.model.SubscriptionResponse;
import com.techacsent.route_recon.model.sendtrip.SendRouteListResponse;
import com.techacsent.route_recon.model.sendtrip.SendTripRejectResponse;
import com.techacsent.route_recon.model.tracklisthistory.TrackListHistoryResponse;
import com.techacsent.route_recon.model.TripAcceptResponse;
import com.techacsent.route_recon.model.TripListResponse;
import com.techacsent.route_recon.model.TripStartResponse;
import com.techacsent.route_recon.model.UpdateChecklistResponse;
import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.model.WaypointResponse;
import com.techacsent.route_recon.model.request_body_model.CancelUserModel;
import com.techacsent.route_recon.model.request_body_model.CreateAccountModel;
import com.techacsent.route_recon.model.request_body_model.CreateCatagorizedChkListModel;
import com.techacsent.route_recon.model.request_body_model.CreateCategoryModel;
import com.techacsent.route_recon.model.request_body_model.CreateChecklistModel;
import com.techacsent.route_recon.model.request_body_model.CreateLandmarkModel;
import com.techacsent.route_recon.model.request_body_model.CreateTripChecklistModel;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.DeleteGroupresponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;
import com.techacsent.route_recon.model.request_body_model.DeleteCategoryModel;
import com.techacsent.route_recon.model.request_body_model.DeleteTripChecklistModel;
import com.techacsent.route_recon.model.request_body_model.EditContactModel;
import com.techacsent.route_recon.model.request_body_model.EditGroupModel;
import com.techacsent.route_recon.model.EmergencyContactListResponse;
import com.techacsent.route_recon.model.SharedUserResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.SuccessResponse;
import com.techacsent.route_recon.model.request_body_model.EditMarkerModel;
import com.techacsent.route_recon.model.request_body_model.EmergenctContactModel;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.LocationShareResponse;
import com.techacsent.route_recon.model.LoginResponse;
import com.techacsent.route_recon.model.request_body_model.FeedbackModel;
import com.techacsent.route_recon.model.request_body_model.HazardMarkerModel;
import com.techacsent.route_recon.model.request_body_model.PaymentModel;
import com.techacsent.route_recon.model.request_body_model.ReportInRangeModel;
import com.techacsent.route_recon.model.request_body_model.SaveLocationModel;
import com.techacsent.route_recon.model.SaveLocationResponse;
import com.techacsent.route_recon.model.ShareLocationModel;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.SendEmailModel;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.model.request_body_model.SetBadgeModel;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;
import com.techacsent.route_recon.model.request_body_model.TripMarkerGetModel;
import com.techacsent.route_recon.model.TripMarkerResponse;
import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.model.request_body_model.UpdateChecklistModel;
import com.techacsent.route_recon.model.request_body_model.UpdateGroupNameReqBody;
import com.techacsent.route_recon.model.request_body_model.UserTokenModel;
import com.techacsent.route_recon.model.request_body_model.UserTripSharingResponseModel;
import com.techacsent.route_recon.model.request_body_model.VerifyAccountModel;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ApiCaller implements ApiService {

    @Override
    public void updateFirebaseToken(String token, UserTokenModel userTokenModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUpdateUserToken(token, userTokenModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Device token updated")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });
    }


    @Override
    public void sendTripAcceptRequest(String token, String trip_id, ResponseCallback<TripAcceptResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("trip_id", trip_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());

        apiInterface.sendTripAcceptRequest(token,requestBody).enqueue(new Callback<TripAcceptResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripAcceptResponse> call, @NonNull Response<TripAcceptResponse> response) {
                if (response.isSuccessful()) {

                        responseCallback.onSuccess(response.body());


                } else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TripAcceptResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });
    }

    @Override
    public void sendTripRejectRequest(String token, String trip_id, ResponseCallback<SendTripRejectResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("trip_id", trip_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());

        apiInterface.sendTripRejectRequest(token,requestBody).enqueue(new Callback<SendTripRejectResponse>() {
            @Override
            public void onResponse(@NonNull Call<SendTripRejectResponse> call, @NonNull Response<SendTripRejectResponse> response) {
                if (response.isSuccessful()) {

                    responseCallback.onSuccess(response.body());


                } else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendTripRejectResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });
    }


    @Override
    public void addShortTrip( String token,String name, String time, String speed, String distance, String start_lat, String start_long, String end_lat, String end_long, ResponseCallback<ShortTripResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("time", time);
            jsonObject.put("speed", speed);
            jsonObject.put("distance", distance);
            jsonObject.put("start_lat", start_lat);
            jsonObject.put("start_long", start_long);
            jsonObject.put("end_lat", end_lat);
            jsonObject.put("end_long", end_long);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());

        apiInterface.addShortTrip(token,requestBody).enqueue(new Callback<ShortTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShortTripResponse> call, @NonNull Response<ShortTripResponse> response) {
                if (response.isSuccessful()) {

                    responseCallback.onSuccess(response.body());
                }
                else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShortTripResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });


    }

    @Override
    public void getTrackHistoryList(String token,int limit, int offset, ResponseCallback<TrackListHistoryResponse> responseCallback) {
       
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            apiInterface.getTrackHistory(token, limit, offset).enqueue(new Callback<TrackListHistoryResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrackListHistoryResponse> call, @NonNull Response<TrackListHistoryResponse> response) {
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                      
                        /*if (response.body() != null && response.body().getList() != null && response.body().getList().size() > 0) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }*/

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TrackListHistoryResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    responseCallback.onError(t);
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetPassword(String email, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callResetPassword(requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Route Recon send a code to your email shortly.")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }
    }

    @Override
    public void validateCode(String email, String verificationCode, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("code", verificationCode);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callValidationCode(requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Your Code is valid.")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }


    }

    @Override
    public void recoverAccount(String email, String verificationCode, String password, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("code", verificationCode);
            jsonObject.put("new-password", password);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callRecoverAccount(requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Your New Password Successfully Saved.")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }
    }

    @Override
    public void unblockUser(String token, String shouldBlock, String blockId, int position, ResponseCallback<BlockResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("block", shouldBlock);
            jsonObject.put("block-id", blockId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callUserBlock(token, requestBody).enqueue(new Callback<BlockResponse>() {
                @Override
                public void onResponse(@NonNull Call<BlockResponse> call, @NonNull Response<BlockResponse> response) {
                    if (response.isSuccessful()) {
                        BlockResponse blockResponse = response.body();
                        if (blockResponse.getSuccess().getMessage().equals("User blocked successfully")) {
                            responseCallback.onSuccess(blockResponse);
                        } else {
                            responseCallback.onError(new Exception(blockResponse.getSuccess().getMessage()));
                        }

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }

                }

                @Override
                public void onFailure(@NonNull Call<BlockResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void login(String email, String password, ResponseCallback<LoginResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callLogin(Constant.JWT_HASH_SIGNETURE_FOR_LOGIN, requestBody).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    Timber.d(String.valueOf(response.body()));
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Login successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }

    }

    @Override
    public void createAccount(CreateAccountModel createAccountModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.createAccount(Constant.JWT_HASH_SIGNETURE_FOR_LOGIN, createAccountModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                //Log.e(TAG + "--> CREATE_ACCOUNT", new Gson().toJson(response));
                Timber.d(String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("A code is sent to your email,please verify your account using this code")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void verifyAccount(String token, VerifyAccountModel verifyAccountModel, ResponseCallback<CreateAccountResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callverifyAccount(token, verifyAccountModel).enqueue(new Callback<CreateAccountResponse>() {
            @Override
            public void onResponse(@NotNull Call<CreateAccountResponse> call, @NotNull Response<CreateAccountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Your account is verified successfully.")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateAccountResponse> call, @NotNull Throwable t) {
                responseCallback.onError(new Exception(t.getMessage()));

            }
        });

    }

    @Override
    public void searchUserByName(String token, String username, int limit, int offset, ResponseCallback<UserSearchResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUserSearch(token, username, limit, offset).enqueue(new Callback<UserSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserSearchResponse> call, @NonNull Response<UserSearchResponse> response) {
                if (response.isSuccessful()) {
                    UserSearchResponse userSearchResponse = response.body();
                    responseCallback.onSuccess(userSearchResponse);
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSearchResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void followUnfollowUser(int path, String token, String amFollower, ResponseCallback<FollowUnfollowResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am-follower", amFollower);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callFollowUnfollow(path, token, requestBody).enqueue(new Callback<FollowUnfollowResponse>() {
                @Override
                public void onResponse(@NonNull Call<FollowUnfollowResponse> call, @NonNull Response<FollowUnfollowResponse> response) {
                    if (response.isSuccessful()) {
                        FollowUnfollowResponse followUnfollowResponse = response.body();
                        responseCallback.onSuccess(followUnfollowResponse);
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FollowUnfollowResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroup(String token, String groupId, ResponseCallback<DeleteGroupresponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", groupId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeteleGroup(token, requestBody).enqueue(new Callback<DeleteGroupresponse>() {
                @Override
                public void onResponse(@NonNull Call<DeleteGroupresponse> call, @NonNull Response<DeleteGroupresponse> response) {
                    if (response.isSuccessful()) {
                        DeleteGroupresponse deleteGroupresponse = response.body();
                        if (deleteGroupresponse.getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(deleteGroupresponse);
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeleteGroupresponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void followUnfollow(int path, String token, String amFollower, ResponseCallback<FollowUnfollowResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am-follower", amFollower);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callFollowUnfollow(path, token, requestBody).enqueue(new Callback<FollowUnfollowResponse>() {
                @Override
                public void onResponse(@NonNull Call<FollowUnfollowResponse> call, @NonNull Response<FollowUnfollowResponse> response) {
                    if (response.isSuccessful()) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FollowUnfollowResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createTripMarker(String token, CreateTripMarkerModelClass createTripMarkerModelClass, ResponseCallback<TripMarkerCreateResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateTrip(token, createTripMarkerModelClass).enqueue(new Callback<TripMarkerCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripMarkerCreateResponse> call, @NonNull Response<TripMarkerCreateResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Create marks successfully")) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                    //responseResponseCallback.onError(new Exception(response.message()));
                }


            }

            @Override
            public void onFailure(@NonNull Call<TripMarkerCreateResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
            }
        });


    }

    @Override
    public void getTripMarker(String token, TripMarkerGetModel tripMarkerGetModel, ResponseCallback<TripMarkerResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGetTripMarker(token, tripMarkerGetModel).enqueue(new Callback<TripMarkerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripMarkerResponse> call, @NonNull Response<TripMarkerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null && response.body().getList() != null) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TripMarkerResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void shareLiveLocation(String token, ShareLocationModel shareLocationModel, ResponseCallback<LocationShareResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callShareLocation(token, shareLocationModel).enqueue(new Callback<LocationShareResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationShareResponse> call, @NonNull Response<LocationShareResponse> response) {
                if (response.isSuccessful()) {
                    if ((response.body() != null) && (response.body().getSuccess() != null) && response.body().getSuccess().equals("location create successful")) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LocationShareResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
            }
        });
    }

    @Override
    public void saveCurrentLocation(int shareLocationId, String token, SaveLocationModel saveLocationModel, ResponseCallback<SaveLocationResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSaveLocation(shareLocationId, token, saveLocationModel).enqueue(new Callback<SaveLocationResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveLocationResponse> call, @NonNull Response<SaveLocationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getLiveLocationId() != null) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));

                }
            }

            @Override
            public void onFailure(@NonNull Call<SaveLocationResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);

            }
        });
    }

    @Override
    public void createEmergencyContact(String token, EmergenctContactModel emergenctContactModel, ResponseCallback<CreateContactSuccessResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateContect(token, emergenctContactModel).enqueue(new Callback<CreateContactSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateContactSuccessResponse> call, @NonNull Response<CreateContactSuccessResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Create contact successfully")) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));

                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateContactSuccessResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void editGroupInfo(String token, EditGroupModel editGroupModel, ResponseCallback<SuccessResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callEditGroup(token, editGroupModel).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Update successfully")) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);

            }
        });
    }

    @Override
    public void deleteTripMarker(String token, String tripId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteMarker(token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deleteHazardPin(String token, int pointerId, ResponseCallback<DeleteHazardPinResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", pointerId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.deleteHazardPin(token, requestBody).enqueue(new Callback<DeleteHazardPinResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeleteHazardPinResponse> call, @NonNull Response<DeleteHazardPinResponse> response) {


                    if (response.isSuccessful()) {

                            responseCallback.onSuccess(response.body());

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeleteHazardPinResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void getSharedTrip(String token, int status, int limit, int offset, ResponseCallback<BeingSharedTripResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGetSharedTrip(token, status, limit, offset).enqueue(new Callback<BeingSharedTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<BeingSharedTripResponse> call, @NonNull Response<BeingSharedTripResponse> response) {
                if (response.isSuccessful()) {


                    if (response.body()!=null && response.body().getList() != null && response.body().getList().size() > 0) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BeingSharedTripResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getSendRouteList(String token, ResponseCallback<SendRouteListResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.getSendRouteList(token,20,0).enqueue(new Callback<SendRouteListResponse>() {
            @Override
            public void onResponse(@NonNull Call<SendRouteListResponse> call, @NonNull Response<SendRouteListResponse> response) {
                if (response.isSuccessful()) {
                        responseCallback.onSuccess(response.body());

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendRouteListResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });
    }

    @Override
    public void callUserActionForTripAttending(String token, UserTripSharingResponseModel userTripSharingResponseModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUserActionForTripAttending(token, userTripSharingResponseModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body());
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getContactList(String token, ResponseCallback<EmergencyContactListResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGetEmergencyContactList(token).enqueue(new Callback<EmergencyContactListResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmergencyContactListResponse> call, @NonNull Response<EmergencyContactListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() != null && response.body().getSuccess().getContacts() != null && response.body().getSuccess().getContacts().size() > 0)
                        responseResponseCallback.onSuccess(response.body());
                    else {
                        responseResponseCallback.onSuccess(response.body());
                    }

                } else {

                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmergencyContactListResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
            }
        });
    }

    @Override
    public void getSharedLocationUser(String token, String liveLocation, int limit, int offset, ResponseCallback<SharedUserResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getSharedLocationUser(token, liveLocation, limit, offset).enqueue(new Callback<SharedUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<SharedUserResponse> call, @NonNull Response<SharedUserResponse> response) {
                if (response.isSuccessful()) {
                    responseResponseCallback.onSuccess(response.body());
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SharedUserResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
            }
        });
    }



    @Override
    public void editHazardPinMarker(String token, EditHazardPinModel editHazardPinModel, ResponseCallback<EditHazardPinResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.editHazardPinSubmit(token, editHazardPinModel).enqueue(new Callback<EditHazardPinResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditHazardPinResponse> call, @NonNull Response<EditHazardPinResponse> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body());

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EditHazardPinResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });
    }

    @Override
    public void editTripMarker(String token, EditMarkerModel editMarkerModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callEditMarker(token, editMarkerModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body());

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });
    }



    @Override
    public void deleteShareLiveLocation(int path, String token, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callDeleteShareLocation(path, token).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage() != null) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getAllTripList(String token, int type, int limit, int offset, ResponseCallback<TripListResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            apiInterface.callTripList(token, type, limit, offset).enqueue(new Callback<TripListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TripListResponse> call, @NonNull Response<TripListResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getList() != null && response.body().getList().size() > 0) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TripListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    responseCallback.onError(t);
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editSharedLocation(int shareId, String token, ShareLocationModel shareLocationModel, ResponseCallback<LocationShareResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callEditShareLocation(shareId, token, shareLocationModel).enqueue(new Callback<LocationShareResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationShareResponse> call, @NonNull Response<LocationShareResponse> response) {
                if (response.isSuccessful()) {
                    responseResponseCallback.onSuccess(response.body());
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LocationShareResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);

            }
        });
    }

    @Override
    public void getMarkerDetails(String token, String markerId, ResponseCallback<MarkerDetailResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", markerId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.getMarkerDetails(token, requestBody).enqueue(new Callback<MarkerDetailResponse>() {
                @Override
                public void onResponse(@NonNull Call<MarkerDetailResponse> call, @NonNull Response<MarkerDetailResponse> response) {
                    if (response.isSuccessful()) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseResponseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MarkerDetailResponse> call, @NonNull Throwable t) {
                    responseResponseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getPinDetails(String token, String markerId, ResponseCallback<PolygonPinDetailsResponse> responseResponseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("location_id", markerId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.getPinDetails(token, requestBody).enqueue(new Callback<PolygonPinDetailsResponse>() {
                @Override
                public void onResponse(@NonNull Call<PolygonPinDetailsResponse> call, @NonNull Response<PolygonPinDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseResponseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PolygonPinDetailsResponse> call, @NonNull Throwable t) {
                    responseResponseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void createLandmarkImage(String token, CreateLandmarkModel createLandmarkModel, ResponseCallback<LandmarkCreateResponseV2> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateLandmarkImage(token, createLandmarkModel).enqueue(new Callback<LandmarkCreateResponseV2>() {
            @Override
            public void onResponse(@NonNull Call<LandmarkCreateResponseV2> call, @NonNull Response<LandmarkCreateResponseV2> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Landmark image uploaded successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                        Log.e("ErrMessa","tau "+new Exception(response.message()));

                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                    Log.e("ErrMessa","tau "+new Exception(response.message()));

                }
            }

            @Override
            public void onFailure(@NonNull Call<LandmarkCreateResponseV2> call, @NonNull Throwable t) {
                responseCallback.onError(t);
                Log.e("ErrMessa","tau "+t.toString());


            }
        });
    }

    @Override
    public void createPolygonPinImage(String token, PolygonPinIImageUploadModel polygonPinIImageUploadModel, ResponseCallback<PolygonImageUploadRespone> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreatePolygonPinImage(token, polygonPinIImageUploadModel).enqueue(new Callback<PolygonImageUploadRespone>() {
            @Override
            public void onResponse(@NonNull Call<PolygonImageUploadRespone> call, @NonNull Response<PolygonImageUploadRespone> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Landmark image uploaded successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PolygonImageUploadRespone> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });
        
    }

    @Override
    public void deleteLandmarkImage(String token, int landmarkId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", landmarkId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteLandmarkImage(token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteHazardPinImage(String token, int landmarkId, ResponseCallback<DeleteHazardImageResponse> responseCallback) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", landmarkId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteHazardPinImage(token, requestBody).enqueue(new Callback<DeleteHazardImageResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeleteHazardImageResponse> call, @NonNull Response<DeleteHazardImageResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeleteHazardImageResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void tripStart(String token, int tripId, String tripStart, ResponseCallback<TripStartResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            jsonObject.put("trip_status", tripStart);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callTripStart(token, requestBody).enqueue(new Callback<TripStartResponse>() {
                @Override
                public void onResponse(@NonNull Call<TripStartResponse> call, @NonNull Response<TripStartResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equalsIgnoreCase("Route started successfully") ||
                                response.body().getSuccess().getMessage().equalsIgnoreCase("Route stopped successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }

                }

                @Override
                public void onFailure(@NonNull Call<TripStartResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }
    }

    @Override
    public void shareTripLocation(String token, int tripId, double latitude, double longitude, ResponseCallback<SharingTripLocationResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            jsonObject.put("lat", latitude);
            jsonObject.put("lon", longitude);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callShareTripLocation(token, requestBody).enqueue(new Callback<SharingTripLocationResponse>() {
                @Override
                public void onResponse(@NonNull Call<SharingTripLocationResponse> call, @NonNull Response<SharingTripLocationResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equalsIgnoreCase("Route Location share successfully")) {
                            responseCallback.onSuccess(response.body());

                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }

                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SharingTripLocationResponse> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }
    }

    @Override
    public void getTripLocation(String token, int tripId, String getLocation,/* int userId,*/ ResponseCallback<GetTripLocationModel> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            jsonObject.put("get_location", getLocation);
            /*jsonObject.put("user_id", userId);*/
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callGetTripLocation(token, requestBody).enqueue(new Callback<GetTripLocationModel>() {
                @Override
                public void onResponse(@NonNull Call<GetTripLocationModel> call, @NonNull Response<GetTripLocationModel> response) {
                    if (response.isSuccessful()) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetTripLocationModel> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            responseCallback.onError(e);
        }

    }

    @Override
    public void postCrimeReport(String toke, PostCrimeReportModel postCrimeReportModel, ResponseCallback<PostCrimeReportResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callPostCrimeReport(toke, postCrimeReportModel).enqueue(new Callback<PostCrimeReportResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostCrimeReportResponse> call, @NonNull Response<PostCrimeReportResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null && response.body().getSuccess().getMessage().equals("Your feedback added successfully,Thanks")) {//Your feedback added successfully, Thanks--Create report map successfully
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostCrimeReportResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getSecurityCheckList(String token, int tripId, ResponseCallback<SecurityChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSecurityChecklist(token, tripId).enqueue(new Callback<SecurityChecklistResponse>() {
            @Override
            public void onResponse(@NonNull Call<SecurityChecklistResponse> call, @NonNull Response<SecurityChecklistResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getChecklist() != null && response.body().getChecklist().size() > 0) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "No checklist listed yet", Toast.LENGTH_SHORT).show();
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }

                } else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SecurityChecklistResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });
    }

    @Override
    public void createChecklist(String token, CreateChecklistModel createChecklistModel, ResponseCallback<CreateChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateChecklist(token, createChecklistModel).enqueue(new Callback<CreateChecklistResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateChecklistResponse> call, @NonNull Response<CreateChecklistResponse> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body());

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateChecklistResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void deleteSecurityChecklist(String token, int checklistId, int categoryId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("checklistId", checklistId);
            jsonObject.put("categoryId", categoryId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteSecurityChecklist(token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }

                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateChecklist(String token, UpdateChecklistModel updateChecklistModel, ResponseCallback<UpdateChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUpdateSecurityChecklist(token, updateChecklistModel).enqueue(new Callback<UpdateChecklistResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateChecklistResponse> call, @NonNull Response<UpdateChecklistResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Update successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateChecklistResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });
    }

    @Override
    public void createWaypoint(String token, WaypointModel waypointModel, ResponseCallback<WaypointResponse> responseResponseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callAddWaypoint(token, waypointModel).enqueue(new Callback<WaypointResponse>() {
            @Override
            public void onResponse(@NonNull Call<WaypointResponse> call, @NonNull Response<WaypointResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("Waypoint added successfully")) {
                        responseResponseCallback.onSuccess(response.body());
                    } else {
                        responseResponseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseResponseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NonNull Call<WaypointResponse> call, @NonNull Throwable t) {
                responseResponseCallback.onError(t);
            }
        });

    }

    @Override
    public void createBasicTrip(String token, CreateTripModelClass dataBean, ResponseCallback<UpdatedCreateTripResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateTrip(token, dataBean).enqueue(new Callback<UpdatedCreateTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Response<UpdatedCreateTripResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("Create route successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));

                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void getSharedTripDetails(String token, int sharedtripid, ResponseCallback<MyTripDescriptionModel> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSharedTripDetails(token, sharedtripid).enqueue(new Callback<MyTripDescriptionModel>() {
            @Override
            public void onResponse(@NonNull Call<MyTripDescriptionModel> call, @NonNull Response<MyTripDescriptionModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                    //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyTripDescriptionModel> call, @NonNull Throwable t) {
                responseCallback.onError(t);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void getMyTrip(String token, int limit, int offset, ResponseCallback<MyTripsResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callMyTrip(token, limit, offset).enqueue(new Callback<MyTripsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyTripsResponse> call, @NonNull Response<MyTripsResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getList() != null) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.toString()));
                            //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "you have no shared trips", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }

                } catch (Exception e) {
                    responseCallback.onError(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyTripsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void addDnagerMarker(String token, HazardMarkerModel hazardMarkerModel, ResponseCallback<TripMarkerCreateResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callAddDangerMarker(token, hazardMarkerModel).enqueue(new Callback<TripMarkerCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripMarkerCreateResponse> call, @NonNull Response<TripMarkerCreateResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().getMessage().equals("Create marks successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.toString()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TripMarkerCreateResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getBadgeCount(String token, ResponseCallback<BadgeCountResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callBadgeCount(token).enqueue(new Callback<BadgeCountResponse>() {
            @Override
            public void onResponse(@NonNull Call<BadgeCountResponse> call, @NonNull Response<BadgeCountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Get badge count")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NonNull Call<BadgeCountResponse> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void updateTrip(String token, CreateTripModelClass dataBean, ResponseCallback<UpdatedCreateTripResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUpdateTrip(token, dataBean).enqueue(new Callback<UpdatedCreateTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Response<UpdatedCreateTripResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("Update route successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void deleteContect(String token, int id, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteContact(token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void sendSosEmail(String token, SendSosModel sendSosModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSendSoS(token, sendSosModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("SoS email send successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.errorBody().toString()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void deleteTrip(String token, int tripId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callDeleteTrip(token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NonNull Call<SuccessArray> call, @NonNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Delete successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessArray> call, @NonNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setBadgeCount(String token, SetBadgeModel setBadgeModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSetBadgeCount(token, setBadgeModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().getMessage().equals("Badge updated successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void removeMemberFromGroup(int groupId, String token, int userId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user-id", userId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callRemoveMemberInGroup(groupId, token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().getMessage().equals("Successfully removed member from group") /*||
                                response.body().getSuccess().getMessage().equals("Successfully removed member from group")*/) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                    responseCallback.onError(t);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addMemberFromGroup(int groupId, String token, int userId, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user-id", userId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callAddMemberInGroup(groupId, token, requestBody).enqueue(new Callback<SuccessArray>() {
                @Override
                public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                    if (response.isSuccessful()) {
                        if ((response.body()!=null && response.body().getSuccess()!=null)) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        String errorMessage = Utils.showErrorMessage(response);
                        responseCallback.onError(new Exception(errorMessage));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                    responseCallback.onError(t);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getGroupMember(String token, int groupId, int limit, int offset, ResponseCallback<GroupMemberResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGroupMember(token, groupId, limit, offset).enqueue(new Callback<GroupMemberResponse>() {
            @Override
            public void onResponse(@NotNull Call<GroupMemberResponse> call, @NotNull Response<GroupMemberResponse> response) {
                if (response.isSuccessful()) {
                    if ((response.body() != null) && (response.body().getList() != null)) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<GroupMemberResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getReportInRange(String token, ReportInRangeModel reportInRangeModel, ResponseCallback<ReportInRangeResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callReportInRange(token, reportInRangeModel).enqueue(new Callback<ReportInRangeResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportInRangeResponse> call, @NotNull Response<ReportInRangeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Display report list with feedback")) {
                        if (response.body().getList() != null && response.body().getList().size() > 0) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportInRangeResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void sendfeedbackOverIssue(String token, FeedbackModel feedbackModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callPostFeedback(token, feedbackModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Report feedback update successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void getCategoryWiseChecklist(String token, int id, ResponseCallback<CategoryWiseChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCategoryWise(token, id).enqueue(new Callback<CategoryWiseChecklistResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryWiseChecklistResponse> call, @NotNull Response<CategoryWiseChecklistResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getList() != null && response.body().getList().size() > 0) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryWiseChecklistResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void callArchiveTrip(String token, int limit, int status, ResponseCallback<BeingSharedTripResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callArchivedtrip(token, limit, status).enqueue(new Callback<BeingSharedTripResponse>() {
            @Override
            public void onResponse(@NotNull Call<BeingSharedTripResponse> call, @NotNull Response<BeingSharedTripResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getList() != null) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<BeingSharedTripResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void sendFeedback(String token, SendEmailModel sendEmailModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callSendEmail(token, sendEmailModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess()
                            .getMessage().equals("E-mail send successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void editContact(String token, EditContactModel editContactModel, ResponseCallback<EditContactResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callEditContact(token, editContactModel).enqueue(new Callback<EditContactResponse>() {
            @Override
            public void onResponse(@NotNull Call<EditContactResponse> call, @NotNull Response<EditContactResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess()
                            .getMessage().equals("Update successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<EditContactResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void getCustomChecklist(String token, String status, ResponseCallback<CustomChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCustomChecklist(token, status).enqueue(new Callback<CustomChecklistResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustomChecklistResponse> call, @NotNull Response<CustomChecklistResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getCustomChecklist() != null && response.body().getCustomChecklist().size() > 0) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<CustomChecklistResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void getSubscriptionStatus(String token, SubscriptionModel subscriptionModel, ResponseCallback<SubscriptionResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            apiInterface.getSubscriptionStatus(token, subscriptionModel).enqueue(new Callback<SubscriptionResponse>() {
                @Override
                public void onResponse(@NotNull Call<SubscriptionResponse> call, @NotNull Response<SubscriptionResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            String errorMessage = Utils.showErrorMessage(response);
                            responseCallback.onError(new Exception(errorMessage));
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call<SubscriptionResponse> call, @NotNull Throwable t) {
                    responseCallback.onError(t);

                }
            });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getPackage(String token, String status, ResponseCallback<PackageResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getAllPackage(token, status).enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(@NotNull Call<PackageResponse> call, @NotNull Response<PackageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getAllPackage() != null) {
                        responseCallback.onSuccess(response.body());
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<PackageResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void callPayment(String token, PaymentModel paymentModel, ResponseCallback<PaymentResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callPayment(token, paymentModel).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(@NotNull Call<PaymentResponse> call, @NotNull Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null) {
                        if (response.body().getSuccess().getMessage().equals("Stripe payment successfully")) {
                            responseCallback.onSuccess(response.body());
                        } else {
                            responseCallback.onError(new Exception(response.message()));
                        }
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<PaymentResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);

            }
        });


    }

    @Override
    public void getReportMarkerType(String token, String status, ResponseCallback<ReportListResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callMarkertype(token, status).enqueue(new Callback<ReportListResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportListResponse> call, @NotNull Response<ReportListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMarkerReportList() != null) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportListResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });
    }

    @Override
    public void cancelUserOnTrip(String token, CancelUserModel cancelUserModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCancelUserOnTrip(token, cancelUserModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);

            }
        });

    }

    @Override
    public void callGetCategory(String token, String type, ResponseCallback<CategoryListResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGetCategory(token, type).enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryListResponse> call, @NotNull Response<CategoryListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryListResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void callCreateCategory(String token, CreateCategoryModel createCategoryModel, ResponseCallback<CategoryCreateResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateCategory(token, createCategoryModel).enqueue(new Callback<CategoryCreateResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryCreateResponse> call, @NotNull Response<CategoryCreateResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().getCustomChecklist().equals("Checklist category added successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<CategoryCreateResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void deleteCategory(String token, DeleteCategoryModel deleteCategoryModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callDeleteCategory(token, deleteCategoryModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().getMessage().equals("Category deleted successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });


    }

    @Override
    public void callCreateCategorizedChecklist(String token, CreateCatagorizedChkListModel createCatagorizedChkListModel, ResponseCallback<ChecklistCreateResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateChecklistUnderCategory(token, createCatagorizedChkListModel).enqueue(new Callback<ChecklistCreateResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChecklistCreateResponse> call, @NotNull Response<ChecklistCreateResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().getMessage().equals("Security checklist created successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<ChecklistCreateResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void callTripCheckList(String token, CreateTripChecklistModel createTripChecklistModel, ResponseCallback<CreateTripChecklistResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callCreateTripChecklist(token, createTripChecklistModel).enqueue(new Callback<CreateTripChecklistResponse>() {
            @Override
            public void onResponse(@NotNull Call<CreateTripChecklistResponse> call, @NotNull Response<CreateTripChecklistResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess().getMessage().equalsIgnoreCase("Checklist added with route")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<CreateTripChecklistResponse> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });

    }

    @Override
    public void callDeleteTripChecklist(String token, DeleteTripChecklistModel deleteTripChecklistModel, ResponseCallback<SuccessArray> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callDeleteTripChecklist(token, deleteTripChecklistModel).enqueue(new Callback<SuccessArray>() {
            @Override
            public void onResponse(@NotNull Call<SuccessArray> call, @NotNull Response<SuccessArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() != null && response.body().getSuccess().getMessage().equals("Delete successfully")) {
                        responseCallback.onSuccess(response.body());
                    } else {
                        responseCallback.onError(new Exception(response.message()));
                    }
                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }

            }

            @Override
            public void onFailure(@NotNull Call<SuccessArray> call, @NotNull Throwable t) {
                responseCallback.onError(t);
            }
        });


    }

    @Override
    public void updateGroupName(UpdateGroupNameReqBody body, ResponseCallback<CreateGroupResponse> responseCallback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.editGroupName(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), body).enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(Call<CreateGroupResponse> call, Response<CreateGroupResponse> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body());

                } else {
                    String errorMessage = Utils.showErrorMessage(response);
                    responseCallback.onError(new Exception(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<CreateGroupResponse> call, Throwable t) {
                responseCallback.onError(t);
            }
        });



    }
}
