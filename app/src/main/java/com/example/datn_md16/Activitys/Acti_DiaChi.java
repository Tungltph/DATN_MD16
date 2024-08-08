package com.example.datn_md16.Activitys;

import static com.example.datn_md16.Activitys.DangNhap.PREFS_NAME;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.datn_md16.Interface.ApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.DiaChiAdapter;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.Interface.ApiResponse;
import com.example.datn_md16.Interface.ApiService;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Acti_DiaChi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiaChiAdapter diaChiAdapter;
    private List<DiaChiDTO> diaChiList;
    private List<GioHangDTO> gioHangList;
    private ApiService apiService;
    private Gson gson;
    private static final String KEY_USER_ID = "user_id";

    private void selectAddress(DiaChiDTO diaChi) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedAddress", diaChi);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = ApiClient.getClient();

        Toolbar toolbar = findViewById(R.id.toolbarDiaChi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.toolbarDiaChi));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = retrofit.create(ApiService.class);

        diaChiList = new ArrayList<>();
        gioHangList = new ArrayList<>();
        diaChiAdapter = new DiaChiAdapter(diaChiList, apiService, this, gioHangList);
        recyclerView.setAdapter(diaChiAdapter);

        ImageView imgDiaChi = findViewById(R.id.imgThemDiaChi);
        imgDiaChi.setOnClickListener(v -> showDialogThemDiaChi());

        fetchData();
    }

    private void showDialogThemDiaChi() {
        Dialog dialog = new Dialog(Acti_DiaChi.this);
        dialog.setContentView(R.layout.dialog_them_diachi);

        TextInputEditText etName = dialog.findViewById(R.id.etNameDiaChi);
        TextInputEditText etPhoneNumber = dialog.findViewById(R.id.etPhoneNumberDiaChi);
        TextInputEditText etAddress = dialog.findViewById(R.id.etAddressDiaChi);

        Button btnCancel = dialog.findViewById(R.id.btnCancelDiaChi);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDiaChi);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString(KEY_USER_ID, null);

            String name = etName.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String idAccount = userId;  // Thay thế bằng giá trị thực tế


            Log.d("Themdiachi","ten" +name);
            Log.d("Themdiachi","sdt" +phoneNumber);
            Log.d("Themdiachi","diachi" +address);
            Log.d("Themdiachi","idacou" +idAccount);


            if (validateInput(name, phoneNumber, address,idAccount)) {
                DiaChiDTO newDiaChi = new DiaChiDTO(name, phoneNumber, address, idAccount);
                addAddress(dialog, newDiaChi);
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private boolean validateInput(String name, String phoneNumber, String address, String idAccount) {
        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Số điện thoại không để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!phoneNumber.matches("\\d+")) {
            Toast.makeText(getApplicationContext(), "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.length() != 10) {
            Toast.makeText(getApplicationContext(), "Số điện thoại chỉ được 10 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addAddress(Dialog dialog, DiaChiDTO newDiaChi) {
        Call<Void> call = apiService.addDiaChi(newDiaChi);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AddAddress", "Response successful: " + response.code());
                    Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    diaChiList.add(newDiaChi);
                    diaChiAdapter.notifyDataSetChanged();
                } else {
                    Log.d("AddAddress", "Response failed: " + response.code() + ", " + response.message());
                    Toast.makeText(getApplicationContext(), "Thêm địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddAddress", "Request failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Thêm địa chỉ thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }



    private void fetchData() {
        Call<ApiResponse> call = apiService.getAllDiaChi();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DiaChiDTO> fetchedData = response.body().getData();
                    diaChiList.clear();
                    diaChiList.addAll(fetchedData);
                    diaChiAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
