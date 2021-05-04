package com.atisapp.hoorateb.MainFragment.ui.basket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private static final String TAG = "FavoriteAdapter";
    private List<ProductModel> itemOrderModels;
    private ProductModel itemOrderModel;
    private Context ctx;
    private CardAPIService cardAPIService;
    private List<BasketMultiViewModel> totalMultiViewModelList;
    private BasketMultiViewAdapter basketMultiViewAdapter;
    private String product_id,order_id;
    private int count;

    public FavoriteAdapter(Context context, List<ProductModel> itemOrderModels) {
        this.ctx = context;
        this.itemOrderModels = itemOrderModels;
        cardAPIService = new CardAPIService(ctx);
        Log.i(TAG, "FavoriteProductListAdapter: ");
    }

    public void updateAdapterData(List<ProductModel> data, String type) {
        this.itemOrderModels = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_favorite_products,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)  {
        Log.i(TAG, "onBindViewHolder: ");
        itemOrderModel = itemOrderModels.get(position);

        product_id = itemOrderModels.get(position).getProductId();

        holder.basket_product_name.setText(itemOrderModel.getTitle());
        holder.basket_product_description.setText(itemOrderModel.getDescription());
        holder.basket_product_price.setText(convertNumberToBalance(itemOrderModel.getPrice()));

        Glide.with(ctx)
                .load(itemOrderModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.basket_product_image);

        holder.basket_product_list_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("productId",product_id);
                ctx.startActivity(intent);
            }
        });
        holder.basket_product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardAPIService.DeleteFromFavorite(itemOrderModels.get(position).getProductId(), new CardAPIService.onDeleteFromFavorite() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(ctx,"این محصول از لیست علاقمندی های شما حذف گردید",Toast.LENGTH_LONG).show();
                    }
                });
                itemOrderModels.remove(itemOrderModels.get(position));
                notifyDataSetChanged();

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


        private LinearLayout basket_product_list_box;
        public TextView basket_product_name,basket_product_description,basket_product_price;
        public ImageView basket_product_image;
        public ImageView basket_product_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            basket_product_list_box = itemView.findViewById(R.id.eachItem_favorite_box);
            basket_product_name = itemView.findViewById(R.id.eachItem_favorite_name);
            basket_product_description = itemView.findViewById(R.id.eachItem_favorite_description);
            basket_product_price = itemView.findViewById(R.id.eachItem_favorite_price);
            basket_product_image = itemView.findViewById(R.id.eachItem_favorite_image);
            basket_product_delete = itemView.findViewById(R.id.eachItem_favorite_delete);

        }
    }
    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }


}
