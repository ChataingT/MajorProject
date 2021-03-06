package model.sensor;
import java.util.List;

import model.Coord;



/**
 * Interface Detector
 * @author thibaut.chataing
 */
public interface Detector {

  //
  // Fields
  //

  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  //
  // Other methods
  //

  /** Return the relative coordinate of the case detected
   * @return  Map
   */
  public List<Coord> detection();


}
