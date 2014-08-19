package de.u5b.pikdroid.component;

/**
 * The Movement component contains parameter to move the Entity
 * Created by Foxel on 19.08.2014.
 */
public class Movement extends Component{
    private float linearSpeed;      // max linear speed
    private float angularSpeed;     // max angular speed

    public Movement(float linearSpeed, float angularSpeed) {
        this.linearSpeed = linearSpeed;
        this.angularSpeed = angularSpeed;
    }
}
