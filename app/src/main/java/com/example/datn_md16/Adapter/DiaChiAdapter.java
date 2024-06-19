package com.example.datn_md16.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_DiaChi;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiViewHolder> {

    private List<DiaChiDTO> diaChiList;

    public DiaChiAdapter(List<DiaChiDTO> diaChiList) {
        this.diaChiList = diaChiList;
    }

    @Override
    public DiaChiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diachi, parent, false);
        return new DiaChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiaChiViewHolder holder, int position) {
        DiaChiDTO diaChi = diaChiList.get(position);
        holder.tvName.setText("Tên: " + diaChi.getName());
        holder.tvAddress.setText("Địa chỉ: " + diaChi.getAddress());
        holder.tvPhone.setText("Sđt: " + diaChi.getPhone());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog xác nhận xóa
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa địa chỉ này không?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            // Xóa mục khỏi danh sách và thông báo adapter
                            diaChiList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, diaChiList.size());
                            Toast.makeText(v.getContext(), "Đã xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog chỉnh sửa
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_update_diachi);

                // Lấy các view trong dialog
                TextInputEditText etName = dialog.findViewById(R.id.etName);
                TextInputEditText etPhoneNumber = dialog.findViewById(R.id.etPhoneNumber);
                TextInputEditText etAddress = dialog.findViewById(R.id.etAddress);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);

                // Điền thông tin cũ vào các trường
                etName.setText(diaChi.getName());
                etPhoneNumber.setText(diaChi.getPhone());
                etAddress.setText(diaChi.getAddress());

                // Xử lý sự kiện hủy
                btnCancel.setOnClickListener(view -> dialog.dismiss());

                // Xử lý sự kiện xác nhận
                btnConfirm.setOnClickListener(view -> {
                    // Cập nhật thông tin mới
                    diaChi.setName(etName.getText().toString());
                    diaChi.setPhone(etPhoneNumber.getText().toString());
                    diaChi.setAddress(etAddress.getText().toString());

                    // Thông báo adapter để cập nhật giao diện
                    notifyItemChanged(position);

                    // Hiển thị thông báo
                    Toast.makeText(v.getContext(), "Đã cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaChiList.size();
    }

    public static class DiaChiViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvPhone;
        public ImageView btnDelete;
        public ImageView btnEdit;

        public DiaChiViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
