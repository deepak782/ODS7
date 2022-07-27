package com.example.ods7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyJsonAdapter extends RecyclerView.Adapter<MyJsonAdapter.MyHolder> {
    Context context;
    List<JsonModel> jsonModelList=new ArrayList<>();

    public MyJsonAdapter(Context context, List<JsonModel> jsonModelList) {
        this.context = context;
        this.jsonModelList = jsonModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.cusomt_json,parent,false);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.userId.setText(""+jsonModelList.get(position).getUserid());
        holder.id.setText(""+jsonModelList.get(position).getId());
        holder.title.setText(""+jsonModelList.get(position).getTitle());
        holder.body.setText(""+jsonModelList.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return jsonModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView userId,id,title,body;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            userId=itemView.findViewById(R.id.UserID);
            id=itemView.findViewById(R.id.ID);
            title=itemView.findViewById(R.id.Title);
            body=itemView.findViewById(R.id.Body);
        }
    }
}
