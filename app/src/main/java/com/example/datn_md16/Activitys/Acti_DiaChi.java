        package com.example.datn_md16.Activitys;

        import android.app.Dialog;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import okhttp3.OkHttpClient;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.datn_md16.Adapter.DiaChiAdapter;
        import com.example.datn_md16.DTO.DiaChiDTO;
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
            private ApiService apiService;
            private Gson gson;

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

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiService.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();


                Toolbar toolbar = findViewById(R.id.toolbarDiaChi);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                setTitle(getString(R.string.toolbarDiaChi));

                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                apiService = retrofit.create(ApiService.class);

                diaChiList = new ArrayList<>();
                diaChiAdapter = new DiaChiAdapter(diaChiList, apiService);
                recyclerView.setAdapter(diaChiAdapter);

                ImageView imgDiaChi = findViewById(R.id.imgThemDiaChi);
                imgDiaChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogThemDiaChi();
                    }
                });

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

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString().trim();
                        String phoneNumber = etPhoneNumber.getText().toString().trim();
                        String address = etAddress.getText().toString().trim();

                        if (name.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Tên không được để trống", Toast.LENGTH_SHORT).show();
                        } else if (phoneNumber.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Số điện thoại không để trống", Toast.LENGTH_SHORT).show();
                        } else if (!phoneNumber.matches("\\d+")) {
                            Toast.makeText(getApplicationContext(), "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
                        } else if (phoneNumber.length() != 10) {
                            Toast.makeText(getApplicationContext(), "Số điện thoại chỉ được 10 kí tự", Toast.LENGTH_SHORT).show();
                        } else if (address.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            DiaChiDTO newDiaChi = new DiaChiDTO(name, phoneNumber, address);

                            Call<Void> call = apiService.addDiaChi(newDiaChi);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        diaChiList.add(newDiaChi);
                                        diaChiAdapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Thêm địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Thêm địa chỉ thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
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
