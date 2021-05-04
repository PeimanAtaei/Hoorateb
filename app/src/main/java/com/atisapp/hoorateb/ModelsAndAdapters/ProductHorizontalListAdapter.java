package com.atisapp.hoorateb.ModelsAndAdapters;

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

import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class ProductHorizontalListAdapter extends RecyclerView.Adapter<ProductHorizontalListAdapter.ViewHolder> {

    private static final String TAG = "CourseListAdapter";
    private List<ProductModel> productList;
    private ProductModel productModel;
    private Context ctx;
    private Random rnd;
    private int rndNum;
    private String mType;
    private int viewType;
    private boolean clickable = true;
    private String product_id;

    public ProductHorizontalListAdapter(Context context, List<ProductModel> productList, int viewType) {
        this.ctx = context;
        this.productList = productList;
        this.viewType = viewType;
    }

    public void updateAdapterData(List<ProductModel> data, String type) {
        this.productList = data;
        this.mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_horizontal_product_lists_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        productModel = productList.get(position);
        Log.i(TAG, "onBindViewHolder: "+productModel.getTitle());
        holder.product_list_each_item_name.setText(productModel.getTitle());
        product_id = productList.get(position).getProductId();
        holder.product_list_each_item_description.setText(productModel.getDescription());
        holder.product_list_each_item_brand.setText(productModel.getBrand());
        holder.product_list_each_item_price.setText(convertNumberToBalance(productModel.getPrice()));
        holder.product_list_each_item_discount.setText(productModel.getDisCount()+" % تخفیف");
        Log.i(TAG, "onBindViewHolder: "+productModel.getCover());

        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.product_list_each_item_image);

        holder.product_list_each_item_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("productId",productList.get(position).getProductId());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (this.productList != null)
            return productList.size();
        else
            return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout product_list_each_item_box;
        public TextView product_list_each_item_name,product_list_each_item_description,product_list_each_item_brand,product_list_each_item_price,product_list_each_item_discount;
        public ImageView product_list_each_item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_list_each_item_box = itemView.findViewById(R.id.product_box);
            product_list_each_item_name = itemView.findViewById(R.id.product_title);
            product_list_each_item_description = itemView.findViewById(R.id.product_description);
            product_list_each_item_brand = itemView.findViewById(R.id.product_brand);
            product_list_each_item_price = itemView.findViewById(R.id.product_price);
            product_list_each_item_discount = itemView.findViewById(R.id.product_discount);
            product_list_each_item_image = itemView.findViewById(R.id.product_image);


        }


    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }


}
