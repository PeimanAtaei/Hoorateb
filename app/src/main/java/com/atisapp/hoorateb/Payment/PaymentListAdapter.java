package com.atisapp.hoorateb.Payment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.Comment.CommentAPIService;
import com.atisapp.hoorateb.Comment.CommentModel;
import com.atisapp.hoorateb.R;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.ViewHolder> {

    private static final String TAG = "PaymentListAdapter";
    private List<PaymentModel> paymentModelList;
    private PaymentModel paymentModel;
    private Context context;
    private String status;

    public PaymentListAdapter(Context context, List<PaymentModel> paymentModelList) {
        this.context = context;
        this.paymentModelList = paymentModelList;
    }

    public void updateAdapterData(List<PaymentModel> data) {
        this.paymentModelList = data;
    }

    @NonNull
    @Override
    public PaymentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_payment_list,parent,false);
        PaymentListAdapter.ViewHolder viewHolder = new PaymentListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        paymentModel = paymentModelList.get(position);

        holder.price.setText(convertNumberToBalance(paymentModel.getPaymentPrice()));
        holder.number.setText(paymentModel.getPaymentNumber());
        holder.date.setText(paymentModel.getPaymentDate());

        if(paymentModel.getPaymentPaid())
        {
            status = paymentModelList.get(position).getPaymentStatus();
            switch (status)
            {
                case "CANCELED":
                {
                    holder.arcProgress.setProgress(100);
                    holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.colorAccent));
                    holder.title.setText("لغو شده");
                    break;
                }
                case "PENDING":
                {
                    holder.arcProgress.setProgress(25);
                    holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.progress_orange));
                    holder.title.setText("بررسی سفارش");
                    break;
                }
                case "PROCESSING":
                {
                    holder.arcProgress.setProgress(50);
                    holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.progress_purple));
                    holder.title.setText("آماده سازی سفارش");
                    break;
                }
                case "ON_THE_WAY":
                {
                    holder.arcProgress.setProgress(75);
                    holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.progress_blue));
                    holder.title.setText("ارسال شده");
                    break;
                }
                case "DELIVERED":
                {
                    holder.arcProgress.setProgress(100);
                    holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.progress_green));
                    holder.title.setText("تحویل داده شده");
                    break;
                }
            }

        }else {
            holder.arcProgress.setProgress(100);
            holder.arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.colorAccent));
            holder.title.setText("لغو شده");
        }

        holder.paymentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PaymentDetailActivity.class);
                intent.putExtra("paymentId",paymentModelList.get(position).getPaymentId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (this.paymentModelList != null)
            return paymentModelList.size();
        else
            return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout paymentBox;
        private TextView title,number,price,date;
        private com.github.lzyzsd.circleprogress.ArcProgress arcProgress;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paymentBox = itemView.findViewById(R.id.payment_eachItem_order_box);
            title = itemView.findViewById(R.id.payment_eachItem_title);
            number = itemView.findViewById(R.id.payment_eachItem_order_number);
            price = itemView.findViewById(R.id.payment_eachItem_order_price);
            date = itemView.findViewById(R.id.payment_eachItem_order_date);
            arcProgress = itemView.findViewById(R.id.payment_eachItem_progress);

        }


    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }

}
