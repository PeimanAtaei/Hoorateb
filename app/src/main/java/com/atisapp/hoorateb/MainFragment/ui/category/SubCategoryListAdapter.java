package com.atisapp.hoorateb.MainFragment.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Product.ProductListActivity;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder> {

    private static final String TAG = "VideoListAdapter";
    private List<ProductModel> productList;
    private ProductModel productModel;
    private Context ctx;
    private String mType;
    private String product_id;

    public SubCategoryListAdapter(Context context, List<ProductModel> productList) {
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
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_horizontal_subcategory_lists_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        productModel = productList.get(position);
        holder.eachItem_category_title.setText(productModel.getTitle());
        //holder.eachItem_video_description.setText(productModel.getBrief());
        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.eachItem_category_image);

        //holder.product_list_each_item_time.setText(productModel.getTime());
        product_id = productList.get(position).getProductId();
        holder.category_list_each_item_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ctx, ProductListActivity.class);
                    intent.putExtra("categoryId",productList.get(position).getProductId());
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

        private LinearLayout category_list_each_item_box;
        public TextView eachItem_category_title;
        public ImageView eachItem_category_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_list_each_item_box = itemView.findViewById(R.id.subcategory_box);
            eachItem_category_image = itemView.findViewById(R.id.subcategory_image);
            eachItem_category_title = itemView.findViewById(R.id.subcategory_title);

        }


    }


}
