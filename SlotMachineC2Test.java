/**
 * Pruebas funcionales del Ciclo 2 de SlotMachine.
 *
 * Se prueban:
 * 1. Intercambio de ruedas (swap)
 * 2. Bloqueo y desbloqueo de ruedas
 * 3. Giro de una rueda por un numero de pasos
 * 4. Configuracion de la maquina
 * 5. Comportamientos invalidos
 *
 * Todas las pruebas se realizan con la maquina invisible.
 */
public class SlotMachineC2Test {

    /**
     * Ejecuta todas las pruebas del Ciclo 2.
     */
    public void runAll() {
        System.out.println("======================================");
        System.out.println("     PRUEBAS SLOT MACHINE - CICLO 2");
        System.out.println("======================================");

        testSwap();
        testSwapInvalido();

        testLock();
        testUnlock();
        testLockInvalido();

        testSpinSteps();
        testSpinStepsInvalido();

        testSpinConRuedaBloqueada();

        testSetSymbols();
        testSetSymbolsInvalido();

        testSpinAllConRuedaBloqueada();

        System.out.println("--------------------------------------");
        System.out.println("TODAS LAS PRUEBAS DEL CICLO 2 PASARON");
        System.out.println("======================================");
    }

    /**
     * Prueba que swap intercambia correctamente dos ruedas.
     */
    public void testSwap() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");

        check(machine.ok(), "No se pudieron crear las ruedas o simbolos.");

        String[] antes = machine.configuration();

        check(
            antes[0].equals("red") &&
            antes[1].equals("blue") &&
            antes[2].equals("green"),
            "La configuracion inicial no es la esperada."
        );

        machine.swap(1, 3);

        check(machine.ok(), "swap(1,3) deberia ser valido.");

        String[] despues = machine.configuration();

        check(
            despues[0].equals("green") &&
            despues[1].equals("blue") &&
            despues[2].equals("red"),
            "swap no intercambio correctamente las ruedas."
        );

        System.out.println("OK - testSwap");
    }

    /**
     * Prueba que swap rechaza posiciones inexistentes.
     */
    public void testSwapInvalido() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        String[] antes = machine.configuration();

        machine.swap(1, 5);

        check(!machine.ok(), "swap con una rueda inexistente deberia fallar.");

        String[] despues = machine.configuration();

        check(
            mismaConfiguracion(antes, despues),
            "Un swap invalido no deberia modificar la configuracion."
        );

        System.out.println("OK - testSwapInvalido");
    }

    /**
     * Prueba que una rueda puede bloquearse.
     */
    public void testLock() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.lock(1);

        check(machine.ok(), "lock(1) deberia ser valido.");

        String[] antes = machine.configuration();

        machine.spin(1);

        String[] despues = machine.configuration();

        check(
            mismaConfiguracion(antes, despues),
            "Una rueda bloqueada no deberia girar."
        );

        System.out.println("OK - testLock");
    }

    /**
     * Prueba que una rueda puede desbloquearse.
     */
    public void testUnlock() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.lock(1);

        String[] bloqueada = machine.configuration();

        machine.unlock(1);

        check(machine.ok(), "unlock(1) deberia ser valido.");

        machine.spin(1);

        String[] despues = machine.configuration();

        check(
            !mismaConfiguracion(bloqueada, despues),
            "Una rueda desbloqueada deberia poder girar."
        );

        System.out.println("OK - testUnlock");
    }

    /**
     * Prueba que lock rechaza una rueda inexistente.
     */
    public void testLockInvalido() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.lock(5);

        check(!machine.ok(), "lock con una rueda inexistente deberia fallar.");

        System.out.println("OK - testLockInvalido");
    }

    /**
     * Prueba el giro de una rueda una cantidad determinada de pasos.
     */
    public void testSpinSteps() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");

        String[] antes = machine.configuration();

        check(
            antes[0].equals("red"),
            "La configuracion inicial deberia comenzar en red."
        );

        machine.spin(1, 2);

        check(machine.ok(), "spin(1,2) deberia ser valido.");

        String[] despues = machine.configuration();

        check(
            despues[0].equals("green"),
            "La rueda no avanzo correctamente 2 pasos."
        );

        System.out.println("OK - testSpinSteps");
    }

    /**
     * Prueba que spin por pasos rechaza una rueda inexistente.
     */
    public void testSpinStepsInvalido() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        String[] antes = machine.configuration();

        machine.spin(5, 2);

        check(
            !machine.ok(),
            "spin con una rueda inexistente deberia fallar."
        );

        String[] despues = machine.configuration();

        check(
            mismaConfiguracion(antes, despues),
            "Un spin invalido no deberia modificar la configuracion."
        );

        System.out.println("OK - testSpinStepsInvalido");
    }

    /**
     * Comprueba que una rueda bloqueada no gira aunque se soliciten pasos.
     */
    public void testSpinConRuedaBloqueada() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");

        machine.lock(1);

        String[] antes = machine.configuration();

        machine.spin(1, 2);

        String[] despues = machine.configuration();

        check(
            mismaConfiguracion(antes, despues),
            "Una rueda bloqueada no deberia girar con spin por pasos."
        );

        System.out.println("OK - testSpinConRuedaBloqueada");
    }

    /**
     * Prueba que setSymbols establece correctamente
     * la configuracion solicitada.
     */
    public void testSetSymbols() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "green");

        machine.addSymbol(3, "green");
        machine.addSymbol(3, "yellow");

        String[] nuevaConfiguracion = {
            "blue",
            "green",
            "yellow"
        };

        machine.setSymbols(nuevaConfiguracion);

        check(
            machine.ok(),
            "setSymbols deberia aceptar una configuracion valida."
        );

        String[] configuracion = machine.configuration();

        check(
            configuracion[0].equals("blue") &&
            configuracion[1].equals("green") &&
            configuracion[2].equals("yellow"),
            "setSymbols no establecio correctamente la configuracion."
        );

        System.out.println("OK - testSetSymbols");
    }

    /**
     * Prueba que setSymbols rechaza una configuracion
     * que contiene simbolos que no corresponden.
     */
    public void testSetSymbolsInvalido() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "green");

        String[] antes = machine.configuration();

        String[] configuracionInvalida = {
            "yellow",
            "green"
        };

        machine.setSymbols(configuracionInvalida);

        check(
            !machine.ok(),
            "setSymbols deberia rechazar un simbolo inexistente."
        );

        String[] despues = machine.configuration();

        check(
            mismaConfiguracion(antes, despues),
            "Una configuracion invalida no deberia modificar la maquina."
        );

        System.out.println("OK - testSetSymbolsInvalido");
    }

    /**
     * Prueba que spin() respeta las ruedas bloqueadas.
     */
    public void testSpinAllConRuedaBloqueada() {
        SlotMachine machine = crearMaquina();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.addSymbol(2, "green");
        machine.addSymbol(2, "yellow");

        machine.addSymbol(3, "blue");
        machine.addSymbol(3, "green");

        machine.lock(2);

        String[] antes = machine.configuration();

        machine.spin();

        String[] despues = machine.configuration();

        check(
            despues[1].equals(antes[1]),
            "La rueda bloqueada no deberia girar cuando se hace spin()."
        );

        check(
            !despues[0].equals(antes[0]) &&
            !despues[2].equals(antes[2]),
            "Las ruedas desbloqueadas deberian poder girar."
        );

        System.out.println("OK - testSpinAllConRuedaBloqueada");
    }

    /**
     * Crea una maquina nueva en modo invisible.
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