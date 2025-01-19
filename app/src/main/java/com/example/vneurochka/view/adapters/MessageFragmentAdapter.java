package com.example.vneurochka.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageFragmentAdapter extends RecyclerView.Adapter<MessageFragmentAdapter.MessageHolder> {

    private static final int MSG_TYPE_LEFT_RECEIVED = 0;
    private static final int MSG_TYPE_RIGHT_RECEIVED = 1;
    private ArrayList<Message> messageArrayList;
    private Context context;
    private String senderId;


    public MessageFragmentAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT_RECEIVED) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right_sent, parent, false);
            return new MessageHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left_received, parent, false);
            return new MessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = messageArrayList.get(position);
        long timeStamp = message.getTimestamp();
        String messageReceived = timeStampConversionToTime(timeStamp);
        String messageText = message.getMessage();
        Boolean seen = message.getSeen();
        holder.tv_time.setText(messageReceived);
        holder.tv_msg.setText(messageText);



//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        if (position == messageArrayList.size() - 1) {
//            if (seen > 1) {
//                holder.tv_seen.setVisibility(View.VISIBLE);
//                holder.tv_seen.setText("Seen");
//            } else {
//                holder.tv_seen.setVisibility(View.VISIBLE);
//                holder.tv_seen.setText("Delivered");
//            }
//        } else {
//            holder.tv_seen.setVisibility(View.GONE);
//        }
    }



    public String timeStampConversionToTime(long timeStamp) {
        Date date = new Date(timeStamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat jdf = new SimpleDateFormat("hh:mm a");
        jdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return jdf.format(date);
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView tv_time;
        TextView tv_seen;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_chat_received);
            tv_time = itemView.findViewById(R.id.tv_chat_time_received);
            tv_seen = itemView.findViewById(R.id.tv_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageArrayList.get(position).getSenderId().equals(this.senderId)) {
            return MSG_TYPE_LEFT_RECEIVED;
        } else return MSG_TYPE_RIGHT_RECEIVED;
    }

    public void setItems(List<Message> messageList) {
        this.messageArrayList = (ArrayList<Message>) messageList;
    }
}
