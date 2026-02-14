package models;

import com.google.gson.annotations.SerializedName;

public class CentroSalud {
    @SerializedName("Código")
    private String codigo;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Dirección")
    private String direccion;

    @SerializedName("Municipio")
    private String municipio;

    @SerializedName("Teléfono")
    private String telefono;

    @SerializedName("Latitud")
    private String latitud;

    @SerializedName("Longitud")
    private String longitud;

    // Constructor vacío (necesario para las librerías de JSON)
    public CentroSalud() {}

    // Getters para poder usar los datos después
    public String getNombre() { return nombre; }
    public String getMunicipio() { return municipio; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return "Centro: " + nombre + " en " + municipio;
    }
}