package com.example.botver2;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {
    List<ResponseMessage> responseMessageList;
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }

    public MessageAdapter(List<ResponseMessage> responseMessageList) {
        this.responseMessageList = responseMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessageList.get(position).isMe()){
            return R.layout.me_bubble;
        }
        return R.layout.bot_bubble;
    }

    @Override
    public int getItemCount() {
        return  responseMessageList.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }


    @Override
    public void onBindViewHolder(MessageAdapter.CustomViewHolder holder, int position) {
        holder.textView.setText(responseMessageList.get(position).getTextMessage());
        /*
        if(MainActivity.hasLink) {

          holder.textView.setText(Html.fromHtml("Here is a <a href=\"http://www.Locked.de\">link</a>"));
          holder.textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
           //MainActivity.hasLink=false;
        }
        */
    }


}
