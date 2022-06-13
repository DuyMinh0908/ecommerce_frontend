package com.vku.daduyminh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vku.daduyminh.R;
import com.vku.daduyminh.databinding.OrderListItemBinding;
import com.vku.daduyminh.model.Order;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final List<Order> orderList;
    private Order currentOrder;

    private final OrderAdapter.OrderAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OrderAdapterOnClickHandler {
        void onClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OrderAdapter.OrderAdapterOnClickHandler clickHandler) {
        this.orderList = orderList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        OrderListItemBinding orderListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_item,parent,false);
        return new OrderViewHolder(orderListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        currentOrder = orderList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentOrder.getProductPrice());
        holder.binding.productPrice.setText(formattedPrice + " VNĐ");
        holder.binding.orderNumber.setText(currentOrder.getOrderNumber());
        holder.binding.orderDate.setText(currentOrder.getOrderDate());
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final OrderListItemBinding binding;
        private OrderViewHolder(OrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            currentOrder = orderList.get(position);
            clickHandler.onClick(currentOrder);
        }
    }
}
