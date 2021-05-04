package com.atisapp.hoorateb.ForgetPassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.atisapp.hoorateb.MainFragment.MainActivity;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;
import com.google.firebase.analytics.FirebaseAnalytics;

public class PasswordActivity extends AppCompatActivity {

    private static final String TAG = "PasswordActivity";
    private Context context;
    private EditText password_firstPass_edit,password_secondPass_edit;
    private Button password_next_btn;
    private ForgetApiService forgetApiService;
    private IdentitySharedPref identitySharedPref;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        SetupVIew();

        password_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String data = intent.getStringExtra("data");
                Log.i(TAG, "onClick: "+data);

                if(password_firstPass_edit.getText().length()<4)
                {
                    Toast.makeText(context,"رمز عبور باید حداقل 4 کاراکتر داشته باشد",Toast.LENGTH_LONG).show();
                }
                else if(!password_firstPass_edit.getText().toString().equals(password_secondPass_edit.getText().toString()))
                {
                    Toast.makeText(context,"رمز عبور جدید و تکرار رمز عبور باید یکسان باشند",Toast.LENGTH_LONG).show();
                }
                else {
                    forgetApiService.ChangePassword(password_firstPass_edit.getText().toString(),data,new ForgetApiService.onSetPassword() {
                        @Override
                        public void onSet(boolean change_response) {
                            if(change_response == true)
                            {
                                identitySharedPref.setIsRegistered(1);
                                Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(context,"رمز عبور با موفقیت تغییر کرد",Toast.LENGTH_LONG).show();
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
        context = PasswordActivity.this;
        password_firstPass_edit = (EditText) findViewById(R.id.password_firstPass_edit_text);
        password_secondPass_edit = (EditText) findViewById(R.id.password_secondPass_edit_text);
        password_next_btn  = (Button) findViewById(R.id.password_next_btn);
        forgetApiService = new ForgetApiService(context);
        identitySharedPref = new IdentitySharedPref(context);

        firebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
    }
}
