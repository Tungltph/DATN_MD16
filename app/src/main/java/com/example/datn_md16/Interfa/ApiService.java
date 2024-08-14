package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.AccountRequest;
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.DTO.DanhGiaDTO;
import com.example.datn_md16.DTO.DoiPassDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.DTO.TimKiemDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/khuyenMai")
    Call<ApiResponseKhuyenMai> getKhuyenMai();

    @GET("/api/sanPham/hot")
    Call<List<ProductHome>> getHotProducts();

    @GET("/api/sanPham/new")
    Call<List<ProductHome>> getNewProducts();


    @GET("/api/sanPham")
    Call<List<TimKiemDTO>> getTimKiem();

    @GET("/api/sanPham/topBanChay")
    Call<List<TimKiemDTO>> getBanChay();

    @GET("/api/account")
    Call<AccountResponse> getAccounts();


    @GET("api/giohang")
    Call<List<GioHangDTO>> getGioHang();

    @GET("/api/sanPhamYT/")
    Call<SanPhamYeuThichResponse> getSanPhamYeuThich();

    @GET("api/sanpham/{id}")
    Call<ProductHome> getProductById(@Path("id") String productId);

    @GET("api/account/{id}")
    Call<AccountResponse> getAccount(@Path("id") String id);

    @POST("/api/gioHang/add/")
    Call<Void> adddToCart(@Body GioHangDTO gioHangDTO);




    @DELETE("/api/gioHang/delete/{id}")
    Call<Void> deleteItemFromCart(@Path("id") String id);



    @POST("/api/account/")
    Call<AccountRequest> registerAccount(@Body AccountRequest accountRequest);

    @GET("/api/sanPham")
    Call<List<ProductHome>> getProducts();


    @GET("/api/thongBao/{id}")
    Call<ThongBaoResponse> getThongBaoById(@Path("id") String id);

    @DELETE("/api/thongbao/{id}")
    Call<Void> deleteThongBao(@Path("id") String id);

    @DELETE("/api/sanPhamYT/{id}")
    Call<Void> removeFavorite(@Path("id") String id);



    @GET("/api/donHang/{idKH}")
    Call<DonHangDTO> getDonHangByUser(@Path("idKH") String userId);

    @POST("/api/sanPhamYT/")
    Call<Void> addYeuThich(@Body SanPhamYeuThichDTO sanPhamYeuThichDTO);


    @GET("/api/sanPhamYT/{id}")
    Call<SanPhamYeuThichResponse> getSanPhamYeuThichByUserId(@Path("id") String userId);

    @GET("/api/account/{id}")
    Call<AccountRequest> getAccountById(@Path("id") String id);

    @POST("/api/danhGia")
    Call<Void> themDanhGia(@Body DanhGiaDTO danhGiaDTO);

    @GET("/api/danhGia")
    Call<DanhGiaDTO> getDanhGia();


    @Headers("Content-Type: application/json")
    @PUT("/api/account/edit-pass/{id}")
    Call<ResponseBody> changePassword(@Path("id") String id, @Body DoiPassDTO passwordDTO);

}
