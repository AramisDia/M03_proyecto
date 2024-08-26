/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorlibros;

/**
 *
 * @author arami
 */
import java.io.*;
import java.util.Scanner;

public class Gestorlibros {
    private static final String FILE_NAME = "libros.csv";
    private static final int MAX_LIBROS = 100;
    private static String[][] libros = new String[MAX_LIBROS][3];
    private static int contadorLibros = 0;

    public static void main(String[] args) {
        try {
            cargarLibros(); // cargar los libros al iniciar el programa
        } catch (Exception e) {
            System.out.println("Error al cargar los libros: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Buscar libro");
            System.out.println("4. Listar libros");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        agregarLibro(scanner);
                        break;
                    case 2:
                        eliminarLibro(scanner);
                        break;
                    case 3:
                        buscarLibro(scanner);
                        break;
                    case 4:
                        listarLibros();
                        break;
                    case 5:
                        salir = true;
                        guardarLibros(); // Guardar los libros antes de salir
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar entrada incorrecta
            }
        }

        scanner.close();
    }

    // Método para agregar un libro con validación de título duplicado y vacío
    private static void agregarLibro(Scanner scanner) {
        if (contadorLibros >= MAX_LIBROS) {
            System.out.println("No se pueden agregar más libros.");
            return;
        }

        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim(); // Trim elimina los espacios en blanco al principio y al final

            // Verificar si el título está vacío
            if (titulo.isEmpty()) {
                System.out.println("Error: El título no puede estar vacío.");
                return;
            }

            // Verificar si ya existe un libro con el mismo título
            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    System.out.println("Error: Ya existe un libro con ese título.");
                    return;
                }
            }

            System.out.print("Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Año: ");
            String año = scanner.nextLine();

            libros[contadorLibros][0] = titulo;
            libros[contadorLibros][1] = autor;
            libros[contadorLibros][2] = año;
            contadorLibros++;

            System.out.println("Libro agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar el libro: " + e.getMessage());
        }
    }

    // Método para eliminar un libro
    private static void eliminarLibro(Scanner scanner) {
        try {
            System.out.print("Título del libro a eliminar: ");
            String titulo = scanner.nextLine();
            boolean encontrado = false;

            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    // Desplazar los libros restantes hacia adelante
                    for (int j = i; j < contadorLibros - 1; j++) {
                        libros[j][0] = libros[j + 1][0];
                        libros[j][1] = libros[j + 1][1];
                        libros[j][2] = libros[j + 1][2];
                    }
                    contadorLibros--;
                    encontrado = true;
                    System.out.println("Libro eliminado.");
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Libro no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        }
    }

    // Método para buscar un libro
    private static void buscarLibro(Scanner scanner) {
        try {
            System.out.print("Título del libro a buscar: ");
            String titulo = scanner.nextLine();
            boolean encontrado = false;

            for (int i = 0; i < contadorLibros; i++) {
                if (libros[i][0].equalsIgnoreCase(titulo)) {
                    System.out.println("Libro encontrado:");
                    System.out.println("Título: " + libros[i][0]);
                    System.out.println("Autor: " + libros[i][1]);
                    System.out.println("Año: " + libros[i][2]);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Libro no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el libro: " + e.getMessage());
        }
    }

    // Método para listar todos los libros
    private static void listarLibros() {
        try {
            if (contadorLibros == 0) {
                System.out.println("No hay libros registrados.");
            } else {
                System.out.println("Lista de libros:");
                for (int i = 0; i < contadorLibros; i++) {
                    System.out.println((i + 1) + ". Título: " + libros[i][0] + ", Autor: " + libros[i][1] + ", Año: " + libros[i][2]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los libros: " + e.getMessage());
        }
    }

    // Método para cargar los libros desde un archivo
    private static void cargarLibros() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null && contadorLibros < MAX_LIBROS) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    libros[contadorLibros][0] = data[0];
                    libros[contadorLibros][1] = data[1];
                    libros[contadorLibros][2] = data[2];
                    contadorLibros++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            throw e;
        }
    }

    // Método para guardar los libros en un archivo
    private static void guardarLibros() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
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
