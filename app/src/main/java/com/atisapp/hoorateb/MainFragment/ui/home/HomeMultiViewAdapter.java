package com.atisapp.hoorateb.MainFragment.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.Comment.CommentActivity;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.MainFragment.ui.category.SubCategoryActivity;
import com.atisapp.hoorateb.ModelsAndAdapters.CategoryListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductHorizontalListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductReviewsListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;
import com.atisapp.hoorateb.Product.ProductAPIService;
import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.Product.ProductListActivity;
import com.atisapp.hoorateb.Product.ProductSliderAdapter;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.CATEGORY_HORIZONTAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_HORIZONTAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_SLIDER;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.MAIN_VERTICAL_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_EMPTY;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_HEADER;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_REVIEWS;
import static com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel.PRODUCT_DETAIL_SPECIFICATION;

public class HomeMultiViewAdapter extends RecyclerView.Adapter {

    private static final                String TAG = "HomeMultiView";
    private Context                     context;
    private CardAPIService              cardAPIService;
    private IdentitySharedPref          identitySharedPref;
    private ProductDetailActivity       productDetailActivity;
    private List<HomeMultiViewModel>    multiViewModelList;
    private ViewGroup                   viewGroup;
    private int                         lastPosition = -1;
    private ProductHorizontalListAdapter productAdapter;
    private CategoryListAdapter         categoryListAdapter;
    private ProductSpecificationListAdapter specificationListAdapter;
    private ProductReviewsListAdapter productReviewsListAdapter;
    private RecyclerView.RecycledViewPool viewPool ;
    private boolean                     isCard,isFavorite;
    private ProductAPIService           productAPIService;
    private ProductModel                myProductModel;





    public HomeMultiViewAdapter(List<HomeMultiViewModel> multiViewModelList, Context context) {

        this.multiViewModelList = multiViewModelList;
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
        cardAPIService = new CardAPIService(context);
        productAPIService = new ProductAPIService(context);
        productDetailActivity = new ProductDetailActivity();
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (multiViewModelList.get(position).getViewType())
        {
            case 0:{
                return MAIN_SLIDER;
            }
            case 1:{
                return MAIN_HORIZONTAL_LIST;
            }
            case 2:{
                return MAIN_VERTICAL_LIST;
            }
            case 3:{
                return PRODUCT_DETAIL_HEADER;
            }
            case 4:{
                return PRODUCT_DETAIL_SPECIFICATION;
            }
            case 5:{
                return CATEGORY_HORIZONTAL_LIST;
            }
            case 6:{
                return PRODUCT_DETAIL_EMPTY;
            }
            case 7:{
                return PRODUCT_DETAIL_REVIEWS;
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
            case MAIN_SLIDER: {

                View sliderView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_main_slider, parent, false);
                return new SliderLayout(sliderView);
            }
            case MAIN_HORIZONTAL_LIST:
            {

                View horizontalListView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_main_multiview_subrecycler, parent, false);
                return new HorizontalListLayout(horizontalListView);
            }

            case PRODUCT_DETAIL_HEADER:
            {
                View productHeaderLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_product_detail_header, parent, false);
                return new ProductDetailHeaderLayout(productHeaderLayout);
            }

            case PRODUCT_DETAIL_SPECIFICATION:
            {

                View specificationListView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_specification_multiview_subrecycler, parent, false);
                return new SpecificationListLayout(specificationListView);
            }

            case CATEGORY_HORIZONTAL_LIST:
            {

                View categoryListView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_main_multiview_subrecycler, parent, false);
                return new CategoryListLayout(categoryListView);
            }

            case PRODUCT_DETAIL_EMPTY: {

                View emptyView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.eachitem_product_list_empty_area, parent, false);
                return new EmptyLayout(emptyView);
            }

            case PRODUCT_DETAIL_REVIEWS:
            {
                Log.i(TAG, "onCreateViewHolder: start");
                View reviewsListView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_specification_multiview_subrecycler, parent, false);
                return new ReviewsListLayout(reviewsListView);
            }

            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (multiViewModelList.get(position).getViewType())
        {
            case MAIN_SLIDER:
            {

                List<HomeSliderModel> homeSliderModelList = multiViewModelList.get(position).getSliderModelList();
                ((SliderLayout)holder).setData(homeSliderModelList);
                break;
            }

            case MAIN_HORIZONTAL_LIST:
            {
                String title = multiViewModelList.get(position).getListTitle();
                String type = multiViewModelList.get(position).getType();
                String categoryId = multiViewModelList.get(position).getCategoryId();
                List<ProductModel> productModelList = multiViewModelList.get(position).getProductModelList();

                ((HorizontalListLayout)holder).setData(title,type,categoryId,productModelList);
                break;
            }

            case PRODUCT_DETAIL_HEADER:
            {

                ProductModel productModel = multiViewModelList.get(position).getProductModel();
                ((ProductDetailHeaderLayout)holder).setData(productModel);
                break;
            }

            case PRODUCT_DETAIL_SPECIFICATION:
            {
                String title = multiViewModelList.get(position).getListName();
                List<ProductSpecificationModel> specificationModels = multiViewModelList.get(position).getSpecificationModels();

                ((SpecificationListLayout)holder).setData(title,specificationModels);
                break;
            }

            case CATEGORY_HORIZONTAL_LIST:
            {
                CategoryModel categoryModel = multiViewModelList.get(position).getCategoryModel();
                ((CategoryListLayout)holder).setData(categoryModel);
                break;
            }

            case PRODUCT_DETAIL_EMPTY:
            {

                boolean isEmpty = multiViewModelList.get(position).isEmpty();
                ((EmptyLayout)holder).setData(isEmpty);
                break;
            }

            case PRODUCT_DETAIL_REVIEWS:
            {
                Log.i(TAG, "onBindViewHolder: "+ multiViewModelList.get(position).getReviews().size());
                List<ProductSpecificationModel> reviewsModels = multiViewModelList.get(position).getReviews();

                ((ReviewsListLayout)holder).setData(reviewsModels);
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

    class SliderLayout extends RecyclerView.ViewHolder
    {
        private Slider slider;

        public SliderLayout(@NonNull View itemView) {
            super(itemView);
            slider = itemView.findViewById(R.id.main_banner_slider);
        }

        private void setData(final List<HomeSliderModel> sliderModelList)
        {
            Log.i(TAG, "setData: slider set data");
            if (sliderModelList.size() > 0) {
                Slider.init(new PicassoImageLoadingService(context));
                slider.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        slider.setAdapter(new MainSliderAdapter(context,sliderModelList));
                        slider.setSelectedSlide(0);
                        slider.setLoopSlides(true);
                        slider.setInterval(5000);
                        slider.setAnimateIndicators(true);
                        slider.setSelectedSlideIndicator(context.getResources().getDrawable(R.drawable.selector_botton_orange_round));
                        slider.setUnSelectedSlideIndicator(context.getResources().getDrawable(R.drawable.selector_botton_white_round_orange));
                        slider.setOnSlideClickListener(new OnSlideClickListener() {
                            @Override
                            public void onSlideClick(int position) {
                                Intent intent = new Intent(context, ProductDetailActivity.class);
                                intent.putExtra("productId",sliderModelList.get(position).getSliderId());
                                context.startActivity(intent);
                            }
                        });

                    }
                }, 1500);
            }
        }
    }

    class HorizontalListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView;
        private TextView title,moreBtn;

        public HorizontalListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView = itemView.findViewById(R.id.main_horizontal_subRecycler);
            title = itemView.findViewById(R.id.main_horizontal_title);
            moreBtn = itemView.findViewById(R.id.main_horizontal_more);

        }

        private void setData(String title, final String type, final String categoryId, final List<ProductModel> productModelList)
        {
            this.title.setText(title);

            Log.i(TAG, "setData: "+title);
            this.title.setTextColor(context.getResources().getColor(R.color.text_color));
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext(),LinearLayoutManager.HORIZONTAL,false
            );
            layoutManager.setInitialPrefetchItemCount(productModelList.size());
            productAdapter = new ProductHorizontalListAdapter(itemView.getContext(),productModelList,1);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setAdapter(productAdapter);
            viewPool = new RecyclerView.RecycledViewPool();
            subRecyclerView.setRecycledViewPool(viewPool);

            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(type.equals("category"))
                    {
                        intent.putExtra("isMainList",0);
                        intent.putExtra("categoryId",categoryId);
                    }else {
                        intent.putExtra("isMainList",1);
                        intent.putExtra("type",type);
                    }

                    context.startActivity(intent);
                }
            });

        }
    }

    class ProductDetailHeaderLayout extends RecyclerView.ViewHolder
    {
        private TextView categoryName;
        private TextView  name;
        private TextView  description;
        private TextView  price;
        private TextView  viewCount;
        private TextView  commentText;
        private TextView  brandText;
        private ImageView productImage;
        private ImageButton cardBtn,favoriteBtn,commentBtn;
        private Slider slider;

        public ProductDetailHeaderLayout(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_detail_herder_name);
            description = itemView.findViewById(R.id.product_detail_herder_description);
            price = itemView.findViewById(R.id.eachItem_product_price);
            viewCount = itemView.findViewById(R.id.product_detail_herder_view_count);
            slider = itemView.findViewById(R.id.product_detail_herder_slider);
            cardBtn = itemView.findViewById(R.id.eachItem_product_card);
            favoriteBtn = itemView.findViewById(R.id.eachItem_product_favorite);
            commentBtn = itemView.findViewById(R.id.eachItem_product_comment);
            brandText = itemView.findViewById(R.id.eachItem_product_brand);
            viewGroup = itemView.findViewById(android.R.id.content);
        }

        private void setData(final ProductModel productModel)
        {
            myProductModel = productModel;
            this.name.setText(myProductModel.getTitle());
            this.description.setText(myProductModel.getDescription());
            this.price.setText(convertNumberToBalance(myProductModel.getPrice()));
            this.viewCount.setText(myProductModel.getViewCount()+"");
            this.brandText.setText(myProductModel.getBrand());

            if (productModel.getImagesUrl().size()>0) {
                Slider.init(new PicassoImageLoadingService(context));
                slider.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        slider.setAdapter(new ProductSliderAdapter(context,myProductModel));
                        slider.setSelectedSlide(0);
                        slider.setLoopSlides(true);
                        slider.setInterval(5000);
                        slider.setAnimateIndicators(true);
                        slider.setSelectedSlideIndicator(context.getResources().getDrawable(R.drawable.selector_botton_orange_round));
                        slider.setUnSelectedSlideIndicator(context.getResources().getDrawable(R.drawable.selector_botton_white_round_orange));

                    }
                }, 1500);
            }

            productAPIService.GetProductFeatures(myProductModel.getProductId(), new ProductAPIService.onGetProductFeatures() {
                @Override
                public void onGetFeatures(boolean res, boolean favorite, boolean cart) {
                    myProductModel.setInBasket(cart);
                    myProductModel.setInWishList(favorite);

                    if(cart)
                    {
                        cardBtn.setColorFilter(context.getResources().getColor(R.color.white));
                        cardBtn.setBackgroundResource(R.drawable.selector_botton_orange_round);

                    }else {
                        cardBtn.setColorFilter(context.getResources().getColor(R.color.colorAccent2));
                        cardBtn.setBackgroundResource(R.drawable.selector_botton_white_round_orange);
                    }

                    if(favorite)
                    {
                        favoriteBtn.setColorFilter(context.getResources().getColor(R.color.white));
                        favoriteBtn.setBackgroundResource(R.drawable.selector_botton_orange_round);
                    }else {

                        favoriteBtn.setColorFilter(context.getResources().getColor(R.color.colorAccent2));
                        favoriteBtn.setBackgroundResource(R.drawable.selector_botton_white_round_orange);
                    }


                }});

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CheckRegistration())
                    {
                        Intent intent = new Intent(context, CommentActivity.class);
                        intent.putExtra("productId",myProductModel.getProductId());
                        context.startActivity(intent);
                    }else
                    {
                        Toast.makeText(context,"برای ورود به بخش نظرات ابتدا باید حساب کاربری خود را تکمیل نمایید",Toast.LENGTH_LONG).show();
                    }
                }
            });

            cardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(CheckRegistration())
                    {
                        SetInCard(myProductModel.getProductId());
                    } else
                    {
                        Toast.makeText(context,"برای افزودن محصول به سبد خرید ابتدا حساب کاربری خورد را تکمیل نمایید",Toast.LENGTH_LONG).show();
                    }
                }
            });

            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(CheckRegistration())
                    {
                        SetInFavorite(myProductModel.getProductId());
                    } else
                    {
                        Toast.makeText(context,"برای افزودن محصول به لیست علاقه مندی ابتدا حساب کاربری خورد را تکمیل نمایید",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        private void SetInCard(String productId)
        {
            isCard = myProductModel.isInBasket();

            if(isCard)
            {
                cardBtn.setColorFilter(context.getResources().getColor(R.color.colorAccent2));
                cardBtn.setBackgroundResource(R.drawable.selector_botton_white_round_orange);

                isCard = false;

                cardAPIService.DeleteFromCard(productId, new CardAPIService.onDeleteFromCard() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(context,"این محصول از سبد خرید شما حذف گردید",Toast.LENGTH_SHORT).show();
                    }
                });

            }else {

                cardBtn.setColorFilter(context.getResources().getColor(R.color.white));
                cardBtn.setBackgroundResource(R.drawable.selector_botton_orange_round);

                isCard = true;

                cardAPIService.AddToCard(productId, new CardAPIService.onAddToCard() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(context,"این محصول به سبد خرید شما افزوده شد",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

        private void SetInFavorite(String productId)
        {
            isFavorite = myProductModel.isInWishList();
            if(isFavorite)
            {
                favoriteBtn.setColorFilter(context.getResources().getColor(R.color.colorAccent2));
                favoriteBtn.setBackgroundResource(R.drawable.selector_botton_white_round_orange);

                isFavorite = false;

                cardAPIService.DeleteFromFavorite(productId, new CardAPIService.onDeleteFromFavorite() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(context,"این محصول از لیست مورد علاقه شما حذف گردید",Toast.LENGTH_SHORT).show();

                    }
                });

            }else {

                favoriteBtn.setColorFilter(context.getResources().getColor(R.color.white));
                favoriteBtn.setBackgroundResource(R.drawable.selector_botton_orange_round);

                isFavorite = true;

                cardAPIService.AddToFavorite(productId, new CardAPIService.onAddToFavorite() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(context,"این محصول به لیست مورد علاقه شما افزوده شد",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    class SpecificationListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView;
        private TextView title;

        public SpecificationListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView = itemView.findViewById(R.id.specification_list_recycler);
            title = itemView.findViewById(R.id.specification_list_title);
        }

        private void setData(String title,List<ProductSpecificationModel> specificationModels)
        {
            this.title.setText(title);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext(),LinearLayoutManager.VERTICAL,false
            );
            layoutManager.setInitialPrefetchItemCount(specificationModels.size());
            Log.i(TAG, "setData: "+specificationModels.size());
            specificationListAdapter = new ProductSpecificationListAdapter(itemView.getContext(),specificationModels,1);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setAdapter(specificationListAdapter);
            viewPool = new RecyclerView.RecycledViewPool();
            subRecyclerView.setRecycledViewPool(viewPool);

        }
    }

    class ReviewsListLayout extends RecyclerView.ViewHolder
    {

        private RecyclerView subRecyclerView;

        public ReviewsListLayout(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "ReviewsListLayout: ");
            subRecyclerView = itemView.findViewById(R.id.specification_list_recycler);
        }

        private void setData(List<ProductSpecificationModel> reviewsModels)
        {
            Log.i(TAG, "setFinalData: ");
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext(),LinearLayoutManager.VERTICAL,false
            );
            layoutManager.setInitialPrefetchItemCount(reviewsModels.size());
            Log.i(TAG, "setData: "+reviewsModels.size());
            productReviewsListAdapter = new ProductReviewsListAdapter(itemView.getContext(),reviewsModels,1);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setAdapter(productReviewsListAdapter);
            viewPool = new RecyclerView.RecycledViewPool();
            subRecyclerView.setRecycledViewPool(viewPool);

        }
    }

    class CategoryListLayout extends RecyclerView.ViewHolder
    {
        private RecyclerView subRecyclerView;
        private TextView title,moreBtn,image;

        public CategoryListLayout(@NonNull View itemView) {
            super(itemView);
            subRecyclerView = itemView.findViewById(R.id.main_horizontal_subRecycler);
            title = itemView.findViewById(R.id.main_horizontal_title);
            moreBtn = itemView.findViewById(R.id.main_horizontal_more);
        }

        private void setData(final CategoryModel categoryModelList)
        {
            this.title.setText(categoryModelList.getName());
            this.title.setTextColor(context.getResources().getColor(R.color.text_color));

            final String categoryId = categoryModelList.getCategoryId();

            this.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SubCategoryActivity.class);
                    intent.putExtra("categoryId",categoryId);
                    context.startActivity(intent);
                }
            });

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext(),LinearLayoutManager.HORIZONTAL,false
            );
            layoutManager.setInitialPrefetchItemCount(categoryModelList.getChildren().size());
            categoryListAdapter = new CategoryListAdapter(itemView.getContext(),categoryModelList.getChildren(),1);
            subRecyclerView.setLayoutManager(layoutManager);
            subRecyclerView.setAdapter(categoryListAdapter);
            viewPool = new RecyclerView.RecycledViewPool();
            subRecyclerView.setRecycledViewPool(viewPool);

        }
    }

    class EmptyLayout extends RecyclerView.ViewHolder
    {

        public EmptyLayout(@NonNull View itemView) {
            super(itemView);
        }

        private void setData(final boolean empty)
        {
            Log.i(TAG, "setEmptyData: "+empty);
        }
    }

    private boolean CheckRegistration()
    {
        boolean isRegistered = false;

        if(identitySharedPref.getIsRegistered() == 1)
            isRegistered = true;

        return isRegistered;
    }

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }
}
