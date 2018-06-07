package Simulation.model.sensor;

import java.util.*;

import Simulation.model.Coord;


/**
 * Class Sensor
 * Sensor can detect two cases in North, East, South, West and one case in North-East, North-West, South-East, South-West.
 */
public class Sensor implements Detector {

  //
  // Fields
  //

  private List<Coord> listDetect;
  
  //
  // Constructors
  //
  public Sensor () { 
	  // 
	  listDetect = new ArrayList<Coord>();
	  listDetect.add(new Coord(1, 0));
	  listDetect.add(new Coord(2, 0));
	  listDetect.add(new Coord(0, 1));
	  listDetect.add(new Coord(0, 2));
	  listDetect.add(new Coord(-1, 0));
	  listDetect.add(new Coord(-2, 0));
	  listDetect.add(new Coord(0, -1));
	  listDetect.add(new Coord(0, -2));
	  listDetect.add(new Coord(1, 1));
	  listDetect.add(new Coord(1, -1));
	  listDetect.add(new Coord(-1, 1));
	  listDetect.add(new Coord(-1, -1));
  };
  
  //
  // Methods
  //



  /**
   * @return  List<Coord> a list of the relative coordinate of the case detected by this sensor.
   */
  public List<Coord> detection()
  {
	return this.listDetect;
  }


}
