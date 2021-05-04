package com.atisapp.hoorateb.MainFragment.ui.basket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import java.util.List;

import static com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel.BASKET_PRODUCT_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel.BASKET_TOTAL_ORDER;
import static com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel.FAVORITE_PRODUCT_LIST;

public class BasketMultiViewAdapter extends RecyclerView.Adapter{

    private static final String TAG = "MultiViewAdapter";
    private Context context;
    private IdentitySharedPref identitySharedPref;
    private List<BasketMultiViewModel> multiViewModelList;
    private int lastPosition = -1;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private BasketProductListAdapter productAdapter;
    private FavoriteAdapter favoriteAdapter;
    private int myBalance = 0 , coursePrice , likeCount;
    private String courseID;
    private boolean hasLiked,hasFavorite;
    private boolean myClickable = true;
    private ViewGroup viewGroup;
    private Button payButton;


    public BasketMultiViewAdapter(List<BasketMultiViewModel> multiViewModelList, Context context,String name) {

        this.multiViewModelList = multiViewModelList;
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
        Log.i(TAG, "BasketMultiViewAdapter: " + name);
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (multiViewModelList.get(position).getViewType())
        {
            case 0:{
                return BASKET_PRODUCT_LIST;
            }
            case 1:{
                return BASKET_TOTAL_ORDER;
            }
            case 2:{
                return FAVORITE_PRODUCT_LIST;
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
            case BASKET_PRODUCT_LIST: {
                //Log.i(TAG, "onCreateViewHolder: USER_INFO_LAYOUT");
                Log.i(TAG, "onCreateViewHolder: basket");
                View productLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.multiview_subrecycler_view_layout, parent, false);
                return new BasketProductListLayout(productLayout);
            }
            case BASKET_TOTAL_ORDER:
            {
                //Log.i(TAG, "onCreateViewHolder: AD_INFO_LAYOUT");
                View totalOrderLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_basket_total_orders, parent, false);
                return new TotalOrderLayout(totalOrderLayout);
            }

            case FAVORITE_PRODUCT_LIST: {
                //Log.i(TAG, "onCreateViewHolder: USER_INFO_LAYOUT");
                Log.i(TAG, "onCreateViewHolder: favorite");
                View favoriteLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.multiview_subrecycler_view_layout2, parent, false);
                return new FavoriteProductListLayout(favoriteLayout);
            }

            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (multiViewModelList.get(position).getViewType())
        {
            case BASKET_PRODUCT_LIST: {

                Log.i(TAG, "onBindViewHolder: list basket");
                List<ItemOrderModel> itemOrderModelList = multiViewModelList.get(position).getProductModelList();
                ((BasketProductListLayout)holder).setData(itemOrderModelList);
                break;

            }
            case BASKET_TOTAL_ORDER:
            {
                TotalOrderModel totalOrderModel = multiViewModelList.get(position).getTotalOrderModel();
                ((TotalOrderLayout)holder).setData(totalOrderModel);
                break;
            }

            case FAVORITE_PRODUCT_LIST: {

                Log.i(TAG, "onBindViewHolder: list favorite");
                List<ProductModel> productModelList = multiViewModelList.get(position).getFavoriteList();
                ((FavoriteProductListLayout)holder).setData(productModelList);
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

    class TotalOrderLayout extends RecyclerView.ViewHolder
    {

        private TextView price,count,discount,finalPrice;

        public TotalOrderLayout(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.basket_total_order_price);
            count = itemView.findViewById(R.id.basket_total_order_count);
            discount = itemView.findViewById(R.id.basket_total_order_discount);
            finalPrice = itemView.findViewById(R.id.basket_total_order_final_price);


        }

        private void setData(TotalOrderModel totalOrderModel)
        {
            this.price.setText(totalOrderModel.getTotalPrice()+" تومان ");
            this.count.setText(totalOrderModel.getTotalCount()+" محصول ");
            this.discount.setText("0 تومان ");
            this.finalPrice.setText(totalOrderModel.getTotalPrice()+" تومان ");
        }
    }

    class BasketProductListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView;

        public BasketProductListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView = itemView.findViewById(R.id.product_list_subRecyclerView);
        }

        private void setData(List<ItemOrderModel> itemOrderModels)
        {

//            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(),1);
////            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
////            //gridLayoutManager.setReverseLayout(true);
////            gridLayoutManager.setInitialPrefetchItemCount(productModelList.size());

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            subRecyclerView.setLayoutManager(layoutManager);

            productAdapter = new BasketProductListAdapter(itemView.getContext(),itemOrderModels);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setHasFixedSize(true);
            subRecyclerView.setAdapter(productAdapter);
            subRecyclerView.setRecycledViewPool(viewPool);

        }
    }

    class FavoriteProductListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView2;

        public FavoriteProductListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView2 = itemView.findViewById(R.id.product_list_subRecyclerView2);
        }

        private void setData(List<ProductModel> productModelList)
        {

//            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(),1);
////            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
////            //gridLayoutManager.setReverseLayout(true);
////            gridLayoutManager.setInitialPrefetchItemCount(productModelList.size());

            Log.i(TAG, "setData: favorite");

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            subRecyclerView2.setLayoutManager(layoutManager);

            favoriteAdapter = new FavoriteAdapter(context,productModelList);
            subRecyclerView2.setLayoutManager(layoutManager);
            subRecyclerView2.setHasFixedSize(true);
            subRecyclerView2.setAdapter(favoriteAdapter);
            subRecyclerView2.setRecycledViewPool(viewPool);

            Log.i(TAG, "setData: end");

        }
    }

    public void UpdateList(List<BasketMultiViewModel> multiViewModelList)
    {
        this.multiViewModelList = multiViewModelList;
        notifyDataSetChanged();
    }



}
