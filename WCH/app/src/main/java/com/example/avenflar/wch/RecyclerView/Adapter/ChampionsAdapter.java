package com.example.avenflar.wch.RecyclerView.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.avenflar.wch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ChampionsAdapter extends RecyclerView.Adapter<ChampionsAdapter.ChampionsHolder>{
    private JSONArray results;
    private RequestManager glide;

    public ChampionsAdapter(JSONArray results, RequestManager glide){
        this.results=results;
        this.glide = glide;

    }


    @Override
    public ChampionsHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.rv_champions,parent,false);
        return new ChampionsHolder(view);
    }


    @Override
    public void onBindViewHolder(ChampionsHolder holder,int position){
        try{
            JSONObject obj=results.getJSONObject(position);
            holder.display(obj,glide);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return results.length();
    }

    public void setNewChamps(JSONArray results){
        this.results=results;
        notifyDataSetChanged();
    }


    public class ChampionsHolder extends RecyclerView.ViewHolder { ;
        private final TextView name;
        private final ImageView picture;
        private final TextView title;



        public ChampionsHolder(View view) {
            super(view);
            picture = (ImageView) itemView.findViewById(R.id.rv_picture);
            name = (TextView) itemView.findViewById(R.id.rv_name);
            title = (TextView) itemView.findViewById(R.id.rv_title);


        }

        public void display(JSONObject obj,RequestManager glide) {

            try {


                glide.load(obj.getString("icon")).into(picture);
                name.setText(obj.getString("name"));
                title.setText(obj.getString("title"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
