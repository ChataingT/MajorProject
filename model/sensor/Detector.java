package Simulation.model.sensor;
import java.util.List;

import Simulation.model.Coord;



/**
 * Interface Detector
 */
public interface Detector {



  /** Return the relative coordinate of the case detected
   * @return  Map
   */
  public List<Coord> detection();


}
