package org.kinelights.core.manager;

import org.kinelights.core.interfaces.BeatDataInterface;
import org.kinelights.core.interfaces.BeatManagerInterface;


public class BeatManager  implements BeatManagerInterface {
	
	private Routine routine;

	public BeatManager(Routine routine) {
		this.routine=routine;

		
	}
	public boolean update(BeatDataInterface beatData) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
