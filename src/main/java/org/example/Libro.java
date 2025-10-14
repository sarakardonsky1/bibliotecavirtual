package org.example;

import java.util.*;

public class Libro {
    private final String titulo;
    private final String autor;
    private boolean prestado;
    private int vecesPrestado;
    private final List<String> resenas;
    private final List<Integer> calificaciones;

    public Libro(String titulo, String autor) {
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        this.prestado = false;
        this.vecesPrestado = 0;
        this.resenas = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isPrestado() { return prestado; }
    public int getVecesPrestado() { return vecesPrestado; }

    public void prestar() {
        if (prestado) {
            System.out.println("⚠ El libro ya estaba prestado.");
            return;
        }
        prestado = true;
        vecesPrestado++;
        System.out.println("📚 Prestado: \"" + titulo + "\" de " + autor);
    }

    public void devolver() {
        if (!prestado) {
            System.out.println("⚠ El libro no estaba prestado.");
            return;
        }
        prestado = false;
        System.out.println("✅ Devuelto: \"" + titulo + "\" de " + autor);
    }

    public void agregarResena(String texto, int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            System.out.println("⚠ La calificación debe ser entre 1 y 5.");
            return;
        }
        resenas.add(texto);
        calificaciones.add(calificacion);
        System.out.println("⭐ Reseña agregada.");
    }

    public double promedioCalificacion() {
        if (calificaciones.isEmpty()) return 0.0;
        int suma = 0;
        for (int c : calificaciones) suma += c;
        return (double) suma / calificaciones.size();
    }

    public void mostrarResenas() {
        if (resenas.isEmpty()) {
            System.out.println("Sin reseñas.");
            return;
        }
        System.out.println("=== Reseñas de \"" + titulo + "\" ===");
        for (int i = 0; i < resenas.size(); i++) {
            System.out.println(" - " + resenas.get(i) + " | ⭐ " + calificaciones.get(i) + "/5");
        }
    }

    @Override
    public String toString() {
        String estado = prestado ? "Prestado" : "Disponible";
        return "📖 " + titulo + " — " + autor + " [" + estado + "]  Veces prestado: " + vecesPrestado +
                "  | ⭐ " + String.format(Locale.US, "%.1f", promedioCalificacion());
    }
}
