package com.atisapp.hoorateb.MainFragment.ui.account;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.atisapp.hoorateb.ForgetPassword.PhoneActivity;
import com.atisapp.hoorateb.Login.RegisterActivity;
import com.atisapp.hoorateb.Payment.PaymentListActivity;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    private Context             context;
    private IdentitySharedPref  identitySharedPref;
    private ProfileAPIService   profileAPIService;
    private UserModel           accountModel;
    private AddressModel        addressModel;
    private View                accountRoot;
    private LinearLayout        userInfoAlarm,userInfoLayout,addressAlarm,addressLayout;
    private LinearLayout        profileEditBtn,addressEditBtn,passBtn,historyBtn,addressBtn;
    private TextView            userName,userFamily,userMobile
                                ,userNationalCode,userCardNumber,userPhone;
    private TextView            accountAddress,accountPlate,accountUnit,accountPostCode;
    private CircleImageView     userAvatar;
    private Button              registerAlarmBtn,addressAlarmBtn,addressManagement;
    private  Bitmap             bitmap;

    private int                 CODE_GALLERY_REQUEST=999;


    @Override
    public void onResume() {
        super.onResume();
        SetupViews();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult: ");
        if(requestCode == CODE_GALLERY_REQUEST)
        {
            Log.i(TAG, "onRequestPermissionsResult3: ");
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"تصویر حساب کاربری خود را انتخاب نمایید"),CODE_GALLERY_REQUEST);
            }else {
                Toast.makeText(context,"شما اجازه دسترسی به گالری را به اپلیکیشن حورا طب نداده اید",Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                userAvatar.setImageBitmap(bitmap);

                UploadAvatar();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        accountRoot = inflater.inflate(R.layout.fragment_account, container, false);
        SetupViews();

        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditUserActivity.class);
                context.startActivity(intent);
            }
        });
        addressEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accountModel.getAddressModel()!= null)
                {
                    Intent intent = new Intent(context,EditAdressActivity.class);
                    intent.putExtra("type","edit");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressModel", accountModel.getAddressModel());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        registerAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        addressAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditAdressActivity.class);
                intent.putExtra("type","add");
                context.startActivity(intent);
            }
        });

        addressManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddressManagementActivity.class);
                context.startActivity(intent);
            }
        });

        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddressManagementActivity.class);
                context.startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentListActivity.class);
                context.startActivity(intent);
            }
        });

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhoneActivity.class);
                context.startActivity(intent);
            }
        });

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: avatar");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                },CODE_GALLERY_REQUEST);
            }
        });



        return accountRoot;
    }

    private void SetupViews()
    {
        context = getContext();
        identitySharedPref = new IdentitySharedPref(context);
        profileAPIService = new ProfileAPIService(context);

        userInfoAlarm = accountRoot.findViewById(R.id.registration_alarm_layout);
        userInfoLayout = accountRoot.findViewById(R.id.profile_user_info_layout);
        addressAlarm = accountRoot.findViewById(R.id.address_alarm_layout);
        addressLayout = accountRoot.findViewById(R.id.profile_address_layout);

        registerAlarmBtn = accountRoot.findViewById(R.id.profile_user_info_alarm_btn);
        addressAlarmBtn = accountRoot.findViewById(R.id.profile_address_alarm_btn);
        addressManagement = accountRoot.findViewById(R.id.profile_user_address_management);

        userAvatar = accountRoot.findViewById(R.id.avatar_image);

        passBtn = accountRoot.findViewById(R.id.profile_user_password);
        addressBtn = accountRoot.findViewById(R.id.profile_user_addresses);
        historyBtn = accountRoot.findViewById(R.id.profile_user_history);

        userName = accountRoot.findViewById(R.id.user_info_name);
        userMobile = accountRoot.findViewById(R.id.user_info_mobile);
        userPhone = accountRoot.findViewById(R.id.user_info_phone);
        userNationalCode = accountRoot.findViewById(R.id.user_info_national_code);
        userCardNumber = accountRoot.findViewById(R.id.user_info_card_number);

        accountAddress = accountRoot.findViewById(R.id.account_address);
        accountPlate = accountRoot.findViewById(R.id.account_personal_plate);
        accountUnit = accountRoot.findViewById(R.id.account_personal_local_unit);
        accountPostCode = accountRoot.findViewById(R.id.account_personal_post_code);

        profileEditBtn = accountRoot.findViewById(R.id.profile_user_edit_btn);
        addressEditBtn = accountRoot.findViewById(R.id.profile_address_edit_btn);

        CheckRegistration();

    }

    // User Info -----------------------------------------------------------------------------------
    private void CheckRegistration()
    {
        if(identitySharedPref.getIsRegistered() == 1)
        {
            userInfoAlarm.setVisibility(View.GONE);
            userInfoLayout.setVisibility(View.VISIBLE);
            GetUserInfo();
        }
    }
    private void GetUserInfo()
    {
        profileAPIService.GetUserInfo(new ProfileAPIService.onGetUserInfo() {
            @Override
            public void onGet(boolean msg, UserModel userModel) {

                accountModel = userModel;

                if(userModel.getFirstName()!=null && userModel.getLastName()!=null)
                    userName.setText(userModel.getFirstName()+" "+userModel.getLastName());
                userMobile.setText(userModel.getMobileNumber());
                userPhone.setText(userModel.getPhoneNumber());
                userNationalCode.setText(userModel.getNationalCode());
                userCardNumber.setText(userModel.getCardNumber());
                Log.i(TAG, "onGet: "+userModel.getPhoneNumber());

                CheckAddress();
            }
        });
    }

    // UserAddress ---------------------------------------------------------------------------------

    private void CheckAddress()
    {

        profileAPIService.GetUserAddresses(new ProfileAPIService.onGetUserAddress() {
            @Override
            public void onGet(boolean msg, List<AddressModel> addressModelList, int length) {
                if(length >0)
                {
                    addressAlarm.setVisibility(View.GONE);
                    addressLayout.setVisibility(View.VISIBLE);
                    SetAddress();
                }
                else{
                    addressAlarm.setVisibility(View.VISIBLE);
                    addressLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    private void SetAddress()
    {
        AddressModel addressModel = accountModel.getAddressModel();
        if(addressModel!= null)
        {
            accountAddress.setText(addressModel.getFullAddress());
            accountPostCode.setText(addressModel.getPostalCode());
            accountPlate.setText(addressModel.getPlate()+"");
            accountUnit.setText(addressModel.getUnit()+"");
        }

    }

    private String ImageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private void UploadAvatar()
    {
        String imageData = ImageToString(bitmap);
        profileAPIService.UploadAvatar(imageData, new ProfileAPIService.onUploadAvatar() {
            @Override
            public void onGet(boolean msg, String url) {
                Log.i(TAG, "onGet:"+url);
            }
        });
    }
}