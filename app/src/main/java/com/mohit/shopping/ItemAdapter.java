package com.mohit.shopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private List<Item> items = new ArrayList<>();
    private OnAdapterItemListener onAdapterItemListener;

    public ItemAdapter(OnAdapterItemListener onAdapterItemListener) {
        this.onAdapterItemListener = onAdapterItemListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final Item item = items.get(position);

        holder.text_view_name.setText("Item : " + item.getName());
        holder.text_view_price.setText("₹" + item.getPrice());
        holder.text_view_count.setText(String.valueOf(item.getCount()));

        holder.btn_add.setOnClickListener(v -> onAdapterItemListener.onAdapterItemClick(true, item));
        holder.btn_subtract.setOnClickListener(v -> onAdapterItemListener.onAdapterItemClick(false, item));

        holder.text_view_total.setText(String.valueOf(item.getPrice() * item.getCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Item getItemAt(int position) {
        return items.get(position);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        private final TextView text_view_count, text_view_name, text_view_price, text_view_total;
        private final ImageButton btn_add, btn_subtract;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            text_view_name = itemView.findViewById(R.id.text_view_name);
            text_view_price = itemView.findViewById(R.id.text_view_price);
            text_view_count = itemView.findViewById(R.id.text_view_count);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_subtract = itemView.findViewById(R.id.btn_subtract);
            text_view_total = itemView.findViewById(R.id.text_view_total);
        }
    }
}
