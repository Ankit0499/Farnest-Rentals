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

public class shopadapter extends RecyclerView.Adapter<shopadapter.MyViewHolder>{

    Context context;
    ArrayList<PostShopdetails> shopdata;

    public shopadapter(Context c,ArrayList<PostShopdetails> hd){
        context = c;
        shopdata = hd;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.houseview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.srent.setText(shopdata.get(i).getSrent());
        myViewHolder.sarea.setText(shopdata.get(i).getSlandmark());
        myViewHolder.ssid.setText(shopdata.get(i).getUidshop());
        Picasso.get().load(shopdata.get(i).getSimgurl()).into(myViewHolder.shopfimage);

        final String hid = myViewHolder.ssid.getText().toString();

        myViewHolder.ls1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ccshopdetails.class);
                i.putExtra("ShopID",hid);
                context.startActivity(i);
            }
        });




    }

    @Override
    public int getItemCount() {
        return shopdata.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView srent,sbhk,sarea,ssid;
        ImageView shopfimage;
        LinearLayout ls1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srent = (TextView)itemView.findViewById(R.id.id_price);
            sarea = (TextView)itemView.findViewById(R.id.id_area);

            ssid = (TextView)itemView.findViewById(R.id.id_hid);

            shopfimage = (ImageView)itemView.findViewById(R.id.house_view_id);

            ls1 = (LinearLayout)itemView.findViewById(R.id.ll1);

        }
    }

}
