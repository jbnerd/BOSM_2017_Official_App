package pilani.bosm_official_app.Fragments.TabFragments;

import java.util.Calendar;

/**
 * Created by Saksham on 28 Aug 2016.
 */
public class Event {

    public static final int TYPE_A_VS_B = 0;
    public static final int TYPE_TRACK = 1;

    private int id;
    private int sport;
    private String description;
    private String venue;
    private Calendar dateTime;
    private int status;
    private String team_a;
    private String team_b;
    private String winner;
    private int type;
    private boolean betting_done;

    public Event(int id, int sport, String description, String venue, Calendar dateTime, int status, String team_a, String team_b, String winner, boolean betting_done) {
        this.id = id;
        this.sport = sport;
        this.description = description;
        this.venue = venue;
        this.dateTime = dateTime;
        this.status = status;
        this.team_a = team_a;
        this.team_b = team_b;
        this.winner = winner;
        this.type = TYPE_A_VS_B;
        this.betting_done = betting_done;
    }

    public Event(int id, int sport, String description, String venue, Calendar dateTime, int status, String team_a, String team_b, String winner) {
        this.id = id;
        this.sport = sport;
        this.description = description;
        this.venue = venue;
        this.dateTime = dateTime;
        this.status = status;
        this.team_a = team_a;
        this.team_b = team_b;
        this.winner = winner;
        this.type = TYPE_A_VS_B;
    }

    public Event(int id, int sport, String description, String venue, Calendar dateTime, int status) {
        this.id = id;
        this.sport = sport;
        this.description = description;
        this.venue = venue;
        this.dateTime = dateTime;
        this.status = status;
        this.type = TYPE_TRACK;
    }

    public int getId() {
        return id;
    }

    public int getSport() {
        return sport;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public int getStatus() {
        return status;
    }

    public String getTeam_a() {
        return team_a;
    }

    public String getTeam_b() {
        return team_b;
    }

    public String getWinner() {
        return winner;
    }

    public int getType() {
        return type;
    }

    public boolean isBetting_done() {
        return betting_done;
    }
}
