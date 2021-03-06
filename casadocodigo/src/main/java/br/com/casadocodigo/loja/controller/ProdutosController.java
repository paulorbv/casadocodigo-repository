package br.com.casadocodigo.loja.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	FileSaver fileSaver;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos",TipoPreco.values());

		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {
		
		Produto produto = produtoDAO.find(id);				
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		modelAndView.addObject("produto",produto);
		

		return modelAndView;
	}	
	
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView listar() {
		
		List<Produto> produtos = produtoDAO.listar();
		
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos",produtos);

		return modelAndView;
	}	
	
	@RequestMapping( method= RequestMethod.POST)
	public ModelAndView grava(MultipartFile sumario,@Valid Produto produto,BindingResult result, RedirectAttributes redirectAttributes) {
		
		System.out.println(produto);
		System.out.println("Arquivo: " + sumario.getOriginalFilename());

		if (result.hasErrors()) {
			return form(produto);
		}

		String path  = fileSaver.write("arquivos-sumario", sumario);
		
		System.out.println("Pasta do Arquivo : " + path);
		
		produto.setSumarioPath(path);
		
		produtoDAO.gravar(produto);
				
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");		
		
		return new ModelAndView("redirect:produtos");
		
	}
	
}
