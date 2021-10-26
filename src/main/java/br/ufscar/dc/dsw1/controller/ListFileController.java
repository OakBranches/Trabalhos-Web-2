package br.ufscar.dc.dsw1.controller;

import java.io.IOException;

import br.ufscar.dc.dsw1.forms.CarroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufscar.dc.dsw1.services.spec.IFileService;

@Controller
public class ListFileController {

	@Autowired
	private IFileService service;

	@GetMapping("/example")
	public String list(ModelMap model) throws IOException {
		if (model.getAttribute("form") == null)
			model.addAttribute("form", new CarroForm());
		model.addAttribute("files", service.buscarTodos());
		
		return "formCarro";
	}
}
