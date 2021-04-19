package testsjpa;

import entities.ClasseEntity;
import modele.ModeleClasse;

public class TestModelClasse {
    public static void main(String[] args) {
        ModeleClasse mdc = new ModeleClasse();
        ClasseEntity cl = null;

        try {
            System.out.println("création d'une classe");
            cl = new ClasseEntity();
            cl.setSigle("Cl4");
            cl.setAnnee(2);
            cl.setTitulaire("Sullivan");
            mdc.create(cl);
            int idclasse = cl.getIdClasse();
            System.out.println("id classe = "+ idclasse);
            ClasseEntity ncl = new ClasseEntity();
            ncl.setSigle("Cl5");
            ncl.setAnnee(3);
            ncl.setTitulaire("Nelson");

            mdc.update(cl,ncl);
            cl = mdc.read(idclasse);
            System.out.println("nouvelle classe = "+cl.getSigle()+" "+ cl.getAnnee()+" "+ cl.getTitulaire() + " ");
            System.out.println("id classe trouvé par query :"+ cl.getIdClasse());

        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            mdc.del(cl);
            mdc.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
