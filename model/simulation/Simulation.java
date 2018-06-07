package Simulation.model.simulation;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import Simulation.model.Coord;
import Simulation.model.robot.Measure;
import Simulation.model.robot.Orientation;
import Simulation.model.robot.RobotControler;
import Simulation.model.environnement.Case;
import Simulation.model.environnement.Environnement;
import Simulation.model.environnement.Etat;
import Simulation.model.environnement.Map;


/**
 * Class Simulation
 */
public class Simulation {

  //
  // Fields
  //

  private RobotControler robotControler;
  private Environnement environment; // all of the environment of the robot
  private Measure measure;
  private String[][] matrixEnvironment;
  private String[][] matrixViewRobot;
  
  //
  // Constructors
  //
  public Simulation (Integer x, Integer y, Integer obstaclePourcent, Integer speedBumpPourcent) { 
	  this.robotControler = new RobotControler();
	  this.environment = new Environnement(new Coord(x, y), obstaclePourcent, speedBumpPourcent);
	  this.measure = new Measure();
	  this.refreshAttribut();

  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //



  /**
   * Get the value of Environment
   * @return the value of Environnement
   */
  public Environnement getEnvironnement () {
    return environment;
  }

  /**
   * Get the value of the environment in a string 2D
   * @return the value of matrixEnvironnement
   */
  public String[][] getMatrixEnvironment() {
	return matrixEnvironment;
  }


/**
 * Get the value of the view of the robot in a string 2D
 * @return the value of Environnement
 */
  public String[][] getMatrixViewRobot() {
	return matrixViewRobot;
	}

  /**
   * Get the board of the environment already discovered by the robot
   */
  public String[][] getMatrixKnownEnvironment()
  {
	  Map map = this.robotControler.getRobotEnvironnement();
	  Environnement e = new Environnement(this.environment.getSize());
	  Etat etatCase;
	  
	  for(Coord coordCase : map.getListCoord()){
		  etatCase = environment.getEtat(coordCase);
		  e.setEtat(coordCase, environment.getEtat(coordCase));
	  }
	  String[][] sEnvRobot = e.environmentToMatrixAff();
	  return sEnvRobot;
  }
  
  /**
   * Get the coordinate of the robot
   */
  public List<Integer> getCoordRobot(){
	  List<Integer> ret = new ArrayList<Integer>();
	  Coord c = this.robotControler.getRobotCoord();
	  ret.add(c.x);
	  ret.add(c.y);
	  return ret;
  }
  
  /**
   * Get the count of the command done since the start of the simulation
   * @return Integer
   */
  public Integer getNbCmd(){
	  return this.measure.getNbCmd();
  }
  
  /**
   * Get the count of the obstacle met since the start of the simulation
   * @returnInteger
   */
  public Integer getNbMetObst(){
	  return this.measure.getNbMetObst();
  }
  
  /**
   * Get the number of obstacle detect by the robot at this moment
   * @return Integer
   */
  public Integer getNbSeeingObst(){
	  return this.measure.getNbSeeingObst();
  }
  
  /**
   * Get the distance traveled (in case ~= 20cm) since the start of the simulation
   * @return Integer
   */
  public Integer getDistanceTraveled(){
	  return this.measure.getDistanceTraveled();
  }
  
  /**
   * Get the current orientation of the robot
   * @return Orientation 
   */
  public Orientation getRobotOrientation(){
	  return this.robotControler.getRobotOrientation();
  }
  
 
  
  /**Extract the view of the robot defined by his sensor
   * @return String[][] board with the information of each case : unknown/free/obstacle/speedbump
   */
  public void getView()
  {
	  Environnement e = new Environnement(this.environment.getSize());
	  Map map= new Map();
	  Integer nbrObst = 0;
	  List<Coord> caseDetected = this.robotControler.getView();
	  Etat etatCase;
	  
	  for(Coord coordCase : caseDetected){
		  etatCase = environment.getEtat(coordCase);
		  
		  if (etatCase.equals(Etat.OBSTACLE)){
			  nbrObst++;
		  }
		  e.setEtat(coordCase, environment.getEtat(coordCase));
		  map.addCase(coordCase,environment.getEtat(coordCase));
	  }
	this.measure.setNbSeeingObst(nbrObst);
	matrixViewRobot = e.environmentToMatrixAff();
	this.updateRobotDiscoveredMap(map);

  }
  //
  // Other methods
  //

/**Test if next case is in the environment and if there is not an obstacle on the way
   * If not, make the move
   * @return       Boolean to acquire 
   */
  public Boolean up()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(-1, 0));
	  Boolean ret = false;
	  if (newCoord.x >= 0){ // Test the limit of the environnement
		  Etat etatNextCase = this.environment.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.up();
			  this.measure.incDistanceTraveled();
		  }
		  else {
			  this.measure.incNbMetObst();
		  }
	  }

	  refreshAttribut();
	  this.measure.incNbCmd();
	  return ret;
  }


  /**Test if next case is in the environment and if there is not an obstacle on the way
   * If not, make the move
   * @return       Boolean to acquire 
   */
  public Boolean down()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(1, 0));
	  Boolean ret = false;
	  if ( newCoord.x <= this.environment.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environment.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.down();
			  this.measure.incDistanceTraveled();
		  }
		  else {
			  this.measure.incNbMetObst();
		  }
	  }

	  this.measure.incNbCmd();
	  refreshAttribut();
	  return ret;
  }


  /**Test if next case is in the environment and if there is not an obstacle on the way
   * If not, make the move
   * @return       Boolean to acquire 
   */
  public Boolean left()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(0, -1));
	  Boolean ret = false;
	  if (newCoord.y >= 0){ // Test the limit of the environment
		  Etat etatNextCase = this.environment.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.left();
			  this.measure.incDistanceTraveled();
		  }
		  else {
			  this.measure.incNbMetObst();
		  }
	  }

	  this.measure.incNbCmd();
	  refreshAttribut();
	  return ret;
  }


  /**Test if next case is in the environment and if there is not an obstacle on the way
   * If not, make the move
   * @return       Boolean to acquire 
   */
  public Boolean right()
  {
	  Coord newCoord = Coord.add(this.robotControler.getRobotCoord(), new Coord(0, 1));
	  Boolean ret = false;
	  if (newCoord.x <= this.environment.getSize().x){ // Test the limit of the environment
		  Etat etatNextCase = this.environment.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.right();
			  this.measure.incDistanceTraveled();

		  }
		  else {
			  this.measure.incNbMetObst();
		  }
	  }

	  this.measure.incNbCmd();
	  refreshAttribut();
	  return ret;
  }



  
  /**
   * Update of the map of the robot
   * @param map
   */
  public void updateRobotDiscoveredMap(Map map){
	  this.robotControler.updateDiscoveredMap(map);
  }

 
  
  /**
   * Add the robot in the board
   * @param e all environment
   * @param string which will have the robot add on
   * @return board with all of the environment and the robot
   */
  private String[][] addRobotInMatrix(String[][] string){
	  String [][] ret = string;
	  Coord coordRobot = this.robotControler.getRobotCoord();
	  ret[coordRobot.x][coordRobot.y] = "R : "+this.robotControler.getRobotOrientation().toString();
	  return ret;
  }
  
  /**
   * Convert a board in a 1D string
   * @param matrix board to convert
   * @param sizeX size of the board
   * @param sizeY size of the board
   * @return string 1D on the board. Ready to be print
   */
  public static String printMatrix(String[][] matrix,int sizeX,int sizeY) {
	  StringBuilder sb = new StringBuilder();
	  Formatter formatter = new Formatter(sb, Locale.FRENCH);
	  String formatS = "%1$5s";
	  String[] valueTab = new String[sizeX+1];
	  valueTab[0]="";
	  for (int index = 0; index < sizeX; index++) {
		  formatS = formatS + " %" + (index + 2) + "$5s";
		  valueTab[index+1] = String.valueOf(index);
	  }
	  formatter.format(formatS + "\n", valueTab);
	  formatter.format("%1$5s | %2$47s\n", "",
			  new String(new char[sizeX]).replace("\0", "_"));
	  for (int i = 0; i < sizeY; i++) {
		  String formatS2 = "%1$5s | ";
		  String[] valueTab2 = new String[sizeY+1];
		  valueTab2[0]=String.valueOf(i);
		  for (int j = 0; j < sizeX; j++) {
			  formatS2 = formatS2 + " %" + (j + 2) + "$5s";
			  valueTab2[j+1] = matrix[i][j];
		  }
		  formatter.format(formatS2 + "\n", valueTab2);
	  }
	return formatter.toString() ;
  }
  
  /**
   * Refresh the environment and the robot in the attribute matrix
   */
  private void refreshAttribut(){
	  this.matrixEnvironment = environment.environmentToMatrixAff();
	  this.getView();
  }
  
  
  ////
  //method to display in command line
  ////
  
  /**
   * Display in command line the environment
   */
  public void affEnvironnement(){
	  this.matrixEnvironment = this.addRobotInMatrix(this.matrixEnvironment);
	  System.out.println(printMatrix(matrixEnvironment,environment.getSize().x,environment.getSize().y));

  }
  
  /**
   * Display in command line of the view of the robot
   */
  public void affView(){
	this.matrixViewRobot = this.addRobotInMatrix(this.matrixViewRobot);
	System.out.println(printMatrix(matrixViewRobot,environment.getSize().x,environment.getSize().y));

  }
  
  /**
   * Display in command line of the view of the robot
   */
  public void affRobotDiscoveredMap(){
	  String[][] matrixRobotEnv = this.getMatrixKnownEnvironment();
	  System.out.println(printMatrix(matrixRobotEnv,environment.getSize().x,environment.getSize().y));

  }


}
