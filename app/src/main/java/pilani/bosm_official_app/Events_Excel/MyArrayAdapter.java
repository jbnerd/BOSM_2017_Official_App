package pilani.bosm_official_app.Events_Excel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import pilani.bosm_official_app.R;

/**
 * Created by Prashant on 16-05-2017.
 */

public class MyArrayAdapter extends ArrayAdapter<MyDataModel> {

    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.e_list_layout, parent, false);
            vh = ViewHolder.create(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.event_location.setText(item.getLocation());
        vh.event_name.setText(item.getName());
        vh.event_date.setText(item.getDate());
        vh.event_time.setText(item.getTime());
        vh.event_desc.setText(item.getDesc());

        return vh.rootView;
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2016-01-05 00:50:26 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final View rootView;

        public final TextView event_name;
        public final TextView event_location;
        public final TextView event_date;
        public final TextView event_time;
        public final TextView event_desc;


        private ViewHolder(View rootView,TextView event_name, TextView event_location, TextView event_date,  TextView event_time,TextView event_desc) {
            this.rootView = rootView;
            this.event_name = event_name;
            this.event_location = event_location;
            this.event_date = event_date;
            this.event_time = event_time;
            this.event_desc = event_desc;
        }

        public static ViewHolder create(View rootView) {
            TextView event_name = (TextView) rootView.findViewById(R.id.name);
            TextView event_location = (TextView) rootView.findViewById(R.id.location);
            TextView event_date = (TextView) rootView.findViewById(R.id.date);
            TextView event_time = (TextView) rootView.findViewById(R.id.time);
            TextView event_desc = (TextView) rootView.findViewById(R.id.desc);


            return new ViewHolder(rootView, event_name, event_location,event_date,event_time,event_desc);
        }
    }
}