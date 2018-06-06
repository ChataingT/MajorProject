package javaSimulation;
import model.Coord;
import model.robot.RobotControler;
import model.environnement.Environnement;
import model.environnement.Etat;


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
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(1, 0));
	  Boolean ret = false;
	  if (newCoord.x >= this.environnement.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environnement.etatCase(newCoord);
		  if ( !(etatNextCase.compareTo(Etat.OBSTACLE)) ){ // Test if the next case is an obstacle
			  ret = this.robotControler.up();
		  }
	  }
	  return ret;
  }


  /**
   * @return       Boolean
   */
  public Boolean down()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(-1, 0));
	  Boolean ret = false;
	  if (newCoord.x >= this.environnement.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environnement.etatCase(newCoord);
		  if ( !(etatNextCase.compareTo(Etat.OBSTACLE)) ){ // Test if the next case is an obstacle
			  ret = this.robotControler.down();
		  }
	  }
	  return ret;
  }


  /**
   * @return       Boolean
   */
  public Boolean left()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(0, -1));
	  Boolean ret = false;
	  if (newCoord.x >= this.environnement.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environnement.etatCase(newCoord);
		  if ( !(etatNextCase.compareTo(Etat.OBSTACLE)) ){ // Test if the next case is an obstacle
			  ret = this.robotControler.left();
		  }
	  }
	  return ret;
  }


  /**
   * @return       Boolean
   */
  public Boolean right()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(0, 1));
	  Boolean ret = false;
	  if (newCoord.x >= this.environnement.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environnement.etatCase(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.right();
		  }
	  }
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
