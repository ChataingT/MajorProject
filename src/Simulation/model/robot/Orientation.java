package Simulation.model.robot;

public enum Orientation {
	NORTH, SOUTH, EAST, WEST;
	
	@Override
	public String toString(){
		String ret = null;
		if (this.equals(NORTH) ){
			ret = "N";
		}
		else if (this.equals(SOUTH)){
			ret = "S";
		}
		else if (this.equals(EAST)){
			ret = "E";
		}
		else {
			ret = "W";
		}
		
		return ret;
	}
}
