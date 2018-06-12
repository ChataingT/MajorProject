package Simulation.model.robot;

import java.util.*;

import Simulation.model.Coord;
import Simulation.model.environnement.Map;
import Simulation.model.sensor.Detector;
import Simulation.model.sensor.Sensor;


/**
 * Class Robot
 * model of the robot
 */
public class Robot {

  //
  // Fields
  //

  private Coord coord;
  private Orientation orientation;
  private Map discoveredMap;
  private List<Detector> listDetector;
  
  //
  // Constructors
  //
  
  /**
   * Constructor
   * 	initial coordinate : (0,0)
   * 	initial orientation NORTH
   */
  public Robot () {
	  this.coord = new Coord(0, 0); // coordinate initial 
	  this.orientation = Orientation.NORTH; // Orientation initial
	  this.listDetector = new ArrayList<Detector>(); // add of the sensor
	  this.listDetector.add(new Sensor());
	  this.discoveredMap = new Map();
  };
  
  /**
   * Constructor
   * @param x coordinate initial in x
   * @param y coordinate initial in y
   * @param orientation initial
   */
  public Robot(Integer x, Integer y, Orientation orientation){
	  this.coord = new Coord(x, y); // coordinate initial 
	  this.orientation = orientation; // Orientation initial
	  this.listDetector = new ArrayList<Detector>(); // add of the sensor
	  this.listDetector.add(new Sensor());
	  this.discoveredMap = new Map();

  }
  
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of coord
   * @param newVar the new value of coord
   */
  protected void setCoord (Coord coord) {
    this.coord = coord;
  }

  /**
   * Get the value of coord
   * @return the value of coord
   */
  protected Coord getCoord () {
    return coord;
  }

  /**
   * Set the value of Orientation
   * @param newVar the new value of Orientation
   */
   protected void setOrientation (Orientation newVar) {
    this.orientation = newVar;
  }

  /**
   * Get the value of Orientation
   * @return the value of Orientation
   */
   protected Orientation getOrientation () {
    return orientation;
  }

  /**
   * Set the value of discoveredMap with the union of the old one and the new one.
   * @param newVar the new value of discoveredMap
   */
   protected void setDiscoveredMap (Map map) {
	   Map.unionMap(discoveredMap, map);
  }

  /**
   * Get the value of discoveredMap
   * @return the value of discoveredMap
   */
   public Map getDiscoveredMap () {
    return discoveredMap;
  }

  

  //
  // Other methods
  //

 


  /** Get the list of coordinate of all the case detected by the robot.
   * The coordinate are in absolute referential.
   * @return   List<Coord>    
   */
   protected List<Coord> getView()
  {
	  List<Coord> ret = new ArrayList<Coord>();
	  List<Coord> list = new ArrayList<Coord>();
	  Coord newCoord = new Coord(0, 0);

	  for (Detector detector : listDetector) { // Concatenation of all coordinate of all case detected by the robot
		  list.addAll(detector.detection());
	  }

	  for (Coord c : list){
		  newCoord = Coord.add(this.coord, c); // Go to the absolute referential
		  if ( (newCoord.x >=0) && (newCoord.y >=0) ){
			  ret.add(newCoord);
		  }
	  }

	  return ret;
  }
  
  public String toString(){
	  String ret = "[ ROBOT : Current coordinate : "+ this.coord 
			  + "   Current orientation : " + this.orientation + " ]";
	  return ret;
  }
  public static void main(String[] args) {
	Robot robot = new Robot();

	
}
  
}