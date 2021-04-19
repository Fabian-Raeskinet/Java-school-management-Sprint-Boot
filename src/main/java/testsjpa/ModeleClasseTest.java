package testsjpa;

import entities.ClasseEntity;
import entities.EleveEntity;
import modele.ModeleClasse;
import modele.ModeleEleve;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModeleClasseTest {
    ModeleClasse mdcl;
    ModeleEleve mde;
    ClasseEntity cl;

    @BeforeEach
    void setUp() {
        mdcl = new ModeleClasse();
        mde = new ModeleEleve();
        try{
            cl = new ClasseEntity();
            cl.setSigle("SigleTest");
            cl.setAnnee(3);
            cl.setTitulaire("TitulaireTest");
            mdcl.create(cl);
        }
        catch (Exception e){
            fail("erreur de création de la classe");
        }
    }

    @AfterEach
    void tearDown() {
        try{
            mdcl.del(cl);
        }
        catch (Exception e){
            fail("erreur d'éffacement de la classe");
        }
        mdcl.close();
        mde.close();
    }

    @Test
    void create() {
        assertNotEquals(0, cl.getIdClasse(), "id classe non incrémenté");
    }

    @Test
    void creationDoublon(){
        ClasseEntity cl2 = new ClasseEntity();
        cl2.setSigle("SigleTest");
        cl2.setAnnee(1);
        cl2.setTitulaire("TitulaireTest");
        Assertions.assertThrows(Exception.class, () -> {
            mdcl.create(cl2);
        },"création d'un doublon");
    }

    @Test
    void read() {
        try{
            int numclasse = cl.getIdClasse();
            ClasseEntity cl2 = mdcl.read(numclasse);
            assertEquals("SigleTest", cl2.getSigle(), "sigles différents "+"SigleTest - "+cl2.getSigle());
        }
        catch (Exception e){
            fail("recherche infructueuse "+e);
        }
    }

    @Test
    void update() {
        ClasseEntity cl2 = new ClasseEntity();
        cl2.setSigle("SigleTest2");
        cl2.setTitulaire("TitulaireTest2");
        cl2.setAnnee(2);
        try{
            mdcl.update(cl, cl2);
            cl = mdcl.read(cl.getIdClasse());
            assertEquals("SigleTest2", cl.getSigle(), "sigles différents SigleTest2 - "+cl.getSigle());
            assertEquals("TitulaireTest2", cl.getTitulaire(), "Titulaires différents TitulaireTest2 - "+cl.getTitulaire());
            assertEquals(2,cl.getAnnee(), "années différentes 1 - "+cl.getAnnee());
        }
        catch (Exception e){
            fail("erreur d'update "+e);
        }
    }

    @Test
    void del() {
        try{
            mdcl.del(cl);
            Assertions.assertThrows(Exception.class, () ->{
                mdcl.read(cl.getIdClasse());
            }, "record non effacé");
            setUp();
        }
        catch (Exception e){
            fail("erreur d'effacement "+e);
        }
    }

    @Test
    void delAvecEleve(){
        try{
            EleveEntity el = new EleveEntity();
            el.setClasseByIdClasse(cl);
            el.setMatricule("MatriculeTest");
            el.setNom("NomTest");
            el.setPrenom("PrenomTest");
            el.setTel("00001");
            el.setDateNaissance(new Date(2000,5,17));
            mde.create(el);
            Assertions.assertThrows(Exception.class, () ->{
                mdcl.del(cl);
            }, "effacement réussi malgré eleve lié");
            mde.del(el);
        }
        catch(Exception e){
            fail("erreur de création d'élève "+e);
        }
    }

    @Test
    void all() {
        List<ClasseEntity> lcl = mdcl.all();
        boolean trouve = false;
        for(ClasseEntity c : lcl){
            if(c.getSigle().equals("SigleTest")){
                trouve = true;
                break;
            }
        }
        assertTrue(trouve, "record non trouvé dans la liste");
    }

    @Test
    void rech() {
        try{
            ClasseEntity cl = mdcl.rech("SigleTest");
            boolean trouve = false;
                if(cl.getSigle().equals("SigleTest")){
                    trouve = true;
                }

            assertTrue(trouve, "record non trouvé dans la liste");
        }
        catch (Exception e){
            fail("erreur de reccherche");
        }
    }
}
