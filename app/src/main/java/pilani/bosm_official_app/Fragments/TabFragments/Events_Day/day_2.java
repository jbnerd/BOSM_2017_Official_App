package pilani.bosm_official_app.Fragments.TabFragments.Events_Day;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pilani.bosm_official_app.Events_Excel.JSONParser_day2;
import pilani.bosm_official_app.Events_Excel.MyArrayAdapter;
import pilani.bosm_official_app.Events_Excel.MyDataModel;
import pilani.bosm_official_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class day_2 extends Fragment {


    public day_2() {
        // Required empty public constructor
    }



    private GridView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day_2,container,false);


        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapter(getContext(),list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (GridView) rootView.findViewById(R.id.day2);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        listView.setAdapter(adapter);

        new GetDataTask().execute();

        return rootView;
    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {

        //  ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            spinner.setVisibility(View.VISIBLE);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser_day2.getDataFromWeb();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                MyDataModel model = new MyDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String time = innerObject.getString(Keys.KEY_TIME);
                                String location = innerObject.getString(Keys.KEY_LOCATION);
                                String date = innerObject.getString(Keys.KEY_DATE);
                                String desc = innerObject.getString(Keys.KEY_DESC);


                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setName(name);
                                model.setLocation(location);
                                model.setDate(date);
                                model.setTime(time);
                                model.setDesc(desc);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "error",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException je) {
                Log.i(JSONParser_day2.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            spinner.setVisibility(View.GONE);
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "No Data Found",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Keys {

        public static final String KEY_CONTACTS = "day2";
        public static final String KEY_NAME = "Name_of_Event";
        public static final String KEY_DATE = "Date";
        public static final String KEY_TIME = "Time";
        public static final String KEY_LOCATION = "Place";
        public static final String KEY_DESC = "Description";

    }

}
