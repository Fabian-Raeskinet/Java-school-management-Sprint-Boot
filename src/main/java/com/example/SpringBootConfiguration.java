package com.example;

import modele.InterfModeleClasse;
import modele.InterfModeleEleve;
import modele.ModeleClasse;
import modele.ModeleEleve;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class SpringBootConfiguration implements WebMvcConfigurer {
    //WebMvcConfigurer est une interface destinée à gérer les locales
    //elle demande le développement de la méthode "addInterceptors"

    @Bean
    public LocaleResolver localeResolver() {
        //la locale est un objet encapsulant la langue en cours d'utilisation
        //ce bean permet de connaître la locale actuellement en cours d'utilisation
        //et de la positionner à une valeur par défaut
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.FRANCE);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        //ce bean permet de détecter l'envoi d'un paramètre "lang"
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //on enregistre le bean ci-dessus dans la liste des "intercepteurs" à activer
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    //singleton par défaut => le même pour toutes les sessions mais ici on utilise le mode session  pour avoir des caches moins chargés
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    InterfModeleClasse mdcl() {
        System.out.println("création du modèle Classe");
        return new ModeleClasse();
        // return new ModeleClientList();
    }
    @Bean
    //singleton par défaut => le même pour toutes les sessions mais ici on utilise le mode session  pour avoir des caches moins chargés
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    InterfModeleEleve mde() {
        System.out.println("création du modèle Eleve");
        return new ModeleEleve();
    }
}
