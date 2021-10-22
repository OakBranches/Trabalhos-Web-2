package br.ufscar.dc.dsw1.controller;

import br.ufscar.dc.dsw1.domain.Cliente;
import br.ufscar.dc.dsw1.security.UsuarioDetails;
import br.ufscar.dc.dsw1.services.spec.ICarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class DefaultController {
    @Autowired
    ICarroService service;

    @PostMapping("/result")
    public String result(){
        //todo fazer logica

//        Gson gsonBuilder = new GsonBuilder().create();
//        List<String> carros = new ArrayList<>();
//
//        List<Carro> listaCarros = service.buscarTodosComNome(nome);
//        for (Carro carro : listaCarros) {
//            carro.setImagens(carro.getImages(getServletContext().getRealPath("upload")));
//            carros.add(carro.toString());
//        }
//
//        System.out.println(gsonBuilder.toJson(carros));
//        response.getWriter().write(gsonBuilder.toJson(carros));
//

        return "ablublue";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("carros", service.buscarTodos());
        return "index";
    }
}
