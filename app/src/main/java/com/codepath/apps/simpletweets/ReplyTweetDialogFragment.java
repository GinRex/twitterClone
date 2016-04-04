package com.codepath.apps.simpletweets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ginrex on 02/04/2016.
 */
public class ReplyTweetDialogFragment extends DialogFragment {
    Tweet tweet;
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

    private String title;
    public ReplyTweetDialogFragment() {

    }

    public static ReplyTweetDialogFragment newInstance(String title) {
        ReplyTweetDialogFragment frag = new ReplyTweetDialogFragment();
        frag.title = title;
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));



        Dialog  dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, v);
        getData();
        return v;
    }

    private void getData(){
        tweet = getArguments().getParcelable("tweet");
        Log.d("debug", tweet.getBody());
        Log.d("debug", tweet.getUser().getName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(ivUserProfilePic);
        tvDateago.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
        Picasso.with(getContext()).load(tweet.getPicURL()).into(ivEpic);
        //tvLike.setText(tweet.getLikeC());
        //tvShare.setText(tweet.getShareC());
    }
}
