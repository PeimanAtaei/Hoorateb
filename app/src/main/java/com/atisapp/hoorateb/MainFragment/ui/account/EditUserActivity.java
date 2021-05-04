package com.atisapp.hoorateb.MainFragment.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atisapp.hoorateb.MainFragment.MainActivity;
import com.atisapp.hoorateb.R;

public class EditUserActivity extends AppCompatActivity {

    private static final String TAG = "EditUserActivity";
    private Context context;
    private ProfileAPIService profileAPIService;
    private EditText name,family,phone,nationalCode,card;
    private Button nextBtn;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.i(TAG, "onResumeFragments: ");
        SetupViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        SetupViews();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().length()==0 || family.getText().length()==0 || phone.getText().length()==0 || nationalCode.getText().length()==0 || card.getText().length()==0 )
                {
                    Toast.makeText(context,"تمام اطلاعات را به شکل کامل وارد نمایید",Toast.LENGTH_LONG).show();
                }else {
                    UserModel userModel = new UserModel();
                    userModel.setFirstName(name.getText().toString());
                    userModel.setLastName(family.getText().toString());
                    userModel.setPhoneNumber(phone.getText().toString());
                    userModel.setNationalCode(nationalCode.getText().toString());
                    userModel.setCardNumber(card.getText().toString());

                    SendUserInfo(userModel);
                }
            }
        });
    }

    private void SetupViews()
    {
        context = getApplicationContext();
        profileAPIService = new ProfileAPIService(context);

        name            = findViewById(R.id.edit_user_name);
        family          = findViewById(R.id.edit_user_family);
        phone           = findViewById(R.id.edit_user_local_phone);
        nationalCode    = findViewById(R.id.edit_user_national_code);
        card            = findViewById(R.id.edit_user_card);
        nextBtn         = findViewById(R.id.user_edit_next_btn);

        GetUserInfo();
    }

    private void GetUserInfo()
    {
        profileAPIService.GetUserInfo(new ProfileAPIService.onGetUserInfo() {
            @Override
            public void onGet(boolean msg, UserModel userModel) {
                name.setText(userModel.getFirstName());
                family.setText(userModel.getLastName());
                phone.setText(userModel.getPhoneNumber());
                nationalCode.setText(userModel.getNationalCode());
                card.setText(userModel.getCardNumber());
            }
        });
    }

    private void SendUserInfo(UserModel userModel)
    {
        profileAPIService.SendUserInfo(userModel, new ProfileAPIService.onSendUserInfo() {
            @Override
            public void onGet(boolean msg) {
                Toast.makeText(context,"اطلاعات شما با موفیقت ثبت گردید",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

}