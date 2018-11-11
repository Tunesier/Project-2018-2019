package Model;

import java.util.ArrayList;

import Graph.Client;

public class Driver {

	private int id;
	private boolean familiarity[];
	private int hiringDay;
	private int skill;
	public ArrayList<Client> clients = new ArrayList<>();// clients this driver should serve

	// private boolean newInWork; //not for now! Skills ??????????

	public Driver(int id, int length) {
		this.id = id;
		this.familiarity = new boolean[length];
		this.hiringDay = 1;// standard value
		this.skill = 0;
		// this.newInWork = newInWork;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public boolean checkIfFamiliarWith(int familiarityId) {
		return familiarity[familiarityId - 1] == true;
	}

	public void setAsFamiliarWith(int familiarityId) {
		familiarity[familiarityId - 1] = true;
	}

	public int getId() {
		return id;
	}

	public int getHiringDay() {
		return hiringDay;
	}

	public void setHiringDay(int hiringDay) {
		if (hiringDay <= 0) {// hiring Day must be from the intervall [1, infinity]
			return;
		}
		this.hiringDay = hiringDay;
	}

	// public boolean isNewInWork() {
	// return newInWork;
	// }

}