package com.test.cesar.amkprueba.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.cesar.amkprueba.utils.ICommunication;
import com.test.cesar.amkprueba.R;
import com.test.cesar.amkprueba.model.Category;
import com.test.cesar.amkprueba.model.Constants;

import java.util.ArrayList;

/**
 * Created by Cesar on 26/08/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> items;
    public ICommunication mCallBack;

    public CategoryAdapter(ArrayList<Category> items, ICommunication mCallBack) {
        this.items = items;
        this.mCallBack=mCallBack;
    }


    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Category category = items.get(position);

        holder.tvName.setText(category.getName());
        holder.ivImage.setImageDrawable(category.getImage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView ivImage;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_category_name);
            ivImage = v.findViewById(R.id.iv_category_image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallBack.onResponse(Constants.CATEGORY_ITEM, items.get(getAdapterPosition()));
        }
    }


}
