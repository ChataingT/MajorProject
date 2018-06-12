package Simulation.model.simulation.autoNavigation;

import java.util.ArrayList;
import java.util.List;

import Simulation.model.Coord;
import Simulation.model.simulation.Simulation;

public class AutoNav {
		private Simulation simulation;
		private Move move = Move.DOWN;
		private Integer idMove = 2; // 0 = up / 1 = right / 2 = down / 3 = left
		private Boolean freeWay = false;
		private List<Obstacle> listKnownObst;
		private List<Coord> listMove;
		

		
		
		
		
	public AutoNav(Simulation simulation) {
			super();
			this.simulation = simulation;
			this.listKnownObst = new ArrayList<Obstacle>();
			this.listMove = new ArrayList<Coord>();

	}
	
	public List<Coord> getListMove(){
		return this.listMove;
	}
	public List<Coord> dumpNav(){
		List<Coord> ret = new ArrayList<Coord>();
		ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
		for(int i=0; i<10;i++){
			move();
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
		}
		return ret;
		
	}
	
	private void move(){
		switch (this.move) {
		case UP:
			if ( !(freeWay) || (this.simulation.getCloseObst().get(0)==1) ){
				this.move = Move.RIGHT;
				this.freeWay = simulation.right();
			}
			else{
				this.freeWay=simulation.up();
			}
			break;
		case RIGHT:
			if ( !(freeWay) || (this.simulation.getCloseObst().get(1)==1) ){
				this.move = Move.DOWN;
				this.freeWay = simulation.down();
			}
			else{
				this.freeWay=simulation.right();
			}
			break;
		case DOWN:
			if ( !(freeWay) || (this.simulation.getCloseObst().get(2)==1) ){
				this.move = Move.LEFT;
				this.freeWay = simulation.left();
			}
			else{
				this.freeWay=simulation.down();
			}
			break;
		case LEFT:
			if ( !(freeWay) || (this.simulation.getCloseObst().get(3)==1) ){
				this.move = Move.UP;
				this.freeWay = simulation.up();
			}
			else{
				this.freeWay=simulation.left();
			}
			break;

		default:
			break;
		}
	}
	
	public List<Coord> searchObst(){
		this.move = Move.DOWN;
System.out.println("Start auto nav");
		while (this.simulation.getPercentDiscoveredMap() < 100){
			moveAutoNav();
		}
		return this.getListMove();
		
	}
	
	private Boolean moveAutoNav(){
		Boolean ret = false ;
System.out.println(this.simulation.getCoordRobot());
System.out.println("obst : "+this.simulation.getCloseObst());
		switch(this.move){
		case UP:
			if( 0 <= (this.simulation.getCoordRobot().get(0)-1)) { // Test border
				if (this.simulation.getCloseObst().get(0) == 0){
					this.simulation.up();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
					this.move = Move.UP;
System.out.println("up");

				}
				else{
this.simulation.affEnvironnement();
					if (this.haveObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),0)) ){ // if the obstacle is known
						Obstacle obstacle = this.getObstacle(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),0));
						move(obstacle.getCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1))));
						this.move = Move.UP;
System.out.println("Obst-up");
					}
					else{
						this.moveMappingUp(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
						this.move = Move.UP;
System.out.println("map-up");

					}
				}
			}
			else{
				int i = 0;
				while ((i<5) && (this.simulation.getCloseObst().get(1)==0)){
					this.simulation.right();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
					this.move = Move.DOWN;
					i++;
System.out.println("right-up");
				}
			}
			break;
		case DOWN:
			if( !(this.simulation.getSize().x == (this.simulation.getCoordRobot().get(0)+1)) ){ // Test if it's the border
				if (this.simulation.getCloseObst().get(2) == 0){
					this.simulation.down();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
System.out.println("down");
					this.move = Move.DOWN;
				}
				else{
					if (this.haveObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),3)) ){ // if the obstacle is known
						Obstacle obstacle = this.getObstacle(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),3));
						move(obstacle.getCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1))));
						this.move = Move.DOWN;
System.out.println("obst-down");

					}
					else{
						this.moveMappingDown(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
						this.move = Move.DOWN;
System.out.println("map-down");

					}
				}
			}
			else{
				int i = 0;

				while ((i<5) && (this.simulation.getCloseObst().get(1)==0)){
					this.simulation.right();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
System.out.println("right-down");

					this.move = Move.UP;
					i++;
				}
			}
			break;
		default:
			
			break;
		}
		return ret;
	}
	
	private void move(List<Coord> listCoordMoveToDo){
		for(Coord c : listCoordMoveToDo){
			Coord robotCoord = new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1));
			if(c.y == robotCoord.y){
				if(c.x < robotCoord.x){
					this.simulation.up();
				}
				else{
					this.simulation.down();
				}
			}
			else{
				if (c.y > robotCoord.y){
					this.simulation.right();
				}
				else{
					this.simulation.left();
				}
			}
			this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));

		}
	}
	
	private Boolean haveObst(Coord c){
		Boolean ret = false;
		
		for(Obstacle ob : listKnownObst){
			if (ob.isInListCoordObst(c)){
				ret = true;
			}
		}
		
		return ret;
	}
	
	private Obstacle getObstacle(Coord c){
		Obstacle ret = null;
		
		for(Obstacle ob : listKnownObst){
			if (ob.isInListCoordObst(c)){
				ret = ob;
			}
		}
		
		return ret;	
	}
	
	private void move(Integer i){
		if (i==0){
			this.simulation.up();
		}
		if (i==1){
			this.simulation.right();
		}
		if (i==2){
			this.simulation.down();
		}
		if (i==3){
			this.simulation.left();
		}
		this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));

	}

	private void moveMappingDown( Coord c){
		this.simulation.affEnvironnement();
		List<Coord> ret =  new ArrayList<Coord>();
		Obstacle obstacle = new Obstacle(c, Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), 3));
		Coord startCoord = new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1));
		Coord caseTest = Coord.add(startCoord, 1);
		this.idMove = 3; // start with a left movement 
		int lastMove = ( 3 + 2 % 4 + 4) % 4;
 // last move was a down
		do{
			boolean searchRightMove = true; // true = the good direction is chosen
			int i = idMove; // equivalent of the enum Move
			while (searchRightMove){
System.out.println("search right move");
System.out.println(this.simulation.getCloseObst());
				if (this.simulation.getCloseObst().get(i) == 0){
					searchRightMove = false;
					int a = (i -1 % 4 + 4) % 4; // For an always positiv modulo
					int b = (i -2 % 4 + 4) % 4;;
					if ( (this.simulation.getCloseObst().get(a) == 0) && 
							( (a != lastMove) && (this.simulation.getCloseObst().get(b) == 0) ) ){
						i = (i -1 % 4 + 4) % 4;
						this.idMove = i;
						i = (i -1 % 4 + 4) % 4;

					}
					else {
						this.idMove = i;
						i = (i -1 % 4 + 4) % 4;

					}
	
				}
				else{
					obstacle.addListCoordObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), i ));
					i = (i +1 % 4 + 4) % 4;
					caseTest = Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), i );
				}
			
			}
			move(idMove);
			lastMove = (this.idMove + 2 % 4 + 4) % 4;
this.simulation.affEnvironnement();
			this.idMove = i;
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
			obstacle.addListCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
		}while ( !(startCoord.equals(ret.get(ret.size()-1))) ); // test to wait until the robot has gone around the obstacle
		this.listKnownObst.add(obstacle);

	}
		
	
	private void moveMappingUp( Coord c){
this.simulation.affEnvironnement();
		List<Coord> ret =  new ArrayList<Coord>();
		Obstacle obstacle = new Obstacle(c, Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), 0));
		Coord startCoord = new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1));
		Coord caseTest = Coord.add(startCoord, 1);
		this.idMove = 1; // start with a right movement 
		int lastMove = ( 0 + 2 % 4 + 4) % 4;
		do{
			boolean searchRightMove = true; // true = the good direction is chosen
			int i = idMove; // equivalent of the enum Move
			while (searchRightMove){
System.out.println("search right move");
System.out.println(this.simulation.getCloseObst());
				if (this.simulation.getCloseObst().get(i) == 0){
					searchRightMove = false;
					int a = (i -1 % 4 + 4) % 4; // For an always positiv modulo
					int b = (i -2 % 4 + 4) % 4;;
					if ( (this.simulation.getCloseObst().get(a) == 0) && 
							( (a != lastMove) && (this.simulation.getCloseObst().get(b) == 0) ) ){
						i = (i -1 % 4 + 4) % 4;
						this.idMove = i;
						i = (i -1 % 4 + 4) % 4;

					}
					else {
						this.idMove = i;
						i = (i -1 % 4 + 4) % 4;

					}
	
				}
				else{
					obstacle.addListCoordObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), i ));
					i = (i +1 % 4 + 4) % 4;
					caseTest = Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), i );
				}
			
			}
			move(idMove);
			lastMove = (this.idMove + 2 % 4 + 4) % 4;
this.simulation.affEnvironnement();
			this.idMove = i;
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
			obstacle.addListCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
		}while ( !(startCoord.equals(ret.get(ret.size()-1))) ); // test to wait until the robot has gone around the obstacle
		this.listKnownObst.add(obstacle);

	}

	public static void main(String[] args) {
		
		
	}
	/**
	 * Edit coordinates to make the robot map the obstacle
	 * @return List<Coord> 
	 */
	/*public List<Coord> mappingObstacle(Simulation simulation){
		List<Coord> ret = new ArrayList<Coord>();
		List<Integer> coordRobot = simulation.getCoordRobot();
		List<Integer> listCloseObst = simulation.getCloseObst();
		
		if(listCloseObst.get(3) == 0){
			this.freeWay = true;
			this.move = Move.LEFT;
		}
		
		
		return ret;
		
	}
*/
}
