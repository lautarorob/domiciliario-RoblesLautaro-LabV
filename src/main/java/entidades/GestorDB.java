/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.enterprise.inject.Produces;

/**
 *
 * @author roble
 */
public class GestorDB {

    @PersistenceContext(name = "com.mycompany_domiciliario_war_1.0-SNAPSHOTPU")

    EntityManager em;

    @RequestScoped
    @Produces
    public EntityManager generarEM() {
        return em;
    }

}
