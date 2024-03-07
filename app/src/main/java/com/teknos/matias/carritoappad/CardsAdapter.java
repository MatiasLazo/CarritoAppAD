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
import com.teknos.matias.carritoappad.Entitats.CreditCard;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

    private List<CreditCard> creditCards;
    private Context context;
    onSelectedCard listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivCard;
        private final TextView tvTitular;
        private final ConstraintLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            ivCard = view.findViewById(R.id.ivCard);
            tvTitular = view.findViewById(R.id.tvTitular);
            parentLayout = view.findViewById(R.id.oneLineProductLayout);

        }

    }

    public CardsAdapter(List<CreditCard> creditCards, Context context) {
        this.creditCards = creditCards;
        this.context = context;
        listener = (onSelectedCard) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_line_card, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = holder;
        final CreditCard item = creditCards.get(position);
        vh.tvTitular.setText(item.getName());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);

        Glide.with(context).load(creditCards.get(position).getURL()).apply(options).into((vh.ivCard));

        vh.ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnSelectedDetail(item);
            }
        });
    }


    interface onSelectedCard {
        void OnSelectedDetail(CreditCard creditCard);
    }

    @Override
    public int getItemCount() {
        return creditCards.size();
    }

}
