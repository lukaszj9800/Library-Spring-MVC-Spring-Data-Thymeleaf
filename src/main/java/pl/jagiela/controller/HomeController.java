package pl.jagiela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Book;
import pl.jagiela.repository.BookRepository;

import java.util.List;

@Controller
public class HomeController {

    private BookRepository bookRepository;

    @Autowired
    public HomeController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String showAllBook(Model model){
        List<Book> allBook = bookRepository.findAll();
        model.addAttribute("allBooks", allBook);
        model.addAttribute("book", new Book());
        model.addAttribute("message", "All books:");
        return "home";
    }


}
