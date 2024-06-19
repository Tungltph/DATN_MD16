package com.example.datn_md16.Activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.DiaChiAdapter;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Acti_DiaChi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiaChiAdapter diaChiAdapter;
    private List<DiaChiDTO> diaChiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarDiaChi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarDiaChi));

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy dữ liệu mẫu
        diaChiList = getData();
        diaChiAdapter = new DiaChiAdapter(diaChiList);
        recyclerView.setAdapter(diaChiAdapter);

        ImageView imgDiaChi = findViewById(R.id.imgThemDiaChi);
        imgDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemDiaChi();
            }
        });
    }

    private void showDialogThemDiaChi() {
        Dialog dialog = new Dialog(Acti_DiaChi.this);
        dialog.setContentView(R.layout.dialog_them_diachi);

        // Lấy các EditText từ dialog
        TextInputEditText etName = dialog.findViewById(R.id.etNameDiaChi);
        TextInputEditText etPhoneNumber = dialog.findViewById(R.id.etPhoneNumberDiaChi);
        TextInputEditText etAddress = dialog.findViewById(R.id.etAddressDiaChi);

        // Lấy nút huỷ và xác nhận từ dialog
        Button btnCancel = dialog.findViewById(R.id.btnCancelDiaChi);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDiaChi);

        // Xử lý sự kiện click cho nút huỷ
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Xử lý sự kiện click cho nút xác nhận
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ EditText
                String name = etName.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                // Kiểm tra dữ liệu nhập vào
                if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                    // Hiển thị thông báo nếu có dữ liệu nhập thiếu
                    // Có thể thêm logic xử lý lỗi tại đây
                    return;
                }

                // Tạo đối tượng DiaChiDTO mới
                DiaChiDTO newDiaChi = new DiaChiDTO(name, address, phoneNumber);

                // Thêm vào danh sách và cập nhật RecyclerView
                diaChiList.add(newDiaChi);
                diaChiAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Thêm địa chỉ thành công ",Toast.LENGTH_SHORT).show();

                // Đóng dialog sau khi thêm thành công
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private List<DiaChiDTO> getData() {
        List<DiaChiDTO> data = new ArrayList<>();
        data.add(new DiaChiDTO("User 1", "16 Trịnh Văn Bô", "0343357756"));
        data.add(new DiaChiDTO("User 2", "12 Nguyễn Văn Cừ", "0343357757"));
        data.add(new DiaChiDTO("User 3", "8 Lý Thường Kiệt", "0343357758"));
        // Thêm nhiều dữ liệu nếu cần
        return data;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
