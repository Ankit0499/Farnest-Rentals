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

public class MypostViewadapter extends RecyclerView.Adapter<MypostViewadapter.MypostViewHolder>{

    Context c;
    ArrayList<PostHousedetails> myhdata;

    public MypostViewadapter (Context c1,ArrayList<PostHousedetails> hd1){
        c = c1;
        myhdata = hd1;
    }

    @NonNull
    @Override
    public MypostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MypostViewHolder(LayoutInflater.from(c).inflate(R.layout.mypostview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MypostViewHolder mypostViewHolder, int i) {
        mypostViewHolder.mhrent.setText("₹-"+myhdata.get(i).getRent());
        mypostViewHolder.mharea.setText(myhdata.get(i).getLandmark());
        Picasso.get().load(myhdata.get(i).getImgurl()).fit().into(mypostViewHolder.mhomefimage);

        mypostViewHolder.mhhid.setText(myhdata.get(i).getUidhome());
        final String mhid = mypostViewHolder.mhhid.getText().toString();
        mypostViewHolder.lv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,mcchomedetails.class);
                i.putExtra("MyHomeID",mhid);
                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myhdata.size();
    }

    class MypostViewHolder extends RecyclerView.ViewHolder{

        TextView mhrent,mharea,mhhid;
        ImageView mhomefimage;
        LinearLayout lv2;

        public MypostViewHolder(@NonNull View itemView) {
            super(itemView);
            mhrent = (TextView)itemView.findViewById(R.id.id_mprice);
            mharea = (TextView)itemView.findViewById(R.id.id_marea);
            mhhid = (TextView)itemView.findViewById(R.id.id_hname);

            mhomefimage = (ImageView)itemView.findViewById(R.id.mhouse_view_id);
            lv2 = (LinearLayout)itemView.findViewById(R.id.lv2);
        }
    }
}
