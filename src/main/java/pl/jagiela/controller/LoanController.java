package pl.jagiela.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Loan;
import pl.jagiela.repository.LoanRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class LoanController {

    private LoanRepository loanRepository;

    @Autowired
    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping("/addLoan")
    public String addLoan(Model model){
        model.addAttribute("loan", new Loan());
        return "addLoan";
    }

    @PostMapping("/addLoan")
    public String addLoan(@ModelAttribute Loan loan) {
        loanRepository.save(loan);
        return "redirect:showLoans";
    }

    @GetMapping("/showLoans")
    public String showLoans(Model model){
        List<Loan> allLoans = loanRepository.findAll();
        model.addAttribute("allLoans", allLoans);
        return "showLoans";
    }

    @PostMapping("/deleteLoan")
    public String deleteLoan(@ModelAttribute Loan loan){
        loanRepository.deleteById(loan.getId());
        return "redirect:showLoans";
    }

    @GetMapping("/editLoan")
    public String editLoan(@ModelAttribute Loan loan, Model model){
        Loan loanToEdit = loanRepository.getOne(loan.getId());
        model.addAttribute("loanToEdit", loanToEdit);
        return "editLoan";
    }


    @PostMapping("/editLoan")
    public String editLoan(@ModelAttribute Loan loan){
        loanRepository.save(loan);
        return "redirect:showLoans";
    }


}
