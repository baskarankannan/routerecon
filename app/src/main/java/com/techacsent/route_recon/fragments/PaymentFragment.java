package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardMultilineWidget;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.PackageResponse;
import com.techacsent.route_recon.model.PaymentResponse;
import com.techacsent.route_recon.model.request_body_model.PaymentModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.service.RouteReconIntentService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private CardMultilineWidget cardWidget;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private PackageResponse.AllPackageBean item;


    public PaymentFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable("parcel");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivClose = view.findViewById(R.id.iv_close);
        cardWidget = view.findViewById(R.id.card_multiline_widget);
        Button btnConfirm = view.findViewById(R.id.btn_confirm_payment);

        ivClose.setOnClickListener(v -> requireActivity().finish());

        btnConfirm.setOnClickListener(v -> makePayment());
    }


    private void makePayment() {
        Card card = cardWidget.getCard();
        if (card == null) {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "Invalid Card Data",
                    Toast.LENGTH_SHORT).show();
        } else {
            fragmentActivityCommunication.showProgressDialog(true);
            Intent intent = new Intent(getActivity(), RouteReconIntentService.class);
            intent.setAction(RouteReconIntentService.ACTION_MAKE_PAYMENT);
            Bundle bundle = new Bundle();
            bundle.putString("card_number", card.getNumber());
            bundle.putString("cvc", card.getCvc());
            bundle.putInt("product_id", item.getId());
            bundle.putString("zip", card.getAddressZip());
            if (card.getExpYear() != null) {
                bundle.putInt("year", card.getExpYear());
            }
            if (card.getExpMonth() != null) {
                bundle.putInt("month", card.getExpMonth());
            }
            bundle.putDouble("balance", item.getPrice());
            intent.putExtras(bundle);
            intent.putExtra(RouteReconIntentService.ACTION_GET_PAYMENT_RESULT, new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    fragmentActivityCommunication.showProgressDialog(false);
                    boolean isFinished = resultData.getBoolean("is_finished");
                    if (isFinished) {
                        requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getActivity().finish();
                    }else {
                        fragmentActivityCommunication.showProgressDialog(false);
                    }
                }
            });
            requireActivity().startService(intent);
        }

    }

    private void setPayment() {
        Card card = cardWidget.getCard();
        if (card == null) {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "Invalid Card Data",
                    Toast.LENGTH_SHORT).show();
        } else {
            fragmentActivityCommunication.hideBottomNav(false);
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setPackageId(item.getId());
            paymentModel.setPayableAmount(item.getPrice());
            paymentModel.setCardNumber(card.getNumber());
            if (card.getExpMonth() != null) {
                paymentModel.setExpirationMonth(card.getExpMonth());
            }
            if (card.getExpYear() != null) {
                paymentModel.setExpirationYear(card.getExpYear());
            }
/*
            paymentModel.setCvc(card.getCVC());
*/
            paymentModel.setBillingZip(card.getAddressZip());
            paymentModel.setDeviceId(Utils.getDeviceId());
            paymentModel.setRecipientEmail(PreferenceManager.getString(Constant.KEY_USER_EMAIL));

            ApiService apiService = new ApiCaller();
            apiService.callPayment(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), paymentModel, new ResponseCallback<PaymentResponse>() {
                @Override
                public void onSuccess(PaymentResponse data) {
                    PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, "notpayable");
                    PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_DATE, data.getPackages().getCurrentDateTime());
                    requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().finish();
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    fragmentActivityCommunication.hideBottomNav(true);

                }

                @Override
                public void onError(Throwable th) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    th.printStackTrace();
                    fragmentActivityCommunication.hideBottomNav(true);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }
}
