package demo19088;

import base.*;
import java.util.ArrayList;

public class NetworkDemo extends Network{

    public NetworkDemo(){
        super();
        this.hubs = new ArrayList<Hub>();
        this.veichles = new ArrayList<Truck>();
        this.hwy = new ArrayList<Highway>();
    }
    
    @Override
    public void add(final Hub h) {
        this.hubs.add(h);
    }

    @Override
    public void add(final Truck truck) {
        this.veichles.add(truck);
    }

    @Override
    public void add(final Highway hy) {
        this.hwy.add(hy);
    }

    @Override
    public void start() {
        for (final Hub t : hubs) {
            t.start();
        }

        for (final Truck t : veichles) {
            t.start();
        }
    }

    @Override
    public void redisplay(final Display disp) {
        for (final Hub t : hubs) {
            t.draw(disp);
        }

        for (final Highway t : hwy) {
            t.draw(disp);
        }

        for (final Truck t : veichles) {
            t.draw(disp);
        }
    }

    @Override
    protected Hub findNearestHubForLoc(final Location loc) {
        int dist = Integer.MAX_VALUE;
        Hub near = null;
        int temp;

        for (final Hub t : hubs) {
            temp = loc.distSqrd(t.getLoc());

            if (temp < dist) {
                dist = temp;
                near = t;
            }
        }

        return near;
    }

    private final ArrayList<Hub> hubs;
    private final ArrayList<Truck> veichles;
    private final ArrayList<Highway> hwy;
}