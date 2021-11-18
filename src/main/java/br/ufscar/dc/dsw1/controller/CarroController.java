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
    ILojaService service;

    @Autowired
    ICarroService carservice;

    @Autowired
    private IFileService fileService;

//    @PostMapping(path = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public String createCar(@ModelAttribute("form") @Valid CarroForm form, BindingResult result, RedirectAttributes attr) {
//
//        if (result.hasErrors()) {
//            System.out.println("Há erros");
//            System.out.println(result.getAllErrors());
//            return "loja/formCarro";
//        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
//        Loja loja = service.buscaPorId(user.getId());
//        Carro carro = new Carro(BigDecimal.valueOf(form.getValor()), BigDecimal.valueOf(form.getKm()), form.getPlaca(), form.getModelo(), form.getDescricao(), form.getChassi(), form.getAno(), loja);
//        carro = carservice.salvar(carro);
//        System.out.println(carro);
//        int count = 0;
//
//        List<MultipartFile> file = form.getImagens();
//
//        for (int i=0; i< file.size(); i++) {
//
//			try {
//                String fileName = StringUtils.cleanPath(file.get(i).getOriginalFilename());
//                FileEntity entity = new FileEntity(fileName, file.get(i).getContentType(), file.get(i).getBytes(), carro);
//                System.out.println(entity);
//                if (entity.isImage()) {
//                    count++;
//                    System.out.println("salvando");
//                    fileService.salvar(entity);
//                }
//
//                if(count >= 10){
//                    break;
//                }
//
//            } catch(IOException e){
//                System.out.println("Ocorreu um erro ao pegar as imagens");
//            }
//		}
//        System.out.println("terminou o cadastro");
//        return "redirect:/carro/listar";
//    }

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

//    @SuppressWarnings("unchecked")
//    private void parse(@Valid Loja loja, JSONObject map) throws ParseException {
//
//        Object id = map.get("id");
//        if (id != null){
//            System.out.println(id);
//            if (id instanceof Integer) {
//                loja.setId(((Integer) id).longValue());
//            } else {
//                loja.setId((Long) id);
//            }
//        }
//
//        loja.setPapel(2);
//        loja.setCnpj((String) map.get("cnpj"));
//        loja.setDescricao((String) map.get("descricao"));
//        loja.setEmail((String) map.get("email"));
//        loja.setNome((String) map.get("nome"));
//        loja.setSenha((String) map.get("senha"));
//
//        if (!cnpjIsValid(loja.getCnpj(), loja.getId()) || !service.emailIsValid(loja)){
//            throw new ParseException("Loja já cadastrado", 1);
//        }
//
//    }


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


//    @PostMapping(path = "/veiculos/lojas/{id}")
//    public ResponseEntity<Carro> cria(@PathVariable("id") long id) {
//
//        List<Carro> carros = carservice.buscarTodosDaLoja(id);
//        if (carros.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(carros);
//    }

}
