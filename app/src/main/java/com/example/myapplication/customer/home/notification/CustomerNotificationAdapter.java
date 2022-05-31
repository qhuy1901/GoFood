package com.example.myapplication.customer.home.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomerNotificationAdapter extends RecyclerView.Adapter<CustomerNotificationAdapter.CustomerNotificationViewHolder>{
    private final List<Notification> notificationList;
    private Context context;

    public CustomerNotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomerNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new CustomerNotificationAdapter.CustomerNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerNotificationViewHolder holder, int position) {
        Notification notification =  notificationList.get(position);
        if(notification == null)
            return ;
        holder.tvTitle.setText(notification.getTitle());
        holder.tvContent.setText(notification.getContent());
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        holder.tvDate.setText(dateFormat.format(notification.getNotificationTime()));

        holder.ivIcon.setImageResource(R.drawable.loa_noti);
        if(notification.getTitle().contains("Giao hàng thành công"))
            holder.ivIcon.setImageResource(R.drawable.order_done);
        if(notification.getTitle().contains("vận chuyển"))
            holder.ivIcon.setImageResource(R.drawable.shipper_noti);
//        if(notification.getTitle().contains("Đặt hàng thành công") || notification.getTitle().contains("trạng thái đơn hàng"))
//            holder.ivIcon.setImageResource(R.drawable.loa_noti);
    }

    @Override
    public int getItemCount() {
        if(notificationList != null)
            return notificationList.size();
        return 0;
    }

    public class CustomerNotificationViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvContent, tvDate;
        private ImageView ivIcon;

        public CustomerNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_notification_tv_title);
            tvContent = itemView.findViewById(R.id.item_notification_tv_content);
            tvDate= itemView.findViewById(R.id.item_notification_tv_date);
            ivIcon = itemView.findViewById(R.id.item_notification_iv_icon);
        }
    }
}
