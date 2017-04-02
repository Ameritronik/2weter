package com.codepath.apps.tweeter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.tweeter.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ComposeTweetFragment extends DialogFragment {
    @Bind(R.id.etTweetTo)     EditText etTweetTo;
    @Bind(R.id.etTweet) EditText etTweet;
    private String MYTWEETID = "@pjaytumkur";
    private int mTweetWordCountMax = 140;
    private int mTweetWordCountMin = 1;
    SendTweetListener composeDone;

    // Defines the listener interface with a method passing back data result.
    public interface SendTweetListener {
        public void onFinishEditDialog(String twBody, String twURL);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            composeDone = (SendTweetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ToolbarListener");
        }
    }

    public ComposeTweetFragment() {
        //Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        ButterKnife.bind(this, rootView);
        getDialog().setTitle("Compose Tweet");
        TextInputLayout etTweetWC = (TextInputLayout) rootView.findViewById(R.id.etTweetWC);
        etTweetWC.getEditText().addTextChangedListener(new CharacterCountErrorWatcher(etTweetWC, mTweetWordCountMin, mTweetWordCountMax));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnTweet = (Button) view.findViewById(R.id.btnTweet);
        Button btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Cancel????", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        btnTweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tweetTo = etTweetTo.getText().toString();
                String tweetText = etTweet.getText().toString();
                if(tweetTo == null) {
                    tweetTo = MYTWEETID;
                }
                Log.d("DEBUG"," Tweet2 "+ tweetTo+" TwTxt "+tweetText);
                if (tweetText != null ) {
                    composeDone.onFinishEditDialog(tweetText, tweetTo);
                } else  {
                    //TimelineActivity.showToast(getContext(),"Blank Tweet text -- not posted");
                }
                dismiss();
            }
        });
     }
}