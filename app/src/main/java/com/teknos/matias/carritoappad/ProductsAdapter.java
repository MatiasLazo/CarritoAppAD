package com.teknos.matias.carritoappad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teknos.matias.carritoappad.Entitats.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{

    private List<Product> products;
    private Context context;
    OnSelectedProduct listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivProduct;
        private final TextView tvName;
        private final TextView tvEvalue;
        private final TextView tvPrice;
        private final TextView tvQuantity;
        private final ConstraintLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            ivProduct = view.findViewById(R.id.ivProduct);
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvEvalue = view.findViewById(R.id.tvEValue);
            parentLayout = view.findViewById(R.id.oneLineProductLayout);

        }

    }

    public ProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
        listener = (OnSelectedProduct) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_line_product, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = holder;
        final Product item = products.get(position);
        vh.tvName.setText(item.getName());
        vh.tvEvalue.setText(String.valueOf(item.getEnergeticValue()));
        vh.tvPrice.setText(String.valueOf(item.getPrice()));
        vh.tvQuantity.setText(String.valueOf(item.getQuantity()));

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);

        Glide.with(context).load(products.get(position).getURL()).apply(options).into((vh.ivProduct));

        vh.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnSelectedDetail(item);
            }
        });
    }


    interface OnSelectedProduct{
        void OnSelectedDetail(Product product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
