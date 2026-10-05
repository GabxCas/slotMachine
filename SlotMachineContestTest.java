/**
 * Unit tests for Cycle 3.
 * Verifies the SlotMachine used as a testing tool for the contest.
 */
public class SlotMachineContestTest {

    /**
     * Runs all Cycle 3 tests.
     */
    public void runAll() {
        System.out.println("======================================");
        System.out.println("     SLOT MACHINE TESTS - CYCLE 3");
        System.out.println("======================================");

        testCreateMachineWithEqualWheelsAndSymbols();
        testDistinctSymbolsCount();
        testSpinStepsReachesTarget();
        testSolveSimpleCase();
        testSolveImpossibleCase();

        System.out.println("--------------------------------------");
        System.out.println("ALL CYCLE 3 TESTS PASSED");
        System.out.println("======================================");
    }

    /**
     * Verifies that a machine can be created with the same number
     * of wheels and symbols.
     */
    public void testCreateMachineWithEqualWheelsAndSymbols() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(1, "blue", "normal");
        machine.addSymbol(1, "green", "normal");

        machine.addSymbol(2, "red", "normal");
        machine.addSymbol(2, "blue", "normal");
        machine.addSymbol(2, "green", "normal");

        machine.addSymbol(3, "red", "normal");
        machine.addSymbol(3, "blue", "normal");
        machine.addSymbol(3, "green", "normal");

        check(machine.ok(), "Machine creation should be valid.");
        check(machine.getNumberOfWheels() == 3, "There should be 3 wheels.");
        check(machine.getNumberOfDistinctSymbols() == 3,
              "There should be 3 distinct symbols.");

        System.out.println("OK - testCreateMachineWithEqualWheelsAndSymbols");
    }

    /**
     * Verifies that distinctSymbols returns the correct count.
     */
    public void testDistinctSymbolsCount() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(1, "blue", "normal");

        machine.addSymbol(2, "red", "normal");
        machine.addSymbol(2, "blue", "normal");

        machine.addSymbol(3, "red", "normal");
        machine.addSymbol(3, "blue", "normal");

        check(machine.distinctSymbols() == 2,
              "There should be 2 distinct symbols.");

        System.out.println("OK - testDistinctSymbolsCount");
    }

    /**
     * Verifies that spinning a wheel a given number of steps
     * reaches the expected target symbol.
     */
    public void testSpinStepsReachesTarget() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(1, "blue", "normal");
        machine.addSymbol(1, "green", "normal");

        machine.spin(1, 2);

        check(machine.ok(), "spin(1,2) should be valid.");
        check(machine.configuration()[0].equals("green"),
              "After 2 spins the wheel should show green.");

        System.out.println("OK - testSpinStepsReachesTarget");
    }

    /**
     * Verifies that Solver returns a valid minimum number of spins
     * for a solvable machine.
     */
    public void testSolveSimpleCase() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(1, "blue", "normal");
        machine.addSymbol(1, "green", "normal");

        machine.addSymbol(2, "red", "normal");
        machine.addSymbol(2, "blue", "normal");
        machine.addSymbol(2, "green", "normal");

        machine.addSymbol(3, "red", "normal");
        machine.addSymbol(3, "blue", "normal");
        machine.addSymbol(3, "green", "normal");

        Solver solver = new Solver();
        int result = solver.solve(machine);

        check(result >= 0, "Solver should find a solution.");

        System.out.println("OK - testSolveSimpleCase");
    }

    /**
     * Verifies that Solver returns -1 for a machine that cannot be solved.
     */
    public void testSolveImpossibleCase() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(2, "red", "normal");

        Solver solver = new Solver();
        int result = solver.solve(machine);

        check(result == -1,
              "Solver should return -1 when wheels and symbols differ.");

        System.out.println("OK - testSolveImpossibleCase");
    }

    /**
     * Checks a condition.
     */
    private void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAIL: " + message);
        }
    }
}
