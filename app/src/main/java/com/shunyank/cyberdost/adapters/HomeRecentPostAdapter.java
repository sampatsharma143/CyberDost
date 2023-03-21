package com.shunyank.cyberdost.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shunyank.cyberdost.R;
import com.shunyank.cyberdost.models.PostModel;
import com.shunyank.cyberdost.utils.AppUtils;

import java.util.ArrayList;

public class HomeRecentPostAdapter extends RecyclerView.Adapter<HomeRecentPostAdapter.Viewholder> {
    ArrayList<PostModel> data;
    Context context;

    public interface RecentPostCallback{
        void onLikeClicked(String id, Long count, int pos);
        void onVictimClicked(String id,Long count);
        void onCommentClicked(String id);
    }
    public HomeRecentPostAdapter(Context context, ArrayList<PostModel> data){
        this.data = data;
        this.context = context;
    }
    RecentPostCallback recentPostCallback;

    public void setSelectCatCallback(RecentPostCallback recentPostCallback) {
        this.recentPostCallback = recentPostCallback;
    }

    @NonNull
    @Override
    public HomeRecentPostAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecentPostAdapter.Viewholder holder, int position) {
            int pos = position;
            PostModel post = data.get(pos);
            holder.postContent.setText(post.getPost_content());
            Glide.with(context).load(post.getProfile_url()).into(holder.userPic);
            holder.userHandle.setText("@"+post.getUser_handle());
            holder.usersName.setText(post.getUsers_name());
            holder.date.setText(AppUtils.Companion.getAgoFormat(post.getCreate_at()));
            if(post.getComments_count()==0){
                holder.commentCount.setText("Add comments");
                holder.latest_comment_pic_cardview.setVisibility(View.GONE);
                holder.commentUserName.setVisibility(View.GONE);
                holder.line_view.setVisibility(View.GONE);
            }else {
                holder.commentCount.setText("Read "+post.getComments_count()+" comments");

                holder.commentUserPic.setVisibility(View.VISIBLE);
                holder.commentUserName.setVisibility(View.VISIBLE);
                holder.line_view.setVisibility(View.VISIBLE);
            }
            holder.likeCount.setText(post.getLikes_count()+" Likes");
            holder.victimsCount.setText(post.getVictims_count()+" Victims");
            holder.likeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("like","clicked adapter");

                    recentPostCallback.onLikeClicked(post.getId(), post.getLikes_count(),pos);
                }
            });
        holder.commentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentPostCallback.onCommentClicked(post.getId());
            }
        });
        holder.victimsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recentPostCallback.onVictimClicked(post.getId(),post.getVictims_count());
            }
        });
        if(post.isAlreadyLiked()){
            holder.likeImage.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.warning_red)));
        }else {
            holder.likeImage.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.grey)));

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView usersName,userHandle,postContent,likeCount,commentCount,victimsCount,date;

        // Comment views
        ImageView commentUserPic,likeImage;
        CardView latest_comment_pic_cardview;
        TextView commentUserName;
        View line_view;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userPic = itemView.findViewById(R.id.profile_pic);
            usersName = itemView.findViewById(R.id.name);
            userHandle = itemView.findViewById(R.id.username);
            postContent = itemView.findViewById(R.id.post_content);
            likeCount = itemView.findViewById(R.id.like_count);
            victimsCount = itemView.findViewById(R.id.victims_count);
            commentCount = itemView.findViewById(R.id.comments_count_text);

            line_view = itemView.findViewById(R.id.line_view);
            commentUserPic = itemView.findViewById(R.id.latest_comment_pic);
            likeImage = itemView.findViewById(R.id.like_image);
            latest_comment_pic_cardview = itemView.findViewById(R.id.latest_comment_pic_cardview);
            commentUserName = itemView.findViewById(R.id.name_comment);
            date = itemView.findViewById(R.id.date);

        }
    }
}
