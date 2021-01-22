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


import com.basica.ProjetoKellyLojaUnit.Fornecedor;
import com.basica.ProjetoKellyLojaUnit.Marca;
import com.basica.ProjetoKellyLojaUnit.Produto;

import com.repositorio.ProjetoKellyLojaunit.FornecedorRepositorio;
import com.repositorio.ProjetoKellyLojaunit.MarcaRepositorio;
import com.repositorio.ProjetoKellyLojaunit.ProdutoRepositorio;

@Controller // This means that this class is a Controller
@RequestMapping(path="/produto") // This means URL's start with /demo (after Application path)

public class produtoController {
			
	@Autowired
	private FornecedorRepositorio fornecedorRepository;
	@Autowired
	private MarcaRepositorio marcaRepository;
	@Autowired
	private ProdutoRepositorio produtoRepository;
	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewProduto (@RequestParam String nomeProduto
	      , @RequestParam String descricao, @RequestParam double precoUnitario,
	      @RequestParam Integer unidade, @RequestParam Integer idFornecedor,
			@RequestParam Integer idMarca) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Produto p = new Produto();
	    p.setNomeProduto(nomeProduto);
	    p.setDescricao(descricao);
	    p.setPrecoUnitario(precoUnitario);
	    p.setUnidade(unidade);
	    Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).get();
		p.setFornecedor(fornecedor);
		Marca marca = marcaRepository.findById(idMarca).get();
		p.setMarca(marca);	  	    	    
	    produtoRepository.save(p);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<Produto> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return produtoRepository.findAll();
	  }

	  @GetMapping(path="/procurar/{id}")
		public @ResponseBody Optional<Produto> getProdutoById(@PathVariable("id")Integer id){
			return produtoRepository.findById(id);
		}
		
		@DeleteMapping(path="/excluir/{id}")
		public @ResponseBody String deleteBydId(@PathVariable("id")Integer id) {
			if(produtoRepository.existsById(id)) {
				produtoRepository.deleteById(id);
				return "Produto excluído";
			}
			return "Produto não encontrado para exclusão, verifique o ID";
		}
		
		
		@PutMapping(path="/atualizar/{id}")
		public @ResponseBody String updateProdutoById(@RequestParam String nomeProduto
			      , @RequestParam String descricao, @RequestParam double precoUnitario,
			      @RequestParam Integer unidade, @RequestParam Integer idFornecedor,
					@RequestParam Integer idMarca,
				@PathVariable("id")Integer idProduto) {
			
			if(produtoRepository.existsById(idProduto)) {
				 Produto p = new Produto();
				    p.setNomeProduto(nomeProduto);
				    p.setDescricao(descricao);
				    p.setPrecoUnitario(precoUnitario);
				    p.setUnidade(unidade);
				   	Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).get();
					p.setFornecedor(fornecedor);
					Marca marca = marcaRepository.findById(idMarca).get();
					p.setMarca(marca);	
					produtoRepository.save(p);
				return "Produto atualizado: ";
			}
			return "Produto não encontrado";
		}
}