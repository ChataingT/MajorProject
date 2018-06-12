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
  private List<Integer> closeObst =  new ArrayList<Integer>(4); // 0=free 1=obstacle up/right/down/left;
  
  //
  // Constructors
  //
  public Simulation (Integer x, Integer y, Integer obstaclePourcent, Integer speedBumpPourcent) { 
	  this.robotControler = new RobotControler();
	  this.environment = new Environnement(new Coord(x, y), obstaclePourcent, speedBumpPourcent);
	  this.measure = new Measure();
	  this.refreshAttribut();
	  this.closeObst.set(0,0);
	  this.closeObst.set(1,0);
	  this.closeObst.set(2,0);
	  this.closeObst.set(3,0);

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
  
  public Coord getSize(){
	  return this.environment.getSize();
  }
 
  
  /**Extract the view of the robot defined by his sensor
   * 
   */
  private void getView()
  {
	  Environnement e = new Environnement(this.environment.getSize());
	  Map map= new Map();
	  Integer nbrObst = 0;
	  List<Coord> caseDetected = this.robotControler.getView();
	  Etat etatCase;
	  List<Integer> listObst = new ArrayList<Integer>();
	  for (int i =0; i<4; i++){ // clear old data
		  listObst.add(0);
	  }
	  
	  for(Coord coordCase : caseDetected){
		  etatCase = environment.getEtat(coordCase);
		  
		  if (etatCase.equals(Etat.OBSTACLE)){
			  nbrObst++;
			  if (coordCase.equals(Coord.add(this.robotControler.getRobotCoord(), 0)))
					  {
				  listObst.set(0, 1); // obstacle in up case
			  }
			  if (coordCase.equals(Coord.add(this.robotControler.getRobotCoord(), 1)))
			  {
				  listObst.set(1, 1); // obstacle in right case
			  }
			  if (coordCase.equals(Coord.add(this.robotControler.getRobotCoord(), 2)))
			  {
				  listObst.set(2, 1); // obstacle in down case
			  }
			  if (coordCase.equals(Coord.add(this.robotControler.getRobotCoord(), 3)))
			  {
				  listObst.set(3, 1); // obstacle in left case
			  }
		  }
		  e.setEtat(coordCase, environment.getEtat(coordCase));
		  map.addCase(coordCase,environment.getEtat(coordCase));
	  }
	this.measure.setNbSeeingObst(nbrObst);
	matrixViewRobot = e.environmentToMatrixAff();
	this.updateRobotDiscoveredMap(map);
	this.closeObst = listObst;
	this.testBord();

  }
  
  public List<Integer> getCloseObst(){
	  return this.closeObst;
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
System.out.println("Obst !!!");
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
	  if ( newCoord.x < this.environment.getSize().x){ // Test the limit of the environnement
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
	  if (newCoord.y < this.environment.getSize().y){ // Test the limit of the environment
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
   * Test if the robot is next to a border and upload listBorder
   */
  private void testBord(){
	  Coord coordRobot = this.robotControler.getRobotCoord();
	  List<Integer> listBord = new ArrayList<Integer>();
	  for (int i =0; i<4; i++){ // clear old data
		  listBord.add(0);
	  }
	  if (coordRobot.x == 0){
		  listBord.set(0, 1);
	  }
	  if (coordRobot.y == this.environment.getSize().y-1){
		  listBord.set(1, 1);
	  }
	  if (coordRobot.x == this.environment.getSize().x-1){
		  listBord.set(2, 1);
	  }
	  if (coordRobot.y == 0){
		  listBord.set(3, 1);
	  }
	  
	  for (int i =0; i<4; i++){ // clear old data
		  this.closeObst.set(i, this.closeObst.get(i)+listBord.get(i) );
	  }	  
  }
  
  /**
   * Update of the map of the robot
   * @param map
   */
  private void updateRobotDiscoveredMap(Map map){
	  this.robotControler.updateDiscoveredMap(map);
  }

  public Integer getPercentDiscoveredMap(){
	  Double ret = 0.;
	  List<Coord> discoveredMapToModify = this.robotControler.getDiscoveredMap().getListCoord();
	  List<Coord> discoveredMap = new ArrayList<Coord>();
	  discoveredMap.addAll(discoveredMapToModify);
	  for(Coord c : discoveredMapToModify){
		  if ( (c.x >= this.environment.getSize().x) || (c.y >= this.environment.getSize().y) ){
			  discoveredMap.remove(c);
		  }
	  }
	  double nbrKnown = discoveredMap.size();
	  double nbrCaseMap = this.environment.getSize().x * this.environment.getSize().y;
	  ret = Math.floor(nbrKnown / nbrCaseMap * 100);
	  return ret.intValue();
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
  private static String printMatrix(String[][] matrix,int sizeX,int sizeY) {
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
