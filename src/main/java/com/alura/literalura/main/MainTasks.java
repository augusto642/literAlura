package com.alura.literalura.main;

import com.alura.literalura.model.AuthorInfo;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.Data;
import com.alura.literalura.model.Languages;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.services.DataConvert;
import com.alura.literalura.services.RequestAPI;

import java.util.*;

public class MainTasks {

    private Scanner scanner = new Scanner(System.in);
    private RequestAPI requestAPI = new RequestAPI();
    private DataConvert dataConvert = new DataConvert();
    private BookRepository repository;
    private final String BASE_URL = "https://gutendex.com/books/";
    private List<Book> books;

    public MainTasks(BookRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu() {
        int opcao;

        do {
            String menu = """
                    
                    ================= MENU PRINCIPAL =================
                    
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos após um determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    
                    ==================================================
                    """;

            System.out.println(menu);
            System.out.print("Digite sua opção: ");

            // Valida a entrada do menu
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número entre 0 e 5.");
                scanner.next();
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private String obterTituloDoUsuario() {
        System.out.println("Digite o título do livro que deseja buscar:");
        return scanner.nextLine();
    }

    private Data obterDadosDoLivroAPI(String titulo) {
        String urlFormatada = BASE_URL + "?search=" + titulo.replace(" ", "+");
        String json = requestAPI.getData(urlFormatada);
        return dataConvert.getData(json, Data.class);
    }

    private Optional<Book> encontrarLivroPorTitulo(Data dadosLivro, String titulo) {
        return dadosLivro.results().stream()
                .filter(l -> l.title().toLowerCase().contains(titulo.toLowerCase()))
                .map(b -> new Book(b.title(), b.languages(), b.downloads(), b.authors()))
                .findFirst();
    }

    private void buscarLivro() {
        String titulo = obterTituloDoUsuario();
        Data dados = obterDadosDoLivroAPI(titulo);

        Optional<Book> livro = encontrarLivroPorTitulo(dados, titulo);

        if (livro.isPresent()) {
            repository.save(livro.get());
            System.out.println("\nLivro encontrado e salvo:\n" + livro.get());
        } else {
            System.out.println("\nNenhum livro encontrado com o título informado.\n");
        }
    }

    private void listarLivrosRegistrados() {
        books = repository.findAll();

        if (books.isEmpty()) {
            System.out.println("\nNenhum livro registrado.\n");
            return;
        }

        System.out.println("\nLista de livros registrados:\n");
        books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<AuthorInfo> autores = repository.getAuthorsInfo();

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor registrado.\n");
            return;
        }

        System.out.println("\nLista de autores registrados:\n");
        autores.stream()
                .sorted(Comparator.comparing(AuthorInfo::getName))
                .forEach(a -> System.out.printf("Autor: %s | Nascido: %s | Falecido: %s%n",
                        a.getName(), a.getBirthYear(), a.getDeathYear()));
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano a partir do qual deseja buscar autores vivos:");

        // Valida entrada para o ano
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, digite um ano válido.");
            scanner.next();
        }

        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        List<AuthorInfo> autores = repository.getAuthorLiveAfter(ano);

        if (autores.isEmpty()) {
            System.out.printf("\nNenhum autor encontrado vivo após o ano %d.\n", ano);
            return;
        }

        System.out.printf("\nAutores vivos após o ano %d:\n", ano);
        autores.stream()
                .sorted(Comparator.comparing(AuthorInfo::getName))
                .forEach(a -> System.out.printf("Autor: %s | Nascido: %s | Falecido: %s%n",
                        a.getName(), a.getBirthYear(), a.getDeathYear()));
    }

    private void listarLivrosPorIdioma() {
        String menuIdiomas = """
                
                ================= IDIOMAS DISPONÍVEIS =================
                
                en - Inglês
                es - Espanhol
                fr - Francês
                it - Italiano
                pt - Português
                
                ======================================================
                """;

        System.out.println(menuIdiomas);
        System.out.print("Digite o código do idioma: ");
        String codigoIdioma = scanner.nextLine();

        Languages idioma;

        try {
            idioma = Languages.fromString(codigoIdioma);
        } catch (IllegalArgumentException e) {
            System.out.println("\nIdioma inválido. Por favor, tente novamente.\n");
            return;
        }

        List<Book> livrosPorIdioma = repository.findByLanguages(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.printf("\nNenhum livro encontrado no idioma '%s'.\n", codigoIdioma);
            return;
        }

        System.out.printf("\nLista de livros no idioma '%s':\n", codigoIdioma);
        livrosPorIdioma.forEach(System.out::println);
    }
}
