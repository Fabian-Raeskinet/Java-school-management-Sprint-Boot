package com.example.eleve;


import entities.ClasseEntity;
import entities.EleveEntity;
import modele.InterfModeleClasse;
import modele.InterfModeleEleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/eleve")
public class EleveCtrlA {

    @Autowired
    private InterfModeleEleve mde;

    @Autowired
    private InterfModeleClasse mdcl;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Map<String, Object> model) {
        model.put("mode", "");
        return "eleve/gesteleveA";
    }

    @RequestMapping("/nouvel")
    String nouvel(Map<String, Object> model) {
        model.put("mesclasses", mdcl.all());
        model.put("mode", "nouvel");
        return "eleve/gesteleveA";
    }


    @RequestMapping(value = "/addel", method = RequestMethod.POST)
    String ajout(
            @RequestParam("idclasse") int idclasse,
            @RequestParam("matricule") String matricule,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("tel") String tel,
            @RequestParam("date") Date date,
            Map<String, Object> model) throws Exception {
        ClasseEntity classe = mdcl.read(idclasse);
        EleveEntity el = new EleveEntity(0, matricule, nom, prenom, tel, date, classe);
        mde.create(el);
        model.put("mode", "affnouvel");
        model.put("monEl", el);
        return "eleve/gesteleveA";
    }


    @RequestMapping(value = "/rechel", method = RequestMethod.GET)
    String rechel(Map<String, Object> model) {
        model.put("mode", "selection");
        return "eleve/gesteleveA";
    }


    @RequestMapping(value = "/recherche", method = RequestMethod.POST)
    String selection(@RequestParam(value = "idEleve") int idEleve, Map<String, Object> model) throws Exception {
        EleveEntity el = mde.read(idEleve);
        model.put("monEleve", el);
        model.put("mode", "modifel");
        return "eleve/gesteleveA";
    }
    @RequestMapping(value = "/recherchedate", method = RequestMethod.POST)
    String selection(@RequestParam(value = "date") Date date, Map<String, Object> model) throws Exception {
        List<EleveEntity> lel = mde.rechDate(date);
        model.put("mesEleves", lel);
        model.put("mode", "elevestrouves");
        return "eleve/gesteleveA";
    }


    @RequestMapping(value = "/modifel", method = RequestMethod.POST)
    String changement(@RequestParam(value = "idEleve") int idEleve,
                      @RequestParam("date") Date date,
                      @RequestParam("tel") String tel,
                      @RequestParam("nom") String nom,
                      @RequestParam("prenom") String prenom,
                      @RequestParam("matricule") String matricule,
                      @RequestParam("Action") String action
            , Map<String, Object> model) throws Exception {

        EleveEntity el = mde.read(idEleve);
        if (action.equals("Supprimer") || action.equals("Delete")) {
            mde.del(el);
        } else {
            EleveEntity nouveleve = new EleveEntity();
            nouveleve.setTel(tel);
            nouveleve.setNom(nom);
            nouveleve.setPrenom(prenom);
            nouveleve.setMatricule(matricule);
            nouveleve.setDateNaissance(date);
            mde.update(el, nouveleve);
            model.put("monEleve", el);
            List<EleveEntity> liste = mde.all();
            model.put("mesEleves", liste);
            model.put("mode", "elevestrouves");

        }
        return "eleve/gesteleveA";
    }


    @RequestMapping("/touseleves")
    String toutesclasses(Map<String, Object> model) {
        List<EleveEntity> liste = mde.all();
        model.put("mesEleves", liste);
        model.put("mode", "elevestrouves");
        return "eleve/gesteleveA";
    }



    @ExceptionHandler({Exception.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("eleve/gesteleveA");
        model.addObject("mode", "erreur");
        model.addObject("erreur", ex.getMessage());
        return model;
    }
}
