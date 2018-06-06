package javaSimulation;

/**
 * 
 * @author thibaut.chataing
 *
 */
public class Launcher {

	public static void main(String[] args) {
			Simulation sim = new Simulation(15, 15, 10,0);
			sim.right();
			sim.left();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.down();
			sim.left();
			sim.up();
			sim.right();
			sim.right();
			sim.down();
			sim.up();
			sim.right();
			sim.left();
			sim.left();
			sim.down();
			sim.down();
			sim.up();
			sim.affEnvironnement();
			sim.affView();
			sim.affRobotDiscoveredMap();
			System.out.println(sim.getDistanceTraveled());
			System.out.println(sim.getNbCmd());
			System.out.println(sim.getNbSeeingObst());
			System.out.println(sim.getNbMetObst());

	}

}
