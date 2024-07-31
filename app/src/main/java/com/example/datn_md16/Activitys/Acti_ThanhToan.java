package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datn_md16.Adapter.GioHangAdapter;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class Acti_ThanhToan extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_ADDRESS = 1;
    private List<GioHangDTO> selectedItems = new ArrayList<>();
    private TextView textViewAddress;
    private RecyclerView recyclerViewProducts;
    private EditText editTextDiscountCode, editTextAddress;
    private Spinner spinnerPaymentMethod;
    private TextView textViewPaymentDetails;
    private TextView textViewTotalAmount, tvten, tvdiachi, tvsdt;
    private Button buttonPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan);

        // Khởi tạo các phần tử giao diện
        textViewAddress = findViewById(R.id.textViewAddress);
        tvten = findViewById(R.id.tvten);
        tvdiachi = findViewById(R.id.tvdiachi);
        tvsdt = findViewById(R.id.tvsdt);
        recyclerViewProducts = findViewById(R.id.listViewProducts);
        editTextDiscountCode = findViewById(R.id.editTextDiscountCode);
        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);
        textViewPaymentDetails = findViewById(R.id.tv_tong_tien_sp);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            DiaChiDTO diaChi = intent.getParcelableExtra("selectedAddress");
            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
            if (diaChi != null) {
                tvten.setText(diaChi.getTen());
                tvdiachi.setText(diaChi.getDiaChi());
                tvsdt.setText(diaChi.getSdt());
                textViewAddress.setText("Địa chỉ nhận hàng: " + diaChi.getDiaChi());
                if (editTextAddress != null) {
                    editTextAddress.setText(diaChi.getDiaChi());
                }
            }

            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
            if (selectedItems != null) {
                updateUI(selectedItems);
            }
        }


        textViewAddress.setOnClickListener(v -> {
            Intent addressIntent = new Intent(Acti_ThanhToan.this, Acti_DiaChi.class);
            addressIntent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
            startActivityForResult(addressIntent, REQUEST_CODE_SELECT_ADDRESS);
        });


        buttonPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void updateUI(List<GioHangDTO> selectedItems) {
        GioHangAdapter adapter = new GioHangAdapter(selectedItems, this);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.setAdapter(adapter);

        textViewPaymentDetails.setText("Chi tiết thanh toán: ...");

        int totalAmount = calculateTotalAmount(selectedItems);
        textViewTotalAmount.setText("Tổng thanh toán: " + totalAmount + " đ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_ADDRESS && resultCode == RESULT_OK) {
            if (data != null) {
                DiaChiDTO selectedAddress = data.getParcelableExtra("selectedAddress");
                if (selectedAddress != null) {
                    textViewAddress.setText("Địa chỉ nhận hàng: " + selectedAddress.getDiaChi());
                    if (editTextAddress != null) {
                        editTextAddress.setText(selectedAddress.getDiaChi());
                    }
                }
            }
        }
        // Đảm bảo rằng dữ liệu sản phẩm được cập nhật
        updateUI(selectedItems);
    }


    private int calculateTotalAmount(List<GioHangDTO> items) {
        int total = 0;
        for (GioHangDTO item : items) {
            if (item.getSanPham() != null && !item.getSanPham().getMauSchema().isEmpty()) {
                int giaTien = item.getSanPham().getMauSchema().get(0).getGiaTien();
                total += item.getSoLuong() * giaTien;
            }
        }
        return total;
    }

    private void placeOrder() {
        // Xử lý logic đặt hàng
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("selectedItems", new ArrayList<>(selectedItems));
        outState.putString("textViewAddress", textViewAddress.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            selectedItems = savedInstanceState.getParcelableArrayList("selectedItems");
            if (selectedItems != null) {
                updateUI(selectedItems);
            }
            textViewAddress.setText(savedInstanceState.getString("textViewAddress"));
        }
    }
}
