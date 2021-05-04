package com.atisapp.hoorateb.MainFragment.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.Comment.CommentAPIService;
import com.atisapp.hoorateb.Comment.CommentModel;
import com.atisapp.hoorateb.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private static final String TAG = "AddressAdapter";
    private List<AddressModel> addressModels;
    private AddressModel addressModel;
    private Context ctx;
    private ProfileAPIService profileAPIService;

    public AddressAdapter(Context context, List<AddressModel> addressModels) {
        this.ctx = context;
        this.addressModels = addressModels;
        profileAPIService = new ProfileAPIService(context);
    }

    public void updateAdapterData(List<AddressModel> data, String type) {
        this.addressModels = data;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.eachitem_address_management,parent,false);
        AddressAdapter.ViewHolder viewHolder = new AddressAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        addressModel = addressModels.get(position);
        holder.address.setText(addressModel.getFullAddress());
        holder.plate.setText(addressModel.getPlate()+"");
        holder.unit.setText(addressModel.getUnit()+"");
        holder.post.setText(addressModel.getPostalCode());
        if(addressModel.isActive())
        {
            holder.checkBox.setChecked(addressModel.isActive());
            holder.checkTitle.setVisibility(View.VISIBLE);
            holder.address.setTextColor(ctx.getResources().getColor(R.color.green));
        }else
        {
            holder.checkBox.setChecked(addressModel.isActive());
            holder.checkTitle.setVisibility(View.GONE);
            holder.address.setTextColor(ctx.getResources().getColor(R.color.text_color2));
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAddresses(addressModels.get(position).getAddressId());
            }
        });

        holder.addressBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAddresses(addressModels.get(position).getAddressId());
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,EditAdressActivity.class);
                intent.putExtra("type","edit");
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressModel", addressModels.get(position));
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        });
        holder.popupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v,position,addressModels.get(position).getAddressId());
            }
        });

    }

    @Override
    public int getItemCount() {

        if (this.addressModels != null)
            return addressModels.size();
        else
            return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout addressBox,editBtn;
        public TextView address,plate,unit,post,checkTitle;
        public RadioButton checkBox;
        private ImageView popupBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressBox = itemView.findViewById(R.id.eachItem_address_layout);
            editBtn = itemView.findViewById(R.id.profile_address_edit_btn);
            address = itemView.findViewById(R.id.eachItem_address);
            plate = itemView.findViewById(R.id.eachItem_plate);
            unit = itemView.findViewById(R.id.eachItem_unit);
            post = itemView.findViewById(R.id.eachItem_post_code);
            checkBox = itemView.findViewById(R.id.eachItem_check);
            checkTitle = itemView.findViewById(R.id.eachItem_check_title);
            popupBtn = itemView.findViewById(R.id.profile_address_delete_btn);
        }
    }
    private void UpdateAddresses(String id)
    {
        for (AddressModel addressModel :
                addressModels) {
            if(addressModel.getAddressId().equals(id)) {
                addressModel.setActive(true);
            }
            else {
                addressModel.setActive(false);
            }
        }
        notifyDataSetChanged();

        profileAPIService.ChangeAddress(id, new ProfileAPIService.onChangeAddress() {
            @Override
            public void onGet(boolean msg) {
                if(msg)
                {
                    Toast.makeText(ctx,"آدرس شما تغییر کرد",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showPopupMenu(View view, final int position , final String addressId) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.address_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addressModels.remove(position);
                notifyDataSetChanged();
                profileAPIService.DeleteAddress(addressId, new ProfileAPIService.onDeleteAddress() {
                    @Override
                    public void onGet(boolean msg) {
                        Toast.makeText(ctx,"آدرس حذف گردید",Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
        popup.show();
    }


}
