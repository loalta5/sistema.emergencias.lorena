package com.emergencias.detector;

import com.emergencias.model.EmergencyEvent;
import java.util.Scanner;

public class EmergencyDetector {

    private static final String UBICACION_FIJA = "38.267015, -0.700140 (Elche)";
    
    @SuppressWarnings("resource")
    public EmergencyEvent detectEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Módulo de Detección ---");
        System.out.println("¿Activar emergencia? (E para Emergencia, otra tecla para salir):");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("E")) {
            System.out.println("Emergencia confirmada.");
            //Creamos el evento usando la ubicación fija
            return new EmergencyEvent(
                    "General",
                    UBICACION_FIJA,
                    com.emergencias.AplicacionEmergencias.getDatosUsuario());
        } else {
            System.out.println("Activación cancelada o sin emergencia.");
            scanner.close();
            return null;
        }
    }

}
