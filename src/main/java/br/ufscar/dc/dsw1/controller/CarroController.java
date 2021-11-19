package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Carro;
import br.ufscar.dc.dsw1.domain.FileEntity;
import br.ufscar.dc.dsw1.domain.Usuario;
import br.ufscar.dc.dsw1.domain.Loja;
import br.ufscar.dc.dsw1.forms.CarroForm;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import br.ufscar.dc.dsw1.services.spec.IFileService;
import br.ufscar.dc.dsw1.services.spec.ILojaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@CrossOrigin
@RestController
public class CarroController {

    @Autowired
    Validator validator;

    @Autowired
    ILojaService lojaservice;

    @Autowired
    ICarroService carservice;

    @Autowired
    private IFileService fileService;

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
            throw new ParseException("Carro invalido", 1);
        }

    }


    @GetMapping(path = "/veiculos/lojas/{id}")
    public ResponseEntity<List<Carro>> lista(@PathVariable("id") long id) {
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

}
