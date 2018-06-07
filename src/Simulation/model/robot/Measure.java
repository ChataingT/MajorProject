package Simulation.model.robot;

/**
 * Class Measure
 */
public class Measure {


  private Integer nbCmd = 0;
  private Integer nbMetObst = 0;
  private Integer nbSeeingObst = 0;
  private Integer distanceTraveled = 0;
  

  public Measure () { 
	  
  };
  



  /**
   * Get the value of nbCmd
   * @return the value of nbCmd
   */
  public Integer getNbCmd () {
    return nbCmd;
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
  public Integer getNbSeeingObst()
  {
	return nbSeeingObst;
	 
  }

  /**
   * Set the value of nbSeeingObst
   * @param newVar the new value of nbSeeingObst
   */
  public void setNbSeeingObst (Integer nbSeeingObst) {
    this.nbSeeingObst = nbSeeingObst;
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
	  
	  this.nbCmd++;
	
  }


  /**
 * @return Nb d'obstacle rencontre
 * Ajoute le nb d'obstacle rencontré
   */
  public void incNbMetObst()
  {
	 
	  this.nbMetObst++;
	 
  }


  /**
   * Permet de donner la distance parcourue par le robot depuis le début de la session
   * return la distance parcourue
   */
  public void incDistanceTraveled()
  {
	this.distanceTraveled++;
	
  }


}
