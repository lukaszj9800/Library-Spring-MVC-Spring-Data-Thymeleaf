package pl.jagiela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jagiela.model.Client;
import pl.jagiela.repository.ClientRepository;

import java.util.List;

@Controller
public class ClientController {

    private ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/showClients")
    public String showAuthors(Model model){
        List<Client> allClients = clientRepository.findAll();
        model.addAttribute("clients", allClients);
        return "showClients";
    }

    @GetMapping("/addClient")
    public String addClinet(Model model){
        model.addAttribute("client", new Client());
        return "addClient";
    }

    @PostMapping("/addClient")
    public String addClient(@ModelAttribute Client client){
        clientRepository.save(client);
        return "redirect:showClients";
    }

    @PostMapping("/deleteClient")
    public String deleteClient(@ModelAttribute Client client){
        clientRepository.deleteById(client.getId());
        return "redirect:showClients";
    }

    @GetMapping("/editClient")
    public String editClient(@ModelAttribute Client client, Model model){
        Client clientToEdit = clientRepository.getOne(client.getId());
        model.addAttribute("clientToEdit", clientToEdit);
        return "editClient";
    }

    @PostMapping("/editClient")
    public String editClient(@ModelAttribute Client client){
        clientRepository.save(client);
        return "redirect:showClients";
    }

    @GetMapping("/findClient")
    public String findClient(Model model){
        model.addAttribute("client", new Client());
        return "findClient";
    }

    @PostMapping("/findClient")
    public String findClient(@ModelAttribute Client client, Model model){
        List<Client> findResults = clientRepository.findByFirstnameAndLastnameContainingIgnoreCase(client.getFirstname(), client.getLastname());
        model.addAttribute("findResults", findResults);
        return "findClientResults";
    }
}
