package com.alura.literalura.menu;

import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Scanner;

@Service
public class Menu {
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public Menu(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    private String getMenu() {
        return "\t\t\t _____ L I T E R A L U R A _____ \n" +
                "\tDigite o numero da opcao desejada: \n" +
                "\t1 - Pesquisar livro \n" +
                "\t2 - Listar livros registrados \n" +
                "\t3 - Listar autores registrados \n" +
                "\t4 - Listar autores vivos em algum ano\n" +
                "\t5 - Listar livros por idioma\n" +
                "\t0 - Sair ";
    }

    public void showMenu() throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            int option;

            while (true) {
                System.out.println("\t\t-------------------");
                System.out.println(getMenu());
                if (scanner.hasNext()) {
                    option = getOptionInput(scanner.next());
                    scanner.nextLine(); // Limpar buffer
                    switch (option) {
                        case 0:
                            System.out.println("Saindo...");
                            Thread.sleep(3000);
                            System.exit(0);
                            break;
                        case 1:
                            showSearchMenu(scanner);
                            break;
                        case 2:
                            var books = bookService.getBooks();
                            if(books.isEmpty()) {
                                System.out.println("Nenhum livro registrado... Comece a pesquisar!");
                            }else{
                                books.forEach(System.out::println);
                            }
                            break;
                        case 3:
                            var authors = authorService.getAuthors();
                            if(authors.isEmpty()) {
                                System.out.println("Nenhum autor registrado... Comece a pesquisar!");
                            }else{
                                authors.forEach(System.out::println);
                            }
                            break;
                        case 4:
                            getAliveAuthorsMenu(scanner);
                            break;
                        case 5:
                            getBooksByLanguageMenu(scanner);
                            break;
                        default:
                            System.out.println("Digite um numero valido");
                            break;
                    }
                }
            }
        }
    }

    private int getOptionInput(String input) {
        try {
            int parseInput = Integer.parseInt(input);
            if (parseInput < 0 || parseInput > 5) {
                throw new NumberFormatException();
            }
            return parseInput;
        } catch (NumberFormatException e) {
            System.out.println("Digite um numero valido");
            return -1;
        }
    }

    private void getBooksByLanguageMenu(Scanner scanner) {
        List<String> languages = List.of("en", "es", "fr", "pt");
        System.out.println("\t\t-------------------");
        System.out.println("\t\tDigite um dos idiomas abaixo ou \"sair\" para voltar:");
        languages.forEach(language -> System.out.println("\t" + language));

        while (true) {
            try {
                System.out.print("Idioma: ");
                String languageInput = scanner.nextLine().trim();

                if (languageInput.equalsIgnoreCase("sair")) {
                    return;
                } else if (!languages.contains(languageInput)) {
                    System.out.println("Digite um idioma válido...");
                } else {
                    var books = bookService.getBooksByLanguage(languageInput);
                    if(books.isEmpty()) {
                        System.out.println("Nenhum livro encontrado...");
                    }else{
                        books.forEach(System.out::println);
                    }
                    Thread.sleep(3000);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro. Tente novamente.");
            }
        }
    }

    private void getAliveAuthorsMenu(Scanner scanner) {
        System.out.println("\t\t-------------------");
        System.out.println("\tDigite um ano: ");
        while (true) {
            try {
                if (scanner.hasNextInt()) {
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    authorService.getAliveAuthors(year)
                            .forEach(System.out::println);
                    break;
                } else {
                    scanner.nextLine(); // Descartar entrada inválida
                    System.out.println("Digite um ano valido");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    private void showSearchMenu(Scanner scanner) {
        while (true) {
            System.out.println("\t\t-------------------");
            System.out.println("\t\t_____ PESQUISA _____ ");
            System.out.println("\tDigite o titulo do livro ou \"sair\" para voltar: \n");

            if (scanner.hasNextLine()) {
                String inputTitle = scanner.nextLine().trim();

                if (inputTitle.isEmpty()) {
                    System.out.println("Digite o titulo do livro...");
                } else if (inputTitle.equalsIgnoreCase("sair")) {
                    break;
                } else {
                    bookService.tryFindAndRegisterBook(inputTitle);
                }
            }
        }
    }
}
