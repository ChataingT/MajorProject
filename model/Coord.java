package Simulation.model;


import java.io.Serializable;


/**
 * @author francoise.perrin
 *
 */
public class Coord implements Serializable, Comparable<Coord> {
	
	public int x, y;
	
	/**
	 * @param x
	 * @param y
	 */
	public Coord(int x, int y) {
		this.x = x; 
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true si les coordonn�es sont valides (dans un plateau de 8*8)
	 */
	public static boolean coordonnees_valides(int x, int y){
		return ( (x<=7) && (x>=0) && (y<=7) && (y>=0) );
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public static Coord add(Coord a, Coord b){
		Coord c = new Coord(0, 0);
		c.x = a.x + b.x;
		c.y = a.y + b.y;
		return c;
	}
	
	public static Coord add(Coord a, int i){
		Coord c = new Coord(0, 0);
		if(i==0){
			c = Coord.add(a, new Coord(-1,0));
		}
		else if(i==1){
			c = Coord.add(a, new Coord(0,1));
		}
		else if(i==2){
			c = Coord.add(a, new Coord(1,0));
		}
		else{
			c = Coord.add(a, new Coord(0,-1));
		}

		return c;
	}

	@Override
	public int compareTo(Coord arg0) {
		int ret = 0;
		if (this.x == arg0.x){
			ret = 0;
		}
		else if (this.x > arg0.x){
			ret = 1;
		}
		else {
			ret = -1;
		}
		return ret;
	}
	

}
