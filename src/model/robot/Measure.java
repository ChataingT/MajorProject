package model.robot;

/**
 * Class Measure
 */
public class Measure {


  private Integer nbCmd = 0;
  private Integer nbMetObst = 0;
  private Integer nbSeenObst = 0;
  private Integer distanceTraveled = 0;
  

  public Measure () { 
	  
  };
  

  /**
   * Set the value of nbCmd
   * @param newVar the new value of nbCmd
   */
  public void setNbCmd (Integer nbCmd) {
    nbCmd = nbCmd;
  }

  /**
   * Get the value of nbCmd
   * @return the value of nbCmd
   */
  public Integer getNbCmd () {
    return nbCmd;
  }

  /**
   * Set the value of nbMetObst
   * @param newVar the new value of nbMetObst
   */
  public void setNbMetObst (Integer nbMetObst) {
    nbMetObst = nbMetObst;
  }

  /**
   * Get the value of nbMetObst
   * @return the value of nbMetObst
   */
  public Integer getNbMetObst () {
    return nbMetObst;
  }



  /**
   * Nb d'obstacle que voit à l'instant t le robot
   *  * @return le nb d'ostacle visible grâce au controler
   */
  //TODO
  public Integer getNbSeenObst()
  {
	return nbSeenObst;
	 
  }

  /**
   * Set the value of nbSeenObst
   * @param newVar the new value of nbSeenObst
   */
  public void setNbSeenObst (Integer nbSeenObst) {
    nbSeenObst = nbSeenObst;
  }



  /**
   * Set the value of distanceTraveled
   * @param newVar the new value of distanceTraveled
   */
  public void setDistanceTraveled (Integer distanceTraveled) {
    distanceTraveled = distanceTraveled;
  }

  /**
   * Get the value of distanceTraveled
   * @return the value of distanceTraveled
   */
  public Integer getDistanceTraveled () {
    return distanceTraveled;
  }

  /**
   * Ajoute 1 au nb de commande
   */
  public void incNbCmd()
  {
	  
	  this.nbCmd=getNbCmd()+1;
	
  }


  /**
 * @return Nb d'obstacle rencontre
 * Ajoute le nb d'obstacle rencontré
   */
  public void incNbMetObst()
  {
	 
	  this.nbMetObst=getNbMetObst()+getNbSeenObst();
	 
  }


  /**
   * Permet de donner la distance parcourue par le robot depuis le début de la session
   * return la distance parcourue
   */
  public void incDistanceTraveled()
  {
	this.distanceTraveled=getDistanceTraveled()+distanceTraveled;
	
  }


}
