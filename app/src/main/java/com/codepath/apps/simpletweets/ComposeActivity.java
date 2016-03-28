package com.codepath.apps.simpletweets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends DialogFragment {

    private TwitterClient client;
    private EditText etStatus;
    private Button btTweet;

    public ComposeActivity () {

    }

    public static ComposeActivity newInstance(String title) {
        ComposeActivity frag = new ComposeActivity();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    //the listener
    public interface ComposeActivityListener {
        void onFinishEditDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get field
        etStatus = (EditText) view.findViewById(R.id.etStatus);
        btTweet = (Button) view.findViewById(R.id.btTweet);
        client = TwitterApplication.getRestClient();

        //set click button
        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String status = etStatus.getText().toString();
                compose(status);
                ComposeActivityListener listener = (ComposeActivityListener) getActivity();
                listener.onFinishEditDialog();
                dismiss();
            }
        });

        String title = getArguments().getString("title", "What 's on ur mind?");
        getDialog().setTitle(title);
        //set softKeyboard
        //etStatus.requestFocus();
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_compose);
//
//        client = TwitterApplication.getRestClient();
//
//        etStatus = (EditText) findViewById(R.id.etStatus);
//        btTweet = (Button) findViewById(R.id.btTweet);
//
//        btTweet.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                final String status = etStatus.getText().toString();
//                compose(status);
//                finish();
//            }
//        });
//
//    }


    private void compose (final String status) {
        client.composeNewTweet(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", errorResponse.toString() + status);
            }
        });
    }

}
