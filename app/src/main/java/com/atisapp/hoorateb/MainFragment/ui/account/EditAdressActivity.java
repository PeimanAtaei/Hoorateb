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

import com.atisapp.hoorateb.R;

public class EditAdressActivity extends AppCompatActivity {

    private static final String TAG = "EditAdressActivity";
    private Context context;
    private ProfileAPIService profileAPIService;
    private AddressModel addressModel;
    private EditText fullAddress,plat,postCode,unit;
    private Button sendBtn;
    private String type,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_adress);
        SetupViews();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAddress();
            }
        });
    }

    private void SetupViews()
    {
        context = EditAdressActivity.this;
        profileAPIService = new ProfileAPIService(context);

        fullAddress = findViewById(R.id.edit_address_full);
        plat = findViewById(R.id.edit_address_plate);
        postCode = findViewById(R.id.edit_address_post_code);
        unit = findViewById(R.id.edit_address_unit);
        sendBtn = findViewById(R.id.address_next_btn);
        addressModel = new AddressModel();

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(type.equals("edit")) {
            Bundle bundle = intent.getExtras();
            addressModel = (AddressModel) bundle.getSerializable("addressModel");
            SetAddress(addressModel);
        }
    }

    private void SetAddress(AddressModel addressModel)
    {
        fullAddress.setText(addressModel.getFullAddress());
        plat.setText(addressModel.getPlate()+"");
        postCode.setText(addressModel.getPostalCode());
        unit.setText(addressModel.getUnit()+"");
    }

    private void GetAddress()
    {
        if(fullAddress.getText().length()>0 && unit.getText().length()>0 && postCode.getText().length()>0 && plat.getText().length()>0)
        {
            addressModel.setFullAddress(fullAddress.getText().toString());
            addressModel.setPostalCode(postCode.getText().toString());
            addressModel.setPlate(Integer.parseInt(plat.getText().toString()));
            addressModel.setUnit(Integer.parseInt(unit.getText().toString()));

            SendAddress(addressModel);

        }else {
            Toast.makeText(context,"تمام اطلاعات بالا را به صورت کامل پر کنید",Toast.LENGTH_LONG).show();
        }
    }

    private void SendAddress(AddressModel addressModel)
    {
        if(type.equals("edit"))
        {
            Log.i(TAG, "SendAddress: edit");
            profileAPIService.UpdateUserAddress(addressModel, new ProfileAPIService.onUpdateUserAddress() {
                @Override
                public void onGet(boolean msg) {
                    Toast.makeText(context,"آدرس شما با موفقیت تغییر کرد",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        if(type.equals("add"))
        {
            Log.i(TAG, "SendAddress: create");
           profileAPIService.CreateUserAddress(addressModel, new ProfileAPIService.onSendUserAddress() {
               @Override
               public void onGet(boolean msg) {
                   Toast.makeText(context,"آین آدرس با موفقیت به لیست شما افزوده شد",Toast.LENGTH_LONG).show();
                   finish();
               }
           });
        }
    }

}