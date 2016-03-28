package com.codepath.apps.simpletweets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ginrex on 27/03/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<Tweet> tweets;
    private Context context;

    public RecyclerAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfilePic;
        ImageView epic;
        TextView tvName;
        TextView tvBody;
        TextView tvDateago;
        TextView tvLike;
        TextView tvShare;

        public ViewHolder(View itemView) {
            super(itemView);

            userProfilePic = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDateago = (TextView) itemView.findViewById(R.id.tvTime);
            epic = (ImageView) itemView.findViewById(R.id.ivePic);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
            tvLike = (TextView) itemView.findViewById(R.id.tvLike);

        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        TextView tvName = holder.tvName;
        TextView tvBody = holder.tvBody;
        ImageView userPPic = holder.userProfilePic;
        ImageView ePic = holder.epic;
        TextView tvTime = holder.tvDateago;
        TextView like = holder.tvLike;
        TextView share = holder.tvShare;

        tvTime.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        like.setText(String.valueOf(tweet.getLikeC()));
        share.setText(String.valueOf(tweet.getShareC()));

        userPPic.setImageResource(0);
        ePic.setImageResource(0);


        Picasso.with(context).load(tweet.getUser().getProfileImageURL()).into(userPPic);
        Picasso.with(context).load(tweet.getPicURL()).into(ePic);
    }


    @Override
    public int getItemCount() {
        return tweets.size();
    }

    /* Within the RecyclerView.Adapter class */



// Clean all elements of the recycler

    public void clear() {

        tweets.clear();

        notifyDataSetChanged();

    }
}
