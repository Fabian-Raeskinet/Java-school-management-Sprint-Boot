package com.example.eleve;

import entities.EleveEntity;
import modele.InterfModeleEleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/eleves")
public class RestEleve {
    @Autowired
    private InterfModeleEleve mde;

    //-------------------Retrouver tous les élèves--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<EleveEntity>> listAllEleves() throws Exception{
        List<EleveEntity> eleves;
        eleves = mde.all();
        return new ResponseEntity<>(eleves, HttpStatus.OK);
    }

    //-------------------Retrouver l'élève poortant un matricule donné--------------------------------------------------------
    @RequestMapping(value = "/matricule={matricule}", method = RequestMethod.GET)
    public ResponseEntity<EleveEntity> listEleveMatricule(@PathVariable(value="matricule") String matricule) throws Exception{
        System.out.println("recherche de "+matricule);
        EleveEntity eleve;
        eleve = mde.rech(matricule);
        return new ResponseEntity<>(eleve, HttpStatus.OK);
    }

    //-------------------Retrouver l'élève correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EleveEntity> getEleve(@PathVariable(value = "id") String id)  throws Exception{
        System.out.println("recherche de l'élève d' id " + id);
        int identifiant = Integer.parseInt(id);
        EleveEntity eleve = mde.read(identifiant);
        return new ResponseEntity<>(eleve, HttpStatus.OK);
    }

    //-------------------Retrouver l'élève correspondant à une date donnée--------------------------------------------------------
    @RequestMapping(value = "/date={date}", method = RequestMethod.GET)
    public ResponseEntity<List<EleveEntity>> getEleve(@PathVariable(value = "date") Date date)  throws Exception{
        System.out.println("recherche de l'élève de date " + date);
        List<EleveEntity> eleves = mde.rechDate(date);
        return new ResponseEntity<>(eleves, HttpStatus.OK);
    }
    //-------------------Créer un élève--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EleveEntity> createEleve(@RequestBody EleveEntity eleve) throws Exception {
        System.out.println("Création de l'élève " + eleve.getMatricule());
        mde.create(eleve);
        return new ResponseEntity<>(eleve, HttpStatus.OK);
    }

    //-------------------Mettre à jour d'un élève d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EleveEntity> majEleve(@PathVariable(value = "id") int id,@RequestBody EleveEntity nouveleve) throws Exception{
        System.out.println("maj de l'élève id =  " + id);
        EleveEntity eleve = mde.read(id);
        mde.update(eleve,nouveleve);
        return new ResponseEntity<>(eleve, HttpStatus.OK);
    }

    //-------------------Effacer un élève d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEleve(@PathVariable(value = "id") int id) throws Exception{
        System.out.println("effacement de l'élève d'id " + id);
        EleveEntity eleve = mde.read(id);
        mde.del(eleve);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void>  handleIOException(Exception ex) {
        System.out.println("erreur : "+ex.getMessage());
        return ResponseEntity.notFound().header("error",ex.getMessage()).build();
    }
}
