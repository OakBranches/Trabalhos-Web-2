package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestfulController {

    @Autowired
    ICarroService service;

    @GetMapping(value = "/results/{modelo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getResult(@PathVariable String modelo) {
        return service.buscarTodosComNome(modelo).toString();
    }
    @GetMapping(value = "/results/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return service.buscarTodos().toString();
    }
}