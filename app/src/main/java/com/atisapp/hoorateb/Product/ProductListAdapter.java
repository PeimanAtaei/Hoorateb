package com.atisapp.hoorateb.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private static final String TAG = "ProductListAdapter";
    private List<ProductModel> productList;
    private ProductModel productModel;
    private Context ctx;
    private String mType;
    private String product_id;

    public ProductListAdapter(Context context, List<ProductModel> productList) {
        this.ctx = context;
        this.productList = productList;
    }

    public void updateAdapterData(List<ProductModel> data, String type) {
        this.productList = data;
        this.mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_product_lists_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        productModel = productList.get(position);
        holder.product_list_brand.setText(productModel.getBrand());
        holder.product_list_title.setText(productModel.getTitle());
        holder.product_list_description.setText(productModel.getDescription());
        holder.product_list_price.setText(convertNumberToBalance(productModel.getPrice()));
        //holder.product_list_discount.setText(productModel.getDisCount());

        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.product_list_image);

        //holder.product_list_each_item_time.setText(productModel.getTime());
        product_id = productList.get(position).getProductId();
        holder.product_list_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ctx, ProductDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("productId",productModel.getProductId());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("categoryId", productModel.getProductId());
//                    intent.putExtras(bundle); //pass bundle to your intent
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

        private LinearLayout product_list_box;
        public TextView product_list_title,product_list_brand,product_list_description,
                product_list_price,product_list_discount;
        public ImageView product_list_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_list_box = itemView.findViewById(R.id.product_list_box);
            product_list_title = itemView.findViewById(R.id.product_list_title);
            product_list_brand = itemView.findViewById(R.id.product_list_brand);
            product_list_description = itemView.findViewById(R.id.product_list_description);
            product_list_price = itemView.findViewById(R.id.product_list_price);
            product_list_discount = itemView.findViewById(R.id.product_list_discount);
            product_list_image = itemView.findViewById(R.id.product_list_image);

        }


    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }


}
