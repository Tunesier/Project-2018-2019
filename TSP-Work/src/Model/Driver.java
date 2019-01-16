package Model;

import java.util.ArrayList;

import Graph.Client;
import Graph.Edge;

/**
 *
 * @author Muaaz
 */
public class Driver {

	private final int ID;
	private int skill;
	//private int hiringDay;
	// private int skill;
	public ArrayList<Client> clientsToServeToday = new ArrayList<>();// clients this driver should serve
	public ArrayList<Edge> edgesICanUseToday = new ArrayList<>();
	public int MinOverAllWeight = Integer.MAX_VALUE;
	public int timeNeededForTour = 0;

	// private boolean newInWork; //not for now! Skills ??????????

	public Driver(int ID) {
		this.ID = ID;
		// this.familiarity = new boolean[length];
		//this.hiringDay = 1;// standard value
		this.skill = 0;
		// this.skill = 0;
		// this.newInWork = newInWork;
	}

	// public int getSkill() {
	// return skill;
	// }
	//
	// public void setSkill(int skill) {
	// this.skill = skill;
	// }
	//
	

	public int getId() {
		return ID;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill += skill;
	}

	/*public int getHiringDay() {
		return hiringDay;
	}

	public void setHiringDay(int hiringDay) {
		if (hiringDay <= 0) {// hiring Day must be from the intervall [1, infinity]
			this.hiringDay = 1;
		}
		this.hiringDay = hiringDay;
	}*/

	// public boolean isNewInWork() {
	// return newInWork;
	// }

}