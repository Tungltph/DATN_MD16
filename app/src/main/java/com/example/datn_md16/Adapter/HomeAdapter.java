package com.example.datn_md16.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HOT = 1;
    private static final int VIEW_TYPE_NEW = 2;

    private List<ProductHome> productList;
    private Context context;

    public HomeAdapter(Context context, List<ProductHome> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HOT; // Assume the first item is "Hot"
        } else {
            return VIEW_TYPE_NEW; // Assume the rest are "New"
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_HOT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot, parent, false);
                return new HotProductViewHolder(view);
            case VIEW_TYPE_NEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new, parent, false);
                return new NewProductViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductHome product = productList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HOT:
                HotProductViewHolder hotViewHolder = (HotProductViewHolder) holder;
                hotViewHolder.txtProductNameHot.setText(product.getTenDienThoai());
                hotViewHolder.txtPriceHot.setText(product.getGiaGoc());
                hotViewHolder.txtRatingHot.setText(String.valueOf(product.getRating()));
                Picasso.get().load(product.getHinhAnh()).into(hotViewHolder.imgSanPhamHot);
                hotViewHolder.rbSaoHot.setRating(product.getRating());
                break;
            case VIEW_TYPE_NEW:
                NewProductViewHolder newViewHolder = (NewProductViewHolder) holder;
                newViewHolder.tvProductNameNew.setText(product.getTenDienThoai());
                newViewHolder.tvPriceNew.setText(product.getGiaGoc());
                newViewHolder.tvRatingNew.setText(String.valueOf(product.getRating()));
                Picasso.get().load(product.getHinhAnh()).into(newViewHolder.imgSanPhamNew);
                newViewHolder.rbSaoNew.setRating(product.getRating());
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder for "Hot" product item
    public static class HotProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPhamHot;
        TextView txtProductNameHot, txtPriceHot, txtRatingHot;
        RatingBar rbSaoHot;

        public HotProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPhamHot = itemView.findViewById(R.id.imgSanPhamHot);
            txtProductNameHot = itemView.findViewById(R.id.txtProductNameHot);
            txtPriceHot = itemView.findViewById(R.id.txtPriceHot);
            txtRatingHot = itemView.findViewById(R.id.txtRatingHot);
            rbSaoHot = itemView.findViewById(R.id.rbSaoHot);
        }
    }

    // ViewHolder for "New" product item
    public static class NewProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPhamNew;
        TextView tvProductNameNew, tvPriceNew, tvRatingNew;
        RatingBar rbSaoNew;

        public NewProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPhamNew = itemView.findViewById(R.id.imgSanPhamNew);
            tvProductNameNew = itemView.findViewById(R.id.tvProductNameNew);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            tvRatingNew = itemView.findViewById(R.id.tvRatingNew);
            rbSaoNew = itemView.findViewById(R.id.rbSaoNew);
        }
    }
}
