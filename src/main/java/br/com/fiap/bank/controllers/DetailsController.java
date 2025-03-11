package br.com.fiap.bank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailsController {
    private static final String projectName = "Fiap Bank";
    private static final String author = "Leandro Correia Alves Filho - RM 556203";

    @GetMapping("/")
    public String projectDetails (){
        return projectName + " - " + "Author: " + author;
    }
}
