package org.example;

import java.util.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final Biblioteca BIB;

    static {
        BIB = new Biblioteca();
    }

    private static Usuario usuarioActual = null;

    static void main() {
        int op;
        do {
            mostrarMenuPrincipal();
            op = leerEntero("Elegí una opción: ");
            switch (op) {
                case 1 -> registrar();
                case 2 -> iniciarSesion();
                case 3 -> System.out.println("👋 ¡Hasta luego!");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (op != 3);
    }


    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
    }

    private static void registrar() {
        System.out.print("Usuario: ");
        String u = SC.nextLine().trim();
        System.out.print("Contraseña: ");
        String p = SC.nextLine().trim();
        if (BIB.registrarUsuario(u, p)) {
            System.out.println("✅ Registrado con éxito.");
        } else {
            System.out.println("❌ No se pudo registrar (usuario existente o datos inválidos).");
        }
    }

    private static void iniciarSesion() {
        System.out.print("Usuario: ");
        String u = SC.nextLine().trim();
        System.out.print("Contraseña: ");
        String p = SC.nextLine().trim();
        usuarioActual = BIB.login(u, p);
        if (usuarioActual == null) {
            System.out.println("❌ Usuario o contraseña incorrectos.");
            return;
        }
        System.out.println("✅ Bienvenido, " + usuarioActual.getUsername() + "!");
        if (usuarioActual.isAdmin()) menuAdmin();
        else menuUsuario();
        usuarioActual = null;
    }


    private static void menuUsuario() {
        int op;
        do {
            System.out.println("\n===== MENÚ USUARIO =====");
            System.out.println("1. Agregar libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Buscar (título o autor contiene...)");
            System.out.println("4. Prestar libro");
            System.out.println("5. Devolver libro");
            System.out.println("6. Mis préstamos");
            System.out.println("7. Mi historial");
            System.out.println("8. Favoritos (ver/agregar/quitar)");
            System.out.println("9. Reseñar / Calificar libro");
            System.out.println("10. Ranking Top 5 prestamos");
            System.out.println("11. Sugerir libro");
            System.out.println("12. Estadísticas");
            System.out.println("13. Cerrar sesión");

            op = leerEntero("Elegí una opción: ");
            switch (op) {
                case 1 -> opcionAgregarLibro();
                case 2 -> BIB.listarLibros();
                case 3 -> opcionBuscarContiene();
                case 4 -> opcionPrestarLibro();
                case 5 -> opcionDevolverLibro();
                case 6 -> usuarioActual.verPrestamos();
                case 7 -> usuarioActual.verHistorial();
                case 8 -> submenuFavoritos();
                case 9 -> opcionResenarCalificar();
                case 10 -> BIB.rankingTop5();
                case 11 -> opcionSugerirLibro();
                case 12 -> BIB.estadisticas();
                case 13 -> System.out.println("👋 Sesión cerrada.");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (op != 13);
    }


    private static void menuAdmin() {
        int op;
        do {
            System.out.println("\n===== MENÚ ADMIN =====");
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Listar libros");
            System.out.println("4. Buscar (título/autor contiene)");
            System.out.println("5. Ranking Top 5 prestamos");
            System.out.println("6. Estadísticas");
            System.out.println("7. Bandeja de sugerencias (ver/aprobar/rechazar)");
            System.out.println("8. Cerrar sesión");

            op = leerEntero("Elegí una opción: ");
            switch (op) {
                case 1 -> opcionAgregarLibro();
                case 2 -> opcionEliminarLibro();
                case 3 -> BIB.listarLibros();
                case 4 -> opcionBuscarContiene();
                case 5 -> BIB.rankingTop5();
                case 6 -> BIB.estadisticas();
                case 7 -> submenuSugerenciasAdmin();
                case 8 -> System.out.println("👋 Sesión cerrada.");
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (op != 8);
    }


    private static void opcionAgregarLibro() {
        System.out.print("Título: ");
        String t = SC.nextLine();
        System.out.print("Autor: ");
        String a = SC.nextLine();
        BIB.agregarLibro(t, a);
    }

    private static void opcionEliminarLibro() {
        System.out.print("Título a eliminar: ");
        String t = SC.nextLine();
        BIB.eliminarLibro(t);
    }

    private static void opcionBuscarContiene() {
        System.out.print("Palabra clave (en título o autor): ");
        String q = SC.nextLine();
        List<Libro> encontrados = BIB.buscarLibrosContiene(q);
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron coincidencias.");
            return;
        }
        System.out.println("\nResultados:");
        for (Libro l : encontrados) System.out.println(" - " + l);
    }

    private static void opcionPrestarLibro() {
        System.out.print("Título a prestar: ");
        String t = SC.nextLine();
        Libro l = BIB.buscarLibroPorTitulo(t);
        if (l == null) { System.out.println("⚠ No existe ese libro."); return; }
        if (l.isPrestado()) { System.out.println("⚠ Ya está prestado."); return; }
        l.prestar();
        usuarioActual.tomarPrestado(l);
    }

    private static void opcionDevolverLibro() {
        System.out.print("Título a devolver: ");
        String t = SC.nextLine();
        Libro l = BIB.buscarLibroPorTitulo(t);
        if (l == null) { System.out.println("⚠ No existe ese libro."); return; }
        if (!l.isPrestado()) { System.out.println("⚠ Ese libro no estaba prestado."); return; }
        l.devolver();
        usuarioActual.devolver(l);
    }

    private static void opcionResenarCalificar() {
        System.out.print("Título: ");
        String t = SC.nextLine();
        Libro l = BIB.buscarLibroPorTitulo(t);
        if (l == null) { System.out.println("⚠ No existe ese libro."); return; }
        System.out.print("Escribí tu reseña: ");
        String r = SC.nextLine();
        int c = leerEntero("Calificación (1-5): ");
        l.agregarResena(r, c);
        System.out.println("Promedio actual: ⭐ " + String.format(Locale.US, "%.1f", l.promedioCalificacion()));
        System.out.println("¿Ver reseñas? (s/n)");
        String ver = SC.nextLine().trim().toLowerCase(Locale.ROOT);
        if (ver.equals("s")) l.mostrarResenas();
    }

    private static void submenuFavoritos() {
        int op;
        do {
            System.out.println("\n--- Favoritos ---");
            System.out.println("1. Ver favoritos");
            System.out.println("2. Agregar a favoritos");
            System.out.println("3. Quitar de favoritos");
            System.out.println("4. Volver");
            op = leerEntero("Elegí: ");
            switch (op) {
                case 1 -> usuarioActual.verFavoritos();
                case 2 -> {
                    System.out.print("Título: ");
                    String t = SC.nextLine();
                    Libro l = BIB.buscarLibroPorTitulo(t);
                    if (l == null) System.out.println("⚠ No existe ese libro.");
                    else usuarioActual.agregarFavorito(l);
                }
                case 3 -> {
                    System.out.print("Título: ");
                    String t = SC.nextLine();
                    Libro l = BIB.buscarLibroPorTitulo(t);
                    if (l == null) System.out.println("⚠ No existe ese libro.");
                    else usuarioActual.quitarFavorito(l);
                }
                case 4 -> { /* volver */ }
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (op != 4);
    }

    private static void opcionSugerirLibro() {
        System.out.print("Título sugerido: ");
        String t = SC.nextLine();
        if (t.isBlank()) {
            System.out.println("⚠ El título no puede ser vacío.");
            return;
        }
        usuarioActual.sugerir(t);
        BIB.agregarSugerenciaGlobal(t);
    }

    private static void submenuSugerenciasAdmin() {
        int op;
        do {
            System.out.println("\n--- Sugerencias (Admin) ---");
            System.out.println("1. Ver bandeja");
            System.out.println("2. Aprobar sugerencia");
            System.out.println("3. Rechazar sugerencia");
            System.out.println("4. Volver");
            op = leerEntero("Elegí: ");
            switch (op) {
                case 1 -> BIB.verBandejaSugerencias();
                case 2 -> {
                    BIB.verBandejaSugerencias();
                    int idx = leerEntero("Número a aprobar: ");
                    System.out.print("Autor (opcional, Enter para 'Autor desconocido'): ");
                    String autor = SC.nextLine();
                    BIB.aprobarSugerencia(idx, autor);
                }
                case 3 -> {
                    BIB.verBandejaSugerencias();
                    int idx = leerEntero("Número a rechazar: ");
                    BIB.rechazarSugerencia(idx);
                }
                case 4 -> {  }
                default -> System.out.println("⚠ Opción inválida.");
            }
        } while (op != 4);
    }


    private static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            String s = SC.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Ingresá un número válido.");
            }
        }
    }
}