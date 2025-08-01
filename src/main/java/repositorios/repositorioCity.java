/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.City;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author roble
 */
@Stateless
public class repositorioCity {

    @Inject
    EntityManager em;

    public void Guardar(City c) {
        if (c.getId() != null && c.getId() > 0) {
            em.merge(c); //actualiza
        } else {
            em.persist(c); //inserta
        }
    }

    public void Eliminar(Integer id) {
        PorId(id).ifPresent(c -> {
            em.remove(c); // Elimina la ciudad si existe
        });
    }

    public Optional<City> PorId(Integer id) {
        return Optional.ofNullable(em.find(City.class, id));
    }

    //busca por nombre
    public City PorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        try {
            Query q = em.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class);
            q.setParameter("name", nombre.trim());
            q.setMaxResults(1);
            return (City) q.getSingleResult();
        } catch (NoResultException e) {
            return null; // No se encontró la ciudad
        } catch (Exception e) {
            System.err.println("Error al buscar ciudad: " + e.getMessage());
            return null;
        }
    }
    
    public List<City> PorDistrito(String distrito) {
        if (distrito == null || distrito.trim().isEmpty()) {
            return null;
        }
        try {
            Query q = em.createQuery("SELECT c FROM City c WHERE c.district = :district", City.class);
            q.setParameter("district", distrito.trim());
          //  q.setMaxResults(1);
            return q.getResultList();
        } catch (NoResultException e) {
            return null; // No se encontró la ciudad
        } catch (Exception e) {
            System.err.println("Error al buscar distrito: " + e.getMessage());
            return null;
        }
    }

    //listar todo
    public List<City> Listar() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    public boolean existeCiudad(String nombre, String distrito, String codigoDePais) {
        Query q = em.createQuery("SELECT c FROM City c WHERE c.name = :name AND c.district = :district AND c.countryCode = :countryCode", City.class);
        q.setParameter("name", nombre);
        q.setParameter("district", distrito);
        q.setParameter("countryCode", codigoDePais);

        return !q.getResultList().isEmpty();
    }

    public List<Object[]> poblacionPorDistrito(String codigoPais) {
        Query q = em.createQuery(
                "SELECT c.district, SUM(c.population), co.name "
                + "FROM City c JOIN Country co ON c.countryCode = co.code "
                + "WHERE co.code = :codigoPais "
                + "GROUP BY c.district, co.name"
        );

        q.setParameter("codigoPais", codigoPais);
        return q.getResultList(); // Lista de Object[]: [district, suma, nombrePais]
    }

}
