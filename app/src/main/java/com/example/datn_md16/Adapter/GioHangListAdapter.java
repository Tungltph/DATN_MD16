package com.example.datn_md16.Adapter;

import com.example.datn_md16.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.datn_md16.DTO.GioHangDTO;

import java.util.List;

public class GioHangListAdapter extends BaseAdapter {
    private List<GioHangDTO> gioHangList;
    private Context context;
    private LayoutInflater inflater;

    public GioHangListAdapter(List<GioHangDTO> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_giohang, parent, false);
        }

        GioHangDTO gioHang = gioHangList.get(position);

        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView mau = convertView.findViewById(R.id.productColor);

        // Gán giá trị cho các view
        productName.setText(gioHang.getSanPham().getTenSanPham());
        productPrice.setText(gioHang.getSanPham().getMauSchema().get(0).getGiaTien() + "đ");
        tvQuantity.setText(String.valueOf(gioHang.getSoLuong()));
        mau.setText("Màu: " + gioHang.getSanPham().getMauSchema().get(0).getMau());
        Glide.with(context).load(gioHang.getSanPham().getHinhAnh()).into(productImage);

        return convertView;
    }
}

