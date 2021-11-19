package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.services.spec.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class LojaRestController extends AbstractRestController{

    @Autowired
    private Validator validator;

    @Autowired
    private ILojaService lojaservice;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public boolean cnpjIsValid(String cnpj, Long Id){
        Usuario loja = lojaservice.buscaPorCnpj(cnpj);
        return cnpj.length() == 18 &&(loja == null ||  Objects.equals(loja.getId(), Id));
    }


    @SuppressWarnings("unchecked")
    private void lojaParse(@Valid Loja loja, JSONObject map) throws ParseException {

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

        if (!cnpjIsValid(loja.getCnpj(), loja.getId()) || !lojaservice.emailIsValid(loja)){
            throw new ParseException("Loja já cadastrada", 1);
        }

        if (!validator.validate(loja).isEmpty()){
            throw new ParseException(validator.validate(loja).toString(), 1);
        }

    }


    @GetMapping(path = "/lojas")
    public ResponseEntity<List<Loja>> listaLoja() {
        List<Loja> lista = lojaservice.buscarTodasLojas();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/lojas/{id}")
    public ResponseEntity<Loja> lista(@PathVariable("id") long id) {
        Loja loja = lojaservice.buscaPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loja);
    }

    @PostMapping(path = "/lojas")
    @ResponseBody
    public ResponseEntity<Loja> criaLoja(@RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Loja loja = new Loja();
                String senha = (String) json.get("senha");
                json.replace("id", null);
                if (senha != null)
                    json.replace("senha", encoder.encode(senha));
                lojaParse(loja, json);
                System.out.println(loja);
                lojaservice.salvar(loja);
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
    public ResponseEntity<Loja> atualizaLoja(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Loja loja = lojaservice.buscaPorId(id);
                if (loja == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    // Campos imutáveis
                    json.replace("senha", loja.getSenha());
                    json.replace("email", loja.getEmail());
                    json.replace("id", id);

                    lojaParse(loja, json);
                    lojaservice.salvar(loja);
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

        Loja loja = lojaservice.buscaPorId(id);

        if (loja == null ) {
            return ResponseEntity.notFound().build();
        }
        else if (lojaservice.LojaTemCarros(loja.getId())){
            return ResponseEntity.unprocessableEntity().build();
        }
        else {
            lojaservice.excluirPorId(id);
            return ResponseEntity.noContent().build();
        }
    }
}
