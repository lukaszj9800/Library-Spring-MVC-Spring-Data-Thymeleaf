package pl.jagiela.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Author;
import pl.jagiela.repository.AuthorRepository;
import pl.jagiela.repository.BookRepository;

import java.util.List;

@Controller
public class AuthorController {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/addAuthor")
    public String addAuthor(Model model){
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute Author author){
        authorRepository.save(author);
        return "redirect:addAuthor";
    }

    @GetMapping("/showAllAuthors")
    public String showAuthors(Model model){
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "showAuthors";
    }

    @PostMapping("/deleteAuthor")
    public String deleteAuthor(@ModelAttribute Author author){
        authorRepository.deleteById(author.getId());
        return "redirect:showAllAuthors";
    }

    @GetMapping("/findAuthor")
    public String findAuthor(Model model){
        model.addAttribute("authors", new Author());
        model.addAttribute("message", "Find author");
        return "findAuthor";
    }


    @PostMapping("/findAuthor")
    public String findAuthor(@ModelAttribute Author author, Model model){
        List<Author> authors = authorRepository.findByFirstnameOrLastnameContainingIgnoreCase(author.getFirstname(), author.getLastname());
        model.addAttribute("findAuthors", authors);
        model.addAttribute("message", "Find results:");
        return "findAuthorResults";
    }

    @GetMapping("/editAuthor")
    public String editAuthor(@ModelAttribute Author author, Model model){
        Author authorToEdit = authorRepository.getOne(author.getId());
        model.addAttribute("authorToEdit", authorToEdit);
        return "editAuthor";
    }

    @PostMapping("/editAuthor")
    public String editAuthor(@ModelAttribute Author author){
        authorRepository.save(author);
        return "redirect:showAllAuthors";
    }

}
