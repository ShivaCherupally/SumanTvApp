package info.sumantv.flow.utils;

import android.view.View;

import info.sumantv.flow.ipoll.fragments.videoplayer.VideoPlayerFragment;

/**
 * Created by Uday Kumay Vurukonda on 27-Nov-19.
 * <p>
 * Mr.Psycho
 */
public class DoubleTapListener implements View.OnClickListener{

    private boolean isRunning= false;
    private int resetInTime =500;
    private int counter=0;

    private DoubleTapCallback listener;

    public DoubleTapListener(VideoPlayerFragment context)
    {
        listener = (DoubleTapCallback)context;
    }

    @Override
    public void onClick(View v) {

        if(isRunning)
        {
            if(counter==1) //<-- makes sure that the callback is triggered on double click
                listener.onDoubleClick(v);
        }

        counter++;

        if(!isRunning)
        {
            isRunning=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter=0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
