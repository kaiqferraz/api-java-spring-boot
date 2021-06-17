package com.kaique.bookstore.resources;


import com.kaique.bookstore.domain.Livro;
import com.kaique.bookstore.dtos.LivroDTO;
import com.kaique.bookstore.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/livros")
public class LivroResources {

    @Autowired
    private LivroService livroService;

    // http://localhost:8080/livros/1  Buscar livro por id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Integer id){
        Livro obj = livroService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // http://localhost:8080/livros?categoria=10  Buscar livros por categoria
    @GetMapping
    public ResponseEntity<List<LivroDTO>> findAll(@RequestParam(value = "categoria",defaultValue = "0") Integer id_cat) {
        List<Livro> list = livroService.findAll(id_cat);
        List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }


    //http://localhost:8080/livros/1  Altera os atributos
    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> update( @PathVariable Integer id,@Valid @RequestBody Livro obj){
        Livro newObj = livroService.update(id, obj);
        return ResponseEntity.ok().body(newObj);
    }

    //http://localhost:8080/livros/1     Altera somente um atributo especifico.
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Livro> updatePatch( @PathVariable Integer id, @Valid @RequestBody Livro obj){
        Livro newObj = livroService.update(id, obj);
        return ResponseEntity.ok().body(newObj);
    }


    // http://localhost:8080/livros?categoria=1  Criar livro para uma categoria selecionada.
    @PostMapping
    public ResponseEntity<Livro> create (  @RequestParam(value = "categoria",defaultValue = "0") Integer id_cat, @Valid @RequestBody Livro obj){
        Livro newObj = livroService.create(id_cat,obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/livros/{id}").buildAndExpand(newObj.getId()).toUri();
        return  ResponseEntity.created(uri).build();
    }


    // http://localhost:8080/livros/1  Deleta um livro.
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        livroService.delete(id);
        return ResponseEntity.noContent().build();

    }






}
