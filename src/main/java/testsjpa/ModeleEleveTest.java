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
import java.util.Collection;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModeleEleveTest {
    ModeleClasse mdcl;
    ModeleEleve mde;
    ClasseEntity cl;
    EleveEntity el;

    @BeforeEach
    void setUp() {
        mdcl = new ModeleClasse();
        mde = new ModeleEleve();
        try {
            cl = new ClasseEntity();
            cl.setSigle("SigleTest");
            cl.setAnnee(1);
            cl.setTitulaire("TitulaireTest");
            mdcl.create(cl);

            el = new EleveEntity();
            el.setClasseByIdClasse(cl);
            el.setMatricule("MatriculeTest");
            el.setNom("NomTest");
            el.setPrenom("PrenomTest");
            el.setTel("00001");
            el.setDateNaissance(new Date(2000, 5, 17));
            mde.create(el);
        } catch (Exception e) {
            fail("erreur de création de l'élève");
        }
    }

    @AfterEach
    void tearDown() {
        try {
            mde.del(el);
            mdcl.del(cl);
        } catch (Exception e) {
            fail("erreur d'effacement de l'élève");
        }
        mdcl.close();
        mde.close();
    }

    @Test
    void create() {
        assertNotEquals(0, el.getIdEleve(), "id de l'élève non incrémenté");
    }

    @Test
    void creationDoublon() {
        EleveEntity el2 = new EleveEntity();
        el2.setMatricule("MatriculeTest");
        el2.setNom("NomTest");
        el2.setPrenom("PrenomTest");
        el2.setDateNaissance(new Date(2000, 5, 17));
        el2.setTel("00001");
        el2.setClasseByIdClasse(cl);
        Assertions.assertThrows(Exception.class, () -> {
            mde.create(el2);
        }, "création d'un doublon");
    }

    @Test
    void affCollection() {
        try {
            cl = mdcl.read(cl.getIdClasse());
            Collection<EleveEntity> le = cl.getElevesByIdClasse();
            boolean trouve = false;
            for (EleveEntity ele : le) {
                if (ele.getIdEleve() == el.getIdEleve()) {
                    trouve = true;
                    break;
                }
            }
            assertTrue(trouve, "eleve non present dans la liste de classe");
        } catch (Exception e) {
            fail("Erreur de recherche " + e);
        }
    }

    @Test
    void read() {
        try {
            int idEleve = el.getIdEleve();
            EleveEntity el2 = mde.read(idEleve);
            assertEquals("MatriculeTest", el2.getMatricule(), "matricule différents Matriculetest - " + el2.getMatricule());
        } catch (Exception e) {
            fail("recherche infructueuse");
        }
    }

    @Test
    void update() {
        EleveEntity el2 = new EleveEntity();
        el2.setMatricule("MatriculeTest2");
        el2.setNom("NomTest2");
        el2.setPrenom("PrenomTest2");
        el2.setTel("00002");
        el2.setDateNaissance(new Date(2000, 2, 23));
        try {
            mde.update(el, el2);
            el = mde.read(el.getIdEleve());
            assertEquals("MatriculeTest2", el.getMatricule(), "matricules différents");
            assertEquals("NomTest2", el.getNom(), "noms différents");
            assertEquals("PrenomTest2", el.getPrenom(), "prenoms différents");
            assertEquals("00002", el.getTel(), "tels différents");
            assertEquals(new Date(2000, 2, 23), el.getDateNaissance(), "tels différents");
        } catch (Exception e) {
            fail("erreur d'update");
        }
    }

    @Test
    void del() {
        try {
            mde.del(el);
            Assertions.assertThrows(Exception.class, () -> {
                mde.read(el.getIdEleve());
            }, "record non effacé");
            mdcl.del(cl);
            setUp();
        } catch (Exception e) {
            fail("erreur d'effacement " + e);
        }
    }

    @Test
    void all() {
        List<EleveEntity> le = mde.all();
        boolean trouve = false;
        for (EleveEntity ele : le) {
            if (ele.getMatricule().equals("MatriculeTest")) {
                trouve = true;
                break;
            }
        }
        assertTrue(trouve, "record non trouvé dans la liste");
    }

    @Test
    void rech() {
        try {
            EleveEntity e = mde.rech("MatriculeTest");
            boolean trouve = false;
            if (e.getMatricule().equals("MatriculeTest")) {
                trouve = true;
            }
            assertTrue(trouve, "record non trouvé dans la liste");
        } catch (Exception e) {
            fail("erreur de reccherche");
        }
    }

    @Test
    void rechNom() {
        EleveEntity el2 = new EleveEntity();
        try {
            el2 = new EleveEntity();
            el2.setClasseByIdClasse(cl);
            el2.setMatricule("MatriculeTest1");
            el2.setNom("NomTest");
            el2.setPrenom("PrenomTest1");
            el2.setTel("00003");
            el2.setDateNaissance(new Date(2000, 5, 17));
            mde.create(el2);
        } catch (Exception e) {
            fail("erreur de création du deuxième élève : " + e);
        }

        try {
            List<EleveEntity> le = mde.rechNom("NomTest");
            boolean trouve = false;
            for (EleveEntity eleve : le) {
                if (eleve.getNom().equals("NomTest")) {
                    trouve = true;
                    break;
                }
            }
            assertTrue(trouve, "record non trouvé dans la liste");

        } catch (Exception e) {
            fail("erreur de recherche : " + e);
        } finally {
            try {
                mde.del(el2);
            } catch (Exception e) {
                fail("erreur de suppresion");
            }
        }
    }
}
