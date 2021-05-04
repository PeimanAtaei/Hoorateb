package com.atisapp.hoorateb.ForgetPassword;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

public class PhoneActivity extends AppCompatActivity {

    private static final String TAG = "PhoneActivity";
    private Context context;
    private EditText forget_phoneNumber_edit;
    private Button forget_next_btn;
    private ForgetApiService forgetApiService;
    private IdentitySharedPref identitySharedPref;
    private TextView supportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        SetupVIew();


        forget_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(forget_phoneNumber_edit.getText().length()<11)
                {
                    Toast.makeText(context,"شماره موبایل خود را به شکل صحیح وارد نمایید",Toast.LENGTH_LONG).show();
                }
                else {
                    forgetApiService.SendPhoneNumber(forget_phoneNumber_edit.getText().toString(),new ForgetApiService.onSetPhoneNumber() {
                        @Override
                        public void onSet(boolean login_response) {
                            if(login_response == true)
                            {
                                identitySharedPref.setPhoneNumber(forget_phoneNumber_edit.getText().toString());
                                Intent intent = new Intent(PhoneActivity.this, VerifyActivity.class);
                                startActivity(intent);
                                Toast.makeText(context,"کد تایید برای موبایل شما پیامک گردید",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

    }


    @SuppressLint("WrongViewCast")
    private void SetupVIew()
    {
        context = PhoneActivity.this;
        forget_phoneNumber_edit = (EditText) findViewById(R.id.forget_phone_edit_text);
        forget_next_btn  = (Button) findViewById(R.id.forget_next_btn);
        forgetApiService = new ForgetApiService(context);
        identitySharedPref = new IdentitySharedPref(context);
    }

}
