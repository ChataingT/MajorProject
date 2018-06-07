package Simulation.model.environnement;

/**
 * Etat possible d'une case
 * @author justine.devaux
 *
 */
public enum Etat {
	UNKNOWN, FREE, OBSTACLE, SPEEDBUMP;

	@Override
	public String toString(){
		String ret = null;
		if (this.equals(FREE) ){
			ret = "FREE";
		}
		else if (this.equals(OBSTACLE)){
			ret = "OBST";
		}
		else if (this.equals(SPEEDBUMP)){
			ret = "SPBP";
		}
		else {
			ret = "UNKN";
		}
		
		return ret;
	}
	
	public String toAff(){
		String ret = null;
		if (this.equals(FREE) ){
			ret = ".";
		}
		else if (this.equals(OBSTACLE)){
			ret = "#";
		}
		else if (this.equals(SPEEDBUMP)){
			ret = "~";
		}
		else {
			ret = "?";
		}
		
		return ret;
	}
	
	public String toSend(){
		String ret = null;
		if (this.equals(FREE) ){
			ret = "0";
		}
		else if (this.equals(OBSTACLE)){
			ret = "1";
		}
		else if (this.equals(SPEEDBUMP)){
			ret = "2";
		}
		else {
			ret = "3";
		}
		
		return ret;
	}
}
