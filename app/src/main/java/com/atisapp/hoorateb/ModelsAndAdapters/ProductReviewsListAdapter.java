package com.atisapp.hoorateb.ModelsAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.R;

import java.util.List;
import java.util.Random;

public class ProductReviewsListAdapter extends RecyclerView.Adapter<ProductReviewsListAdapter.ViewHolder> {

    private static final String TAG = "ProductReviewsListAdapt";
    private List<ProductSpecificationModel> specificationModelList;
    private ProductSpecificationModel specificationModel;
    private Context ctx;
    private Random rnd;
    private int rndNum;
    private String mType;
    private int viewType;
    private boolean clickable = true;
    private String product_id;

    public ProductReviewsListAdapter(Context context, List<ProductSpecificationModel> specificationModelList, int viewType) {
        this.ctx = context;
        this.specificationModelList = specificationModelList;
        this.viewType = viewType;
    }

    public void updateAdapterData(List<ProductSpecificationModel> data, String type) {
        this.specificationModelList = data;
        this.mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_product_detail_reviews,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)  {
        specificationModel = specificationModelList.get(position);

        holder.key.setText(specificationModel.getName());
        holder.value.setText(specificationModel.getValue());

        if(position == (specificationModelList.size()-1))
        {
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        if (this.specificationModelList != null)
            return specificationModelList.size();
        else
            return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView key,value;
        public LinearLayout line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            key = itemView.findViewById(R.id.eachItem_reviews_key);
            value = itemView.findViewById(R.id.eachItem_reviews_value);
            line = itemView.findViewById(R.id.eachItem_reviews_line);
        }


    }


}
