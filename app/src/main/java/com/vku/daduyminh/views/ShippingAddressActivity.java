package com.vku.daduyminh.views;

import static com.vku.daduyminh.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.vku.daduyminh.R;
import com.vku.daduyminh.databinding.ActivityShippingAddressBinding;
import com.vku.daduyminh.model.Shipping;
import com.vku.daduyminh.storage.LoginUtils;
import com.vku.daduyminh.viewmodel.ShippingViewModel;

import java.io.IOException;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityShippingAddressBinding binding;

    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int productId = intent.getIntExtra(PRODUCTID, 0);

        Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productId);

        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
            Toast.makeText(ShippingAddressActivity.this, responseBody+"", Toast.LENGTH_SHORT).show();
            Intent orderProductIntent = new Intent(ShippingAddressActivity.this, OrderProductActivity.class);
            orderProductIntent.putExtra(PRODUCTID,productId);
            startActivity(orderProductIntent);
        });
    }
}
