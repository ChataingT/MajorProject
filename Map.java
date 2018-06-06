
import java.util.*;


/**
 * Class Map
 */
public class Map {

  private ArrayList<Case> listCase;
  private Case case1;

  public Map () { 
	  listCase = new ArrayList<Case>();
	  case1 = new Case(0,0); 
  };
  

public Map (Coord size) { 
	  listCase = new ArrayList<Case>();
	  Integer x=0;
	  Integer y=0;  
	  for (x=0; x<size.x;x++){
		  for(y=0;y<size.y;y++){			  
			  case1 = new Case(x,y);
			  listCase.add(case1);
		  }
	  }		 
	  
  };
  
  /**
   * Set the value of listCase
   * @param listCase the new value of listCase
   */
  private void setListCase (List listCase) {
    listCase = listCase;
  }

  /**
   * Get the value of listCase
   * @return the value of listCase
   */
  
  public void addCase(Case case1){
	  listCase.add(case1);
  }
  
  public List<Case> getListCase () {
    return listCase;  
  }
  
  /*
   * Compare deux maps et ajoute à l'ancienne les cases découvertes en plus de la nouvelle
   */
  public static Map unionMap (Map oldMap, Map newMap){
	  for (Case cases1 :newMap.getListCase()){
		  Integer flag = 0;
		  for (Case case2 : oldMap.getListCase()){
			  //System.out.println ("case1"+cases1.getCoord()+"case2"+case2.getCoord());
			  if (cases1.getCoord().equals(case2.getCoord())){
				  flag=1;
				  //System.out.println ("flag1");
			  }
					
		  }
		  //Signifit qu'une nouvelle case à été découverte
		  if (flag==0){
			  //System.out.println ("ajout"+cases1.getCoord());
			  oldMap.addCase (cases1);
			  
		  }
	  }
	  
	  
	  for (Case cases1: oldMap.getListCase()){
		  System.out.println (cases1.getCoord());
	  }
	  	  
	  return oldMap;
  }
  /**
   * Permet de donner l'etat d'une case
   * @param coord
   * @return
   */
  public Etat getEtatCase (Coord coord){
	Etat etatCase=Etat.UNKNOWN;
	for (Case case1 : getListCase()){
		if (case1.getCoord().equals(coord)){
			etatCase=case1.getEtat();
		}
	}
	 	  
	return etatCase;
	  
	  
  }


  




}