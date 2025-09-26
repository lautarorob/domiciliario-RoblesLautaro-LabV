/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Country;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author roble
 */
@Stateless
public class repositorioCountry {

    @Inject
    EntityManager em;

    public void Guardar(Country cy) {
        if (cy.getCode() != null) {
            em.merge(cy);
        } else {
            em.persist(cy);
        }
    }

    public void Eliminar(String code) {
        PorCode(code).ifPresent(country -> {
            // 1. Eliminar SOLO los idiomas de este país específico
            em.createNativeQuery("DELETE FROM countrylanguage WHERE CountryCode = ?")
                    .setParameter(1, code)
                    .executeUpdate();

            // 2. Eliminar ciudades de este país
            em.createNativeQuery("DELETE FROM city WHERE CountryCode = ?")
                    .setParameter(1, code)
                    .executeUpdate();

            // 3. Forzar ejecución
            em.flush();

            // 4. Eliminar el país
            em.remove(country);
        });
    }

    public Optional<Country> PorCode(String code) {
        return Optional.ofNullable(em.find(Country.class, code));
    }

    public Country PorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        try {
            Query q = em.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class);
            q.setParameter("name", nombre.trim());
            q.setMaxResults(1);
            return (Country) q.getSingleResult();
        } catch (Exception e) {
            System.err.println("No se pudo encontrar la ciudad");
            return null;
        }
    }

    public List<Country> Listar() {
        return em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    }

    public List<Object[]> poblacionYEsperanzaVidaPorRegion() {
        Query q = em.createQuery(
                "SELECT c.continent, c.region, SUM(c.population), AVG(c.lifeExpectancy) "
                + "FROM Country c "
                + "GROUP BY c.continent, c.region "
                + "ORDER BY c.continent ASC, c.region ASC"
        );
        return q.getResultList(); // Lista de Object[]: [continent, region, sumaPoblacion, promedioEsperanzaVida]
    }

}
