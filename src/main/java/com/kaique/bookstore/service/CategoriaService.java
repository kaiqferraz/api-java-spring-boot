package com.kaique.bookstore.service;

import com.kaique.bookstore.domain.Categoria;
import com.kaique.bookstore.dtos.CategoriaDTO;
import com.kaique.bookstore.service.exceptions.DataIntegrityViolationException;
import com.kaique.bookstore.service.exceptions.ObjectNotFoundException;
import com.kaique.bookstore.repositories.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById (Integer id){
        Optional<Categoria> obj = categoriaRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Categoria não encontrada! ID: " + id +  ", TIPO: " + Categoria.class.getName() ));

    }

    public List<Categoria> findAll(){
        return  categoriaRepository.findAll();

    }

    public Categoria create(Categoria obj) {
        obj.setId(null);
        return categoriaRepository.save(obj);
    }

    public Categoria update(Integer id, CategoriaDTO objDto){
        Categoria obj = findById(id);
        obj.setNome(objDto.getNome());
        obj.setDescricao(objDto.getDescricao());
        return categoriaRepository.save(obj);
    }

    public void delete(Integer id){
        findById(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Categoria não pode ser deletada! Possui livros associados");
        }

    }


}
