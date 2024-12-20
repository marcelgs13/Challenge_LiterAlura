package com.alura.literalura.service;

import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String GUTENDEX_URL = "https://gutendex.com/books";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Book> searchBook(String inputTitle) {
        String url = GUTENDEX_URL + "?search=" + inputTitle;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            BookResponse bookResponse = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            List<Book> books = bookResponse.getResults();
            return books.stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(inputTitle))
                    .findFirst();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
