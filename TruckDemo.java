package demo19088;

import base.Truck;
import base.Highway;
import base.Location;
import base.Hub;

class TruckDemo extends Truck {

	TruckDemo(){
		super();
		this.totalTime = 0;
		this.currentHighway = null;
		this.distTravelled = 0;
		this.lastHub = null;
		this.justStarted = true;
		this.hasReached = false;
		this.waitingAtHub = false;
		this.truckNum = ++truckIndex;
	}	

	protected void update(final int deltaT) {
		this.totalTime += deltaT;
		if (this.inRange(this.getLoc().getX(), getDest().getX())
				&& this.inRange(this.getLoc().getY(), getDest().getY()))
			this.hasReached = true;
		if (this.hasReached) {
			return;
		}
		if (this.totalTime < this.getStartTime()) {
			return;
		}
		if (this.waitingAtHub)
			return;

		if (this.justStarted) {
			final Hub start = NetworkDemo.getNearestHub(this.getLoc());

			start.add(this);
			this.setLoc(start.getLoc());
			this.justStarted = false;
		}

		if (this.distTravelled >= this.getHwyLength()) {
			if (this.currentHighway.getEnd().add(this)) {
				this.currentHighway.remove(this);
				this.distTravelled = 0;
				this.setLoc(this.currentHighway.getEnd().getLoc());
				this.waitingAtHub = true;
			}
		} else {
			if (this.currentHighway == null)
				return;

			final double deltaX = this.currentHighway.getEnd().getLoc().getX()
					- this.currentHighway.getStart().getLoc().getX();
			final double deltaY = this.currentHighway.getEnd().getLoc().getY()
					- this.currentHighway.getStart().getLoc().getY();
			final double distance = (double) (this.currentHighway.getMaxSpeed() * (Double.valueOf(deltaT) / 500));
			final int D_x = (int) Math.round(this.getLoc().getX() + distance * Math.cos(Math.atan2(deltaY, deltaX)));
			final int D_y = (int) Math.round(this.getLoc().getY() + distance * Math.sin(Math.atan2(deltaY, deltaX)));
			final Location upd = new Location(D_x, D_y);

			this.setLoc(upd);
			this.distTravelled += this.currentHighway.getMaxSpeed() * (Double.valueOf(deltaT) / 500);
		}
	}

	public String getTruckName() {
		return "Truck19088_" + Integer.toString(this.truckNum);
	}

	public Hub getLastHub() {
		return this.lastHub;
	}

	public void enter(final Highway hwy) {
		this.currentHighway = hwy;
		this.lastHub = this.currentHighway.getStart();
		this.waitingAtHub = false;
	}

	private int getHwyLength() {
		if (this.currentHighway == null) {
			return Integer.MAX_VALUE;
		}
		return (int) Math.round(
				Math.sqrt(this.currentHighway.getStart().getLoc().distSqrd(this.currentHighway.getEnd().getLoc())));
	}

	private boolean inRange(final int val1, final int val2) {
		return Math.abs(val1 - val2) <= 2;
	}

	private int totalTime;
	private static int truckIndex = 0;
	private final int truckNum;
	private Highway currentHighway;
	private int distTravelled;
	private Hub lastHub;
	private boolean justStarted;
	private boolean hasReached;
	private boolean waitingAtHub;
}
