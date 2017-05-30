package pilani.bosm_official_app.leaderboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import pilani.bosm_official_app.R;
import pilani.bosm_official_app.auth.User;

/**
 * Created by Saksham on 12 Sep 2016.
 */
public class LeaderboardListAdapter extends ArrayAdapter<User> {

    Activity activity;
    int rank;

    public LeaderboardListAdapter(Context context, int resource, List<User> objects, int rank, Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
        this.rank = rank;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_leaderboard, null);
            holder = new ViewHolder();
            holder.tvRank = (TextView) convertView.findViewById(R.id.tvRank);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
            holder.container = (RelativeLayout) convertView.findViewById(R.id.container);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final User user = getItem(position);

        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvScore.setText(String.valueOf(user.getScore()));
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        if (position == (rank-1)) {
            holder.container.setBackgroundColor(ContextCompat.getColor(activity, R.color.userBack));
        } else {
            holder.container.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog(user.getScore(), user.getBettingAmount());
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tvRank;
        private TextView tvScore;
        private TextView tvName;
        private TextView tvEmail;
        private RelativeLayout container;
    }

//    private void listItemClicked (int position) {
//    }

    private void showWalletDialog (int score, int bettingAmount) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_wallet);

        final TextView tvAmount = (TextView) dialog.findViewById(R.id.tvAmount);
        final TextView tvBet = (TextView) dialog.findViewById(R.id.tvBet);

        tvAmount.setText("$"+score);
        tvBet.setText("$"+bettingAmount);

        dialog.show();
    }
}
