package org.example;
import java.util.*;

public class Usuario {
    private final String username;
    private final String password;
    private final boolean admin;

    private final List<Libro> prestamosActuales;
    private final List<Libro> historialPrestamos;
    private final Set<Libro> favoritos;
    private final List<String> sugerencias;

    public Usuario(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.prestamosActuales = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
        this.favoritos = new HashSet<>();
        this.sugerencias = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public boolean isAdmin() { return admin; }
    public boolean validarPassword(String pass) { return password.equals(pass); }

    public void tomarPrestado(Libro l) {
        prestamosActuales.add(l);
        historialPrestamos.add(l);
    }

    public void devolver(Libro l) {
        prestamosActuales.remove(l);
    }

    public void verPrestamos() {
        System.out.println("\n=== Préstamos actuales de " + username + " ===");
        if (prestamosActuales.isEmpty()) {
            System.out.println("Sin préstamos.");
            return;
        }
        for (Libro l : prestamosActuales) System.out.println(" - " + l);
    }

    public void verHistorial() {
        System.out.println("\n=== Historial de " + username + " ===");
        if (historialPrestamos.isEmpty()) {
            System.out.println("Sin historial.");
            return;
        }
        for (Libro l : historialPrestamos) System.out.println(" - " + l);
    }

    public void agregarFavorito(Libro l) {
        if (favoritos.add(l)) System.out.println("❤️ Agregado a favoritos.");
        else System.out.println("⚠ Ya estaba en favoritos.");
    }

    public void quitarFavorito(Libro l) {
        if (favoritos.remove(l)) System.out.println("💔 Quitado de favoritos.");
        else System.out.println("⚠ No estaba en favoritos.");
    }

    public void verFavoritos() {
        System.out.println("\n=== Favoritos de " + username + " ===");
        if (favoritos.isEmpty()) {
            System.out.println("Sin favoritos.");
            return;
        }
        for (Libro l : favoritos) System.out.println(" - " + l);
    }

    public void sugerir(String titulo) {
        sugerencias.add(titulo);
        System.out.println("📝 Sugerencia enviada: " + titulo);
    }
}
