package comp;

/**
 * Created by Foxel on 13.08.2014.
 */
public abstract class Component {
    private int id;

    public Component (int id) {
        this.id = id;
    }

    public int getId() {
        return  id;
    }
}
