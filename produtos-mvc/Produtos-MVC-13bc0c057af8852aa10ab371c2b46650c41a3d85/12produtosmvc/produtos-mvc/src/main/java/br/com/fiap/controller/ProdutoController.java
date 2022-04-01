package br.com.fiap.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.model.ProdutoModel;
import br.com.fiap.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	ProdutoRepository repository = ProdutoRepository.getInstance();

	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public String findAll(Model model) {

		model.addAttribute("produtos", repository.findAll());
		return "produtos";
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("produto", repository.findById(id));
		return "produto-detalhe";
	}
	
	@RequestMapping(value = "/produto/new", method = RequestMethod.GET)
	public String openSave() {
		return "produto-novo";
	}
	
	@RequestMapping(value = "/produto/new", method = RequestMethod.POST)
	public String save(@Valid ProdutoModel produtoModel, BindingResult bindingresult, RedirectAttributes redirectAttributes) {
		
		repository.save(produtoModel);
		redirectAttributes.addFlashAttribute("mesages", "Produto cadastrado com sucesso!");
		
		return "redirect:/produto";
		
		if (!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("mensagemProduto",
					ProdutoRepository.getInstance().save(produtoModel));
					return "redirect:/produto";
		}else {
			
			return "produto-novo";
		}
	}
	
	@RequestMapping(value = "produto/update/{id}", method = RequestMethod.GET)
	public String openUpdate(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("produto", repository.findById(id));
		return "produto-editar";
	}
	
	@RequestMapping(value = "/produto/update", method = RequestMethod.POST)
	public String update(Model model, ProdutoModel produtoModel, RedirectAttributes redirectAttributes) {
		
		repository.update(produtoModel);
		redirectAttributes.addFlashAttribute("mesages", "Produto atualizado com sucesso!");
		
		return "redirect:/produto";
	}
	
	@RequestMapping(value = "produto/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes ) {
		
		repository.deleteById(id);
		redirectAttributes.addFlashAttribute("mesages", "Produto excluído com sucesso!");
		
		return "redirect:/produto";
	}
	
	
	

}
