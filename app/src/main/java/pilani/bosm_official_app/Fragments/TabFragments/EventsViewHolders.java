package pilani.bosm_official_app.Fragments.TabFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pilani.bosm_official_app.Navigation;
import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.LoginActivity;


/**
 * Created by Saksham on 28 Aug 2016.
 */
public class EventsViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvName;
    public TextView tvDescription;
    public TextView tvDateTime;
    public TextView tvStatus;
    public TextView tvTeamA;
    public TextView tvTeamB;
    public RelativeLayout teams;
    public ImageView ivLogo;
    public ImageView ivStar;
    private SharedPreferences sharedPreferences;

    private Activity activity;

    public EventsViewHolders(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;
        itemView.setOnClickListener(this);
        sharedPreferences = activity.getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        tvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        tvTeamA = (TextView) itemView.findViewById(R.id.tvTeamA);
        tvTeamB = (TextView) itemView.findViewById(R.id.tvTeamB);
        teams = (RelativeLayout) itemView.findViewById(R.id.teams);
        ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
        ivStar = (ImageView) itemView.findViewById(R.id.star);

    }

    @Override
    public void onClick(View view) {
        if (Now.events.get(getAdapterPosition()).getType() == Event.TYPE_A_VS_B) {
            if(sharedPreferences.getBoolean(LoginActivity.SP_LOGGED_IN,false)==false) {
                // Toast.makeText(getContext(),"yeahhhhh",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra("now", "now");
                intent.putExtra("sport", Integer.toString(Now.events.get(getAdapterPosition()).getSport()));
                intent.putExtra("id", Integer.toString(Now.events.get(getAdapterPosition()).getId()));
                activity.startActivity(intent);

            }else {
                Intent intent = new Intent(activity, Navigation.class);
                intent.putExtra("now", "now");
                intent.putExtra("sport", Integer.toString(Now.events.get(getAdapterPosition()).getSport()));
                intent.putExtra("id", Integer.toString(Now.events.get(getAdapterPosition()).getId()));
                activity.startActivity(intent);
            }
        } else {
//            Toast.makeText(activity, "This is not a team event", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "This is not a team event", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show();
        }
    }
}