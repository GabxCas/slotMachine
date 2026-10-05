/**
 * Shared tests for Cycle 3.
 * They verify functionalities that must remain correct when integrating changes.
 */
public class SlotMachineContestCTest {

    /**
     * Runs all shared tests.
     */
    public void runAll() {
        System.out.println("======================================");
        System.out.println("   SHARED TESTS - CYCLE 3");
        System.out.println("======================================");

        testMachineWithEqualWheelsAndSymbols();
        testDistinctSymbolsMethodAvailable();

        System.out.println("--------------------------------------");
        System.out.println("ALL SHARED TESTS PASSED");
        System.out.println("======================================");
    }

    /**
     * Verifies that the machine supports the same number of wheels and symbols.
     */
    public void testMachineWithEqualWheelsAndSymbols() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red", "normal");
        machine.addSymbol(1, "blue", "normal");

        machine.addSymbol(2, "red", "normal");
        machine.addSymbol(2, "blue", "normal");

        check(machine.getNumberOfWheels() == 2, "There should be 2 wheels.");
        check(machine.getNumberOfDistinctSymbols() == 2,
              "There should be 2 distinct symbols.");

        System.out.println("OK - testMachineWithEqualWheelsAndSymbols");
    }

    /**
     * Verifies that distinctSymbols is available and returns a valid value.
     */
    public void testDistinctSymbolsMethodAvailable() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();

        machine.addWheel(1);
        machine.addSymbol(1, "red", "normal");

        check(machine.distinctSymbols() == 1,
              "distinctSymbols should return 1.");

        System.out.println("OK - testDistinctSymbolsMethodAvailable");
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
