package com.example.ankit_prajapati.login;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyViewHolder>{

    Context context;
    ArrayList<PostHousedetails> housedata;

    public HouseAdapter(Context c,ArrayList<PostHousedetails> hd){
        context = c;
        housedata = hd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.houseview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.hrent.setText(housedata.get(i).getRent());
        myViewHolder.harea.setText(housedata.get(i).getLandmark());

        myViewHolder.hhid.setText(housedata.get(i).getUidhome());

        Picasso.get().load(housedata.get(i).getImgurl()).into(myViewHolder.homefimage);

        final String hid = myViewHolder.hhid.getText().toString();
        myViewHolder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,cchomedetails.class);
                i.putExtra("HomeID",hid);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return housedata.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView hrent,harea,hhid;
        ImageView homefimage;
        LinearLayout ll1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hrent = (TextView)itemView.findViewById(R.id.id_price);
            harea = (TextView)itemView.findViewById(R.id.id_area);
            hhid = (TextView)itemView.findViewById(R.id.id_hid);

            homefimage = (ImageView)itemView.findViewById(R.id.house_view_id);

            ll1 = (LinearLayout)itemView.findViewById(R.id.ll1);

        }
    }
}
