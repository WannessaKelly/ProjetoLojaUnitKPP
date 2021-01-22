package com.negocio.ProjetoKellyLojaUnit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basica.ProjetoKellyLojaUnit.Faq;
import com.basica.ProjetoKellyLojaUnit.Produto;
import com.repositorio.ProjetoKellyLojaunit.FaqRepositorio;
import com.repositorio.ProjetoKellyLojaunit.ProdutoRepositorio;

@Controller
@RequestMapping(path="/faq") 
public class FaqController {
	@Autowired
	private ProdutoRepositorio produtoRepository;
	@Autowired	
	private FaqRepositorio faqRepository;
	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewUser (@RequestParam String dataHora
	      , @RequestParam String texto, @RequestParam Integer idProduto) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Faq f = new Faq();
	    f.setDataHora(dataHora);
	    f.setTexto(texto);
	    Produto produto = produtoRepository.findById(idProduto).get();
		f.setProduto(produto);
	    
	    faqRepository.save(f);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<Faq> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return faqRepository.findAll();
	  }

	  @GetMapping(path="/procurar/{id}")
		public @ResponseBody Optional<Faq> getFaqById(@PathVariable("id")Integer id){
			return faqRepository.findById(id);
		}
		
			
		@DeleteMapping(path="/excluir/{id}")
		public @ResponseBody String deleteById(@PathVariable("id")Integer id) {
			if(faqRepository.existsById(id)) {
				faqRepository.deleteById(id);
				return "Faq deletado com sucesso";
			}
			return "Faq não encontrado para deleção, verifique o ID";
		}
		
		@PutMapping(path="/atualizar/{id}")
		public @ResponseBody String updateFaqById(
				@RequestParam String dataHora
			      , @RequestParam String texto, @RequestParam Integer idProduto,
				@PathVariable("id")Integer id) {
			if(faqRepository.existsById(id)) {
				Produto produto = produtoRepository.findById(idProduto).get();
				 Faq f = new Faq();
				    f.setDataHora(dataHora);
				    f.setTexto(texto);
				    f.setProduto(produto);
				    
				    faqRepository.save(f);
				  
				return "Faq atualizado.";
			}
			return "Faq não atualizado";
		}
	}
