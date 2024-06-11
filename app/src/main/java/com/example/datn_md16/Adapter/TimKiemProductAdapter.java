package com.example.datn_md16.Adapter;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.datn_md16.DTO.Product_TimKiem;
        import com.example.datn_md16.R;

        import java.util.List;

public class TimKiemProductAdapter extends RecyclerView.Adapter<TimKiemProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product_TimKiem> productList;

    public TimKiemProductAdapter(Context context, List<Product_TimKiem> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_timkiem, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product_TimKiem product = productList.get(position);
        holder.imageView.setImageResource(product.getImageRes());
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_image);
            textViewName = itemView.findViewById(R.id.tv_product_name);
            textViewPrice = itemView.findViewById(R.id.tv_product_price);
        }
    }
}
