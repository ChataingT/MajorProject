package Simulation.model.environnement;

import java.util.*;
import Simulation.model.Coord;


/**
 * Class Case
 */
public class Case {

	private Etat etat=Etat.UNKNOWN;
	private Coord coord;
	//private Coord coord;
  
 	public Case (int x, int y) { 
 		this.etat=etat;
 		this.coord=new Coord(x,y);
 	};
  
  /**
   * Set the value of etat
   * @param newVar the new value of etat
   */
  protected void setEtat (Etat etat) {
    this.etat = etat;
  }

  /**
   * Get the value of etat
   * @return the value of etat
   */
  public Etat getEtat () {
    return etat;
  }

  /**
   * Set the value of coord
   * @param newVar the new value of coord
   */
  private void setCoord (Coord coord) {
    this.coord = coord;
  }

  /**
   * Get the value of coord
   * @return the value of coord
   */
  
  protected Coord getCoord() {
	  return coord;
  }



}
