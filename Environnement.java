
import java.util.*;
import java.math.*;

/**
 * Class Environnement
 */
public class Environnement {

  private Coord size;
  private Integer obstaclePercent;
  private Map map;
 
  
  public Environnement (Coord size, Integer obstaclePercent) { 
	  this.size=size;
	  this.obstaclePercent=obstaclePercent;
	  map= new Map(size);
	  double numero;
	  Integer nbObstaclePratique=0;
	  Integer nbCase=size.x*size.y;
	  Integer nbObstacleTheorique=nbCase*obstaclePercent/100;
	  
	  for (Case case1 : map.getListCase()){
		  numero= Math.random() * 100;

		  if ((numero <= obstaclePercent) && (nbObstacleTheorique > nbObstaclePratique)){
			  case1.setEtat(Etat.OBSTACLE);
			  nbObstaclePratique= nbObstaclePratique+1;
			  System.out.println(case1.getEtat());
			  
		  }
		  else {
			  case1.setEtat(Etat.FREE);
			  System.out.println(case1.getEtat());
			  
		  }
		  
	  }
	  
  };
  

  /**
   * Set the value of size
   * @param newVar the new value of size
   */
  public void setSize (Coord size) {
    size = size;
  }

  /**
   * Get the value of size
   * @return the value of size
   */
  private Coord getSize () {
    return size;
  }

  /**
   * Permet de calculer le nombre de case en fonction de size
   * @return nb de case
   */
  private Integer nbCase(){
	  Integer nbCase = 0;
	  nbCase = size.x * size.y ;
	  return nbCase;
  }
  
  /**
   * Set the value of obstaclePercent
   * @param newVar the new value of obstaclePercent
   */
  private void setObstaclePercent (Integer obstaclePercent) {
    obstaclePercent = obstaclePercent;
  }


  /**
   * Get the value of obstaclePercent
   * @return the value of obstaclePercent
   */
  private Integer getObstaclePercent () {
    return obstaclePercent;
  }
  
  
  public Etat getEtat (Coord coord){
	  Etat etatCase= Etat.UNKNOWN;
	  etatCase=map.getEtatCase(coord);
	  return etatCase;
  }
  
  
 

}


