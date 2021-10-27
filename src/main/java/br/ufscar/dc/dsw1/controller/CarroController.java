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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    ILojaService service;

    @Autowired
    ICarroService carservice;

    @Autowired
    private IFileService fileService;

    @GetMapping("/listar")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        List<Carro> carros = service.buscaPorId(user.getId()).getCarros();
        model.addAttribute("carros", carros);
        return "PainelLoja";
    }

    @GetMapping("/create")
    public String list(ModelMap model) throws IOException {
        if (model.getAttribute("form") == null)
            model.addAttribute("form", new CarroForm());
        model.addAttribute("files", service.buscarTodasLojas());

        return "formCarro";
    }


    @PostMapping(path = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createCar(@ModelAttribute("form") @Valid CarroForm form, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println("Há erros");
            System.out.println(result.getAllErrors());
            return "formCarro";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();
        Loja loja = service.buscaPorId(user.getId());
        Carro carro = new Carro(BigDecimal.valueOf(form.getValor()), BigDecimal.valueOf(form.getKm()), form.getPlaca(), form.getModelo(), form.getDescricao(), form.getChassi(), form.getAno(), loja);
        carro = carservice.salvar(carro);
        System.out.println(carro);
        int count = 0;

        List<MultipartFile> file = form.getImagens();

        for (int i=0; i< file.size(); i++) {

			try {
                String fileName = StringUtils.cleanPath(file.get(i).getOriginalFilename());
                FileEntity entity = new FileEntity(fileName, file.get(i).getContentType(), file.get(i).getBytes(), carro);
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
        System.out.println("terminou o cadastro");
        return "redirect:/example";
    }

}
