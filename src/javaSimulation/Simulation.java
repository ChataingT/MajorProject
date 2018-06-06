package javaSimulation;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import model.Coord;
import model.robot.Measure;
import model.robot.RobotControler;
import model.environnement.Case;
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
  private Measure measure;
  private String[][] matrix;
  
  //
  // Constructors
  //
  public Simulation (Integer x, Integer y, Integer obstaclePourcent, Integer speedBumpPourcent) { 
	  this.robotControler = new RobotControler();
	  this.environnement = new Environnement(new Coord(x, y), obstaclePourcent, speedBumpPourcent);
	  this.measure = new Measure();
	  this.refreshAff();

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
  public Environnement getEnvironnement () {
    return environnement;
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
		  Etat etatNextCase = this.environnement.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.up();

		  }
	  }
	  refreshAff();
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
	  if ( newCoord.x <= this.environnement.getSize().x){ // Test the limit of the environnement
		  Etat etatNextCase = this.environnement.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.down();

		  }
	  }
	  refreshAff();
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
		  Etat etatNextCase = this.environnement.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.left();

		  }
	  }
	  refreshAff();
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
	  if (newCoord.x <= this.environnement.getSize().x){ // Test the limit of the environment
		  Etat etatNextCase = this.environnement.getEtat(newCoord);
		  if ( etatNextCase.compareTo(Etat.OBSTACLE) != 0 ){ // Test if the next case is an obstacle
			  ret = this.robotControler.right();

		  }
	  }
	  refreshAff();
	  return ret;
  }


  /**
   */
  public String[][] getView()
  {
	  
	  Environnement e = new Environnement(this.environnement.getSize());
	  String[][] ret = e.environmentToMatrixAff();

	  Integer nbrObst = 0;
	  List<Coord> caseDetected = this.robotControler.getView();
	  Etat etatCase;
	  for(Coord coordCase : caseDetected){
		  etatCase = environnement.getEtat(coordCase);
		  
		  if (etatCase.equals(Etat.OBSTACLE)){
			  nbrObst++;
		  }
		  e.setEtat(coordCase, environnement.getEtat(coordCase));
		  
	  }
	  ret = this.affRobot(e, ret);

	return ret;
  }
  
  //TODO gérer affichage
 /* public void affView(){
	  System.out.println(printMatrix(view,e.getSize().x,e.getSize().y));

  }
*/

  /**
   */
  public void getRobotEnvironnement()
  {
	  this.robotControler.getRobotEnvironnement();
  }
  
    
  private String[][] affRobot(Environnement e, String[][] string){
	  String [][] ret = string;
	  Coord coordRobot = this.robotControler.getRobotCoord();
	  ret[coordRobot.x][coordRobot.y] = "R : "+this.robotControler.getRobotOrientation().toString();
	  return ret;
  }
  
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
  
  private void refreshAff(){
	  this.matrix = environnement.environmentToMatrixAff();
	  this.matrix = this.affRobot(environnement, this.matrix);
  }
  
  public void affEnvironnement(){
	  System.out.println(printMatrix(matrix,environnement.getSize().x,environnement.getSize().y));

  }


}
