package com.codepath.apps.simpletweets.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.ReplyTweetDialogFragment;
import com.codepath.apps.simpletweets.activity.ProfileActivity;
import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public void setTweets(List<Tweet> list){
        this.tweets = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.tvName) TextView tvName;
        @Bind(R.id.tvBody) TextView tvBody;
        @Bind(R.id.tvTime) TextView tvDateago;
        @Bind(R.id.tvLike) TextView tvLike;
        @Bind(R.id.tvShare) TextView tvShare;
        @Bind(R.id.tvRep) TextView tvRep;
        @Bind(R.id.ivProfilePic) ImageView ivUserProfilePic;
        @Bind(R.id.ivePic) ImageView ivEpic;
        @Bind(R.id.ibLike) ImageButton ibLike;
        @Bind(R.id.ibShare) ImageButton ibShare;
        @Bind(R.id.ibRep) ImageButton ibRep;

        public ViewHolder(View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            ibLike.setOnClickListener(this);
            tvBody.setOnClickListener(this);
            ivUserProfilePic.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case (R.id.ibLike):{
                    ibLike.setImageResource(R.drawable.loved);
                    break;
                }
                case (R.id.ivProfilePic):{
                    Intent i = new Intent(v.getContext(), ProfileActivity.class);
                    String screen_name = tweets.get(getAdapterPosition()).getUser().getScreenName();
                    i.putExtra("screen_name", screen_name);
                    v.getContext().startActivity(i);
                    break;
                }
                case (R.id.tvBody):{
                    FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                    ReplyTweetDialogFragment replyTweetDialogFragment = ReplyTweetDialogFragment.newInstance("wtv");
                    replyTweetDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.You_Dialog );
                    Bundle args = new Bundle();
                    Tweet tweet = tweets.get(getAdapterPosition());

                    args.putParcelable("tweet", tweet);
                    replyTweetDialogFragment.setArguments(args);
                    replyTweetDialogFragment.show(fm, "fragment_detail");
                    break;
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(View v);
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

        holder.tvName.setText(tweet.getUser().getName());
        holder.tvBody.setText(tweet.getBody());
        holder.tvDateago.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));

        holder.tvLike.setText(String.valueOf(tweet.getLikeC()));
        holder.tvShare.setText(String.valueOf(tweet.getShareC()));

        holder.ivUserProfilePic.setImageResource(0);
        holder.ivEpic.setImageResource(0);


        Picasso.with(context).load(tweet.getUser().getProfileImageURL()).into(holder.ivUserProfilePic);
        Picasso.with(context).load(tweet.getPicURL()).into(holder.ivEpic);

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
