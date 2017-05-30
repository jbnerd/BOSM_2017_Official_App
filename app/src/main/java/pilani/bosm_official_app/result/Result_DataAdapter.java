package pilani.bosm_official_app.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import pilani.bosm_official_app.R;

/**
 * Created by lenovo on 05-09-2016.
 */
public class Result_DataAdapter extends RecyclerView.Adapter<Result_DataAdapter.ViewHolder> {

    private ArrayList<Integer> countries;
    private ArrayList<String> time;

    public Result_DataAdapter(ArrayList<Integer> countries, ArrayList<String> time) {
        this.countries = countries;
        this.time = time;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.results_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_Event.setImageResource(countries.get(i));
        viewHolder.tv_time.setText(time.get(i));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView tv_Event;
        private TextView tv_time;
        public ViewHolder(View view) {
            super(view);

            tv_time = (TextView)view.findViewById(R.id.textView3);
            tv_Event = (ImageView)view.findViewById(R.id.imageView3);
        }
    }

}