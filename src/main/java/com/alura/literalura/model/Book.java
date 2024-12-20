package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @Id
    private Long id;
    private String title;

    @JsonProperty("download_count")
    private int downloads;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_language", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language") // Nome da coluna na tabela book_language @JsonProperty("languages") private List<String> languages;
    private List<String> languages;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authors;


    public Book() { }

    public Book(Long id, String title, List<Author> authors, int downloads, List<String> language) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.languages = language;
        this.downloads = downloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> language) {
        this.languages = language;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t----------LIVRO-----------\n");
        sb.append("Título: ").append(title).append("\n");
        sb.append("Downloads: ").append(downloads).append("\n");
        sb.append("Idiomas: ");
        for(String language : languages) {
            sb.append(language).append(", ");
        }
        sb.append("\nAutores:\n");
        for (Author author : authors) {
            sb.append("\t").append(author.getName()).append("\n");
            sb.append("\t(").append(author.getBirthYear()).append(" - ").append(author.getDeathYear()).append(")\n");
        }
        sb.append("\t\t-------------------");
        return sb.toString();
    }


    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
