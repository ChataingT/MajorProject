

/**
 * Class Measure
 */
public class Measure {


  private Integer nbCmd = 0;
  private Integer nbMetObst = 0;
  private Integer nbSeenObst = 0;
  private Integer distanceTraveled = 0;
  
  //
  // Constructors
  //
  public Measure () { 
	  
  };
  

  /**
   * Set the value of nbCmd
   * @param newVar the new value of nbCmd
   */
  private void setNbCmd (Integer nbCmd) {
    nbCmd = nbCmd;
  }

  /**
   * Get the value of nbCmd
   * @return the value of nbCmd
   */
  private Integer getNbCmd () {
    return nbCmd;
  }

  /**
   * Set the value of nbMetObst
   * @param newVar the new value of nbMetObst
   */
  private void setNbMetObst (Integer nbMetObst) {
    nbMetObst = nbMetObst;
  }

  /**
   * Get the value of nbMetObst
   * @return the value of nbMetObst
   */
  private Integer getNbMetObst () {
    return nbMetObst;
  }

  /**
   * Set the value of nbSeenObst
   * @param newVar the new value of nbSeenObst
   */
  private void setNbSeenObst (Integer nbSeenObst) {
    nbSeenObst = nbSeenObst;
  }

  /**
   * Get the value of nbSeenObst
   * @return the value of nbSeenObst
   */
  private Integer getNbSeenObst () {
    return nbSeenObst;
  }

  /**
   * Set the value of distanceTraveled
   * @param newVar the new value of distanceTraveled
   */
  private void setDistanceTraveled (Integer distanceTraveled) {
    distanceTraveled = distanceTraveled;
  }

  /**
   * Get the value of distanceTraveled
   * @return the value of distanceTraveled
   */
  private Integer getDistanceTraveled () {
    return distanceTraveled;
  }

  /**
   * Ajoute 1 au nb de commande
   */
  public Integer incNbCmd()
  {
	  Integer NbCmd;
	  NbCmd=getNbCmd()+1;
	  return NbCmd;
  }


  /**
 * @return Nb d'obstacle rencontre
 * Ajoute 1 au nb d'obstacle rencontré
   */
  public Integer incNbMetObst()
  {
	  Integer NbMetObst;
	  NbMetObst=getNbMetObst()+1;
	  return NbMetObst;
  }


  /**
   * Nb d'obstacle que voit à l'instant t le robot
   *  * @return le nb d'ostacle visible grâce au controler
   */
  //TODO
  public Integer nbSeenObst()
  {
	return nbSeenObst;
	 
  }


  /**
   * Permet de donner la distance parcourue par le robot depuis le début de la session
   * return la distance parcourue
   */
  public Integer incDistanceTraveled(Integer distanceTraveled)
  {
	distanceTraveled=getDistanceTraveled()+distanceTraveled;
	return distanceTraveled;
  }


}
