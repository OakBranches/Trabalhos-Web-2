package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class LojaController {

    @Autowired
    private ILojaService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean cnpjIsValid(String cnpj, Long Id){
        Usuario loja = service.buscaPorCnpj(cnpj);
        return cnpj.length() == 18 &&(loja == null ||  Objects.equals(loja.getId(), Id));
    }

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private void parse(@Valid Loja loja, JSONObject map) throws ParseException {

        Object id = map.get("id");
        if (id != null){
            System.out.println(id);
            if (id instanceof Integer) {
                loja.setId(((Integer) id).longValue());
            } else {
                loja.setId((Long) id);
            }
        }

        loja.setPapel(2);
        loja.setCnpj((String) map.get("cnpj"));
        loja.setDescricao((String) map.get("descricao"));
        loja.setEmail((String) map.get("email"));
        loja.setNome((String) map.get("nome"));
        loja.setSenha((String) map.get("senha"));

        if (!cnpjIsValid(loja.getCnpj(), loja.getId()) || !service.emailIsValid(loja)){
            throw new ParseException("Loja já cadastrada", 1);
        }

    }


    @GetMapping(path = "/lojas")
    public ResponseEntity<List<Loja>> lista() {
        List<Loja> lista = service.buscarTodasLojas();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/lojas/{id}")
    public ResponseEntity<Loja> lista(@PathVariable("id") long id) {
        Loja loja = service.buscaPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loja);
    }

    @PostMapping(path = "/lojas")
    @ResponseBody
    public ResponseEntity<Loja> cria(@RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Loja loja = new Loja();
                String senha = (String) json.get("senha");
                json.replace("id", null);
                if (senha != null)
                    json.replace("senha", encoder.encode(senha));
                parse(loja, json);
                System.out.println(loja);
                service.salvar(loja);
                return ResponseEntity.ok(loja);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/lojas/{id}")
    public ResponseEntity<Loja> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Loja loja = service.buscaPorId(id);
                if (loja == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    // Campos imutáveis
                    json.replace("senha", loja.getSenha());
                    json.replace("email", loja.getEmail());
                    json.replace("id", id);

                    parse(loja, json);
                    service.salvar(loja);
                    return ResponseEntity.ok(loja);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/lojas/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

        Loja Loja = service.buscaPorId(id);

        if (Loja == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.excluirPorId(id);
            return ResponseEntity.noContent().build();
        }
    }
}
