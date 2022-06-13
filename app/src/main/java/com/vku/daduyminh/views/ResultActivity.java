package com.vku.daduyminh.views;

import static com.vku.daduyminh.utils.Constant.KEYWORD;
import static com.vku.daduyminh.utils.Constant.PRODUCT;
import static com.vku.daduyminh.utils.InternetUtils.isNetworkConnected;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.vku.daduyminh.R;
import com.vku.daduyminh.adapter.SearchAdapter;
import com.vku.daduyminh.databinding.ActivityResultBinding;
import com.vku.daduyminh.model.Product;
import com.vku.daduyminh.storage.LoginUtils;
import com.vku.daduyminh.viewmodel.SearchByImageViewModel;
import com.vku.daduyminh.viewmodel.SearchViewModel;

import java.io.File;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    private SearchAdapter searchAdapter;
    private List<Product> searchedList;
    private SearchViewModel searchViewModel;
    private int userId;
    private SearchByImageViewModel searchByImageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchByImageViewModel = ViewModelProviders.of(this).get(SearchByImageViewModel.class);
        userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        if(intent.getStringExtra("CLASS").equals("searchtext")){
            String keyword = intent.getStringExtra(KEYWORD);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(keyword);
            if (isNetworkConnected(getApplicationContext())) {
                search(keyword);

            }
        }
        else if (intent.getStringExtra("CLASS").equals("searchimage")){
            String keyword = intent.getStringExtra(KEYWORD);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tìm kiếm bằng hình ảnh ");
            if (isNetworkConnected(getApplicationContext())) {
                searchByImage(keyword);

            }
        }






//        searchByImage(path_image);
    }

    private void search(String query) {

        binding.listOfSearchedList.setHasFixedSize(true);
        binding.listOfSearchedList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));

        searchViewModel.getProductsBySearch(query, userId).observe(this, productApiResponse -> {
            if (productApiResponse != null) {
                searchedList = productApiResponse.getProducts();
                if (searchedList.isEmpty()) {
                    Toast.makeText(ResultActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }

                searchAdapter = new SearchAdapter(getApplicationContext(), searchedList, product -> {
                    Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                    // Pass an object of product class
                    intent.putExtra(PRODUCT, product);
                    startActivity(intent);
                },this);
            }
            binding.listOfSearchedList.setAdapter(searchAdapter);
        });
    }
    private  void searchByImage(String pathname){
        binding.listOfSearchedList.setHasFixedSize(true);
        binding.listOfSearchedList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        searchByImageViewModel.searchPhoto(pathname).observe(this,responseBody -> {
            if (responseBody != null) {
                searchedList = responseBody.getProducts();
                if (searchedList.isEmpty()) {
                    Toast.makeText(ResultActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }

                searchAdapter = new SearchAdapter(getApplicationContext(), searchedList, product -> {
                    Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                    // Pass an object of product class
                    intent.putExtra(PRODUCT, product);
                    startActivity(intent);
                },this);
            }
            binding.listOfSearchedList.setAdapter(searchAdapter);
        });
    }
}
