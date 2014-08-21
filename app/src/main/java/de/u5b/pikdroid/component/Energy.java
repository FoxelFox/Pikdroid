package de.u5b.pikdroid.component;

/**
 * Energy is something like electricity.
 * Created by Foxel on 21.08.2014.
 */
public class Energy extends Component{
    private int load;
    private int capacity;

    /**
     * Create a new Energy Component
     * @param capacity max capacity
     * @param load initialized charge load
     */
    public Energy(int capacity, int load) {
        if(capacity < 0) capacity = 0;
        if(capacity < load) load = capacity;
        this.load = load;
        this.capacity = capacity;
    }

    /**
     * Charge with @charge amount. It returns the charge overflow.
     * @param charge charge amount
     * @return overflow
     */
    public int charge(int charge) {
        int overflow = 0;

        load += charge;
        if(load > capacity) {
            overflow = capacity - load;
            load = capacity;
        }
        return overflow;
    }

    /**
     * Fully discharge the Component
     * @return discharged Energy
     */
    public int discharge() {
        int tmp = load;
        load = 0;
        return load;
    }

    /**
     * Transfer Energy from A to B
     * @param from A
     * @param to B
     */
    public void transfer(Energy from, Energy to) {
        // yeah the law of conservation of energy :D
        from.charge(to.charge(from.discharge()));
    }

    public int getLoad() {
        return load;
    }

    public int getCapacity() {
        return capacity;
    }
}
