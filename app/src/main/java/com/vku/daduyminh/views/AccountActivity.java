package com.vku.daduyminh.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.vku.daduyminh.R;
import com.vku.daduyminh.databinding.ActivityAccountBinding;
import com.vku.daduyminh.storage.LoginUtils;
import com.vku.daduyminh.utils.FlagsManager;
import com.vku.daduyminh.viewmodel.DeleteUserViewModel;
import com.vku.daduyminh.viewmodel.FromHistoryViewModel;
import com.vku.daduyminh.viewmodel.HelpActivity;

import java.io.IOException;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AccountActivity";
    private DeleteUserViewModel deleteUserViewModel;
    private FromHistoryViewModel fromHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.my_account));

        deleteUserViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);

        binding.nameOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.myOrders.setOnClickListener(this);
        binding.myWishList.setOnClickListener(this);
        binding.languages.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
        binding.shareWithFriends.setOnClickListener(this);
        binding.rateUs.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            deleteAllProductsInHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteAllProductsInHistory() {
        fromHistoryViewModel.removeAllFromHistory().observe(this, responseBody -> Log.d(TAG,getString(R.string.all_removed)));
        FlagsManager.getInstance().setHistoryDeleted(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrders:
                Intent ordersIntent = new Intent(this, OrdersActivity.class);
                startActivity(ordersIntent);
                break;
            case R.id.myWishList:
                Intent wishListIntent = new Intent(this, WishListActivity.class);
                startActivity(wishListIntent);
                break;

            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, HelpActivity.class);
                startActivity(helpCenterIntent);
                break;

            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, PasswordActivity.class);
                startActivity(passwordIntent);
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
            default: // Should not get here
        }
    }

    private void deleteAccount() {
        deleteUserViewModel.deleteUser(LoginUtils.getInstance(this).getUserToken(), LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, responseBody -> {
            if(responseBody!= null){
                LoginUtils.getInstance(getApplicationContext()).clearAll();
                try {
                    Toast.makeText(AccountActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: delete account" + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




}