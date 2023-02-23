/* Esta clase representa el programa principal que busca una palabra en archivos de texto dentro de una carpeta
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    // Lista de archivos que se van a leer
    ArrayList<Archivo> archivos = new ArrayList<>();

    /**
     * Método principal que se encarga de ejecutar el programa
     *
     * @param args Argumentos que se pasan al programa
     */
    public static void main(String[] args) {
        Main main = new Main();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta de la carpeta: ");
        String ruta = scanner.nextLine();
        System.out.println("Ingrese la palabra a buscar: ");
        String palabra = scanner.nextLine();

        try {
            main.leerCarpeta(ruta);

            main.contarPalabra(palabra);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    /**
     * Método que crea un nuevo archivo a partir de la ruta especificada
     *
     * @param ruta Ruta del archivo a leer
     * @throws IOException Si el archivo no es un archivo de texto válido
     */
    public void crearArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);

        // Verificar la extensión del archivo
        String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1);
        if (!(extension.equals("txt") || extension.equals("xml") || extension.equals("json") || extension.equals("csv"))) {
            throw new IOException("El archivo " + archivo.getName() + " no es un archivo de texto válido.");
        }

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea);
            sb.append(System.lineSeparator());
        }
        br.close();

        Archivo nuevoArchivo = new Archivo(archivo.getName(), ruta, sb.toString());
        archivos.add(nuevoArchivo);
    }

    /**
     * Método que lee todos los archivos de una carpeta y los agrega a la lista de archivos
     *
     * @param rutaCarpeta Ruta de la carpeta a leer
     * @throws IOException Si la carpeta no existe o no se encuentran archivos en la carpeta
     */
    public void leerCarpeta(String rutaCarpeta) throws IOException {
        File carpeta = new File(rutaCarpeta);
        if (!carpeta.isDirectory()) {
            throw new IOException("No se encontró la carpeta indicada.");
        }
        File[] archivosCarpeta = carpeta.listFiles();
        if (archivosCarpeta == null) {
            throw new IOException("No se encontraron archivos en la carpeta indicada.");
        } else {
            for (File archivo : archivosCarpeta) {
                if (!archivo.isDirectory()) {
                    try {
                        crearArchivo(archivo.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Error al procesar el archivo " + archivo.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Método que cuenta cuántas veces aparece una palabra en todos los archivos de la lista
     *
     * @param palabra Palabra a buscar
     */
    public void contarPalabra(String palabra) {
        int total = 0;
        Pattern pattern = Pattern.compile("\\b" + palabra + "\\b");
        for (Archivo archivo : archivos) {
            int contador = 0;
            String texto = archivo.getTexto();
            Matcher matcher = pattern.matcher(texto);
            while (matcher.find()) {
                contador++;
            }
            System.out.println("La palabra \"" + palabra + "\" aparece " + contador + " veces en el archivo " + archivo.getNombre());
            total += contador;
        }
        System.out.println("La palabra \"" + palabra + "\" aparece " + total + " veces en total");
    }
}
