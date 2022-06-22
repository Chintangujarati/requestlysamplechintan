package com.pinkoochintantest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pinkoochintantest.R;
import com.pinkoochintantest.api.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    JSONArray arrayList;
    boolean isGrid = true;


    public ProductAdapter(Context context, JSONArray arrayList,boolean isGrid) {
        this.context = context;
        this.arrayList = arrayList;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (isGrid) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
        }
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            JSONObject jsonObject = arrayList.getJSONObject(position);

            holder.tvTitle.setText(jsonObject.getString("name"));
            holder.tvPrice.setText("₹"+jsonObject.getInt("price"));


            Glide
                    .with(context)
                    .load(ApiInterface.BASE_URL + jsonObject.getString("productImageUrl"))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(holder.image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPrice;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            image = itemView.findViewById(R.id.image);
        }
    }
}
