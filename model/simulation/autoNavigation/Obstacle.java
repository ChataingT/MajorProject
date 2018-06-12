package Simulation.model.simulation.autoNavigation;

import java.util.ArrayList;
import java.util.List;

import Simulation.model.Coord;

public class Obstacle {
	
	private Integer id;
	private List<Coord> listCoordObst;
	private List<Coord> listCoordMove;
	private Boolean mapped;
	
	
	public Obstacle(Coord c, Coord m){
		this.id = c.x*c.y;
		this.listCoordMove = new ArrayList<Coord>();
		this.listCoordMove.add(c);

		this.listCoordObst = new ArrayList<Coord>();
		this.listCoordObst.add(m);

		this.mapped = false;
	}


	public Integer getId() {
		return id;
	}

	private List<Coord> getListCoordObst() {
		return listCoordObst;
	}


	public List<Coord> getListCoordMove() {
		return listCoordMove;
	}
	
	public void setMapped(Boolean b){
		this.mapped = b;
	}
	
	public Boolean getMapped(){
		return this.mapped;
	}
	
	public void addListCoordObst(Coord c){
		if( !(listCoordObst.contains(c)) ){
			this.listCoordObst.add(c);
		}
	}
	
	public void addListCoordMove(Coord c){
		this.listCoordMove.add(c);
	}
	
	public Boolean testIsKnownObst( Coord obst){
		return this.listCoordObst.contains(obst);
	}
	
	public Boolean isInListCoordObst( Coord c){
		Boolean ret = false;
		ret = this.listCoordObst.contains(c);
		return ret;
	}
	
	public List<Coord> getCoordMove(Coord start){
		List<Coord> ret = new ArrayList<Coord>();
		int iStart = this.listCoordMove.indexOf(start)+1;
		int iEnd = this.getEndCoord(start, iStart);
		if (iStart <= iEnd){
			ret = this.listCoordMove.subList(iStart, iEnd);
		}
		else {
			List<Coord> l = new ArrayList<Coord>(this.listCoordMove.subList(iStart, this.listCoordMove.size()-1));
			l.addAll(this.listCoordMove.subList(0, iEnd));
			ret = l;
		}
		return ret;
		
	}
	
	private int getEndCoord(Coord c, int start){
		int ret = 0;
		
		List<Coord> l1 = new ArrayList<Coord>();
		List<Coord> l2 = new ArrayList<Coord>();

		if (start<this.listCoordMove.size()){
			l1 = new ArrayList<Coord>(this.listCoordMove.subList(start, this.listCoordMove.size()));}
		l1.addAll(this.listCoordMove.subList(1, start));
		for(Coord coord : l1){
			if(c.y == coord.y){
				l2.add(coord);
			}
		}
		Coord max = c;
		Coord min = c;
		for(Coord coord : l2){
			if(coord.x > max.x){
				max = coord;
			}
			if (coord.x < min.x){
				min = coord;
			}
		}
		if(c.equals(max)){
			ret = this.listCoordMove.indexOf(min)+1;
		}
		else {
			ret = this.listCoordMove.indexOf(max)+1;
		}
		return ret;
	}
	
	public static void main(String[] args) {
		Coord c = new Coord(1,1);
		Coord cur = Coord.add(c,2);
		Obstacle o = new Obstacle(c, cur);
		cur = Coord.add(cur,2);
		for(int i =0; i<2; i++){
			o.addListCoordObst(cur);
			cur = Coord.add(cur,2);
		}

		cur = c;
		o.addListCoordMove(cur = Coord.add(cur, 3));
		for(int i =0; i<4; i++){
			o.addListCoordMove(cur = Coord.add(cur, 2));
		}
		o.addListCoordMove(cur = Coord.add(cur, 1));
		o.addListCoordMove(cur = Coord.add(cur, 1));
		for(int i =0; i<4; i++){
			o.addListCoordMove(cur = Coord.add(cur, 0));
		}
		o.addListCoordMove(cur = Coord.add(cur, 3));
		System.out.println("Obst" + o.getListCoordObst().toString());
		System.out.println("Move" + o.getListCoordMove().toString());
		
		System.out.println("escape" + o.getCoordMove(new Coord(5, 1)).toString());


	}
	
	

}
