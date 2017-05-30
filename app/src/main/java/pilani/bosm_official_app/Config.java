package pilani.bosm_official_app;


import java.util.HashMap;

/**
 * Created by Saksham on 28 Aug 2016.
 */
public class Config {

    public HashMap<Integer, Sport> items;

    public Config() {
        items = new HashMap<>();
        items.put(1, new Sport(1, "Athletics", R.drawable.athletics));
        items.put(2, new Sport(2, "Swimming", R.drawable.swimming));
        items.put(3, new Sport(3, "Taekwondo", R.drawable.taekwondo));
        items.put(4, new Sport(4, "Badminton", R.drawable.badminton));
        items.put(5, new Sport(5, "Basketball", R.drawable.basketball));
        items.put(6, new Sport(6, "Cricket", R.drawable.cricket));
        items.put(7, new Sport(7, "Football", R.drawable.football));
        items.put(8, new Sport(8, "Hockey", R.drawable.hockey));
        items.put(9, new Sport(9, "Squash", R.drawable.tennis));
        items.put(10, new Sport(10, "Tennis", R.drawable.tennis));
        items.put(11, new Sport(11, "Table Tennis", R.drawable.table_tennis));
        items.put(12, new Sport(12, "Volleyball", R.drawable.volleyball));
        items.put(13, new Sport(13, "Carom", R.drawable.carrom));
        items.put(14, new Sport(14, "Chess", R.drawable.chessrio));
        items.put(15, new Sport(15, "Weightlifting", R.drawable.weightlifting));
        items.put(16, new Sport(16, "Pool", R.drawable.pool));
        items.put(17, new Sport(17, "Online", R.drawable.online));
        items.put(18, new Sport(18, "Misc", R.drawable.misc));
    }

    public static String getStatusName(int status) {
        if (status == -1) {
            return "Yet to start";
        } else if (status == 0) {
            return "Running";
        } else {
            return "Finished";
        }
    }
}
