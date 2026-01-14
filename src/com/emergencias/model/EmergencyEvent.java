package com.emergencias.model;

public class EmergencyEvent {

        private String tipo;
        private String ubicacion;
        private UserData userData;

    public EmergencyEvent(String tipo, String ubicacion) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.userData = null; // Inicializa los datos del usuario como nulos
    }

        public EmergencyEvent(String tipo, String ubicacion, UserData userData) {
            this.tipo = tipo;
            this.ubicacion = ubicacion;
            this.userData = userData;
        }

        // Método getter para obtener el tipo de emergencia
        public String getTipo() {
            return tipo;
        }

        // Método getter para obtener la ubicación
        public String getUbicacion() {
            return ubicacion;
        }

        public UserData getUserData() {
            return userData;
        }

        @Override
        public String toString() {
            // Definimos una cadena para los datos de contacto
            String datosContacto;

            // Si userData NO es nulo, lo incluimos
            if (this.userData != null) {
                datosContacto = "\n--- Datos de Contacto ---\n" + userData.toString();
            } else {
                // Si userData ES nulo (como cuando lo crea el detector), mostramos un mensaje seguro
                datosContacto = "\n--- Datos de Contacto: No disponibles (Evento Generado por Detector) ---";
            }

            return "Emergencia: Tipo=" + tipo + ", Ubicacion=" + ubicacion + datosContacto;
        }
        
        // --- MÉTODOS AÑADIDOS PARA RESOLVER ERRORES DE COMPILACIÓN ---
        public String getDescription() {
            // Este método se usa en EmergencyLogger.java
            return this.tipo;
        }

        public String getLocation() {
            // Este método se usa en EmergencyLogger.java
            return this.ubicacion;
        }
        // -----------------------------------------------------------

}
