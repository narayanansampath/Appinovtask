package com.scsvmv.ryan.appinovtask.ui.videomodule;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Rational;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.scsvmv.ryan.appinovtask.R;

public class VideoFragment extends Fragment {
    private FragmentActivity myContext;

    private YouTubePlayer YPlayer;
    //FloatingActionButton fab;
    private FrameLayout mFramePlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyCJ-8OibFGOGnTAYzI3ktqTm0_WJ9119jg";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        findViews(rootView);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    //Trigger PiP mode
                    try {
                        Rational rational = new Rational(mFramePlayer.getWidth(), mFramePlayer.getHeight());

                        PictureInPictureParams mParams =
                                new PictureInPictureParams.Builder()
                                        .setAspectRatio(rational)
                                        .build();

                        getActivity().enterPictureInPictureMode(mParams);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "API 26 needed to perform PiP", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("DEVELOPER_KEY", new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    YPlayer.loadVideo("KYxY8dMnFDM");
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }

        });
        return rootView;
    }
        /*@Override
        public void onPictureInPictureModeChanged( boolean isInPictureInPictureMode){
            super.onPictureInPictureModeChanged(isInPictureInPictureMode);

            if (!isInPictureInPictureMode) {
                // Restore your (player) UI
                fab.setVisibility(View.VISIBLE);
//            getApplication().startActivity(new Intent(this, getClass())
//                    .addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT));
            } else {
                // Hide all UI controls except video
                fab.setVisibility(View.GONE);
            }

        }*/

    private void findViews(View container) {
        //this.fab = container.findViewById(R.id.fabpip);
        this.mFramePlayer = container.findViewById(R.id.youtube_fragment);
    }
}
