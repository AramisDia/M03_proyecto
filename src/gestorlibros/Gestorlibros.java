/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorlibros;

/**
 *
 * @author Aramis Díazun sencillo programa con el cual se puede gestionar libros teniendo distintas funciones como:
 * 1.Agregar Libros
 * 2.Eliminar Libros 
 * 3.Buscar Libro
 * 4.Listar todos los libros anteriormente guardados
 * 5.Salir (cuando esta sea ejecutada el programa se cerrará)
 * Esta aplicación se basa en 
 */
import java.io.*;
import java.util.Scanner;

public class Gestorlibros {
    // constantes que definen el nombre del archivo y el limite maximo de libros
    private static final String FILE_NAME = "libros.csv";
    private static final int MAX_LIBROS = 100;

    // matriz donde se almacenaran los libros (titulo, autor, año)
    private static String[][] libros = new String[MAX_LIBROS][3];
    private static int contadorLibros = 0; // contador para el numero de libros

    public static void main(String[] args) {
        // intentar cargar los libros desde el archivo al iniciar el programa
        try {
            cargarLibros();
        } catch (Exception e) {
            System.out.println("Error al cargar los libros: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // bucle principal del programa hasta que el usuario decida salir
        while (!salir) {
            // mostrar el menu de opciones
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Buscar libro");
            System.out.println("4. Listar libros");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opcion: ");
            
            try {
                // leer la opcion del usuario
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                // seleccionar la accion correspondiente segun la opcion
                switch (opcion) {
                    case 1:
                        agregarLibro(scanner); // agrega un libro
                        break;
                    case 2:
                        eliminarLibro(scanner); // elimina un libro
                        break;
                    case 3:
                        buscarLibro(scanner); // busca un libro por titulo
                        break;
                    case 4:
                        listarLibros(); // lista todos los libros
                        break;
                    case 5:
                        salir = true;
                        guardarLibros(); // guarda los libros al salir
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // limpiar entrada incorrecta
            }
        }

        scanner.close(); // cerrar el scanner al finalizar el programa
    }

    // metodo para agregar un libro con validacion de titulo duplicado y vacio
    private static void agregarLibro(Scanner scanner) {
        // verificar si se alcanzo el limite de libros
        if (contadorLibros >= MAX_LIBROS) {
            System.out.println("No se pueden agregar mas libros.");
            return;
        }

        try {
            System.out.print("Titulo: ");
            String titulo = scanner.nextLine().trim(); // eliminar espacios al principio y al final

            // verificar si el titulo esta vacio
            if (titulo.isEmpty()) {
                System.out.println("Error: El titulo no puede estar vacio.");
                return;
            }

            // verificar si ya existe un libro con el mismo titulo
            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    System.out.println("Error: Ya existe un libro con ese titulo.");
                    return;
                }
            }

            // leer autor y año del libro
            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Año: ");
            String año = scanner.nextLine();

            // almacenar el libro 
            libros[contadorLibros][0] = titulo;
            libros[contadorLibros][1] = autor;
            libros[contadorLibros][2] = año;
            contadorLibros++; // incrementar el contador de libros

            System.out.println("Libro agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar el libro: " + e.getMessage());
        }
    }

    // metodo para eliminar un libro por titulo
    private static void eliminarLibro(Scanner scanner) {
        try {
            System.out.print("Titulo del libro a eliminar: ");
            String titulo = scanner.nextLine();
            boolean encontrado = false;

            // buscar el libro en la lista
            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    // desplazar los libros restantes hacia adelante para llenar el espacio vacio
                    for (int j = i; j < contadorLibros - 1; j++) {
                        libros[j][0] = libros[j + 1][0];
                        libros[j][1] = libros[j + 1][1];
                        libros[j][2] = libros[j + 1][2];
                    }
                    contadorLibros--; // disminuir el contador de libros
                    encontrado = true;
                    System.out.println("Libro eliminado.");
                    break;
                }
            }

            // si el libro no fue encontrado
            if (!encontrado) {
                System.out.println("Libro no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        }
    }

    // metodo para buscar un libro por su titulo
    private static void buscarLibro(Scanner scanner) {
        try {
            System.out.print("Titulo del libro a buscar: ");
            String titulo = scanner.nextLine();
            boolean encontrado = false;

            // buscar el libro en la lista
            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    System.out.println("Libro encontrado:");
                    System.out.println("Titulo: " + libros[i][0]);
                    System.out.println("Autor: " + libros[i][1]);
                    System.out.println("Año: " + libros[i][2]);
                    encontrado = true;
                    break;
                }
            }

            // si el libro no fue encontrado
            if (!encontrado) {
                System.out.println("Libro no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }
    }

    // metodo para listar todos los libros almacenados
    private static void listarLibros() {
        try {
            // verificar si hay libros registrados
            if (contadorLibros == 0) {
                System.out.println("No hay libros registrados.");
            } else {
                System.out.println("Lista de libros:");
                for (int i = 0; i < contadorLibros; i++) {
                    System.out.println((i + 1) + ". Titulo: " + libros[i][0] + ", Autor: " + libros[i][1] + ", Año: " + libros[i][2]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los libros: " + e.getMessage());
        }
    }

    // metodo para cargar los libros desde un archivo csv
    private static void cargarLibros() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null && contadorLibros < MAX_LIBROS) {
                // leer cada linea y separarla por comas
                String[] data = line.split(",");
                if (data.length == 3) {
                    // almacenar los datos 
                    libros[contadorLibros][0] = data[0];
                    libros[contadorLibros][1] = data[1];
                    libros[contadorLibros][2] = data[2];
                    contadorLibros++;
                }
            }
        } catch (FileNotFoundException e) {
          
            System.out.println("Error al leer el archivo: " + e.getMessage());
           
        }
    }

    // metodo para guardar los libros en un archivo csv
    private static void guardarLibros() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // escribir cada libro en una linea del archivo
            for (int i = 0; i < contadorLibros; i++) {
                writer.write(libros[i][0] + "," + libros[i][1] + "," + libros[i][2]);
                writer.newLine();
            }
            System.out.println("Libros guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los libros: " + e.getMessage());
        }
    }
}
