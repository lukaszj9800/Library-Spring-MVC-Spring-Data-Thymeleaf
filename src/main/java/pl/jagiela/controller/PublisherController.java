package pl.jagiela.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Publisher;
import pl.jagiela.repository.PublisherRepository;

import java.util.List;

@Controller
public class PublisherController {

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/addPublisher")
    public String addPublisher(Model model){
        model.addAttribute("publisher", new Publisher());
        return "addPublisher";
    }

    @PostMapping("/addPublisher")
    public String addPublisher(@ModelAttribute Publisher publisher){
        publisherRepository.save(publisher);
        return "redirect:addPublisher";
    }

    @GetMapping("/showPublishers")
    public String showPublisher(Model model){
        List<Publisher> publishers = publisherRepository.findAll();
        model.addAttribute("publishers", publishers);
        model.addAttribute("message", "Publishers:");
        return "showPublishers";
    }

    @PostMapping("/deletePublisher")
    public String deletePublisher(@ModelAttribute Publisher publisher){
        publisherRepository.deleteById(publisher.getId());
        return "redirect:showPublishers";
    }

    @GetMapping("/findPublisher")
    public String findPublisher(Model model){
        model.addAttribute("publisher", new Publisher());
        model.addAttribute("message", "Find publisher:");
        return "findPublisher";
    }

    @PostMapping("/findPublisher")
    public String findPublisher(@ModelAttribute Publisher publisher, Model model){
        List<Publisher> publishers = publisherRepository.findByNameContainingIgnoreCase(publisher.getName());
        model.addAttribute("publisher", publishers);
        return "findPublisherResults";
    }
}
