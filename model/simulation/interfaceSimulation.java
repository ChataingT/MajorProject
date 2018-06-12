package Simulation.model.simulation;

import java.util.List;

import Simulation.model.Coord;
import Simulation.model.environnement.Environnement;
import Simulation.model.robot.Orientation;
import Simulation.model.simulation.autoNavigation.AutoNav;

public class interfaceSimulation {
	private Simulation simulation;
	private AutoNav autoNavigation;
	
	
	 /**
	   * Get the value of Environment
	   * @return the value of Environnement
	   */
	  public Environnement getEnvironnement () {
	    return this.simulation.getEnvironnement();
	  }

	  /**
	   * Get the value of the environment in a string 2D
	   * @return the value of matrixEnvironnement
	   */
	  public String[][] getMatrixEnvironment() {
		return this.simulation.getMatrixEnvironment();
	  }


	/**
	 * Get the value of the view of the robot in a string 2D
	 * @return the value of Environnement
	 */
	  public String[][] getMatrixViewRobot() {
		return this.simulation.getMatrixViewRobot();
		}

	  /**
	   * Get the board of the environment already discovered by the robot
	   */
	  public String[][] getMatrixKnownEnvironment(){
		  return this.simulation.getMatrixKnownEnvironment();
	  }
	  
	  /**
	   * Get the coordinate of the robot
	   */
	  public List<Integer> getCoordRobot(){
		  return this.simulation.getCoordRobot();
	  }
	  
	  /**
	   * Get the count of the command done since the start of the simulation
	   * @return Integer
	   */
	  public Integer getNbCmd(){
		  return this.simulation.getNbCmd();
	  }
	  
	  /**
	   * Get the count of the obstacle met since the start of the simulation
	   * @returnInteger
	   */
	  public Integer getNbMetObst(){
		  return this.simulation.getNbMetObst();
	  }
	  
	  /**
	   * Get the number of obstacle detect by the robot at this moment
	   * @return Integer
	   */
	  public Integer getNbSeeingObst(){
		  return this.simulation.getNbSeeingObst();
	  }
	  
	  /**
	   * Get the distance traveled (in case ~= 20cm) since the start of the simulation
	   * @return Integer
	   */
	  public Integer getDistanceTraveled(){
		  return this.simulation.getDistanceTraveled();
	  }
	  
	  /**
	   * Get the current orientation of the robot
	   * @return Orientation 
	   */
	  public Orientation getRobotOrientation(){
		  return this.simulation.getRobotOrientation();
	  }
	  
	  /**
	   * Get the size of the environment
	   * @return
	   */
	  public Coord getSize(){
		  return this.simulation.getSize();
	  }
	  
	  /**
	   * Get the percent of the map discovered
	   * @return
	   */
	  public Integer getPercentDiscoveredMap(){
		  return this.simulation.getPercentDiscoveredMap();
	  }
	  
	  /**
	   * Move the robot up
	   * @return
	   */
	  public Boolean up(){
		  return this.simulation.up();
	  }
	  
	  /**
	   * Move the robot right
	   * @return
	   */
	  public Boolean right(){
		  return this.simulation.right();
	  }
	  
	  /**
	   * Move the robot down
	   * @return
	   */
	  public Boolean down(){
		  return this.simulation.down();
	  }
	  
	  /**
	   * Move the robot left
	   * @return
	   */
	  public Boolean left(){
		  return this.simulation.left();
	  }


	/**
	 * Auto navigation, the robot will go on the map a,nd change direction when there is an obstacle
	 * @return
	 */
	public List<Coord> dumpNav(){
		return this.autoNavigation.dumpNav();
	}

	/**
	 * Auto navigation, the robot will search to discover all of the obstacle of the map
	 * @return
	 */
	public List<Coord> searchObst(){
		return this.autoNavigation.searchObst();
	}
}
