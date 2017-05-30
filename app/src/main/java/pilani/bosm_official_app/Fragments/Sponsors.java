package pilani.bosm_official_app.Fragments;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import javax.microedition.khronos.opengles.GL;

import pilani.bosm_official_app.Cube.MyGLRenderer;
import pilani.bosm_official_app.R;

import static pilani.bosm_official_app.R.id.imageView;

/**
 * Created by Prashant on 14-05-2017.
 */

public class Sponsors extends Fragment {

    private GLSurfaceView glView;
    private MediaPlayer mp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        glView = new GLSurfaceView(this.getContext());           // Allocate a GLSurfaceView
        glView.setRenderer(new MyGLRenderer(this.getContext())); // Use a custom renderer
       // this.getContext().setContentView(glView);                // This activity sets to GLSurfaceView

        //TODO://add music track and uncomment this!
     /*   glView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(getContext(), R.raw.sugar);
                mp.start();
            }
        });
*/

        //TODO://add glview move listener!

        return glView;

    }



    // Call back when the activity is going into the background
  /*  @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }*/
}
