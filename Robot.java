package model.robot;

import model.environment.Coord;

import java.util.*;

import model.environment.Map;
import sensor.Detector;
import sensor.Sensor;


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
  private void setCoord (Coord coord) {
    this.coord = coord;
  }

  /**
   * Get the value of coord
   * @return the value of coord
   */
  private Coord getCoord () {
    return coord;
  }

  /**
   * Set the value of Orientation
   * @param newVar the new value of Orientation
   */
  private void setOrientation (Orientation newVar) {
    this.orientation = newVar;
  }

  /**
   * Get the value of Orientation
   * @return the value of Orientation
   */
  public Orientation getOrientation () {
    return orientation;
  }

  /**
   * Set the value of discoveredMap
   * @param newVar the new value of discoveredMap
   */
  public void setDiscoveredMap (Map newVar) {
    this.discoveredMap = newVar;
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

  /** Move the robot of one case in the north
   * @return       Boolean
   */
  public Boolean up()
  {
	  this.setCoord(Coord.add(this.getCoord(), new Coord(1, 0)));
	  this.setOrientation(Orientation.NORTH);
	  return true;
  }


  /** Move the robot of one case in the South
   * @return       Boolean
   */
  public Boolean down()
  {
	  this.setCoord(Coord.add(this.getCoord(), new Coord(-1, 0)));
	  this.setOrientation(Orientation.SOUTH);
	  return true;
  }


  /** Move the robot of one case in the West
   * @return       Boolean
   */
  public Boolean left()
  {
	  this.setCoord(Coord.add(this.getCoord(), new Coord(0, -1)));
	  this.setOrientation(Orientation.WEST);
	  return true;
  }


  /** Move the robot of one case in the East
   * @return       Boolean
   */
  public Boolean right()
  {
	  this.setCoord(Coord.add(this.getCoord(), new Coord(0, 1)));
	  this.setOrientation(Orientation.EAST);
	  return true;
  }


  /** Get the list of coordinate of all the case detected by the robot.
   * The coordinate are in absolute referential.
   * @return   List<Coord>    
   */
  public List<Coord> getView()
  {
	  List<Coord> ret = new ArrayList<Coord>();
	  List<Coord> list = new ArrayList<Coord>();
	  Coord newCoord = new Coord(0, 0);

	  for (Detector detector : listDetector) { // Concatenation of all coordinate of all case detected by the robot
		  list.addAll(detector.detection());
	  }

	  for (Coord c : list){
		  newCoord = Coord.add(this.coord, c); // Go to the absolute referentiel
		  ret.add(newCoord);
	  }
	  System.out.println(ret);

	  return ret;
  }
  
  public String toString(){
	  String ret = "[ ROBOT : Current coordinate : "+ this.coord 
			  + "\tCurrent orientation : " + this.orientation + " ]";
	  return ret;
  }
  public static void main(String[] args) {
	Robot robot = new Robot();

	System.out.println(robot);
	System.out.println(robot.getView());
	robot.up();
	System.out.println(robot.getCoord());
	System.out.println(robot.getView());
	robot.left();
	System.out.println(robot.getCoord());
	System.out.println(robot.getView());
	robot.down();
	System.out.println(robot.getCoord());
	System.out.println(robot.getView());
	robot.down();
	robot.right();
	System.out.println(robot.getCoord());
	System.out.println(robot.getView());	
	Coord a = new Coord(5, -1);
	Coord b = new Coord(2, 4);
	System.out.println(Coord.add(a, b));

	System.out.println(robot);
}
  
}