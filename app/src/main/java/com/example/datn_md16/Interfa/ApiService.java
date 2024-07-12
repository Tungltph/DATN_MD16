package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.AccountRequest;
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.DTO.ChiTietDienThoaiDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.TimKiemDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/khuyenMai")
    Call<ApiResponseKhuyenMai> getKhuyenMai();

    @GET("/api/sanPham")
    Call<List<ProductHome>> getHotProducts();

    @GET("/api/sanPham")
    Call<List<ProductHome>> getNewProducts();


    @GET("/api/sanPham")
    Call<List<TimKiemDTO>> getTimKiem();

    @GET("/api/account")
    Call<AccountResponse> getAccounts();



    @POST("/api/account/")
    Call<AccountRequest> registerAccount(@Body AccountRequest accountRequest);


}
