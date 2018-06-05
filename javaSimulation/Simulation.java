package javaSimulation;
import model.robot.RobotControler;
import model.environment.Environnement;


/**
 * Class Simulation
 */
public class Simulation {

  //
  // Fields
  //

  private RobotControler robotControler;
  private Environnement environnement;
  
  //
  // Constructors
  //
  public Simulation () { 
	  this.robotControler = new RobotControler();
	  this.environnement = new Environnement();
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //



  /**
   * Get the value of Environnement
   * @return the value of Environnement
   */
  private Environnement getEnvironnement () {
    return environnement;
  }

  //
  // Other methods
  //

  
//TODO test next case obstacle
//TODO test next case in limit
  /**
   * @return       Boolean
   */
  public Boolean up()
  {
	  Boolean ret = false;
	  ret = this.robotControler.up();
	  
	  return ret;
  }


  /**
   * @return       Boolean
   */
  public Boolean down()
  {
	  Boolean ret = false;

	  this.robotControler.down();
	  return ret;

  }


  /**
   * @return       Boolean
   */
  public Boolean left()
  {
	  Boolean ret = false;

	  this.robotControler.left();
	  return ret;

  }


  /**
   * @return       Boolean
   */
  public Boolean right()
  {
	  Boolean ret = false;

	  this.robotControler.right();
	  return ret;

  }


  /**
   */
  public void getView()
  {
	  this.robotControler.getView();
  }


  /**
   */
  public void getRobotEnvironnement()
  {
	  this.robotControler.getRobotEnvironnement();
  }


}
