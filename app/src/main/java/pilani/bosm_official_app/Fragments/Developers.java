package pilani.bosm_official_app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pilani.bosm_official_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Developers extends Fragment {


    public Developers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

}
