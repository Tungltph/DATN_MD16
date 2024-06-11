package com.example.datn_md16.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datn_md16.Adapter.ProductAdapter;
import com.example.datn_md16.DTO.Product;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class YeuThichFrag extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_yeuthich, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Số cột là 2

        productList = new ArrayList<>();

        productList.add(new Product(R.drawable.img_1, "iPhone 15 Pro", 5.0f));
        productList.add(new Product(R.drawable.img_1, "iPhone 15 Pro", 5.0f));
        productList.add(new Product(R.drawable.img_1, "iPhone 15 Pro", 5.0f));
        productList.add(new Product(R.drawable.img_1, "iPhone 15 Pro", 5.0f));


        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_thich, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.imageView.setImageResource(product.getImage());
            holder.textViewName.setText(product.getName());
            holder.ratingBar.setRating(product.getRating());

            // Kiểm tra trạng thái yêu thích của sản phẩm
            if (product.isFavorite()) {
                holder.imageViewYeuThich.setImageResource(R.drawable.ic_yeuthich_do);
            } else {
                holder.imageViewYeuThich.setImageResource(R.drawable.ic_favorite);
            }

            // Thêm sự kiện nhấp vào biểu tượng trái tim
            holder.imageViewYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Đảo ngược trạng thái yêu thích của sản phẩm
                    product.setFavorite(!product.isFavorite());
                    // Cập nhật giao diện người dùng
                    if (product.isFavorite()) {
                        holder.imageViewYeuThich.setImageResource(R.drawable.ic_yeuthich_do);
                    } else {
                        holder.imageViewYeuThich.setImageResource(R.drawable.ic_favorite);
                    }
                }
            });
        }



        @Override
        public int getItemCount() {
            return productList.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textViewName;
            RatingBar ratingBar;
            ImageView imageViewYeuThich;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imgSanPham);
                textViewName = itemView.findViewById(R.id.textViewName);
                ratingBar = itemView.findViewById(R.id.rbSao);
                imageViewYeuThich = itemView.findViewById(R.id.imgYeuThich);
            }
        }
    }
}
