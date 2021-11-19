package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.services.spec.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin
@RestController
public class PropostaRestController extends AbstractRestController{

    @Autowired
    private Validator validator;

    @Autowired
    private ICarroService carservice;

    @Autowired
    private IPropostaService propservice;

    @Autowired
    private IClienteService cliservice;


    @SuppressWarnings("unchecked")
    private void parseProp(Proposta proposta, JSONObject map) throws ParseException {

        Object id = map.get("id");
        proposta.setId(toLong(id));

        proposta.setValor(BigDecimal.valueOf((double) map.get("valor")));
        proposta.setStatus((int) map.get("status"));
        proposta.setCondPag((String) map.get("condPag"));
        proposta.setData(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("data")));

        proposta.setCliente(cliservice.buscaPorId(toLong(map.get("cliente"))));

        if (proposta.getCarro() == null || proposta.getCliente() == null ||
                cliservice.clienteTemPropostasAbertasNoCarro(proposta.getCliente().getId(), proposta.getCarro().getId())){
            throw new ParseException("ID errado", 1);
        }

        if (!validator.validate(proposta).isEmpty()){
            throw new ParseException(validator.validate(proposta).toString(), 1);
        }
    }

    @GetMapping(path = "/propostas/veiculos/{id}")
    public ResponseEntity<List<Proposta>> listaProp(@PathVariable("id") long id) {
        Carro carro = carservice.buscaPorId(id);

        if (carro.getPropostas().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carro.getPropostas());
    }

    @PostMapping(path = "/propostas/veiculos/{id}")
    @ResponseBody
    public ResponseEntity<Proposta> criaProp(@PathVariable("id") long id, @RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Proposta proposta = new Proposta();
                json.replace("id", null);
                proposta.setCarro(carservice.buscaPorId(id));
                parseProp(proposta, json);
                propservice.salvar(proposta);
                return ResponseEntity.ok(proposta);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
