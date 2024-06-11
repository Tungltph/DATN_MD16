    package com.example.datn_md16.Fragment;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.example.datn_md16.Activitys.Acti_GioHang;
    import com.example.datn_md16.Activitys.Acti_TimKiem;
    import com.example.datn_md16.DTO.ProductHome;
    import com.example.datn_md16.R;

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFrag extends Fragment {

        private LinearLayout llHotProducts;
        private LinearLayout llNewProducts;
        private TextView tvSearchHome;
        private ImageView idGioHangHome;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.frag_home, container, false);

            llHotProducts = rootView.findViewById(R.id.llHotProducts);
            llNewProducts = rootView.findViewById(R.id.llNewProducts);
            tvSearchHome = rootView.findViewById(R.id.tvSearchHome);
            idGioHangHome = rootView.findViewById(R.id.idGioHangHome);

            tvSearchHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Acti_TimKiem.class);
                    startActivity(intent);
                }
            });

            idGioHangHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Acti_GioHang.class);
                    startActivity(intent);
                }
            });


            List<ProductHome> hotProducts = getHotProducts();
            List<ProductHome> newProducts = getNewProducts();

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());


            for (ProductHome product : hotProducts) {
                View itemProductView = layoutInflater.inflate(R.layout.item_hot, llHotProducts, false);

                TextView textViewProductName = itemProductView.findViewById(R.id.txtProductNameHot);

                textViewProductName.setText(product.getName());

                llHotProducts.addView(itemProductView);
            }

            for (ProductHome product : newProducts) {
                View itemProductView = layoutInflater.inflate(R.layout.item_new, llNewProducts, false);

                TextView textViewProductName = itemProductView.findViewById(R.id.tvProductNameNew);
                TextView textViewPrice = itemProductView.findViewById(R.id.tvPriceNew);
                TextView textViewRating = itemProductView.findViewById(R.id.tvRatingNew);
                ImageView imageViewProduct = itemProductView.findViewById(R.id.imgSanPhamNew);

                textViewProductName.setText(product.getName());
                textViewPrice.setText(product.getPrice());
                textViewRating.setText(String.valueOf(product.getRating()));
                imageViewProduct.setImageResource(product.getImageRes());

                llNewProducts.addView(itemProductView);
            }

            return rootView;
        }

        private List<ProductHome> getHotProducts() {
            List<ProductHome> hotProducts = new ArrayList<>();
            // Thêm các sản phẩm vào danh sách hotProducts
            hotProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "8.999.000đ", 5.0f));
            hotProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "8.999.000đ", 5.0f));
            hotProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "8.999.000đ", 5.0f));
            hotProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "8.999.000đ", 5.0f));
            return hotProducts;
        }
        private List<ProductHome> getNewProducts() {
            List<ProductHome> newProducts = new ArrayList<>();
            newProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "10.999.000đ", 5.0f));
            newProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "10.999.000đ", 5.0f));
            newProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "10.999.000đ", 5.0f));
            newProducts.add(new ProductHome(R.drawable.img_1, "Iphone 15 Pro", "10.999.000đ", 5.0f));
            return newProducts;
        }


    }
