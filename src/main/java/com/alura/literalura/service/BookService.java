package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GutendexService gutendexService;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByLanguage(String language) {
        return getBooks().stream()
                .filter(b -> b.getLanguages() != null && b.getLanguages().contains(language))
                .collect(Collectors.toList());
    }

    @Transactional
    public void tryFindAndRegisterBook(String inputTitle) {
        try {
            var foundBook = gutendexService.searchBook(inputTitle);
            if (foundBook.isPresent()) {
                var book = foundBook.get();
                for (Author author : book.getAuthors()) {
                    authorRepository.save(author);
                }
                System.out.println(saveBook(book));
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao registrar o livro: " + e.getMessage());
        }
    }
}
