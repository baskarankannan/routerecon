package com.techacsent.route_recon.utills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnDialogViewItemClickListener;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;


public class ShowDialog {



    public static void showUpdateAppDialog(final AppCompatActivity appCompatActivity, final String update_location,

                                           OnDialogViewItemClickListener onDialogViewItemClickListener) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup

        //then we will inflate the custom alert dialog xml th«at we created
        ViewGroup viewGroup = appCompatActivity.findViewById(R.id.content);
        View dialogView = LayoutInflater.from(appCompatActivity).inflate(R.layout.dialog_track_confirmation, viewGroup, false);

        //   Button confirmBtn = dialogView.findViewById(R.id.btn_yes);
        Button buttonYes = dialogView.findViewById(R.id.button_dailog_yes);
        Button buttonNo = dialogView.findViewById(R.id.button_dailog_no);

        TextView textViewTitle = dialogView.findViewById(R.id.text_view_dialog_title);
        TextView textViewDeatils = dialogView.findViewById(R.id.text_view_dialog_details);

        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(appCompatActivity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
       // textViewTitle.setText("Forbidden!");

        //textViewDeatils.setText("Skipped user is not allowed to change character");


        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onDialogViewItemClickListener.onClickYes();
                alertDialog.dismiss();

            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public static void showSendRequestReceivedDialog(final AppCompatActivity appCompatActivity, final String receiverName,
                                                      final String tripId,String routeName, OnDialogViewReceivedSendRequestListener listener) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup

        //then we will inflate the custom alert dialog xml th«at we created
        ViewGroup viewGroup = appCompatActivity.findViewById(R.id.content);
        View dialogView = LayoutInflater.from(appCompatActivity).inflate(R.layout.dialog_send_trip_received_confirmation, viewGroup, false);

        //   Button confirmBtn = dialogView.findViewById(R.id.btn_yes);
        Button buttonYes = dialogView.findViewById(R.id.button_dailog_yes);
        Button buttonNo = dialogView.findViewById(R.id.button_dailog_no);

        TextView textViewTitle = dialogView.findViewById(R.id.text_view_dialog_title);
        TextView textViewDeatils = dialogView.findViewById(R.id.text_view_dialog_details);

        textViewTitle.setText(receiverName+" "+ "is sending you route "+ routeName);

        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(appCompatActivity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        // textViewTitle.setText("Forbidden!");

        //textViewDeatils.setText("Skipped user is not allowed to change character");


        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.onAccept(tripId);
                alertDialog.dismiss();

            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onDenied();
            }
        });

        alertDialog.show();
    }

    public static void showSaveTrackedInfoDialog(final AppCompatActivity appCompatActivity,OnDialogViewReceivedSendRequestListener listener) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup

        //then we will inflate the custom alert dialog xml th«at we created
        ViewGroup viewGroup = appCompatActivity.findViewById(R.id.content);
        View dialogView = LayoutInflater.from(appCompatActivity).inflate(R.layout.dialog_save_track_data, viewGroup, false);

        //   Button confirmBtn = dialogView.findViewById(R.id.btn_yes);
        Button buttonSave = dialogView.findViewById(R.id.button_dailog_save);
        Button buttonCancel= dialogView.findViewById(R.id.button_dailog_cancel);
        EditText editTextName = dialogView.findViewById(R.id.edit_text_name);

        TextView textViewTitle = dialogView.findViewById(R.id.text_view_dialog_title);
        TextView textViewDeatils = dialogView.findViewById(R.id.text_view_dialog_details);

        //textViewTitle.setText(receiverName+" "+ "is sending you route "+ routeName);

        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(appCompatActivity);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        // textViewTitle.setText("Forbidden!");

        //textViewDeatils.setText("Skipped user is not allowed to change character");


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name  = editTextName.getText().toString();

                if(name.isEmpty()){

                    Toast.makeText(appCompatActivity, "Please provide trip name", Toast.LENGTH_SHORT).show();
                }else{

                    listener.onAccept(name);
                    alertDialog.dismiss();
                }




            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

                listener.onDenied();
            }
        });

        alertDialog.show();
    }

}
