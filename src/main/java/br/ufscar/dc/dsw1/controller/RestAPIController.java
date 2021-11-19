package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.*;
import br.ufscar.dc.dsw1.services.spec.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class RestAPIController {

    @Autowired
    private Validator validator;

    @Autowired
    private ILojaService lojaservice;

    @Autowired
    private ICarroService carservice;

    @Autowired
    private IPropostaService propservice;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IClienteService cliservice;

    @Autowired
    private BCryptPasswordEncoder encoder;

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


    @SuppressWarnings("unchecked")
    private void cliParse(@Valid Cliente cliente, JSONObject map) throws ParseException {

        Object id = map.get("id");
        if (id != null){
            System.out.println(id);
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            } else {
                cliente.setId((Long) id);
            }
        }

        cliente.setPapel(3);
        cliente.setCpf((String) map.get("cpf"));
        cliente.setSexo((String) map.get("sexo"));
        cliente.setEmail((String) map.get("email"));
        cliente.setNome((String) map.get("nome"));
        cliente.setTelefone((String) map.get("telefone"));
        cliente.setSenha((String) map.get("senha"));
        cliente.setNascimento(new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("nascimento")));

        if (!cpfIsValid(cliente.getCpf(), cliente.getId()) || !cliservice.emailIsValid(cliente)){
            throw new ParseException("Cliente já cadastrado", 1);
        }

        if (!validator.validate(cliente).isEmpty()){
            throw new ParseException(validator.validate(cliente).toString(), 1);
        }

    }

    @SuppressWarnings("unchecked")
    private void carParse(@Valid Carro carro, JSONObject map) throws ParseException {

        Object id = map.get("id");
        if (id != null){
            carro.setId(toLong(id));
        }

        carro.setAno((int) map.get("ano"));
        carro.setChassi((String) map.get("chassi"));
        carro.setDescricao((String) map.get("descricao"));
        carro.setValor((BigDecimal) BigDecimal.valueOf((double) map.get("valor")));
        carro.setKm((BigDecimal) BigDecimal.valueOf((double) map.get("km")));
        carro.setModelo((String) map.get("modelo"));
        carro.setPlaca((String) map.get("placa"));

        if (!validator.validate(carro).isEmpty()){
            throw new ParseException(validator.validate(carro).toString(), 1);
        }

    }


    @PutMapping(path = "/veiculos/fotos/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Carro> upImage(@PathVariable("id") long id, @RequestPart List<MultipartFile> fotos) {

        Carro carro = carservice.buscaPorId(id);
        List<FileEntity> imagens = carro.getImagens();
        for (int j=0; j < imagens.size(); j++){
            fileService.excluir(imagens.get(j).getId());
        }

        int count = 0;

        for (int i=0; i< fotos.size(); i++) {
            try {
                String fileName = StringUtils.cleanPath(fotos.get(i).getOriginalFilename());
                FileEntity entity = new FileEntity(fileName, fotos.get(i).getContentType(), fotos.get(i).getBytes(), carro);
                System.out.println(entity);
                if (entity.isImage()) {
                    count++;
                    System.out.println("salvando");
                    fileService.salvar(entity);
                }

                if(count >= 10){
                    break;
                }

            } catch(IOException e){
                System.out.println("Ocorreu um erro ao pegar as imagens");
            }
        }
        return ResponseEntity.ok(carro);
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




    @GetMapping(path = "/veiculos/lojas/{id}")
    public ResponseEntity<List<Carro>> listaCar(@PathVariable("id") long id) {
        List<Carro> carros = carservice.buscarTodosDaLoja(id);
        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carros);
    }

    @GetMapping(path = "/veiculos/modelos/{modelo}")
    public ResponseEntity<List<Carro>> listaModelo(@PathVariable("modelo") String modelo) {
        List<Carro> carros = carservice.buscarTodosComNome(modelo);
        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carros);
    }


    @PostMapping(path = "/veiculos/lojas/{id}")
    @ResponseBody
    public ResponseEntity<Carro> cria(@PathVariable("id") long id, @RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Carro carro = new Carro();
                json.replace("id", null);
                carro.setLoja(lojaservice.buscaPorId(id));
                carParse(carro, json);
                carservice.salvar(carro);
                return ResponseEntity.ok(carro);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }


    public boolean cpfIsValid(String cpf, Long Id){
        Usuario loja = cliservice.buscaPorCpf(cpf);
        return cpf.length() == 14 && (loja == null || Objects.equals(loja.getId(), Id));
    }



    @GetMapping(path = "/clientes")
    public ResponseEntity<List<Cliente>> lista() {
        List<Cliente> lista = cliservice.buscarTodosClientes();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> listaCli(@PathVariable("id") long id) {
        Cliente cliente = cliservice.buscaPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(path = "/clientes")
    @ResponseBody
    public ResponseEntity<Cliente> criaCli(@RequestBody JSONObject json) {

        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = new Cliente();
                String senha = (String) json.get("senha");
                json.replace("id", null);
                if (senha != null)
                    json.replace("senha", encoder.encode(senha));
                cliParse(cliente, json);
                System.out.println(cliente);
                cliservice.salvar(cliente);
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/clientes/{id}")
    public ResponseEntity<Cliente> atualizaCli(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = cliservice.buscaPorId(id);
                if (cliente == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    // Campos imutáveis
                    json.replace("senha", cliente.getSenha());
                    json.replace("email", cliente.getEmail());
                    json.replace("id", id);

                    cliParse(cliente, json);
                    cliservice.salvar(cliente);
                    return ResponseEntity.ok(cliente);
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

    @DeleteMapping(path = "/clientes/{id}")
    public ResponseEntity<Boolean> removeCli(@PathVariable("id") long id) {

        Cliente cliente = cliservice.buscaPorId(id);

        if (cliente == null ) {
            return ResponseEntity.notFound().build();
        } else if (cliservice.clienteTemPropostas(cliente.getId())){
            return ResponseEntity.unprocessableEntity().build();
        } else{
            cliservice.excluirPorId(id);
            return ResponseEntity.noContent().build();
        }
    }


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
