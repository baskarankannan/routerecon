package com.techacsent.route_recon.retrofit_api;

import com.techacsent.route_recon.model.BadgeCountResponse;
import com.techacsent.route_recon.model.CategoryCreateResponse;
import com.techacsent.route_recon.model.CategoryListResponse;
import com.techacsent.route_recon.model.CategoryWiseChecklistResponse;
import com.techacsent.route_recon.model.ChecklistCreateResponse;
import com.techacsent.route_recon.model.CreateChecklistResponse;
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
import com.techacsent.route_recon.model.sendtrip.SendTripResponse;
import com.techacsent.route_recon.model.tracklisthistory.TrackListHistoryResponse;
import com.techacsent.route_recon.model.TripAcceptResponse;
import com.techacsent.route_recon.model.TripFollowerResponse;
import com.techacsent.route_recon.model.TripStartResponse;
import com.techacsent.route_recon.model.UpdateChecklistResponse;
import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.model.WaypointResponse;
import com.techacsent.route_recon.model.request_body_model.AddMoreUserModel;
import com.techacsent.route_recon.model.AddMoreUserResponse;
import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.BlockedUserResponse;
import com.techacsent.route_recon.model.CancelSharedTripResponse;
import com.techacsent.route_recon.model.ChangePasswordResponse;
import com.techacsent.route_recon.model.CountUserResponse;
import com.techacsent.route_recon.model.CreateAccountResponse;
import com.techacsent.route_recon.model.CreateContactSuccessResponse;
import com.techacsent.route_recon.model.CreateGroupResponse;
import com.techacsent.route_recon.model.request_body_model.CancelUserModel;
import com.techacsent.route_recon.model.request_body_model.CreateAccountModel;
import com.techacsent.route_recon.model.request_body_model.CreateCatagorizedChkListModel;
import com.techacsent.route_recon.model.request_body_model.CreateCategoryModel;
import com.techacsent.route_recon.model.request_body_model.CreateChecklistModel;
import com.techacsent.route_recon.model.request_body_model.CreateLandmarkModel;
import com.techacsent.route_recon.model.request_body_model.CreateTripChecklistModel;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;
import com.techacsent.route_recon.model.DeleteGroupresponse;
import com.techacsent.route_recon.model.request_body_model.DeleteCategoryModel;
import com.techacsent.route_recon.model.request_body_model.DeleteTripChecklistModel;
import com.techacsent.route_recon.model.request_body_model.EditContactModel;
import com.techacsent.route_recon.model.request_body_model.EditGroupModel;
import com.techacsent.route_recon.model.EmergencyContactListResponse;
import com.techacsent.route_recon.model.SharedUserResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.SuccessResponse;
import com.techacsent.route_recon.model.EditProfileResponse;
import com.techacsent.route_recon.model.request_body_model.EditMarkerModel;
import com.techacsent.route_recon.model.request_body_model.EmergenctContactModel;
import com.techacsent.route_recon.model.LocationShareResponse;
import com.techacsent.route_recon.model.request_body_model.FeedbackModel;
import com.techacsent.route_recon.model.request_body_model.HazardMarkerModel;
import com.techacsent.route_recon.model.request_body_model.PaymentModel;
import com.techacsent.route_recon.model.request_body_model.ReportInRangeModel;
import com.techacsent.route_recon.model.request_body_model.SaveLocationModel;
import com.techacsent.route_recon.model.SaveLocationResponse;
import com.techacsent.route_recon.model.ShareLocationModel;
import com.techacsent.route_recon.model.request_body_model.SendEmailModel;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.model.request_body_model.SetBadgeModel;
import com.techacsent.route_recon.model.request_body_model.ShareTripModel;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.model.LoginResponse;
import com.techacsent.route_recon.model.LogoutResponse;
import com.techacsent.route_recon.model.MyTripDescriptionModel;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.model.PendingAcceptResponse;
import com.techacsent.route_recon.model.TripDetailsResponse;
import com.techacsent.route_recon.model.TripListResponse;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;
import com.techacsent.route_recon.model.request_body_model.TripMarkerGetModel;
import com.techacsent.route_recon.model.TripMarkerResponse;
import com.techacsent.route_recon.model.TripShareResponse;
import com.techacsent.route_recon.model.UserDetailsResponse;
import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.model.request_body_model.UpdateChecklistModel;
import com.techacsent.route_recon.model.request_body_model.UpdateGroupNameReqBody;
import com.techacsent.route_recon.model.request_body_model.UserTokenModel;
import com.techacsent.route_recon.model.request_body_model.UserTripSharingResponseModel;
import com.techacsent.route_recon.model.request_body_model.VerifyAccountModel;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.utills.Constant;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("login")
    Call<LoginResponse> callLogin(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("trip-accept")
    Call<TripAcceptResponse> sendTripAcceptRequest(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth,@Body RequestBody requestBody);

    @POST("trip-reject")
    Call<SendTripRejectResponse> sendTripRejectRequest(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);


    @POST("add-short-trip")
    Call<ShortTripResponse> addShortTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);



    @GET("get-short-trip")
    Call<TrackListHistoryResponse> getTrackHistory(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("limit") int limit, @Query("offset") int offset);


    @POST("send-validation-code")
    Call<SuccessArray> callResetPassword(@Body RequestBody requestBody);

    @POST("check-validation-code")
    Call<SuccessArray> callValidationCode(@Body RequestBody requestBody);

    @POST("reset-password")
    Call<SuccessArray> callRecoverAccount(@Body RequestBody requestBody);

    @POST("user-token")
    Call<SuccessArray> callUpdateUserToken(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body UserTokenModel userTokenModel);

    @POST("create-account")
    Call<SuccessArray> createAccount(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateAccountModel requestBody);

    @POST("account-verify")
    Call<CreateAccountResponse> callverifyAccount(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body VerifyAccountModel verifyAccountModel);

    @GET("tripdata")
    Call<TripListResponse> callTripList(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("type") int type, @Query("limit") int limit, @Query("offset") int offset);

    @POST("trip-details")
    Call<TripDetailsResponse> callTripDetails(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("create-trip")
    Call<UpdatedCreateTripResponse> callCreateTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateTripModelClass requestBody);

    @POST("update-trip")
    Call<UpdatedCreateTripResponse> callUpdateTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateTripModelClass requestBody);

    @POST("logout")
    Call<LogoutResponse> callLogout(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth);

    @POST("update-account")
    Call<EditProfileResponse> callEditProfile(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auht, @Body RequestBody requestBody);

    @GET("users")
    Call<UserSearchResponse> callUserSearch(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("username") String username, @Query("limit") int limit, @Query("offset") int offset);

    @GET("users")
    Call<BlockedUserResponse> callBlockList(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("blocked") String yes, @Query("limit") int limit, @Query("offset") int offset);

    @GET("users")
    Call<FollowersResponse> callFollowers(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("is-following") String isFollower, @Query("limit") int limit, @Query("offset") int offset);

    @GET("users")
    Call<TripFollowerResponse> callTripFollower(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("is-following") String isFollower, @Query("trip_id") int tripid, @Query("limit") int limit, @Query("offset") int offset);

    @GET("users")
    Call<FollowersResponse> callFollowings(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("am-follower") String isFollower, @Query("limit") int limit, @Query("offset") int offset);

    @POST("blockuser")
    Call<BlockResponse> callUserBlock(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @GET("my-trips")
    Call<MyTripsResponse> callMyTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("limit") int limit, @Query("offset") int offset);

    @POST("user-follow/{user_id}")
    Call<FollowUnfollowResponse> callFollowUnfollow(@Path(value = "user_id", encoded = true) int userId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("user-follow/{user_id}")
    Call<PendingAcceptResponse> callPendingAction(@Path(value = "user_id", encoded = true) int userId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @GET("my-profile")
    Call<UserDetailsResponse> callUserDetails(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("id") int userId);

    @POST("change-password")
    Call<ChangePasswordResponse> callChangePassword(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @GET("get-follow-and-followers")
    Call<CountUserResponse> callFollowerCount(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("am-follower") String amFollower, @Query("is-following") String isFollowing);

    @POST("sharing-trip")
    Call<TripShareResponse> callShareTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body ShareTripModel requestBody);

    @POST("sharing-trip")
    Call<SendTripResponse> callSendTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body ShareTripModel requestBody);

    @GET("get-send-trips")
    Call<SendRouteListResponse> getSendRouteList(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token,@Query("limit") int limit, @Query("offset") int offset);


    @PUT("sharing-trip")
    Call<AddMoreUserResponse> callAddMoreUser(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body AddMoreUserModel requestBody);

    @GET("get-shared-trip-details")
    Call<MyTripDescriptionModel> callSharedTripDetails(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("trip_sharing_id") int tripSharingId);

    @PUT("cancel-shared-trip")
    Call<CancelSharedTripResponse> callCancelSharedTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("groups")
    Call<CreateGroupResponse> callCreateGroup(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @GET("groups")
    Call<GroupListResponse> callGroupList(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("limit") int limit, @Query("offset") int offset);

    @PUT("groups")
    Call<SuccessResponse> callEditGroup(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body EditGroupModel editGroupModel);

    @HTTP(method = "DELETE", path = "groups", hasBody = true)
    Call<DeleteGroupresponse> callDeteleGroup(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "trip-mark-delete", hasBody = true)
    Call<SuccessArray> callDeleteMarker(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "delete-pin", hasBody = true)
    Call<DeleteHazardPinResponse> deleteHazardPin(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);


    @POST("trip-mark-create")
    Call<TripMarkerCreateResponse> callCreateTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateTripMarkerModelClass createTripMarkerModelClass);

    @POST("trip-mark-update")
    Call<SuccessArray> callEditMarker(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body EditMarkerModel editMarkerModel);

    @POST("pin-update")
    Call<EditHazardPinResponse> editHazardPinSubmit(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body EditHazardPinModel editHazardPinModel);


    @POST("trip-marks")
    Call<TripMarkerResponse> callGetTripMarker(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body TripMarkerGetModel tripMarkerGetModel);

    @POST("live-location")
    Call<LocationShareResponse> callShareLocation(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body ShareLocationModel shareLocationModel);

    @PUT("live-location/{share_location_id}")
    Call<LocationShareResponse> callEditShareLocation(@Path(value = "share_location_id", encoded = true) int shareLocationId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body ShareLocationModel shareLocationModel);

    @HTTP(method = "DELETE", path = "my-location/{share_location_id}")
    Call<SuccessArray> callDeleteShareLocation(@Path(value = "share_location_id", encoded = true) int shareId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth);

    //share location to other user
    @POST("my-location/{share_location_id}")
    Call<SaveLocationResponse> callSaveLocation(@Path(value = "share_location_id", encoded = true) int shareLocationId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body SaveLocationModel saveLocationModel);

    @POST("create-contact")
    Call<CreateContactSuccessResponse> callCreateContect(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body EmergenctContactModel emergenctContactModel);

    @GET("get-shared-trips")
    Call<BeingSharedTripResponse> callGetSharedTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("status") int status, @Query("limit") int limit, @Query("offset") int offset);

    @PUT("user-trip-sharing-response")
    Call<SuccessArray> callUserActionForTripAttending(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body UserTripSharingResponseModel userTripSharingResponseModel);

    @GET("emergency-contact")
    Call<EmergencyContactListResponse> callGetEmergencyContactList(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth);

    //live location share
    @GET("users")
    Call<SharedUserResponse> getSharedLocationUser(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("live-location") String liveLocation, @Query("limit") int limit, @Query("offset") int offset);

    @POST("get-mark-details")
    Call<MarkerDetailResponse> getMarkerDetails(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("get-pin-details")
    Call<PolygonPinDetailsResponse> getPinDetails(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("upload-image")
    Call<LandmarkCreateResponseV2> callCreateLandmarkImage(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateLandmarkModel createLandmarkModel);

    @POST("upload-image")
    Call<PolygonImageUploadRespone> callCreatePolygonPinImage(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body PolygonPinIImageUploadModel polygonPinIImageUploadModel);



    @HTTP(method = "DELETE", path = "delete-image", hasBody = true)
    Call<SuccessArray> callDeleteLandmarkImage(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);



    @HTTP(method = "DELETE", path = "delete-pin-image", hasBody = true)
    Call<DeleteHazardImageResponse> callDeleteHazardPinImage(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);


    @POST("start-trip")
    Call<TripStartResponse> callTripStart(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("sharing-trip-location")
    Call<SharingTripLocationResponse> callShareTripLocation(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("get-trip-location")
    Call<GetTripLocationModel> callGetTripLocation(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("report-map-create")
    Call<PostCrimeReportResponse> callPostCrimeReport(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body PostCrimeReportModel postCrimeReportModel);

    @GET("tripwise-checklist")
    Call<SecurityChecklistResponse> callSecurityChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("trip_id") int tripId);

    @POST("create-checklist")
    Call<CreateChecklistResponse> callCreateChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateChecklistModel createChecklistModel);

    @HTTP(method = "DELETE", path = "delete-checklist", hasBody = true)
    Call<SuccessArray> callDeleteSecurityChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("update-checklist")
    Call<UpdateChecklistResponse> callUpdateSecurityChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body UpdateChecklistModel updateChecklistModel);

    @POST("add-waypoint")
    Call<WaypointResponse> callAddWaypoint(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body WaypointModel waypointModel);

    @POST("trip-mark-create")
    Call<TripMarkerCreateResponse> callAddDangerMarker(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body HazardMarkerModel hazardMarkerModel);

    @GET("get-badge-counts")
    Call<BadgeCountResponse> callBadgeCount(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth);

    @HTTP(method = "DELETE", path = "delete-contact", hasBody = true)
    Call<SuccessArray> callDeleteContact(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @POST("send-sos-email")
    Call<SuccessArray> callSendSoS(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body SendSosModel sendSosModel);

    @HTTP(method = "DELETE", path = "tripdata", hasBody = true)
    Call<SuccessArray> callDeleteTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body RequestBody requestBody);

    @PUT("set-badge-count")
    Call<SuccessArray> callSetBadgeCount(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body SetBadgeModel setBadgeModel);

    @HTTP(method = "DELETE", path = "groups-users/{group_id}", hasBody = true)
    Call<SuccessArray> callRemoveMemberInGroup(@Path(value = "group_id", encoded = true) int groupId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body RequestBody saveLocationModel);

    @POST("groups-users/{group_id}")
    Call<SuccessArray> callAddMemberInGroup(@Path(value = "group_id", encoded = true) int groupId, @Header(Constant.X_TRAVEL_PLANNER_TOKEN) String token, @Body RequestBody saveLocationModel);

    @GET("members")
    Call<GroupMemberResponse> callGroupMember(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("group_id") int groupId, @Query("limit") int limit, @Query("offset") int offset);

    @POST("report-in-range")
    Call<ReportInRangeResponse> callReportInRange(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body ReportInRangeModel reportInRangeModel);

    @POST("report-feedback")
    Call<SuccessArray> callPostFeedback(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body FeedbackModel feedbackModel);

    @GET("checklist-categorywise")
    Call<CategoryWiseChecklistResponse> callCategoryWise(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("categoryId") int id);

    @GET("get-archived-shared-trips")
    Call<BeingSharedTripResponse> callArchivedtrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("limit") int limit, @Query("offset") int offset);

    @POST("send-user-email")
    Call<SuccessArray> callSendEmail(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body SendEmailModel sendEmailModel);

    @POST("update-contact")
    Call<EditContactResponse> callEditContact(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body EditContactModel editContactModel);

    @GET("all-categories-checklist")
    Call<CustomChecklistResponse> callCustomChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("status") String status);

    @POST("subscription-status")
    Call<SubscriptionResponse> getSubscriptionStatus(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body SubscriptionModel subscriptionModel);

    @GET("get-package-info")
    Call<PackageResponse> getAllPackage(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("package_status") String status);

    @POST("package-stripe-payment")
    Call<PaymentResponse> callPayment(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body PaymentModel paymentModel);

    @GET("marker-report-list")
    Call<ReportListResponse> callMarkertype(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("status") String status);

    @PUT("edit-user-shared-trip")
    Call<SuccessArray> callCancelUserOnTrip(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CancelUserModel cancelUserModel);

    @GET("category-list")
    Call<CategoryListResponse> callGetCategory(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Query("category_type") String type);

    @POST("add-checklist-category")
    Call<CategoryCreateResponse> callCreateCategory(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateCategoryModel createCategoryModel);

    @HTTP(method = "DELETE", path = "delete-checklist-category", hasBody = true)
    Call<SuccessArray> callDeleteCategory(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body DeleteCategoryModel deleteCategoryModel);

    @HTTP(method = "DELETE", path = "delete-trip-checklist", hasBody = true)
    Call<SuccessArray> callDeleteTripChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body DeleteTripChecklistModel deleteTripChecklistModel);

    @POST("create-checklist")
    Call<ChecklistCreateResponse> callCreateChecklistUnderCategory(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateCatagorizedChkListModel chkListModel);

    @POST("checklist-addto-trip")
    Call<CreateTripChecklistResponse> callCreateTripChecklist(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body CreateTripChecklistModel createTripChecklistModel);

    @PUT("groups")
    Call<CreateGroupResponse> editGroupName(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth, @Body UpdateGroupNameReqBody body);


    @POST("trip-accept")
    Call<TripAcceptResponse> acceptTripRequest(@Header(Constant.X_TRAVEL_PLANNER_TOKEN) String auth,@Body RequestBody requestBody);




}
