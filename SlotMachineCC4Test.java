/**
 * Shared tests for Cycle 4.
 * They verify functionalities that must remain correct when integrating changes.
 */
public class SlotMachineCC4Test {

    public void runAll() {
      

        testThreeWheelTypes();
        testThreeSymbolTypes();

    }

    public void testThreeWheelTypes() {
        Wheel nw = new NormalWheel(25, 80);
        Wheel lw = new LeftyWheel(110, 80);
        Wheel rw = new RebelWheel(195, 80);
        check(nw != null && lw != null && rw != null, "There must be 3 wheel types");
        System.out.println("OK - testThreeWheelTypes");
    }

    public void testThreeSymbolTypes() {
        Symbol ns = new NormalSymbol("red");
        Symbol es = new EphemeralSymbol("blue");
        Symbol ss = new ShySymbol("green");
        check(ns != null && es != null && ss != null, "There must be 3 symbol types");
        System.out.println("OK - testThreeSymbolTypes");
    }

    private void check(boolean condition, String message) {
        if (!condition) throw new AssertionError("FAIL: " + message);
    }
}
