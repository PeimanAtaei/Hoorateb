package com.atisapp.hoorateb.Payment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentProductListAdapter extends RecyclerView.Adapter<PaymentProductListAdapter.ViewHolder> {

    private static final String TAG = "BasketProductListAdapte";
    private List<ItemOrderModel> itemOrderModels;
    private ItemOrderModel itemOrderModel;
    private ProductModel productModel;
    private Context ctx;
    private CardAPIService cardAPIService;
    private List<BasketMultiViewModel> totalMultiViewModelList;
    private BasketMultiViewAdapter basketMultiViewAdapter;
    private String product_id,order_id;
    private int count;

    public PaymentProductListAdapter(Context context, List<ItemOrderModel> itemOrderModels) {
        this.ctx = context;
        this.itemOrderModels = itemOrderModels;
        cardAPIService = new CardAPIService(ctx);
        Log.i(TAG, "BasketProductListAdapter: ");
    }

    public void updateAdapterData(List<ItemOrderModel> data, String type) {
        this.itemOrderModels = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_payment_products,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)  {
        itemOrderModel = itemOrderModels.get(position);
        productModel = itemOrderModel.getProductModel();

        product_id = itemOrderModels.get(position).getProductModel().getProductId();
        order_id = itemOrderModels.get(position).getItemId();

        holder.payment_product_name.setText(productModel.getTitle());
        holder.payment_product_description.setText(productModel.getDescription());
        holder.payment_product_price.setText(convertNumberToBalance(productModel.getPrice()));
        holder.payment_product_count.setText(itemOrderModel.getQuantity()+"");

        Log.i(TAG, "onBindViewHolder: "+productModel.getCover());

        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.payment_product_image);

        holder.payment_product_list_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("productId",product_id);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (this.itemOrderModels != null)
            return itemOrderModels.size();
        else
            return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {


        private LinearLayout payment_product_list_box;
        public TextView payment_product_name,payment_product_description,
                payment_product_count,payment_product_price;
        public ImageView payment_product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            payment_product_list_box = itemView.findViewById(R.id.eachItem_payment_box);
            payment_product_name = itemView.findViewById(R.id.eachItem_payment_name);
            payment_product_description = itemView.findViewById(R.id.eachItem_payment_description);
            payment_product_count = itemView.findViewById(R.id.eachItem_payment_quantity);
            payment_product_price = itemView.findViewById(R.id.eachItem_payment_price);
            payment_product_image = itemView.findViewById(R.id.eachItem_payment_list_image);

        }


    }
    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(balance);
    }
}
