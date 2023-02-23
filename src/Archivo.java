public class Archivo {
    private String nombre;
    private String ruta;
    private String texto;

    public Archivo(String nombre, String ruta, String texto) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
