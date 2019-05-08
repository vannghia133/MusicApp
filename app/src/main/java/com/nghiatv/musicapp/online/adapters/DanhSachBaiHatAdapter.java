package com.nghiatv.musicapp.online.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nghiatv.musicapp.R;
import com.nghiatv.musicapp.online.activities.PlayMusicActivity;
import com.nghiatv.musicapp.dto.SongDto;
import com.nghiatv.musicapp.webservices.APIService;
import com.nghiatv.musicapp.webservices.DataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatAdapter extends RecyclerView.Adapter<DanhSachBaiHatAdapter.ViewHolder> {
    Context mContext;
    List<SongDto> list;

    public DanhSachBaiHatAdapter(Context mContext, List<SongDto> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_danhsachbaihat, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongDto songDto = list.get(position);
        holder.txtTenCS.setText(songDto.getArtistName());
        holder.txtTenBH.setText(songDto.getTitle());
        holder.txtSTT.setText(position + 1 + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgYeuThich;
        TextView txtSTT, txtTenBH, txtTenCS;

        public ViewHolder(View itemView) {
            super(itemView);
            imgYeuThich = itemView.findViewById(R.id.img_yeuThich);
            txtSTT = itemView.findViewById(R.id.tv_danhSachIndex);
            txtTenBH = itemView.findViewById(R.id.tv_tenCaKhuc);
            txtTenCS = itemView.findViewById(R.id.tv_TenCaSiBH);
            imgYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgYeuThich.setImageResource(R.drawable.iconloved);
                    DataService dataService = APIService.getService();
                    Call<String> mCall = dataService.getDataLuotLikeBaiHat("1", list.get(getPosition()).getId());
                    mCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String resuilt = response.body();
                            if (resuilt.equals("OK")){
                                Toast.makeText(mContext, "Đã Thích Cám Ơn", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(mContext, "Please Check Again !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("TAG",t.toString());
                        }
                    });
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PlayMusicActivity.class);
                    intent.putExtra("cakhuc",list.get(getPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
