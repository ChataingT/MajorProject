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
		

		
		
		
	/**
	 * Constructor for auto navigation	
	 * @param simulation
	 */
	public AutoNav(Simulation simulation) {
			super();
			this.simulation = simulation;
			this.listKnownObst = new ArrayList<Obstacle>();
			this.listMove = new ArrayList<Coord>();

	}
	/**
	 * Get the list of the move : List<Coord>
	 */
	public List<Coord> getListMove(){
		return this.listMove;
	}
	
	/**
	 * Run the mode dumpNav
	 * @return List<Coord> all coord where the robot passed
	 */
	public List<Coord> dumpNav(){
		List<Coord> ret = new ArrayList<Coord>();
		ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
		for(int i=0; i<10;i++){
			move();
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
		}
		return ret;
		
	}
	
	/**
	 * Deplacement for the dumpNav
	 */
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
	
	/**
	 * Auto navigation to search all obstacle
	 * @return List<Coord> all the case the robot passed
	 */
	public List<Coord> searchObst(){
		this.move = Move.DOWN;
System.out.println("Start auto nav");
		while (this.simulation.getPercentDiscoveredMap() < 100){ // waiting for the 100% completion
			moveAutoNav();
		}
		return this.getListMove();
		
	}
	
	/**
	 * Deplacement for the mode searchObst
	 * @return
	 */
	private Boolean moveAutoNav(){
		Boolean ret = false ;
		switch(this.move){ // the robot go up or go down
		case UP:
			if( 0 <= (this.simulation.getCoordRobot().get(0)-1)) { // Test border
				if (this.simulation.getCloseObst().get(0) == 0){ // Test if there is an obstacle in front of it
					this.simulation.up();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
					this.move = Move.UP;

				}
				else{ // if there is an obstacle test if it is known
					if (this.haveObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),0)) ){ // if the obstacle is known
						Obstacle obstacle = this.getObstacle(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),0));
						move(obstacle.getCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1))));
						this.move = Move.UP;
					}
					else{// if it is not known map it
						this.moveMappingUp(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
						this.move = Move.UP;

					}
				}
			}
			else{ // if border move right for 5 case (calibrate one sensor)
				int i = 0;
				while ((i<5) && (this.simulation.getCloseObst().get(1)==0)){
					this.simulation.right();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
					this.move = Move.DOWN;
					i++;
				}
			}
			break;
		case DOWN: // same as up
			if( !(this.simulation.getSize().x == (this.simulation.getCoordRobot().get(0)+1)) ){ // Test if it's the border
				if (this.simulation.getCloseObst().get(2) == 0){
					this.simulation.down();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
					this.move = Move.DOWN;
				}
				else{
					if (this.haveObst(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),3)) ){ // if the obstacle is known
						Obstacle obstacle = this.getObstacle(Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)),3));
						move(obstacle.getCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1))));
						this.move = Move.DOWN;

					}
					else{
						this.moveMappingDown(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
						this.move = Move.DOWN;
					}
				}
			}
			else{
				int i = 0;

				while ((i<5) && (this.simulation.getCloseObst().get(1)==0)){
					this.simulation.right();
					this.listMove.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
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
	/**
	 * Execute the move in function of a list of coordinate
	 * @param listCoordMoveToDo
	 */
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
	
	/**
	 * Test if the robot know this obstacle
	 * @param c
	 * @return
	 */
	private Boolean haveObst(Coord c){
		Boolean ret = false;
		
		for(Obstacle ob : listKnownObst){
			if (ob.isInListCoordObst(c)){
				ret = true;
			}
		}
		
		return ret;
	}
	
	/**
	 * Get the obstacle
	 * @param c coordinate of the obstacle
	 * @return
	 */
	private Obstacle getObstacle(Coord c){
		Obstacle ret = null;
		
		for(Obstacle ob : listKnownObst){
			if (ob.isInListCoordObst(c)){
				ret = ob;
			}
		}
		
		return ret;	
	}
	
	/**
	 * move based on i=0/1/2/3 like the enum Move
	 * @param i
	 */
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

	/**
	 * Methode to map an obstacle
	 * @param c coordinate of the robot
	 */
	private void moveMappingDown( Coord c){ // test in an anti-time clock
		List<Coord> ret =  new ArrayList<Coord>();
		Obstacle obstacle = new Obstacle(c, Coord.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)), 3));
		Coord startCoord = new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1));
		this.idMove = 3; // start with a left movement 
		int lastMove = ( 3 + 2 % 4 + 4) % 4; // last move was a down, modulo positive
		do{
			boolean searchRightMove = true; // true = the good direction is chosen
			int i = idMove; // equivalent of the enum Move
			while (searchRightMove){
				if (this.simulation.getCloseObst().get(i) == 0){ // test obstacle in the direction i
					searchRightMove = false;
					int a = (i -1 % 4 + 4) % 4; // For an always positive modulo
					int b = (i -2 % 4 + 4) % 4;;
					if ( (this.simulation.getCloseObst().get(a) == 0) && // test obstacle in the direction i-1 and i-2 if the last move was note in the opposite of i-1
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
					i = (i +1 % 4 + 4) % 4; // incrément the direction
				}
			
			}
			move(idMove);
			lastMove = (this.idMove + 2 % 4 + 4) % 4;
			this.idMove = i;
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
			obstacle.addListCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
		}while ( !(startCoord.equals(ret.get(ret.size()-1))) ); // test to wait until the robot has gone around the obstacle
		this.listKnownObst.add(obstacle);

	}
		
	
	private void moveMappingUp( Coord c){ // as moveMappingDown
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
			this.idMove = i;
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
			obstacle.addListCoordMove(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)) );
		}while ( !(startCoord.equals(ret.get(ret.size()-1))) ); // test to wait until the robot has gone around the obstacle
		this.listKnownObst.add(obstacle);

	}
}
