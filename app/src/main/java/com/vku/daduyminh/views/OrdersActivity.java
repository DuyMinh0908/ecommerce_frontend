package com.vku.daduyminh.views;

import static com.vku.daduyminh.utils.Constant.ORDER;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vku.daduyminh.R;
import com.vku.daduyminh.adapter.OrderAdapter;
import com.vku.daduyminh.databinding.ActivityOdersBinding;
import com.vku.daduyminh.model.Order;
import com.vku.daduyminh.storage.LoginUtils;
import com.vku.daduyminh.viewmodel.OrderViewModel;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OrderAdapterOnClickHandler {

    private ActivityOdersBinding binding;
    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_oders);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        setUpRecycleView();

        getOrders();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderList.addItemDecoration(dividerItemDecoration);
    }

    private void getOrders() {
        orderViewModel.getOrders(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, orderApiResponse -> {
            orderAdapter = new OrderAdapter( orderApiResponse.getOrderList(),this);
            binding.orderList.setAdapter(orderAdapter);
        });
    }

    @Override
    public void onClick(Order order) {
        Intent intent = new Intent(OrdersActivity.this, StatusActivity.class);
        // Pass an object of order class
        intent.putExtra(ORDER, (order));
        startActivity(intent);
    }
}
