package com.emergencies;

import com.emergencias.model.UserData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionSistema {

    private static UserData datosUsuario;
    private static final String ARCHIVO_USUARIO = "user_data.txt";

    /**
     * Método público y estático para que otras clases puedan acceder a la
     * instancia de UserData que ha sido cargada.
     */
    public static UserData getDatosUsuario() {
        return datosUsuario;
    }

    /**
     * Lee el archivo 'user_data.txt', procesa cada línea y crea el objeto UserData.
     */
    public static void cargarDatosUsuario() {
        String archivo = ARCHIVO_USUARIO;
        Map<String, String> dataMap = new HashMap<>(); // Mapa temporal para guardar los pares Clave:Valor

        // El bloque try-with-resources asegura que el BufferedReader se cierra automáticamente.
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Lee el archivo línea por línea hasta el final (null)
            while ((linea = br.readLine()) != null) {
                // Solo procesa líneas que contienen los dos puntos
                if (linea.contains(":")) {
                    String[] partes = linea.split(":", 2); // Divide la línea en 2 partes
                    String clave = partes[0].trim();
                    String valor = partes[1].trim();
                    dataMap.put(clave, valor);
                }
            }

            // Verifica que se hayan encontrado las 4 claves esperadas
            if (dataMap.containsKey("Nombre") && dataMap.containsKey("Telefono") &&
                    dataMap.containsKey("TipoSangre") && dataMap.containsKey("ContactoEmergencia")) {

                // Crea la instancia final de UserData con los valores extraídos del mapa
                datosUsuario = new UserData(
                        dataMap.get("Nombre"),
                        dataMap.get("Telefono"),
                        dataMap.get("TipoSangre"),
                        dataMap.get("ContactoEmergencia")
                );
            } else {
                System.err.println("Error de formato: El archivo '" + archivo + "' no contiene todos los campos requeridos.");
            }

        } catch (IOException e) {
            // Se ejecuta si el archivo no se encuentra o hay un problema de lectura
            System.err.println("Error I/O: No se pudo leer el archivo '" + archivo + "'. Asegúrese de que esté en la raíz del proyecto.");
            datosUsuario = null; // Indica que la carga falló
        }
    }
}
