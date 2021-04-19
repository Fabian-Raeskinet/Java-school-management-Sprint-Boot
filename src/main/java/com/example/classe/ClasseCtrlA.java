package com.example.classe;
import entities.ClasseEntity;
import entities.EleveEntity;
import modele.InterfModeleClasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/classe")

public class ClasseCtrlA {

    @Autowired
    private InterfModeleClasse mdc;
    @RequestMapping(value="/", method= RequestMethod.GET)
    String index( Map<String, Object> model) {
        model.put("mode","");
        return "classe/gestclasseA";
    }

    @RequestMapping(value="/nouvclasse", method= RequestMethod.GET)
    String nouvcli( Map<String, Object> model) {
        model.put("mode","nouvclasse");
        return "classe/gestclasseA";
    }



    @RequestMapping(value="/addclasse", method= RequestMethod.POST)
    String ajout(
            @RequestParam("sigle") String sigle,
            @RequestParam("titulaire") String titulaire,
            @RequestParam("annee") int annee,

            Map<String, Object> model) throws Exception{
        ClasseEntity cl = new ClasseEntity(0,sigle,annee,titulaire);
        mdc.create(cl);
        model.put("maClasse",cl);
        model.put("mode","affnouvclasse");
        return "classe/gestclasseA";
    }

    @RequestMapping(value="/rechclasse", method= RequestMethod.GET)
    String rechcli(Map<String, Object> model) {
        model.put("mode","selection");
        return "classe/gestclasseA";
    }


    @RequestMapping(value="/recherche", method= RequestMethod.POST)
    String selection(@RequestParam(value="numclasse") int numclasse, Map<String, Object> model) throws Exception{
        ClasseEntity cldb = mdc.read(numclasse);
        model.put("maClasse", cldb);
        model.put("mode","modifclasse");
        return "classe/gestclasseA";
    }

    @RequestMapping(value="/modifclasse", method= RequestMethod.POST)
    String changement(@RequestParam("idclasse")int idclasse,
                      @RequestParam("titulaire") String titulaire,
                      @RequestParam("annee") int annee,
                      @RequestParam("Action") String action,
                      Map<String, Object> model) throws Exception{

        ClasseEntity classe = mdc.read(idclasse);
        if(action.equals("Supprimer") || action.equals("Delete"))
        {
            mdc.del(classe);
        }
        else
        {
            ClasseEntity nouvclasse= new ClasseEntity();
            nouvclasse.setTitulaire(titulaire);
            nouvclasse.setAnnee(annee);
            mdc.update(classe,nouvclasse);
            List<ClasseEntity> liste;
            liste = mdc.all();
            model.put("mesClasses", liste);
            model.put("mode","classestrouvees");
        }
        return "classe/gestclasseA";
    }


    @RequestMapping("/toutesclasses")
    String toutesclasses(Map<String, Object> model) {
        List<ClasseEntity> liste = mdc.all();
        model.put("mesClasses", liste);
        model.put("mode","classestrouvees" );
        return "classe/gestclasseA";
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("classe/gestclasseA");
        model.addObject("mode","erreur");
        model.addObject("erreur", ex.getMessage());
        return model;
    }
}
