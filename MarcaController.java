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

import com.basica.ProjetoKellyLojaUnit.Marca;
import com.repositorio.ProjetoKellyLojaunit.MarcaRepositorio;

@Controller 
@RequestMapping(path="/marca") 
public class MarcaController {
	@Autowired
	private MarcaRepositorio marcaRepository;
	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewMarca (@RequestParam String nomeFabricante
	      , @RequestParam String descricao) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Marca m = new Marca();
	    m.setNomeFabricante(nomeFabricante);
	    m.setDescricao(descricao);
	    
	    marcaRepository.save(m);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<Marca> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return marcaRepository.findAll();
	  }
	  
	  @GetMapping(path="/procurar/{id}")
		public @ResponseBody Optional<Marca> getMarcaById(@PathVariable("id")Integer id){
			return marcaRepository.findById(id);
		}
		
		
		@DeleteMapping(path="/excluir/{id}")
		public @ResponseBody String deleteMarcaById(@PathVariable("id")Integer id) {
			if(marcaRepository.existsById(id)) {
				marcaRepository.deleteById(id);
				return "Marca excluída";
			}
			return "Marca não encontrada para deleção, verifique o ID";
		}
		
		@PutMapping(path="/atualizar/{id}")
		public @ResponseBody String updateMarcaById(@RequestParam String nomeFabricante, @RequestParam String descricao,
				@PathVariable("id")Integer idMarca) {
			if(marcaRepository.existsById(idMarca)) {
				Marca m = new Marca();
				m.setIdMarca(idMarca);
				m.setNomeFabricante(nomeFabricante);
				m.setDescricao(descricao);
				    
				marcaRepository.save(m);
				return "Marca atualizada: ";
			}
			return "Marca não encotrada";
		}
	}

	


