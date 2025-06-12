package com.literalura.catalogo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;

    private Integer numeroDownloads;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> assuntos;

    public Livro() {}

    public Livro(String titulo, List<String> idiomas, Integer numeroDownloads, List<Autor> autores, List<String> assuntos) {
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.numeroDownloads = numeroDownloads;
        this.autores = autores;
        this.assuntos = assuntos;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<String> getIdiomas() { return idiomas; }

    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }

    public Integer getNumeroDownloads() { return numeroDownloads; }

    public void setNumeroDownloads(Integer numeroDownloads) { this.numeroDownloads = numeroDownloads; }

    public List<Autor> getAutores() { return autores; }

    public void setAutores(List<Autor> autores) { this.autores = autores; }

    public List<String> getAssuntos() { return assuntos; }

    public void setAssuntos(List<String> assuntos) { this.assuntos = assuntos; }

    @Override
    public String toString() {
        String nomeAutor = autores != null && !autores.isEmpty() ? autores.get(0).getNome() : "Desconhecido";
        String idioma = idiomas != null && !idiomas.isEmpty() ? idiomas.get(0) : "N/A";
        double downloads = numeroDownloads != null ? numeroDownloads.doubleValue() : 0.0;

        return """
            ----- LIVRO -----
            Título: %s
            Autor: %s
            Idioma: %s
            Número de downloads: %.1f
            ------------------
            """.formatted(titulo, nomeAutor, idioma, downloads);
    }
}
