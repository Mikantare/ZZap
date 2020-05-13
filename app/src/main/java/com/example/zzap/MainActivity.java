package com.example.zzap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zzap.adapter.BrandPartAdapter;
import com.example.zzap.api.ApiFactory;
import com.example.zzap.api.ApiService;
import com.example.zzap.pojo.BrandPart;
import com.example.zzap.pojo.BrandPartResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBrand;
    private BrandPartAdapter brandPartAdapter;

    private final String LOGIN = "garajv@yandex.ru";
    private final String PASSWORD = "4d645c5730";
    private final String SEARCH = "15208AA100";
    private final String API_KEY = "MBmE7rdJlQjqwrEpyY78ho2YSeaGsND9tFzzPCrjfBtX5ico5YkBlGkMZDR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewBrand = findViewById(R.id.recyclerViewBrand);
        recyclerViewBrand.setVisibility(View.VISIBLE);
        recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this));
        brandPartAdapter = new BrandPartAdapter();
        recyclerViewBrand.setAdapter(brandPartAdapter);
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        apiService.getBrandParts(SEARCH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BrandPartResponse>() {
                    @Override
                    public void accept(BrandPartResponse brandPartResponse) throws Exception {
                        brandPartAdapter.setBrandParts(brandPartResponse.getBrandParts());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Ошибка получения данных", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
