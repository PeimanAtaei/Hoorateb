package com.atisapp.hoorateb.MainFragment.ui.basket;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.MainFragment.MainActivity;
import com.atisapp.hoorateb.MainFragment.ui.basket.ui.main.BasketTabFragment;
import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BasketProductListAdapter extends RecyclerView.Adapter<BasketProductListAdapter.ViewHolder> {

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

    public BasketProductListAdapter(Context context, List<ItemOrderModel> itemOrderModels) {
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
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_basket_products,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)  {
        itemOrderModel = itemOrderModels.get(position);
        productModel = itemOrderModel.getProductModel();

        product_id = itemOrderModels.get(position).getProductModel().getProductId();
        order_id = itemOrderModels.get(position).getItemId();

        holder.basket_product_name.setText(productModel.getTitle());
        holder.basket_product_description.setText(productModel.getDescription());
        holder.basket_product_brand.setText(productModel.getBrand());
        holder.basket_product_price.setText(convertNumberToBalance(productModel.getPrice()));
        holder.basket_product_count.setText(itemOrderModel.getQuantity()+"");
        holder.basket_product_total_price.setText(convertNumberToBalance(productModel.getPrice()*itemOrderModel.getQuantity()));

        if (position == (itemOrderModels.size()-1))
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 200);
            holder.basket_product_list_box.setLayoutParams(params);
        }

        //holder.eachItem_video_description.setText(productModel.getBrief());
        Glide.with(ctx)
                .load(productModel.getCover())
                .placeholder(R.drawable.product_eachitem)
                .into(holder.basket_product_image);

        //holder.product_list_each_item_time.setText(productModel.getTime());

        holder.basket_product_list_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("productId",product_id);
                ctx.startActivity(intent);
            }
        });
        holder.basket_product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                count = Integer.parseInt(holder.basket_product_count.getText().toString());
                count++;
                holder.basket_product_count.setText(count+"");
                holder.basket_product_total_price.setText(convertNumberToBalance(productModel.getPrice()*count));
                cardAPIService.UpdateQuantity(itemOrderModels.get(position).getItemId(),count, new CardAPIService.onUpdateQuantity() {
                    @Override
                    public void onGet(boolean msg) {
                        Log.i(TAG, "onGet: plus");
                    }
                });
            }
        });
        holder.basket_product_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(holder.basket_product_count.getText().toString());
                if(count>1)
                count--;
                holder.basket_product_count.setText(count+"");
                holder.basket_product_total_price.setText(convertNumberToBalance(productModel.getPrice()*count));

                cardAPIService.UpdateQuantity(itemOrderModels.get(position).getItemId(),count, new CardAPIService.onUpdateQuantity() {
                    @Override
                    public void onGet(boolean msg) {
                        Log.i(TAG, "onGet: remove");
                    }
                });
            }
        });
        holder.basket_product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardAPIService.DeleteOrder(itemOrderModels.get(position).getItemId(), new CardAPIService.onDeleteOrder() {
                    @Override
                    public void onDelete(boolean msg) {
                        Toast.makeText(ctx,"محصول از سبد خرید شما حذف گردید",Toast.LENGTH_LONG).show();
                    }
                });
                itemOrderModels.remove(itemOrderModels.get(position));
                notifyDataSetChanged();
            }
        });
//        holder.basket_product_favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ctx,"favorite",Toast.LENGTH_SHORT).show();
//            }
//        });

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
        public TextView basket_product_name,basket_product_description,basket_product_brand,
                basket_product_count,basket_product_price,basket_product_total_price;
        public ImageView basket_product_image;
        public ImageButton basket_product_add,basket_product_remove;

        public ImageView basket_product_delete,basket_product_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            basket_product_list_box = itemView.findViewById(R.id.basket_list_each_item_box);
            basket_product_name = itemView.findViewById(R.id.eachItem_product_name);
            basket_product_description = itemView.findViewById(R.id.eachItem_product_description);
            basket_product_brand = itemView.findViewById(R.id.eachItem_product_brand);
            basket_product_count = itemView.findViewById(R.id.eachItem_product_count);
            basket_product_price = itemView.findViewById(R.id.eachItem_product_price);
            basket_product_total_price = itemView.findViewById(R.id.eachItem_product_total_price);
            basket_product_image = itemView.findViewById(R.id.eachItem_product_image);
            basket_product_add = itemView.findViewById(R.id.eachItem_product_add);
            basket_product_remove = itemView.findViewById(R.id.eachItem_product_remove);
            basket_product_delete = itemView.findViewById(R.id.eachItem_product_delete);
            //basket_product_favorite = itemView.findViewById(R.id.eachItem_product_favorite);


        }
    }
    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }

}
