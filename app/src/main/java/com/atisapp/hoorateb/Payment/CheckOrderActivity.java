package com.atisapp.hoorateb.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.atisapp.hoorateb.MainFragment.ui.account.ProfileAPIService;
import com.atisapp.hoorateb.MainFragment.ui.account.UserModel;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.basket.TotalOrderModel;
import com.atisapp.hoorateb.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.DecimalFormat;

public class CheckOrderActivity extends AppCompatActivity {

    private static final String TAG = "CheckOrderActivity";
    private Context context;
    private ProfileAPIService profileAPIService;
    private CardAPIService cardAPIService;
    private TextView userName,userMobile,userPhone,userNationCode,userCard;
    private TextView fullAddress,plate,postCode,unit;
    private TextView orderCount,orderPrice,orderDiscount,finalPrice;
    private TotalOrderModel orderModel;
    private Button paymentBtn;
    private ShimmerFrameLayout checkManagementShimmer;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        SetupViews();

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartPayment();
            }
        });
    }

    private void SetupViews()
    {
        context = getApplicationContext();
        profileAPIService = new ProfileAPIService(context);
        cardAPIService = new CardAPIService(context);

        userName = findViewById(R.id.check_order_name);
        userMobile = findViewById(R.id.check_order_mobile);
        userPhone = findViewById(R.id.check_order_phone);
        userNationCode = findViewById(R.id.check_order_national_code);
        userCard = findViewById(R.id.check_order_card_number);
        checkManagementShimmer = findViewById(R.id.check_order_shimmer_view_container);
        scrollView = findViewById(R.id.check_order_list);
        checkManagementShimmer.startShimmer();

        fullAddress = findViewById(R.id.check_order_address);
        plate = findViewById(R.id.check_order_plate);
        unit = findViewById(R.id.check_order_local_unit);
        postCode = findViewById(R.id.check_order_post_code);

        orderCount = findViewById(R.id.check_order_count);
        orderPrice = findViewById(R.id.check_order_price);
        orderDiscount = findViewById(R.id.check_order_discount);
        finalPrice = findViewById(R.id.check_order_final_price);

        paymentBtn = findViewById(R.id.check_order_complete_payment_button);

        GetUserInfo();
        GetCardInfo();

    }

    private void GetUserInfo()
    {
        profileAPIService.GetUserInfo(new ProfileAPIService.onGetUserInfo() {
            @Override
            public void onGet(boolean msg, UserModel userModel) {
                checkManagementShimmer.stopShimmer();
                checkManagementShimmer.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                userName.setText(userModel.getFirstName() +" "+userModel.getLastName());
                userMobile.setText(userModel.getMobileNumber());
                userPhone.setText(userModel.getPhoneNumber());
                userNationCode.setText(userModel.getNationalCode());
                userCard.setText(userModel.getCardNumber());

                fullAddress.setText(userModel.getAddressModel().getFullAddress());
                plate.setText(userModel.getAddressModel().getPlate()+"");
                unit.setText(userModel.getAddressModel().getUnit()+"");
                postCode.setText(userModel.getAddressModel().getPostalCode());
            }
        });
    }
    private void GetCardInfo()
    {
        cardAPIService.GetUserCard(new CardAPIService.onGetUserCard() {
            @Override
            public void onGet(boolean msg, TotalOrderModel totalOrderModel) {
                orderModel = totalOrderModel;
                orderCount.setText(totalOrderModel.getTotalCount()+" محصول ");
                orderPrice.setText(convertNumberToBalance(totalOrderModel.getTotalPrice())+" تومان ");
                finalPrice.setText(convertNumberToBalance(totalOrderModel.getTotalPrice())+" تومان ");
                orderDiscount.setText("0 تومان");
            }
        });
    }
    private void StartPayment()
    {
        cardAPIService.PaymentOrder(orderModel.getOrderId(), new CardAPIService.onStartPayment() {
            @Override
            public void onStart(boolean msg, String url) {
                Toast.makeText(context,"ورود به درگاه پرداخت",Toast.LENGTH_SHORT).show();
                OpenBrowser(url);
            }
        });
    }

    private void OpenBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }


}