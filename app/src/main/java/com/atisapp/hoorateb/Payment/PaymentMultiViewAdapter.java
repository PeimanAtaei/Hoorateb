package com.atisapp.hoorateb.Payment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.Comment.CommentActivity;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.MainFragment.ui.category.SubCategoryActivity;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeSliderModel;
import com.atisapp.hoorateb.MainFragment.ui.home.MainSliderAdapter;
import com.atisapp.hoorateb.MainFragment.ui.home.PicassoImageLoadingService;
import com.atisapp.hoorateb.ModelsAndAdapters.CategoryListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductHorizontalListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;
import com.shuhart.stepview.StepView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.CATEGORY_HORIZONTAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_HORIZONTAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_SLIDER;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_VERTICAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_HEADER;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_SPECIFICATION;
import static com.atisapp.hoorateb.Payment.PaymentMultiViewModel.PAYMENT_PRODUCT_HEADER;
import static com.atisapp.hoorateb.Payment.PaymentMultiViewModel.PAYMENT_PRODUCT_LIST;
import static com.atisapp.hoorateb.Payment.PaymentMultiViewModel.PAYMENT_STEP_VIEW;
import static com.atisapp.hoorateb.Payment.PaymentMultiViewModel.PAYMENT_TOTAL_ORDER;

public class PaymentMultiViewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "PaymentMultiViewAdapter";
    private Context                     context;
    private List<PaymentMultiViewModel>    multiViewModelList;
    private int                         lastPosition = -1;
    private PaymentProductListAdapter   productAdapter;
    private RecyclerView.RecycledViewPool viewPool ;




    public PaymentMultiViewAdapter(List<PaymentMultiViewModel> multiViewModelList, Context context) {

        this.multiViewModelList = multiViewModelList;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position)
    {
        switch (multiViewModelList.get(position).getViewType())
        {
            case 0:{
                return PAYMENT_STEP_VIEW;
            }
            case 1:{
                return PAYMENT_TOTAL_ORDER;
            }
            case 2:{
                return PAYMENT_PRODUCT_LIST;
            }
            case 3:{
                return PAYMENT_PRODUCT_HEADER;
            }
            default:
                return -1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case PAYMENT_STEP_VIEW: {

                View stepView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_payment_detail_steps, parent, false);
                return new StepLayout(stepView);
            }

            case PAYMENT_TOTAL_ORDER:
            {
                View totalOrderLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_payment_total_orders, parent, false);
                return new TotalOrderLayout(totalOrderLayout);
            }

            case PAYMENT_PRODUCT_LIST:
            {

                View paymentProductListView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.multiview_subrecycler_view_layout, parent, false);
                return new PaymentProductListLayout(paymentProductListView);
            }

            case PAYMENT_PRODUCT_HEADER:
            {

                View paymentHeaderView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_payment_detail_header, parent, false);
                return new PaymentHeaderListLayout(paymentHeaderView);
            }


            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (multiViewModelList.get(position).getViewType())
        {
            case PAYMENT_STEP_VIEW:
            {

                String status = multiViewModelList.get(position).getStatus();
                ((StepLayout)holder).setData(status);
                break;
            }

            case PAYMENT_TOTAL_ORDER:
            {

                PaymentModel productModel = multiViewModelList.get(position).getPaymentModel();
                ((TotalOrderLayout)holder).setData(productModel);
                break;
            }

            case PAYMENT_PRODUCT_LIST:
            {
                List<ItemOrderModel> productModelList = multiViewModelList.get(position).getProductModelList();

                ((PaymentProductListLayout)holder).setData(productModelList);
                break;
            }

            case PAYMENT_PRODUCT_HEADER:
            {
                String headerTitle = multiViewModelList.get(position).getHeaderTitle();

                ((PaymentHeaderListLayout)holder).setData(headerTitle);
                break;
            }
        }

        if (lastPosition < position)
        {
            Animation animation = AnimationUtils.loadAnimation
                    (holder.itemView.getContext(),android.R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return multiViewModelList.size();
    }

    class StepLayout extends RecyclerView.ViewHolder
    {
        private com.shuhart.stepview.StepView stepView;
        private TextView title;

        public StepLayout(@NonNull View itemView) {
            super(itemView);
            stepView = itemView.findViewById(R.id.step_view);
            title = itemView.findViewById(R.id.payment_detail_header_title);
        }

        private void setData(String status)
        {
            stepView.getState()
                    .selectedTextColor(ContextCompat.getColor(context, R.color.white))
                    .animationType(StepView.ANIMATION_CIRCLE)
                    .selectedCircleColor(ContextCompat.getColor(context, R.color.white_gray))
                    .selectedStepNumberColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .doneCircleColor(ContextCompat.getColor(context, R.color.step_color))
                    .nextStepCircleColor(ContextCompat.getColor(context, R.color.white_gray))
                    .doneStepMarkColor(ContextCompat.getColor(context, R.color.white))
                    .doneStepLineColor(ContextCompat.getColor(context, R.color.white_gray2))
                    .stepsNumber(4)
                    .animationDuration(context.getResources().getInteger(android.R.integer.config_shortAnimTime))
                    .commit();

            switch (status)
            {
                case "PENDING":
                {
                    stepView.go(1, true);
                    title.setText("در حال بررسی سفارش");
                    break;
                }
                case "CANCELED":
                {
                    stepView.go(4, true);
                    title.setText("سفارش لغو شده است");
                    break;
                }
                case "PROCESSING":
                {
                    stepView.go(2, true);
                    title.setText("در حال آماده سازی سفارش");
                    break;
                }
                case "ON_THE_WAY":
                {
                    stepView.go(3, true);
                    title.setText("سفارش ارسال شده است");
                    break;
                }
                case "DELIVERED":
                {
                    stepView.go(4, true);
                    title.setText("سفارش تحویل داده شده است");
                    break;
                }
            }

        }
    }

    class PaymentProductListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView;

        public PaymentProductListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView = itemView.findViewById(R.id.product_list_subRecyclerView);
        }

        private void setData(List<ItemOrderModel> productModelList)
        {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            subRecyclerView.setLayoutManager(layoutManager);

            productAdapter = new PaymentProductListAdapter(itemView.getContext(),productModelList);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setAdapter(productAdapter);
            viewPool = new RecyclerView.RecycledViewPool();
            subRecyclerView.setRecycledViewPool(viewPool);

        }
    }

    class TotalOrderLayout extends RecyclerView.ViewHolder
    {
        private TextView  orderCount,orderPrice,orderDiscount,finalPrice,orderDate,orderNumber;

        public TotalOrderLayout(@NonNull View itemView) {
            super(itemView);
            orderCount = itemView.findViewById(R.id.basket_total_order_count);
            orderPrice = itemView.findViewById(R.id.basket_total_order_price);
            orderDiscount = itemView.findViewById(R.id.basket_total_order_discount);
            orderDate = itemView.findViewById(R.id.basket_total_order_date);
            finalPrice = itemView.findViewById(R.id.basket_total_order_final_price);
            orderNumber = itemView.findViewById(R.id.basket_total_payment_number);
        }

        private void setData(final PaymentModel productModel)
        {
            //this.orderCount.setText(productModel.getTitle());
            this.orderPrice.setText(convertNumberToBalance(productModel.getPaymentPrice())+" تومان");
            //this.orderDiscount.setText(productModel.getDiscount()+"");
            this.orderDate.setText(productModel.getPaymentDate());
            this.finalPrice.setText(convertNumberToBalance(productModel.getPaymentPrice())+" تومان");
            this.orderNumber.setText(productModel.getPaymentNumber());
        }
    }

    class PaymentHeaderListLayout extends RecyclerView.ViewHolder
    {
        private TextView headerTitle;

        public PaymentHeaderListLayout(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.payment_header_title);
        }

        private void setData(String title)
        {
            headerTitle.setText(title);
        }
    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }
}
