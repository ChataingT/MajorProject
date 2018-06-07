package Simulation.model.simulation;

import java.util.ArrayList;
import java.util.List;

import Simulation.model.Coord;

public class AutoNav {
		private Simulation simulation;
		private Move move = Move.UP;
		private Boolean freeWay = false;

		
		
		
		
	public AutoNav(Simulation simulation) {
			super();
			this.simulation = simulation;
		}
	//TODO test si la prochaine case contient un obstacle 
	public List<Coord> dumpNav(){
		List<Coord> ret = new ArrayList<Coord>();
		ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
		for(int i=0; i<10;i++){
			move();
			ret.add(new Coord(this.simulation.getCoordRobot().get(0),this.simulation.getCoordRobot().get(1)));
System.out.println(this.move);
		}
		return ret;
		
	}
	
	//TODO
	public List<Coord> searchObst(){
		return null;
		
	}


	private void move(){
		switch (this.move) {
		case UP:
			if ( !(freeWay) ){
				this.move = Move.RIGHT;
				this.freeWay = simulation.right();
			}
			else{
				this.freeWay=simulation.up();
			}
			break;
		case RIGHT:
			if ( !(freeWay) ){
				this.move = Move.DOWN;
				this.freeWay = simulation.down();
			}
			else{
				this.freeWay=simulation.right();
			}
			break;
		case DOWN:
			if ( !(freeWay) ){
				this.move = Move.LEFT;
				this.freeWay = simulation.left();
			}
			else{
				this.freeWay=simulation.down();
			}
			break;
		case LEFT:
			if ( !(freeWay) ){
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


	public static void main(String[] args) {
		
		
	}

}
