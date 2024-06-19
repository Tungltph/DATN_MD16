package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.KhuyenMai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/khuyenMai")
    Call<List<KhuyenMai>> getKhuyenMai();
}
