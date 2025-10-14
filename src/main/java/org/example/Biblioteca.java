package org.example;
import java.util.*;

public class Biblioteca {
    private final Map<String, Usuario> usuarios;
    private final List<Libro> libros;
    private final List<String> bandejaSugerencias;

    public Biblioteca() {
        this.usuarios = new HashMap<>();
        this.libros = new ArrayList<>();
        this.bandejaSugerencias = new ArrayList<>();

        usuarios.put("admin", new Usuario("admin", "1234", true));

        libros.add(new Libro("Cien años de soledad", "García Márquez"));
        libros.add(new Libro("El principito", "Antoine de Saint-Exupéry"));
        libros.add(new Libro("Rayuela", "Julio Cortázar"));
        libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes"));
        libros.add(new Libro("1984", "George Orwell"));
        libros.add(new Libro("Orgullo y prejuicio", "Jane Austen"));
        libros.add(new Libro("Crimen y castigo", "Fiódor Dostoyevski"));
    }


    public boolean registrarUsuario(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) return false;
        if (usuarios.containsKey(username)) return false;
        usuarios.put(username, new Usuario(username, password, false));
        return true;
    }

    public Usuario login(String username, String password) {
        Usuario u = usuarios.get(username);
        if (u == null) return null;
        return u.validarPassword(password) ? u : null;
    }

    public void agregarLibro(String titulo, String autor) {
        if (titulo == null || titulo.isBlank() || autor == null || autor.isBlank()) {
            System.out.println("⚠ Título y autor no pueden estar vacíos.");
            return;
        }
        libros.add(new Libro(titulo, autor));
        System.out.println("📚 Libro agregado: \"" + titulo + "\" — " + autor);
    }

    public void eliminarLibro(String titulo) {
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                it.remove();
                System.out.println("🗑 Libro eliminado: " + titulo);
                return;
            }
        }
        System.out.println("⚠ No se encontró el libro: " + titulo);
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) return l;
        }
        return null;
    }

    public List<Libro> buscarLibrosContiene(String palabra) {
        List<Libro> res = new ArrayList<>();
        String p = palabra.toLowerCase(Locale.ROOT);
        for (Libro l : libros) {
            if (l.getTitulo().toLowerCase(Locale.ROOT).contains(p) ||
                    l.getAutor().toLowerCase(Locale.ROOT).contains(p)) {
                res.add(l);
            }
        }
        return res;
    }

    public void listarLibros() {
        System.out.println("\n=== Catálogo de libros ===");
        if (libros.isEmpty()) {
            System.out.println("No hay libros aún. ¡Agregá el primero!");
            return;
        }
        for (Libro l : libros) System.out.println(" - " + l);
    }

    public void rankingTop5() {
        System.out.println("\n=== Top 5: más prestados ===");
        if (libros.isEmpty()) { System.out.println("No hay datos."); return; }
        libros.stream()
                .sorted(Comparator.comparingInt(Libro::getVecesPrestado).reversed())
                .limit(5)
                .forEach(l -> System.out.println(" - " + l.getTitulo() + " | Veces prestado: " + l.getVecesPrestado()));
    }

    public void estadisticas() {
        int total = libros.size();
        long prestados = libros.stream().filter(Libro::isPrestado).count();
        long disponibles = total - prestados;
        System.out.println("\n=== Estadísticas ===");
        System.out.println("Total de libros: " + total);
        System.out.println("Disponibles: " + disponibles);
        System.out.println("Prestados: " + prestados);
    }

    public void agregarSugerenciaGlobal(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            System.out.println("⚠ La sugerencia no puede ser vacía.");
            return;
        }
        bandejaSugerencias.add(titulo.trim());
        System.out.println("📥 Sugerencia enviada a la bandeja de admin.");
    }

    public void verBandejaSugerencias() {
        System.out.println("\n=== Bandeja de Sugerencias ===");
        if (bandejaSugerencias.isEmpty()) {
            System.out.println("Vacía.");
            return;
        }
        for (int i = 0; i < bandejaSugerencias.size(); i++) {
            System.out.println((i + 1) + ". " + bandejaSugerencias.get(i));
        }
    }

    public void aprobarSugerencia(int indice, String autor) {
        if (indice < 1 || indice > bandejaSugerencias.size()) {
            System.out.println("⚠ Índice inválido.");
            return;
        }
        String titulo = bandejaSugerencias.remove(indice - 1);
        agregarLibro(titulo, autor == null || autor.isBlank() ? "Autor desconocido" : autor);
        System.out.println("✅ Sugerencia aprobada y agregada: " + titulo);
    }

    public void rechazarSugerencia(int indice) {
        if (indice < 1 || indice > bandejaSugerencias.size()) {
            System.out.println("⚠ Índice inválido.");
            return;
        }
        String t = bandejaSugerencias.remove(indice - 1);
        System.out.println("🗑 Sugerencia rechazada: " + t);
    }
}
