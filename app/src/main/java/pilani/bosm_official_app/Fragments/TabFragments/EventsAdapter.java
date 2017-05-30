package pilani.bosm_official_app.Fragments.TabFragments;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import pilani.bosm_official_app.Config;
import pilani.bosm_official_app.R;
import pilani.bosm_official_app.Sport;

/**
 * Created by Saksham on 28 Aug 2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolders> {

    private List<Event> events;
    private Activity activity;

    public EventsAdapter(Activity activity, List<Event> events) {
        this.events = events;
        this.activity = activity;
    }

    @Override
    public EventsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_event_item, parent, false);
        EventsViewHolders holders = new EventsViewHolders(layoutView, activity);
        return holders;
    }

    @Override
    public void onBindViewHolder(EventsViewHolders holder, int position) {
        Event event = events.get(position);
        Config config = new Config();
        event.getSport();
        Sport sport = config.items.get(event.getSport());
        holder.tvName.setText(sport.getName());
        if (event.getDescription().isEmpty()){
            holder.tvDescription.setVisibility(View.GONE);
        } else {
            holder.tvDescription.setText(event.getDescription());
        }
        Calendar calendar = event.getDateTime();
        SimpleDateFormat f = new SimpleDateFormat("EEE dd MMM, hh:mm a");
        holder.tvDateTime.setText(f.format(calendar.getTime()));

        holder.ivLogo.setImageResource(sport.getImage());

        if (event.getType() == Event.TYPE_A_VS_B) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.teams.setVisibility(View.VISIBLE);
            holder.ivStar.setVisibility(View.VISIBLE);
            String status = Config.getStatusName(event.getStatus());
            holder.tvStatus.setText(status);
            holder.tvTeamA.setText(event.getTeam_a());
            holder.tvTeamB.setText(event.getTeam_b());

            if (event.isBetting_done()) {
                holder.ivStar.setImageResource(R.drawable.star_filled_100);
                holder.ivStar.setColorFilter(ContextCompat.getColor(activity, R.color.fav_green));
            } else {
                if (event.getStatus() >= 0) {
                    holder.ivStar.setImageResource(R.drawable.star_filled_100);
                    holder.ivStar.setColorFilter(ContextCompat.getColor(activity, R.color.grey));
                } else {
                    holder.ivStar.setImageResource(R.drawable.star_100);
                    holder.ivStar.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                }
            }
            if (event.getStatus() == -1) {
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
            } else if (event.getStatus() == 0){
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.fav_green));
            } else {
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.errorColor));
            }
        } else {
            holder.tvStatus.setVisibility(View.GONE);
            holder.teams.setVisibility(View.GONE);
            holder.ivStar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}