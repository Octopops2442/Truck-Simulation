package demo19088;

import java.util.ArrayList;
import base.Highway;
import base.Truck;

class HighwayDemo extends Highway {

	public HighwayDemo(){
		this.traffic = new ArrayList<Truck>();
	}

	@Override
	public boolean hasCapacity() {
		if(this.traffic.size() < this.getCapacity()){
			return true;
		}
		return false;
	}

	@Override
	public boolean add(final Truck truck) {
		if (this.hasCapacity()) {
			this.traffic.add(truck);

			return true;
		}
		return false;
	}

	@Override
	public void remove(final Truck truck) {
		this.traffic.remove(truck);
	}

	private final ArrayList<Truck> traffic;
}
