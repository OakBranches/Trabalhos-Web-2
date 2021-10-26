package br.ufscar.dc.dsw1.controller;

import java.io.IOException;
import java.util.List;

import br.ufscar.dc.dsw1.forms.CarroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw1.domain.FileEntity;
import br.ufscar.dc.dsw1.services.spec.IFileService;

import javax.validation.Valid;

@Controller
public class UploadFileController {

	@Autowired
	private IFileService service;

	@PostMapping(path = "/teste", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String uploadFile(@ModelAttribute("form") @Valid CarroForm form, BindingResult result, RedirectAttributes attr) {
//		for (int i=0; i< file.size() ; i++) {
//			String fileName = StringUtils.cleanPath(file.get(i).getOriginalFilename());
//			FileEntity entity = new FileEntity(fileName, file.get(i).getContentType(), file.get(i).getBytes());
//
//			service.salvar(entity);
//		}
		if (result.hasErrors()) {
			return "formCarro";
		}


		return "redirect:/example";
	}

}
