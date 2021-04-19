package com.example;

import entities.ClasseEntity;
import entities.EleveEntity;
import modele.InterfModele;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Racine {
    @Autowired
    private InterfModele<ClasseEntity> mdcl;

    @Autowired
    private InterfModele<EleveEntity> mdc;

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/quitter")
    String quitter() {
        mdcl.close();
        mdc.close();
        return "fin";
    }
}
