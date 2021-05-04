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

import com.atisapp.hoorateb.Product.ProductListActivity;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

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

    public CategoryListAdapter(Context context, List<ProductModel> productList, int viewType) {
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
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_horizontal_category_lists_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        productModel = productList.get(position);
        holder.product_list_each_item_name.setText(productModel.getTitle());
        product_id = productList.get(position).getProductId();
        holder.product_list_each_item_description.setText(productModel.getDescription());

        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.product_list_each_item_image);

        holder.product_list_each_item_box.setOnClickListener(new View.OnClickListener() {
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

        private LinearLayout product_list_each_item_box;
        public TextView product_list_each_item_name,product_list_each_item_description;
        public ImageView product_list_each_item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_list_each_item_box = itemView.findViewById(R.id.category_box);
            product_list_each_item_name = itemView.findViewById(R.id.category_title);
            product_list_each_item_description = itemView.findViewById(R.id.category_description);
            product_list_each_item_image = itemView.findViewById(R.id.category_image);


        }


    }


}
