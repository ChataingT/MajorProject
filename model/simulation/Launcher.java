package Simulation.model.simulation;

public class Launcher {

	public static void main(String[] args) {
			Simulation sim = new Simulation(30, 30, 10,0);
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.down();
			sim.right();
			sim.affEnvironnement();
			sim.affView();
			sim.affRobotDiscoveredMap();
			System.out.println(sim.getDistanceTraveled());
			System.out.println(sim.getNbCmd());
			System.out.println(sim.getNbSeeingObst());
			System.out.println(sim.getNbMetObst());

	}

}
