package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.EmergencyContactAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ContactChooseObject;
import com.techacsent.route_recon.event_bus_object.ContactInfoObj;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnEmergencyContactItemClickListener;
import com.techacsent.route_recon.model.EditContactResponse;
import com.techacsent.route_recon.model.EmergencyContactListResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.EditContactModel;
import com.techacsent.route_recon.model.request_body_model.EmergenctContactModel;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.ContactDescription;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.EmergencyContactViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EmergencyFragment extends Fragment implements OnEmergencyContactItemClickListener<ContactDescription> {
    private ImageView ivCallEmergency;
    private Button btnAddContact;
    private Button btnSendSos;
    private RecyclerView rvContactList;
    private EmergencyContactAdapter mAdapter;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private static final int PICK_CONTACT = 1;
    private static final int REQUEST_PHONE_CALL = 11;
    private String mPermission = Manifest.permission.CALL_PHONE;
    private EmergencyContactViewModel emergencyContactViewModel;
    private LocationService locationService;
    private TextView tvSuccess;
    Handler handler = new Handler();


    public EmergencyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emergency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(Constant.EMERGENCY_SOS);
        locationService = new LocationService(RouteApplication.getInstance().getApplicationContext());
        emergencyContactViewModel = ViewModelProviders.of(this).get(EmergencyContactViewModel.class);
        initializeView(view);
        initializeListener();
        /*if (!PreferenceManager.getBool(Constant.KEY_IS_CONTACT_SYNCED)) {
            loadContactList();
        } else {
            initAdapter();
        }*/
        loadContactList();
    }

    private void initializeView(View view) {
        tvSuccess = view.findViewById(R.id.tv_success);
        ivCallEmergency = view.findViewById(R.id.iv_emergency_call);
        btnAddContact = view.findViewById(R.id.btn_add_contact);
        btnSendSos = view.findViewById(R.id.btn_send_sos);
        rvContactList = view.findViewById(R.id.rv_contact_list);
        rvContactList.invalidate();
        rvContactList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContactList.setItemAnimator(new DefaultItemAnimator());
        rvContactList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initializeListener() {
        ivCallEmergency.setOnClickListener(v -> call911());

        btnSendSos.setOnClickListener(v -> {
            SosFragment sosFragment = new SosFragment();
            sosFragment.show(getChildFragmentManager(), null);

        });

        btnAddContact.setOnClickListener(v -> {
            ContactChooserFragment navigationOptionFragment = new ContactChooserFragment();
            navigationOptionFragment.show(getChildFragmentManager(), navigationOptionFragment.getTag());
            /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);*/
        });
    }

    private void loadContactList() {
        fragmentActivityCommunication.showProgressDialog(true);
        emergencyContactViewModel.getEmergencyContactList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID)).observe(this, emergencyContactListResponse -> {
            if (emergencyContactListResponse != null) {
                AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoContact().nukeTable();
                List<ContactDescription> contactDescriptionList = new ArrayList<>();
                for (EmergencyContactListResponse.SuccessBean.ContactsBean contactsBean : emergencyContactListResponse.getSuccess().getContacts()) {
                    ContactDescription emergenctContactModel = new ContactDescription();
                    emergenctContactModel.setContactName(contactsBean.getContactName());
                    emergenctContactModel.setContactNo(contactsBean.getContactNo());
                    emergenctContactModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
                    emergenctContactModel.setId(contactsBean.getId());
                    emergenctContactModel.setEmail(contactsBean.getEmail());
                    contactDescriptionList.add(emergenctContactModel);
                }
                AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoContact().insertListOfContact(contactDescriptionList);
                fragmentActivityCommunication.showProgressDialog(false);
                //PreferenceManager.updateValue(Constant.KEY_IS_CONTACT_SYNCED, true);
                initAdapter();

            } else {
                fragmentActivityCommunication.showProgressDialog(false);
                initAdapter();
            }
        });
    }

    private void saveContactInServer(EmergenctContactModel emergenctContactModel) {
        fragmentActivityCommunication.showProgressDialog(true);
        emergencyContactViewModel.createEmergencyContact(emergenctContactModel).observe(this, createContactSuccessResponse -> {
            if (createContactSuccessResponse != null) {
                ContactDescription emergenctContactModel1 = new ContactDescription();
                emergenctContactModel1.setContactName(createContactSuccessResponse.getSuccess().getData().getContactName());
                emergenctContactModel1.setContactNo(createContactSuccessResponse.getSuccess().getData().getContactNo());
                emergenctContactModel1.setId(createContactSuccessResponse.getSuccess().getData().getId());
                emergenctContactModel1.setEmail(createContactSuccessResponse.getSuccess().getData().getEmail());

                emergenctContactModel1.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
                AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoContact().insertContact(emergenctContactModel1);
                if (mAdapter != null) {
                    mAdapter.add(emergenctContactModel1);
                    if (mAdapter.getItemCount() == 5) {
                        btnAddContact.setEnabled(false);
                    } else {
                        btnAddContact.setEnabled(true);
                    }
                } else {
                    initAdapter();
                }
                fragmentActivityCommunication.showProgressDialog(false);
            } else {
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });
    }

    private void updateContact(EditContactModel editContactModel, int pos) {
        fragmentActivityCommunication.showProgressDialog(true);
        emergencyContactViewModel.updateEmergencyContact(editContactModel).observe(this, new Observer<EditContactResponse>() {
            @Override
            public void onChanged(@Nullable EditContactResponse editContactResponse) {
                if (editContactResponse != null) {
                    //mAdapter.clear();
                    ContactDescription emergenctContactModel1 = new ContactDescription();
                    emergenctContactModel1.setContactName(editContactResponse.getSuccess().getData().getContactName());
                    emergenctContactModel1.setContactNo(editContactResponse.getSuccess().getData().getContactNo());
                    emergenctContactModel1.setId(editContactResponse.getSuccess().getData().getId());
                    emergenctContactModel1.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
                    emergenctContactModel1.setEmail(editContactResponse.getSuccess().getData().getEmail());
                    AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoContact().updateContact(emergenctContactModel1);
                    mAdapter.updatePosition(pos, emergenctContactModel1);
                    //mAdapter.setItems(AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoContact().fetchContactByUserId(PreferenceManager.getInt(Constant.KEY_USER_ID)));
                    fragmentActivityCommunication.showProgressDialog(false);

                } else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });
    }

    private void deleteContact(ContactDescription item) {
        fragmentActivityCommunication.showProgressDialog(true);
        emergencyContactViewModel.deleteContact(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), item.getId()).observe(this, new Observer<SuccessArray>() {
            @Override
            public void onChanged(@Nullable SuccessArray successArray) {
                if (successArray != null) {
                    AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoContact().deleteContact(item);
                    mAdapter.remove(item);
                    fragmentActivityCommunication.showProgressDialog(false);

                    if (mAdapter.getItemCount() == 5) {
                        btnAddContact.setEnabled(false);
                    } else {
                        btnAddContact.setEnabled(true);
                    }
                } else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });

    }

    private void initAdapter() {
        mAdapter = new EmergencyContactAdapter(getActivity());
        mAdapter.setItems(AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoContact().fetchContactByUserId(PreferenceManager.getInt(Constant.KEY_USER_ID)));
        mAdapter.setListener(this);
        rvContactList.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 5) {
            btnAddContact.setEnabled(false);
        } else {
            btnAddContact.setEnabled(true);
        }
    }

    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Constant.EMERGENCY_NUMBER));
        startActivity(callIntent);
    }

    /*private void call911() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        } else {
            makeCall();
        }
    }*/

    private void call911() {
        Permissions.check(getActivity(), Manifest.permission.CALL_PHONE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                makeCall();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                return super.onBlocked(context, blockedList);
            }

            @Override
            public void onJustBlocked(Context context, ArrayList<String> justBlockedList, ArrayList<String> deniedPermissions) {
                super.onJustBlocked(context, justBlockedList, deniedPermissions);
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData();
                if (uri != null) {
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, projection,
                            null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(numberColumnIndex);
                        int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        String name = cursor.getString(nameColumnIndex);
                        cursor.close();
                        SaveEmergencyContactFragment saveEmergencyContactFragment = new SaveEmergencyContactFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("phone", number);
                        bundle.putBoolean("is_edit", false);
                        saveEmergencyContactFragment.setArguments(bundle);
                        saveEmergencyContactFragment.show(getChildFragmentManager(), null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClicked(ContactDescription item, int itemID, int pos) {
        switch (itemID) {
            case 0:
                AlertSendSoS alertSendSoS = new AlertSendSoS();
                Bundle b = new Bundle();
                b.putString("email", item.getEmail());
                alertSendSoS.setArguments(b);
                alertSendSoS.show(getChildFragmentManager(), null);
                //sendMail(item.getEmail());
                //sendSoS(true, item.getEmail());
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_feature_comming_soon), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                AlertDeleteContact alertDeleteContact = new AlertDeleteContact();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("parcel", item);
                alertDeleteContact.setArguments(bundle1);
                alertDeleteContact.show(getChildFragmentManager(), null);
                //deleteContact(item);
                break;
            case 2:
                SaveEmergencyContactFragment saveEmergencyContactFragment = new SaveEmergencyContactFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", item.getContactName());
                bundle.putString("phone", item.getContactNo());
                bundle.putString("email", item.getEmail());
                bundle.putBoolean("is_edit", true);
                bundle.putInt("id", item.getId());
                bundle.putInt("pos", pos);
                saveEmergencyContactFragment.setArguments(bundle);
                saveEmergencyContactFragment.show(getChildFragmentManager(), null);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSosMail(String email) {
        sendSoS(true, email);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteSoSContact(ContactDescription item) {
        deleteContact(item);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addContact(ContactInfoObj contactInfoObj) {
        if (!contactInfoObj.isEdit()) {
            EmergenctContactModel emergenctContactModel = new EmergenctContactModel();
            emergenctContactModel.setContactName(contactInfoObj.getName());
            emergenctContactModel.setContactNo(contactInfoObj.getNumber());
            emergenctContactModel.setEmail(contactInfoObj.getEmail());
            saveContactInServer(emergenctContactModel);
        } else {
            EditContactModel editContactModel = new EditContactModel();
            editContactModel.setId(contactInfoObj.getId());
            editContactModel.setEmail(contactInfoObj.getEmail());
            editContactModel.setContactName(contactInfoObj.getName());
            editContactModel.setContactNo(contactInfoObj.getNumber());
            updateContact(editContactModel, contactInfoObj.getPos());
            /*ContactDescription emergenctContactModel1 = new ContactDescription();
            emergenctContactModel1.setContactName(editContactModel.getContactName());
            emergenctContactModel1.setContactNo(editContactModel.getContactNo());
            emergenctContactModel1.setId(editContactModel.getId());
            emergenctContactModel1.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
            emergenctContactModel1.setEmail(editContactModel.getEmail());
            mAdapter.updatePosition(contactInfoObj.getPos(),emergenctContactModel1);*/

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void sendSoS(boolean isSingleUser, String email) {
        if (isSingleUser) {
            fragmentActivityCommunication.showProgressDialog(true);
        }
        SendSosModel sendSosModel = new SendSosModel();
        sendSosModel.setLat(locationService.getLatitude());
        sendSosModel.setLongX(locationService.getLongitude());
        if (isSingleUser) {
            sendSosModel.setEmail(email);
        }

        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = spf.format(currentTime);
        sendSosModel.setSosTime(dateString);
        emergencyContactViewModel.sendSoS(sendSosModel).observe(this, successArray -> {
            if (successArray != null) {
                if (isSingleUser) {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
                tvSuccess.setVisibility(View.VISIBLE);
                tvSuccess.setText(successArray.getSuccess().getMessage().toUpperCase());
                handler.postDelayed(() -> tvSuccess.setVisibility(View.GONE), 3000);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), successArray.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                tvSuccess.setBackgroundColor(getResources().getColor(R.color.error_panel_color));
                tvSuccess.setVisibility(View.VISIBLE);
                tvSuccess.setText(R.string.text_can_not_send_sos);
                handler.postDelayed(() -> tvSuccess.setVisibility(View.GONE), 3000);
                if (isSingleUser) {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSos(Boolean bool) {
        sendSoS(false, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadChooser(ContactChooseObject contactChooseObject) {
        if (contactChooseObject.isAdd()) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);

        } else {
            SaveEmergencyContactFragment saveEmergencyContactFragment = new SaveEmergencyContactFragment();
            saveEmergencyContactFragment.show(getChildFragmentManager(), null);
        }

    }
}
