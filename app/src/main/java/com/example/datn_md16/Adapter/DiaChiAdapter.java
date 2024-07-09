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

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.datn_md16.DTO.DiaChiDTO;
        import com.example.datn_md16.Interface.ApiService;
        import com.example.datn_md16.R;
        import com.google.android.material.textfield.TextInputEditText;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiViewHolder> {

            private List<DiaChiDTO> diaChiList;
            private ApiService apiService;

            public DiaChiAdapter(List<DiaChiDTO> diaChiList, ApiService apiService) {
                this.diaChiList = diaChiList;
                this.apiService = apiService;
            }

            @NonNull
            @Override
            public DiaChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diachi, parent, false);
                return new DiaChiViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull DiaChiViewHolder holder, int position) {
                DiaChiDTO diaChi = diaChiList.get(position);
                holder.tvName.setText("Tên: " + diaChi.getTen());
                holder.tvAddress.setText("Địa chỉ: " + diaChi.getDiaChi());
                holder.tvPhone.setText("Sđt: " + diaChi.getSdt());

                holder.btnDelete.setOnClickListener(v -> {
                    // Hiển thị dialog xác nhận xóa
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa địa chỉ này không?")
                            .setPositiveButton("Có", (dialog, which) -> {
                                // Gửi yêu cầu xóa địa chỉ lên server
                                Call<Void> call = apiService.deleteDiaChi(diaChi.getId());
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            // Xóa mục khỏi danh sách và thông báo adapter
                                            diaChiList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, diaChiList.size());
                                            Toast.makeText(v.getContext(), "Đã xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(v.getContext(), "Xóa địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Xóa địa chỉ thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            })
                            .setNegativeButton("Không", null)
                            .show();
                });

                holder.btnEdit.setOnClickListener(v -> {
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
                    etName.setText(diaChi.getTen());
                    etPhoneNumber.setText(diaChi.getSdt());
                    etAddress.setText(diaChi.getDiaChi());

                    // Xử lý sự kiện hủy
                    btnCancel.setOnClickListener(view -> dialog.dismiss());

                    // Xử lý sự kiện xác nhận
                    btnConfirm.setOnClickListener(view -> {
                        // Cập nhật thông tin mới
                        String newName = etName.getText().toString();
                        String newPhoneNumber = etPhoneNumber.getText().toString();
                        String newAddress = etAddress.getText().toString();

                        // Kiểm tra nếu thông tin mới khác thông tin cũ thì mới gửi yêu cầu cập nhật
                        if (!newName.equals(diaChi.getTen())
                                || !newPhoneNumber.equals(diaChi.getSdt())
                                || !newAddress.equals(diaChi.getDiaChi())) {
                            diaChi.setTen(newName);
                            diaChi.setSdt(newPhoneNumber);
                            diaChi.setDiaChi(newAddress);

                            // Gửi yêu cầu cập nhật thông tin lên server
                            Call<Void> call = apiService.updateDiaChi(diaChi.getId(), diaChi);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        // Cập nhật lại item trong danh sách và thông báo adapter
                                        diaChiList.set(holder.getAdapterPosition(), diaChi);
                                        notifyItemChanged(holder.getAdapterPosition());
                                        Toast.makeText(v.getContext(), "Đã cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(v.getContext(), "Cập nhật địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(v.getContext(), "Cập nhật địa chỉ thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            // Nếu không có thay đổi, chỉ đóng dialog
                            dialog.dismiss();
                        }
                    });

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();
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
