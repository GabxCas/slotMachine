import java.util.ArrayList;
import java.util.HashMap;

/**
 * Solves the slot machine contest problem.
 * Given a slot machine with the same number of wheels and symbols,
 * it finds the minimum number of spins needed to reach a jackpot
 * (all wheels showing the same symbol).
 */
public class Solver {

    /**
     * Solves the slot machine contest problem and returns the minimum number
     * of spins required to reach a jackpot.
     *
     * @param machine slot machine to solve (must remain invisible).
     * @return the minimum number of spins, or -1 if it is not possible.
     */
    public int solve(SlotMachine machine) {
        if (machine == null) {
            return -1;
        }
        int wheels = machine.getNumberOfWheels();
        int symbols = machine.getNumberOfDistinctSymbols();

        if (wheels == 0 || symbols == 0 || wheels != symbols) {
            return -1;
        }

        ArrayList<String> symbolList = machine.getDistinctSymbolList();
        int best = Integer.MAX_VALUE;

        for (String target : symbolList) {
            int total = 0;
            boolean possible = true;
            for (int w = 1; w <= wheels; w++) {
                int steps = spinsToReach(machine, w, target);
                if (steps < 0) {
                    possible = false;
                    break;
                }
                total += steps;
            }
            if (possible && total < best) {
                best = total;
            }
        }

        return (best == Integer.MAX_VALUE) ? -1 : best;
    }

    /**
     * Simulates the solution of the slot machine contest problem.
     * The machine must be visible so the user can see the wheels spinning.
     *
     * @param machine slot machine to simulate (must be visible).
     * @return the total number of spins performed, or -1 if it is not possible.
     */
    public int simulate(SlotMachine machine) {
        if (machine == null) {
            return -1;
        }
        int wheels = machine.getNumberOfWheels();
        int symbols = machine.getNumberOfDistinctSymbols();

        if (wheels == 0 || symbols == 0 || wheels != symbols) {
            return -1;
        }

        machine.makeVisible();

        ArrayList<String> symbolList = machine.getDistinctSymbolList();
        String bestTarget = null;
        int bestTotal = Integer.MAX_VALUE;

        for (String target : symbolList) {
            int total = 0;
            boolean possible = true;
            for (int w = 1; w <= wheels; w++) {
                int steps = spinsToReach(machine, w, target);
                if (steps < 0) {
                    possible = false;
                    break;
                }
                total += steps;
            }
            if (possible && total < bestTotal) {
                bestTotal = total;
                bestTarget = target;
            }
        }

        if (bestTarget == null) {
            return -1;
        }

        for (int w = 1; w <= wheels; w++) {
            int steps = spinsToReach(machine, w, bestTarget);
            machine.spin(w, steps);
        }

        return bestTotal;
    }

    /**
     * Returns the minimum number of spins needed for a given wheel
     * to show the target symbol. Returns -1 if it is not possible.
     */
    private int spinsToReach(SlotMachine machine, int wheel, String target) {
        int max = machine.getWheelSymbolCount(wheel);
        if (max <= 0) {
            return -1;
        }
        for (int s = 0; s < max; s++) {
            if (machine.wouldShow(wheel, s, target)) {
                return s;
            }
        }
        return -1;
    }
}
