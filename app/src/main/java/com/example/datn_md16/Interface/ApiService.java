package com.example.datn_md16.Interface;

import com.example.datn_md16.DTO.DiaChiDTO;
        import com.example.datn_md16.Interface.ApiResponse;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.PUT;
        import retrofit2.http.Path;

        public interface ApiService {
            String BASE_URL = "http://192.168.0.102:3000/api/DiaChi/";

            @POST("add")
            Call<Void> addDiaChi(@Body DiaChiDTO diaChi);

            @GET("http://192.168.0.102:3000/api/DiaChi/")
            Call<ApiResponse> getAllDiaChi();

            @GET("{id}")
            Call<DiaChiDTO> getDiaChiById(@Path("id") String id);

            @DELETE("delete/{id}")
            Call<Void> deleteDiaChi(@Path("id") String id);

            @PUT("edit/{id}")
            Call<Void> updateDiaChi(@Path("id") String id, @Body DiaChiDTO diaChi);
        }
