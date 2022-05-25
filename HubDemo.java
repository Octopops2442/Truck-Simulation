package demo19088;

import java.util.*;
import base.Highway;
import base.Hub;
import base.Location;
import base.Truck;

class HubDemo extends Hub {

	private final ArrayList<Truck> trucks;

	public HubDemo(final Location loc) {
		super(loc);
		this.trucks = new ArrayList<Truck>();

	}

	public boolean add(final Truck truck) {
		if (this.trucks.size() >= this.getCapacity())
			return false;
		this.trucks.add(truck);
		return true;
	}

	public void remove(final Truck truck) {
		this.trucks.remove(truck);
	}

	public synchronized boolean search(final Highway hwy, final Hub dest) {
		if (hwy == null) {
			return false;
		}

		if (hwy.getEnd() == dest) {
			return true;
		}

		final Hub end = hwy.getEnd();

		for (final Highway hw : end.getHighways()) {
			final boolean value = search(hw, dest);

			if (value) {
				return true;
			}
		}
		return false;
	}

	public synchronized Highway getNextHighway(final Hub from, final Hub dest) {
		Highway nexthigh = null;

		for (final Highway hwy : this.getHighways()) {
			final boolean val = search(hwy, dest);

			if (val) {
				nexthigh = hwy;
				break;
			}
		}
		return nexthigh;
	}

	protected synchronized void processQ(final int deltaT) {
		for (final Truck t : trucks) {
			final Hub destination = NetworkDemo.getNearestHub(t.getDest());

			if (NetworkDemo.getNearestHub(t.getDest()).getLoc().getX() == this.getLoc().getX()
					&& NetworkDemo.getNearestHub(t.getDest()).getLoc().getY() == this.getLoc().getY()) {
				t.setLoc(t.getDest());
				this.remove(t);
			} else{
				final Highway next = getNextHighway(this, destination);

				if(next == null){
					t.setLoc(t.getDest());
					this.remove(t);
					continue;
				}
				
				if (next.hasCapacity()) {
					next.add(t);
					this.remove(t);
					t.enter(next);
				}
			}
		}
	}
}
