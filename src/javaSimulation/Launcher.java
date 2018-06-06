package javaSimulation;

public class Launcher {

	public static void main(String[] args) {
			Simulation sim = new Simulation(30, 30, 1,1);
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

	}

}
