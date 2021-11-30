package com.alekseev.postman.controller;

import com.alekseev.postman.model.Address;
import com.alekseev.postman.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddressController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping("/postmen/{postmanId}/addresses")
    public String getAddressesByPostman(@PathVariable long postmanId, Model model) {
        model.addAttribute("addresses", addressRepository.findByPostmanId(postmanId));
        model.addAttribute("address", new Address());
        model.addAttribute("postmanId", postmanId);

        return "addAddress";
    }

    @PostMapping("/postmen/{postmanId}/addresses")
    public String createAddress(@ModelAttribute Address address, @PathVariable long postmanId) {
        address.setPostmanId(postmanId);
        addressRepository.insert(address);
        return "redirect:/postmen/" + postmanId + "/addresses";
    }

}
