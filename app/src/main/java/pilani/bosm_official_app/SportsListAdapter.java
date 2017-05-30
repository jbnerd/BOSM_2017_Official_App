package pilani.bosm_official_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class SportsListAdapter extends ArrayAdapter<Sport> {

    Activity activity;

    public SportsListAdapter(Context context, int resource, List<Sport> objects, Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_sports, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sport item = getItem(position);

        holder.tvName.setText(item.getName());
        holder.ivLogo.setImageResource(item.getImage());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClicked(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tvName;
        private ImageView ivLogo;
    }

    private void listItemClicked (int position) {
        Intent in = new Intent(getContext(),SettingsActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        in.putExtra("bla",getItem(position).getName());
        activity.startActivity(in);

       Toast.makeText(getContext(), getItem(position).getName(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"Link is not ready yet!", Toast.LENGTH_SHORT).show();

    }
}