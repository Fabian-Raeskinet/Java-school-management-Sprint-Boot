package com.example.classe;

import entities.ClasseEntity;
import modele.InterfModeleClasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class RestClasse {
    @Autowired
    private InterfModeleClasse mdcl;

    //-------------------Retrouver toutes les Classes--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ClasseEntity>> listAllClasses() throws Exception{
        List<ClasseEntity> classes;
        classes = mdcl.all();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    //-------------------Retrouver la classe portant un sigle donné--------------------------------------------------------
    @RequestMapping(value = "/sigle={sigle}", method = RequestMethod.GET)
    public ResponseEntity<ClasseEntity> listClassesSigle(@PathVariable(value="sigle") String sigle) throws Exception{
        System.out.println("recherche de "+sigle);
        ClasseEntity classes;
        classes = mdcl.rech(sigle);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    //-------------------Retrouver la classe correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClasseEntity> getClasse(@PathVariable(value = "id") String id)  throws Exception{
        System.out.println("recherche de la classe d' id " + id);
        int identifiant = Integer.parseInt(id);
        ClasseEntity classe = mdcl.read(identifiant);
        return new ResponseEntity<>(classe, HttpStatus.OK);
    }
    //-------------------Créer une classe--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ClasseEntity> createClasse(@RequestBody ClasseEntity classe) throws Exception {
        System.out.println("Création de Classe " + classe.getSigle());
        mdcl.create(classe);
        return new ResponseEntity<>(classe, HttpStatus.OK);
    }

    //-------------------Mettre à jour d'une classe d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ClasseEntity> majClasse(@PathVariable(value = "id") int id,@RequestBody ClasseEntity nouvclasse) throws Exception{
        System.out.println("maj de la classe id =  " + id);
        ClasseEntity cl = mdcl.read(id);
        mdcl.update(cl,nouvclasse);
        return new ResponseEntity<>(cl, HttpStatus.OK);
    }

    //-------------------Effacer une classe d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteClasse(@PathVariable(value = "id") int id) throws Exception{
        System.out.println("effacement de la classe d'id " + id);
        ClasseEntity classe = mdcl.read(id);
        mdcl.del(classe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void>  handleIOException(Exception ex) {
        System.out.println("erreur : "+ex.getMessage());
        return ResponseEntity.notFound().header("error",ex.getMessage()).build();
    }
}
