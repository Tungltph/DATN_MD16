package com.example.datn_md16.Interface;

import com.example.datn_md16.DTO.AccountResponse;

import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.ProductHome;

import okhttp3.RequestBody;
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

            @DELETE("api/diaChi/delete/{id}")
            Call<Void> deleteDiaChi(@Path("id") String id);

            @GET("api/sanpham/{id}")
            Call<ProductHome> getProductById(@Path("id") String productId);


            @PUT("api/diaChi/edit/{id}")
            Call<Void> updateDiaChi(@Path("id") String id, @Body DiaChiDTO diaChi);

            @POST("/api/donHang/")
            Call<DonHangDTO> createOrder(@Body DonHangDTO.DonHang donHang);

            @PUT("/api/donHang/update/{id}")
            Call<DonHangDTO> updateoder(@Path("id") String id, @Body DonHangDTO.DonHang donHang);


            @GET("/api/sanpham/mau/{mauId}")
            Call<ProductHome.MauSchema> getMauSchemaById(@Path("mauId") String mauId);

            @PUT("/api/sanpham/edit-mau/{productId}/{mauId}")
            Call<Void> updateMauSchema(@Path("productId") String productId, @Path("mauId") String mauId, @Body ProductHome.MauSchema mauSchema);



            @DELETE("/api/gioHang/delete/{id}")
            Call<Void> deleteItemFromCart(@Path("id") String id);

            @GET("api/account/{id}")
            Call<AccountResponse> getAccount(@Path("id") String id);
//
//            @POST("/api/danhGia/")
//            Call<DanhGiaDTO> adddanhgia(@Body DanhGiaDTO danhgia);
        }
