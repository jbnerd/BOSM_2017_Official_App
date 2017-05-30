package pilani.bosm_official_app;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class Sport {
    int id;
    private String name;
    private int image;
    private boolean favorite;

    public Sport(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
        favorite = false;
    }

    public Sport(String name, int image){
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
