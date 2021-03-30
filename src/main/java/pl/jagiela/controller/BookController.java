package pl.jagiela.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Book;
import pl.jagiela.model.Loan;
import pl.jagiela.repository.BookRepository;
import pl.jagiela.repository.LoanRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private BookRepository bookRepository;
    private LoanRepository loanRepository;

    @Autowired
    public BookController(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @GetMapping("/addBook")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @GetMapping("/exception")
    public String exception(){
        return "exception";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book, Model model){
        Optional<Book> bookCheck = bookRepository.findByIsbn(book.getIsbn());
        if (bookCheck.isEmpty()) {
            bookRepository.save(book);
            return "redirect:/";
        }else{
            model.addAttribute("exception","Isbn exist");
            return "/exception";
        }
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Book book, Model model){
        Book bookToDelete = bookRepository.getOne(book.getId());
        if(bookToDelete.getLoans().isEmpty()){
            bookRepository.deleteById(bookToDelete.getId());
            return "redirect:/";
        }else{
            model.addAttribute("exception", "The book cannot be deleted because it appears in the loan database");
            return "/exception";
        }
    }

    @GetMapping("/findBook")
    public String findBook(Model model){
        model.addAttribute("book", new Book());
        return "findBook";
    }

    @PostMapping("/findBook")
    public String showBook(@ModelAttribute Book book, Model model){
        List<Book> findBooks = bookRepository.findByTitleContainingIgnoreCase(book.getTitle());
        model.addAttribute("findBooks", findBooks);
        return "findBookResults";
    }

    @GetMapping("/editBook")
    public String editBook(@ModelAttribute Book book, Model model){
        Book bookToEdit = bookRepository.getOne(book.getId());
        model.addAttribute("bookToEdit", bookToEdit);
        return "editBook";
    }

    @PostMapping("/editBook")
    public String editBook(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/";
    }
}
