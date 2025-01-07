package com.example.chattingapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattingapplication.R;
import com.example.chattingapplication.Utils.FirebaseUtil;
import com.example.chattingapplication.model.ChatMessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatModelViewHolder> {

    Context context;

    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        Log.i("ChatAdapter", "onBindViewHolder");

        if (model.getSenderId().equals(FirebaseUtil.currentUserId())) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            handleChatMessage(model, holder.rightChatTextview, holder.rightChatImageview, holder.rightChatFileview);
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            handleChatMessage(model, holder.leftChatTextview, holder.leftChatImageview, holder.leftChatFileview);
        }
    }

    private void handleChatMessage(ChatMessageModel model, TextView textView, ImageView imageView, TextView fileView) {
        if (model.getFileType() != null) {
            textView.setVisibility(View.GONE);

            if (model.getFileType().startsWith("image/")) {
                imageView.setVisibility(View.VISIBLE);
                fileView.setVisibility(View.GONE);
                Glide.with(context).load(model.getFileUrl()).into(imageView);
            } else {
                imageView.setVisibility(View.GONE);
                fileView.setVisibility(View.VISIBLE);
                fileView.setText("File: " + model.getFileUrl());
                fileView.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getFileUrl()));
                    context.startActivity(intent);
                });
            }
        } else {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            fileView.setVisibility(View.GONE);
            textView.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new ChatModelViewHolder(view);
    }

    static class ChatModelViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatTextview, rightChatTextview;
        ImageView leftChatImageview, rightChatImageview;
        TextView leftChatFileview, rightChatFileview;

        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
            leftChatImageview = itemView.findViewById(R.id.left_chat_imageview);
            rightChatImageview = itemView.findViewById(R.id.right_chat_imageview);
            leftChatFileview = itemView.findViewById(R.id.left_chat_fileview);
            rightChatFileview = itemView.findViewById(R.id.right_chat_fileview);
        }
    }
}
