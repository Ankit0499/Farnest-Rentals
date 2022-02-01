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

public class MyShopViewadapter extends RecyclerView.Adapter<MyShopViewadapter.myShopViewholder> {
    Context c2;
    ArrayList<PostShopdetails> myshopdata;


    public MyShopViewadapter (Context c3,ArrayList<PostShopdetails> s1){
        c2 = c3;
        myshopdata = s1;
    }

    @NonNull
    @Override
    public myShopViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new myShopViewholder(LayoutInflater.from(c2).inflate(R.layout.mypostview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myShopViewholder myShopViewholder, int i) {
        myShopViewholder.mshrent.setText("₹-"+myshopdata.get(i).getSrent());
        myShopViewholder.msharea.setText(myshopdata.get(i).getSlandmark());
        Picasso.get().load(myshopdata.get(i).getSimgurl()).fit().into(myShopViewholder.mshopfimage);

        myShopViewholder.mshhid.setText(myshopdata.get(i).getUidshop());
        final String mshid = myShopViewholder.mshhid.getText().toString();
        myShopViewholder.llt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c2,mccshopdetails.class);
                i.putExtra("MyShopID",mshid);
                c2.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myshopdata.size();
    }

    class myShopViewholder extends RecyclerView.ViewHolder{

        TextView mshrent,msharea,mshhid;
        ImageView mshopfimage;
        LinearLayout llt1;

        public myShopViewholder(@NonNull View itemView) {
            super(itemView);

            mshrent = (TextView)itemView.findViewById(R.id.id_mprice);
            msharea = (TextView)itemView.findViewById(R.id.id_marea);
            mshhid = (TextView)itemView.findViewById(R.id.id_hname);

            mshopfimage = (ImageView)itemView.findViewById(R.id.mhouse_view_id);
            llt1 = (LinearLayout)itemView.findViewById(R.id.lv2);
        }
    }
}
