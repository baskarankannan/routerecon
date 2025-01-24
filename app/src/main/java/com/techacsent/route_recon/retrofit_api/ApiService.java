package com.techacsent.route_recon.retrofit_api;

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

public interface ApiService {

    void updateFirebaseToken( String token, UserTokenModel userTokenModel, ResponseCallback<SuccessArray> responseCallback);

    void sendTripAcceptRequest( String token,String trip_id,  ResponseCallback<TripAcceptResponse> responseCallback);

    void sendTripRejectRequest( String token,String trip_id,  ResponseCallback<SendTripRejectResponse> responseCallback);

    void addShortTrip( String token, String name,String time, String speed, String distance,
                       String start_lat, String start_long, String end_lat, String end_long,  ResponseCallback<ShortTripResponse> responseCallback);


    void getTrackHistoryList(String token, int limit, int offset, ResponseCallback<TrackListHistoryResponse> responseCallback);


    void resetPassword(String email, ResponseCallback<SuccessArray> responseCallback);

    void validateCode(String email, String verificationCode, ResponseCallback<SuccessArray> responseCallback);

    void recoverAccount(String email, String verificationCode, String password, ResponseCallback<SuccessArray> responseCallback);

    void unblockUser(String token, String shouldBlock, String blockId, int position, ResponseCallback<BlockResponse> responseCallback);

    void login(String email, String password, ResponseCallback<LoginResponse> responseCallback);

    void createAccount(CreateAccountModel createAccountModel, ResponseCallback<SuccessArray> responseCallback);

    void verifyAccount(String token, VerifyAccountModel verifyAccountModel, ResponseCallback<CreateAccountResponse> responseCallback);

    void searchUserByName(String token, String username, int limit, int offset, ResponseCallback<UserSearchResponse> responseCallback);

    void followUnfollowUser(int path, String token, String amFollower, ResponseCallback<FollowUnfollowResponse> responseCallback);

    void deleteGroup(String token, String groupId, ResponseCallback<DeleteGroupresponse> responseCallback);

    void followUnfollow(int path, String token, String amFollower, ResponseCallback<FollowUnfollowResponse> responseCallback);

    void createTripMarker(String token, CreateTripMarkerModelClass createTripMarkerModelClass, ResponseCallback<TripMarkerCreateResponse> responseResponseCallback);

    void getTripMarker(String token, TripMarkerGetModel tripMarkerGetModel, ResponseCallback<TripMarkerResponse> responseResponseCallback);

    void shareLiveLocation(String token, ShareLocationModel shareLocationModel, ResponseCallback<LocationShareResponse> responseResponseCallback);

    void saveCurrentLocation(int shareLocationId, String token, SaveLocationModel saveLocationModel, ResponseCallback<SaveLocationResponse> responseResponseCallback);

    void createEmergencyContact(String token, EmergenctContactModel emergenctContactModel, ResponseCallback<CreateContactSuccessResponse> responseResponseCallback);

    void editGroupInfo(String token, EditGroupModel editGroupModel, ResponseCallback<SuccessResponse> responseResponseCallback);

    void deleteTripMarker(String token, String tripId, ResponseCallback<SuccessArray> responseCallback);

    void deleteHazardPin(String token, int pointerId, ResponseCallback<DeleteHazardPinResponse> responseCallback);

    void getSharedTrip(String token, int status, int limit, int offset, ResponseCallback<BeingSharedTripResponse> responseCallback);

    void getSendRouteList(String token,ResponseCallback<SendRouteListResponse> responseCallback);

    void callUserActionForTripAttending(String token, UserTripSharingResponseModel userTripSharingResponseModel, ResponseCallback<SuccessArray> responseCallback);

    void getContactList(String token, ResponseCallback<EmergencyContactListResponse> responseResponseCallback);

    void getSharedLocationUser(String token, String liveLocation, int limit, int offset, ResponseCallback<SharedUserResponse> responseResponseCallback);

    void editTripMarker(String token, EditMarkerModel editMarkerModel, ResponseCallback<SuccessArray> responseCallback);

    void editHazardPinMarker(String token, EditHazardPinModel editHazardPinModel, ResponseCallback<EditHazardPinResponse> responseCallback);

    void deleteShareLiveLocation(int path, String token, ResponseCallback<SuccessArray> responseCallback);

    void getAllTripList(String token, int type, int limit, int offset, ResponseCallback<TripListResponse> responseCallback);

    void editSharedLocation(int shareId, String token, ShareLocationModel shareLocationModel, ResponseCallback<LocationShareResponse> responseResponseCallback);

    void getMarkerDetails(String token, String markerId, ResponseCallback<MarkerDetailResponse> responseResponseCallback);

    void getPinDetails(String token, String markerId, ResponseCallback<PolygonPinDetailsResponse> responseResponseCallback);

    void createLandmarkImage(String token, CreateLandmarkModel createLandmarkModel, ResponseCallback<LandmarkCreateResponseV2> responseCallback);

    void createPolygonPinImage(String token, PolygonPinIImageUploadModel polygonPinIImageUploadModel, ResponseCallback<PolygonImageUploadRespone> responseCallback);

    void deleteLandmarkImage(String token, int landmarkId, ResponseCallback<SuccessArray> responseCallback);

    void deleteHazardPinImage(String token, int landmarkId, ResponseCallback<DeleteHazardImageResponse> responseCallback);

    void tripStart(String token, int tripId, String tripStart, ResponseCallback<TripStartResponse> responseCallback);

    void shareTripLocation(String token, int tripId, double latitude, double longitude, ResponseCallback<SharingTripLocationResponse> responseCallback);

    void getTripLocation(String token, int tripId, String getLocation, /*int userId,*/ ResponseCallback<GetTripLocationModel> responseCallback);

    void postCrimeReport(String toke, PostCrimeReportModel postCrimeReportModel, ResponseCallback<PostCrimeReportResponse> responseCallback);

    void getSecurityCheckList(String token, int tripId, ResponseCallback<SecurityChecklistResponse> responseCallback);

    void createChecklist(String token, CreateChecklistModel createChecklistModel, ResponseCallback<CreateChecklistResponse> responseCallback);

    void deleteSecurityChecklist(String token, int checklistId, int categoryId, ResponseCallback<SuccessArray> responseCallback);

    void updateChecklist(String token, UpdateChecklistModel updateChecklistModel, ResponseCallback<UpdateChecklistResponse> responseCallback);

    void createWaypoint(String token, WaypointModel waypointModel, ResponseCallback<WaypointResponse> responseResponseCallback);

    void createBasicTrip(String token, CreateTripModelClass dataBean, ResponseCallback<UpdatedCreateTripResponse> responseCallback);

    void getSharedTripDetails(String token, int sharedtripid, ResponseCallback<MyTripDescriptionModel> responseCallback);

    void getMyTrip(String token, int limit, int offset, ResponseCallback<MyTripsResponse> responseCallback);

    void addDnagerMarker(String token, HazardMarkerModel hazardMarkerModel, ResponseCallback<TripMarkerCreateResponse> responseCallback);

    void getBadgeCount(String token, ResponseCallback<BadgeCountResponse> responseCallback);

    void updateTrip(String token, CreateTripModelClass dataBean, ResponseCallback<UpdatedCreateTripResponse> responseCallback);

    void deleteContect(String token, int id, ResponseCallback<SuccessArray> responseCallback);

    void sendSosEmail(String token, SendSosModel sendSosModel, ResponseCallback<SuccessArray> responseCallback);

    void deleteTrip(String token, int tripId, ResponseCallback<SuccessArray> responseCallback);

    void setBadgeCount(String token, SetBadgeModel setBadgeModel, ResponseCallback<SuccessArray> responseCallback);

    void removeMemberFromGroup(int groupId, String token, int userId, ResponseCallback<SuccessArray> responseCallback);

    void addMemberFromGroup(int groupId, String token, int userId, ResponseCallback<SuccessArray> responseCallback);

    void getGroupMember(String token, int groupId, int limit, int offset, ResponseCallback<GroupMemberResponse> responseCallback);

    void getReportInRange(String token, ReportInRangeModel reportInRangeModel, ResponseCallback<ReportInRangeResponse> responseCallback);

    void sendfeedbackOverIssue(String token, FeedbackModel feedbackModel, ResponseCallback<SuccessArray> responseCallback);

    void getCategoryWiseChecklist(String token, int id, ResponseCallback<CategoryWiseChecklistResponse> responseCallback);

    void callArchiveTrip(String token, int limit, int offset, ResponseCallback<BeingSharedTripResponse> responseCallback);

    void sendFeedback(String token, SendEmailModel sendEmailModel, ResponseCallback<SuccessArray> responseCallback);

    void editContact(String token, EditContactModel editContactModel, ResponseCallback<EditContactResponse> responseCallback);

    void getCustomChecklist(String token, String status, ResponseCallback<CustomChecklistResponse> responseCallback);

    void getSubscriptionStatus(String token, SubscriptionModel subscriptionModel, ResponseCallback<SubscriptionResponse> responseCallback);

    void getPackage(String token, String status, ResponseCallback<PackageResponse> responseCallback);

    void callPayment(String token, PaymentModel paymentModel, ResponseCallback<PaymentResponse> responseCallback);

    void getReportMarkerType(String token, String status, ResponseCallback<ReportListResponse> responseCallback);

    void cancelUserOnTrip(String token, CancelUserModel cancelUserModel, ResponseCallback<SuccessArray> responseCallback);

    void callGetCategory(String token, String type, ResponseCallback<CategoryListResponse> responseCallback);

    void callCreateCategory(String token, CreateCategoryModel createCategoryModel, ResponseCallback<CategoryCreateResponse> responseCallback);

    void deleteCategory(String token, DeleteCategoryModel deleteCategoryModel, ResponseCallback<SuccessArray> responseCallback);

    void callCreateCategorizedChecklist(String token, CreateCatagorizedChkListModel createCatagorizedChkListModel, ResponseCallback<ChecklistCreateResponse> responseCallback);

    void callTripCheckList(String token, CreateTripChecklistModel createTripChecklistModel, ResponseCallback<CreateTripChecklistResponse> responseCallback);

    void callDeleteTripChecklist(String token, DeleteTripChecklistModel deleteTripChecklistModel, ResponseCallback<SuccessArray> responseCallback);
    void updateGroupName(UpdateGroupNameReqBody body, ResponseCallback<CreateGroupResponse> responseCallback);

}
