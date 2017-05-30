package pilani.bosm_official_app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pilani.bosm_official_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment implements OnMapReadyCallback {

    SupportMapFragment sMapFragment;

    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_map, container, false);
        sMapFragment = SupportMapFragment.newInstance();

        sMapFragment.getMapAsync(this);
        android.support.v4.app.FragmentManager sFm = getActivity().getSupportFragmentManager();

        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (!sMapFragment.isAdded()) {
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        } else {
            sFm.beginTransaction().show(sMapFragment).commit();
        }

        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
        LatLng clocktower = new LatLng(28.363821, 75.587029);
        googleMap.addMarker(new MarkerOptions().position(clocktower).title("Clock Tower"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clocktower,17));
        //googleMap.setMyLocationEnabled(true);

        LatLng Library = new LatLng(28.363821, 75.587029);
        googleMap.addMarker(new MarkerOptions().position(Library).title("Library"));

        LatLng FD2 = new LatLng(28.363854, 75.585854);
        googleMap.addMarker(new MarkerOptions().position(FD2).title("FD2"));

        LatLng FD3 = new LatLng(28.364074, 75.588062);
        googleMap.addMarker(new MarkerOptions().position(FD3).title("FD3"));

        LatLng SAC = new LatLng(28.360647, 75.585401);
        googleMap.addMarker(new MarkerOptions().position(SAC).title("SAC"));

        LatLng Gym_G = new LatLng(28.359127, 75.590132);
        googleMap.addMarker(new MarkerOptions().position(Gym_G).title("Gym_G"));

        LatLng Birla_mandir = new LatLng(28.357635, 75.588115);
        googleMap.addMarker(new MarkerOptions().position(Birla_mandir).title("Birla_mandir"));

    }
}
