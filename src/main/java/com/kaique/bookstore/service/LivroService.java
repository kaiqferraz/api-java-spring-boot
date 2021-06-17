package com.kaique.bookstore.service;




import com.kaique.bookstore.domain.Categoria;
import com.kaique.bookstore.domain.Livro;
import com.kaique.bookstore.repositories.LivroRepository;
import com.kaique.bookstore.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaService categoriaService;



    public Livro findById (Integer id){
        Optional<Livro> obj = livroRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Livro não encontrado! ID: " + id +  ", TIPO: " + Livro.class.getName() ));

    }

    public List<Livro> findAll(Integer id_cat){
        categoriaService.findById(id_cat);
        return livroRepository.findAllByCategoria(id_cat);

    }

    public Livro update(Integer id, Livro obj){
        Livro newObj = findById(id);
        updateData(newObj,obj);
        return livroRepository.save(newObj);

    }

    public void updateData(Livro  newObj, Livro obj){
        newObj.setNome_autor(obj.getNome_autor());
        newObj.setTexto(obj.getTexto());
        newObj.setTitulo(obj.getTitulo());

    }


    public Livro create(Integer id_cat, Livro obj) {
        obj.setId(null);
        Categoria cat = categoriaService.findById(id_cat);
        obj.setCategoria(cat);
        return livroRepository.save(obj);
    }

    public void delete(Integer id){
        Livro obj = findById(id);
        livroRepository.delete(obj);
    }





}
