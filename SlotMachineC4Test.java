/**
 * Functional tests for Cycle 4.
 * Verifies the behavior of the new wheels and symbols.
 */
public class SlotMachineC4Test {

    public void runAll() {
    

        testNormalWheelSpin();
        testLeftyWheelCopy();
        testRebelWheelNoLock();
        testRebelWheelNoDelete();
        testEphemeralSymbolShrinks();
        testShySymbolTogglesVisibility();
        testGoldenSymbolGrows();

        
    }

    public void testNormalWheelSpin() {
        NormalWheel nw = new NormalWheel(25, 80);
        nw.addSymbol(new NormalSymbol("red"));
        nw.addSymbol(new NormalSymbol("blue"));
        nw.addSymbol(new NormalSymbol("green"));
        check(nw.getVisibleColor().equals("red"), "Starts at red");
        nw.spin();
        check(nw.getVisibleColor().equals("blue"), "After spin, blue");
        System.out.println("OK - testNormalWheelSpin");
    }

    public void testLeftyWheelCopy() {
        NormalWheel left = new NormalWheel(25, 80);
        left.addSymbol(new NormalSymbol("red"));
        left.addSymbol(new NormalSymbol("blue"));
        left.placeSymbol("blue");

        LeftyWheel lefty = new LeftyWheel(110, 80);
        lefty.addSymbol(new NormalSymbol("green"));
        lefty.addSymbol(new NormalSymbol("yellow"));
        lefty.setLeftWheel(left);
        lefty.spin();

        check(lefty.getVisibleColor().equalsIgnoreCase("blue"),
              "LeftyWheel must copy the left wheel state");
        System.out.println("OK - testLeftyWheelCopy");
    }

    public void testRebelWheelNoLock() {
        RebelWheel rw = new RebelWheel(25, 80);
        rw.lock();
        check(!rw.isLocked(), "RebelWheel cannot be locked");
        System.out.println("OK - testRebelWheelNoLock");
    }

    public void testRebelWheelNoDelete() {
        RebelWheel rw = new RebelWheel(25, 80);
        rw.addSymbol(new NormalSymbol("red"));
        rw.delSymbol("red");
        check(rw.getSymbols().size() == 1, "RebelWheel cannot delete symbols");
        System.out.println("OK - testRebelWheelNoDelete");
    }

    public void testEphemeralSymbolShrinks() {
        EphemeralSymbol e = new EphemeralSymbol("blue");
        int initial = e.getSize();
        e.onSpin();
        check(e.getSize() < initial, "EphemeralSymbol must shrink");
        System.out.println("OK - testEphemeralSymbolShrinks");
    }

    public void testShySymbolTogglesVisibility() {
        ShySymbol s = new ShySymbol("green");
        s.makeVisible();
        s.onSelect();
        check(!s.isVisible(), "ShySymbol must hide when selected");
        s.onSelect();
        check(s.isVisible(), "ShySymbol must show again when selected");
        System.out.println("OK - testShySymbolTogglesVisibility");
    }

    public void testGoldenSymbolGrows() {
        GoldenSymbol g = new GoldenSymbol();
        int initial = g.getSize();
        g.onSpin();
        check(g.getSize() > initial, "GoldenSymbol must grow when spun");
        System.out.println("OK - testGoldenSymbolGrows");
    }

    private void check(boolean condition, String message) {
        if (!condition) throw new AssertionError("FAIL: " + message);
    }
}
