package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
    public Optional<Author> getAuthorById(long id){ return authorRepository.findById(id); }
    public List<Author> getAliveAuthors(int year) {
        var authorList = getAuthors();
        return authorList.stream()
                .filter(a -> Integer.parseInt(a.getBirthYear()) <= year &&
                        (a.getDeathYear().isEmpty() || Integer.parseInt(a.getDeathYear()) >= year))
                .collect(Collectors.toList());
    }
    public Author saveBook(Author author){
        return authorRepository.save(author);
    }

    public void deleteAuthor(long id){
        authorRepository.deleteById(id);
    }
}
