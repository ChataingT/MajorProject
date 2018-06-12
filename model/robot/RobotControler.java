package Simulation.model.robot;

import java.util.*;

import Simulation.model.Coord;
import Simulation.model.environnement.Map;


/**
 * Class RobotControler
 */
public class RobotControler {

  //
  // Fields
  //

  private Robot robot;
  
  //
  // Constructors
  //
  public RobotControler () { 
	  this.robot = new Robot();
  };
  


  /**
   * Get the value of robot
   * @return the value of robot
   */
  public Coord getRobotCoord () {
    return robot.getCoord();
  }
  
  public Orientation getRobotOrientation(){
	  return this.robot.getOrientation();
  }
  
  public Map getDiscoveredMap () {
	    return this.robot.getDiscoveredMap();
	  }

  //
  // methods
  //

  /** Move the robot of one case in the north
   * @return       Boolean
   */
  public Boolean up()
  {
	  this.robot.setCoord(Coord.add(this.robot.getCoord(), new Coord(-1, 0)));
	  this.robot.setOrientation(Orientation.NORTH);
	  return true;
  }


  /** Move the robot of one case in the South
   * @return       Boolean
   */
  public Boolean down()
  {
	  this.robot.setCoord(Coord.add(this.robot.getCoord(), new Coord(1, 0)));
	  this.robot.setOrientation(Orientation.SOUTH);
	  return true;
  }


  /** Move the robot of one case in the West
   * @return       Boolean
   */
  public Boolean left()
  {
	  this.robot.setCoord(Coord.add(this.robot.getCoord(), new Coord(0, -1)));
	  this.robot.setOrientation(Orientation.WEST);
	  return true;
  }


  /** Move the robot of one case in the East
   * @return       Boolean
   */
  public Boolean right()
  {
	  this.robot.setCoord(Coord.add(this.robot.getCoord(), new Coord(0, 1)));
	  this.robot.setOrientation(Orientation.EAST);
	  return true;
  }


  /** Get the view of the robot
   * @return       List<Coord> 
   */
  public List<Coord> getView()
  {
	  return this.robot.getView();
  }



  /** Get the map of the environment discovered
   * @return       Map
   */
  public Map getRobotEnvironnement()
  {
	  return this.robot.getDiscoveredMap();
  }
  
  /**
   * Update the map of the environment discovered
   * @param map
   */
  public void updateDiscoveredMap(Map map){
	  this.robot.setDiscoveredMap(map);
  }
  
   @Override
public String toString() {
	return "RobotControler [robot=" + robot + "]";
}



public static void main(String[] args) {
	RobotControler rct = new RobotControler();
	System.out.println(rct);
	rct.down();
	System.out.println(rct);
	rct.up();
	System.out.println(rct);
	rct.left();
	System.out.println(rct);
	System.out.println(rct.getView());
	rct.right();
	System.out.println(rct);

}

}
