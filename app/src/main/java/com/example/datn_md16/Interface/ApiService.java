package com.example.datn_md16.Interface;

import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.Interface.ApiResponse;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.PUT;
        import retrofit2.http.Path;

        public interface ApiService {

            @POST("api/diaChi/add/")
            Call<Void> addDiaChi(@Body DiaChiDTO diaChi);

            @GET("/api/DiaChi/")
            Call<ApiResponse> getAllDiaChi();

            @GET("{id}")
            Call<DiaChiDTO> getDiaChiById(@Path("id") String id);

            @DELETE("delete/{id}")
            Call<Void> deleteDiaChi(@Path("id") String id);
            @GET("api/sanpham/{id}")
            Call<SanPhamDTO> getProductById(@Path("id") String productId);


            @PUT("edit/{id}")
            Call<Void> updateDiaChi(@Path("id") String id, @Body DiaChiDTO diaChi);

            @POST("/api/donHang/")
            Call<DonHangDTO> createOrder(@Body DonHangDTO.DonHang donHang);


            @DELETE("/api/gioHang/delete/{id}")
            Call<Void> deleteItemFromCart(@Path("id") String id);

            @GET("api/account/{id}")
            Call<AccountResponse> getAccount(@Path("id") String id);
        }
