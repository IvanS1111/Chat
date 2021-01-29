package com.example.rchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rchat.MessageActivity;
import com.example.rchat.Model.Chat;
import com.example.rchat.Model.User;
import com.example.rchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chatList;
    private String imageURL;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chatList, String imageURL){
        this.chatList = chatList;
        this.context = context;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.message.setText(chat.getMessage());
        if(imageURL.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }
        else{
            Glide.with(context).load(imageURL).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(firebaseUser.getUid())){
            return TYPE_RIGHT;
        }
        else{
            return TYPE_LEFT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView message;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_show);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }

}
