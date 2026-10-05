/**
 * Pruebas compartidas del Ciclo 2.
 *
 * Estas pruebas verifican funcionalidades que deben
 * mantenerse correctas al integrar los cambios del ciclo.
 */
public class SlotMachineCC2Test {

    /**
     * Ejecuta todas las pruebas compartidas.
     */
    public void runAll() {
        System.out.println("======================================");
        System.out.println("   PRUEBAS COMPARTIDAS - CICLO 2");
        System.out.println("======================================");

        testSwapYConfiguracion();
        testBloqueoYSpin();

        System.out.println("--------------------------------------");
        System.out.println("TODAS LAS PRUEBAS COMPARTIDAS PASARON");
        System.out.println("======================================");
    }

    /**
     * Prueba compartida 1:
     * verifica que swap modifica correctamente la configuracion
     * y que la configuracion final puede consultarse.
     */
    public void testSwapYConfiguracion() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");

        String[] inicial = machine.configuration();

        check(
            inicial[0].equals("red") &&
            inicial[1].equals("blue") &&
            inicial[2].equals("green"),
            "La configuracion inicial no es correcta."
        );

        machine.swap(1, 3);

        check(
            machine.ok(),
            "El intercambio deberia realizarse correctamente."
        );

        String[] finalConfig = machine.configuration();

        check(
            finalConfig[0].equals("green") &&
            finalConfig[1].equals("blue") &&
            finalConfig[2].equals("red"),
            "La configuracion despues del swap no es correcta."
        );

        System.out.println("OK - testSwapYConfiguracion");
    }

    /**
     * Prueba compartida 2:
     * verifica que una rueda bloqueada no gira y que,
     * despues de desbloquearla, puede volver a girar.
     */
    public void testBloqueoYSpin() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.lock(1);

        String[] bloqueada = machine.configuration();

        machine.spin(1);

        String[] despuesBloqueo = machine.configuration();

        check(
            mismaConfiguracion(bloqueada, despuesBloqueo),
            "Una rueda bloqueada no deberia girar."
        );

        machine.unlock(1);

        check(
            machine.ok(),
            "La rueda deberia desbloquearse correctamente."
        );

        machine.spin(1);

        String[] despuesDesbloqueo = machine.configuration();

        check(
            !mismaConfiguracion(bloqueada, despuesDesbloqueo),
            "Una rueda desbloqueada deberia poder girar."
        );

        System.out.println("OK - testBloqueoYSpin");
    }

    /**
     * Crea una maquina en modo invisible.
     */
    private SlotMachine crearMaquina() {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        return machine;
    }

    /**
     * Compara dos configuraciones.
     */
    private boolean mismaConfiguracion(String[] a, String[] b) {
        if (a == null || b == null) {
            return a == b;
        }

        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] == null && b[i] != null) {
                return false;
            }

            if (a[i] != null && !a[i].equals(b[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica una condicion.
     */
    private void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FALLO: " + message);
        }
    }
}