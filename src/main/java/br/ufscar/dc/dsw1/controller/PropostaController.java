package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.domain.Proposta;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.forms.FormAccept;
import br.ufscar.dc.dsw1.forms.FormReject;
import br.ufscar.dc.dsw1.forms.PropostaForm;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import br.ufscar.dc.dsw1.services.spec.IClienteService;
import br.ufscar.dc.dsw1.services.spec.IPropostaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

//import javax.jms.MessageProducer;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@CrossOrigin
@RestController
public class PropostaController {
    @Autowired
    private IPropostaService service;

    @Autowired
    private ICarroService carservice;

    @Autowired
    private IClienteService cliservice;

    @Autowired
    private JavaMailSender emailservice;

    @GetMapping(path = "/propostas/veiculos/{id}")
    public ResponseEntity<List<Proposta>> lista(@PathVariable("id") long id) {
        Carro carro = carservice.buscaPorId(id);

        if (carro.getPropostas().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carro.getPropostas());
    }

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private Long toLong(Object id){
        if (id != null){
            if (id instanceof Integer) {
                return ((Integer) id).longValue();
            } else {
                return (Long) id;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void parse(Proposta proposta, JSONObject map) throws ParseException {

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
    }

    @PostMapping(path = "/propostas/veiculos/{id}")
    @ResponseBody
    public ResponseEntity<Proposta> cria(@PathVariable("id") long id, @RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Proposta proposta = new Proposta();
                json.replace("id", null);
                proposta.setCarro(carservice.buscaPorId(id));
                parse(proposta, json);
                service.salvar(proposta);
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
