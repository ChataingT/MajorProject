package model.robot;


/**
 * Class Measure
 */
public class Measure {

  //
  // Fields
  //

  private Integer nbCmd = 0;
  private Integer nbMetObst = 0;
  private Integer nbSeenObst = 0;
  private Integer distanceTraveled = 0;
  
  //
  // Constructors
  //
  public Measure () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of nbCmd
   * @param newVar the new value of nbCmd
   */
  private void setNbCmd (Integer newVar) {
    nbCmd = newVar;
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
  private void setNbMetObst (Integer newVar) {
    nbMetObst = newVar;
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
  private void setNbSeenObst (Integer newVar) {
    nbSeenObst = newVar;
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
  private void setDistanceTraveled (Integer newVar) {
    distanceTraveled = newVar;
  }

  /**
   * Get the value of distanceTraveled
   * @return the value of distanceTraveled
   */
  private Integer getDistanceTraveled () {
    return distanceTraveled;
  }

  //
  // Other methods
  //

  /**
   */
  public void incNbCmd()
  {
  }


  /**
   */
  public void incNbMetObst()
  {
  }


  /**
   */
  public void incNbSeenObst()
  {
  }


  /**
   */
  public void incDistanceTraveled()
  {
  }


}
