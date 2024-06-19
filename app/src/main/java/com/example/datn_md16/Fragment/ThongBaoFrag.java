package com.example.datn_md16.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.R;

public class ThongBaoFrag extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_thongbao, container, false);

//        recyclerView = view.findViewById(R.id.recyclerViewThongBao);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.43.199:3000/") // Change https to http
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        ApiService apiService = retrofit.create(ApiService.class);

//        apiService.getThongBaos().enqueue(new Callback<List<DTOThongBao>>() {
//            @Override
//            public void onResponse(Call<List<DTOThongBao>> call, Response<List<DTOThongBao>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    adapter = new ThongBaoAdapter(response.body());
//                    recyclerView.setAdapter(adapter);
//                } else {
//                    Log.e("ThongBaoFrag", "Response Error: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DTOThongBao>> call, Throwable t) {
//                Log.e("ThongBaoFrag", "Network Error: " + t.getMessage());
//            }
//        });

        return view;
    }
}
