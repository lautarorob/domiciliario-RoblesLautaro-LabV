/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.City;
import entidades.Country;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import repositorios.repositorioCity;
import repositorios.repositorioCountry;

/**
 *
 * @author roble
 */
@Named(value = "controladorCity")
@RequestScoped
public class controladorCity {

    @Inject
    repositorioCity repositorio;

    private City city;
    private Integer id;

    @Inject
    repositorioCountry repoCountry;

    private List<Country> listaPaises;
    private String codigoPaisSeleccionado;
    private List<Object[]> poblacionDistritos;
    private List<PaisConDistritos> poblacionTodosLosPaises;

    public controladorCity() {
    }

    // Clase interna para representar un país con sus distritos
    public static class PaisConDistritos {
        private String nombrePais;
        private String codigoPais;
        private List<Object[]> distritos;

        public PaisConDistritos(String nombrePais, String codigoPais, List<Object[]> distritos) {
            this.nombrePais = nombrePais;
            this.codigoPais = codigoPais;
            this.distritos = distritos;
        }

        public String getNombrePais() {
            return nombrePais;
        }

        public void setNombrePais(String nombrePais) {
            this.nombrePais = nombrePais;
        }

        public String getCodigoPais() {
            return codigoPais;
        }

        public void setCodigoPais(String codigoPais) {
            this.codigoPais = codigoPais;
        }

        public List<Object[]> getDistritos() {
            return distritos;
        }

        public void setDistritos(List<Object[]> distritos) {
            this.distritos = distritos;
        }
    }

    @Model
    @Produces
    public City city() {
        System.out.println("id " + id);
        if (id != null && id > 0) {
            repositorio.PorId(id).ifPresent(c -> {
                city = c;
            });
        } else {
            city = new City();
        }
        return city;
    }

    public List<City> listadoCities() {
        return repositorio.Listar();
    }

    public City cityPorNombre(String nombre) {
        return repositorio.PorNombre(nombre);
    }
    
    public List<City> cityPorDistrito(String distrito) {
        return repositorio.PorDistrito(distrito);
    }

    public String guardar() {
        if (repositorio.existeCiudad(city.getName(), city.getDistrict(), city.getCountryCode())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La Ciudad ya existe", null));
            return null;
        }
        repositorio.Guardar(city);
        return "/cities/index.xhtml?faces-redirect=true";
    }

    public String eliminar(Integer id) {
        repositorio.Eliminar(id);
        return "/cities/index.xhtml?faces-redirect=true";
    }

    @PostConstruct
    public void init() {
        listaPaises = repoCountry.Listar();
        poblacionDistritos = Collections.emptyList();
        cargarPoblacionTodosLosPaises();
    }

    public void cargarPoblacionTodosLosPaises() {
        poblacionTodosLosPaises = new ArrayList<>();
        
        for (Country pais : listaPaises) {
            List<Object[]> distritosDelPais = repositorio.poblacionPorDistrito(pais.getCode());
            if (!distritosDelPais.isEmpty()) {
                poblacionTodosLosPaises.add(new PaisConDistritos(pais.getName(), pais.getCode(), distritosDelPais));
            }
        }
    }

    public void mostrarPoblacionDistritos() {
        if (codigoPaisSeleccionado != null && !codigoPaisSeleccionado.isEmpty()) {
            poblacionDistritos = repositorio.poblacionPorDistrito(codigoPaisSeleccionado);
        } else {
            poblacionDistritos = Collections.emptyList();
        }
    }

    // Getters y Setters
    public List<Country> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Country> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public City getCity() {
        if (city == null) {
            city();
        }
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrePais(String countryCode) {
        return repoCountry.Listar().stream()
                .filter(c -> c.getCode().equals(countryCode))
                .map(Country::getName)
                .findFirst()
                .orElse("Desconocido");
    }

    public String getCountryCode() {
        return city != null ? city.getCountryCode() : null;
    }

    public void setCountryCode(String countryCode) {
        if (city != null) {
            city.setCountryCode(countryCode);
        }
    }

    public String getCodigoPaisSeleccionado() {
        return codigoPaisSeleccionado;
    }

    public void setCodigoPaisSeleccionado(String codigoPaisSeleccionado) {
        this.codigoPaisSeleccionado = codigoPaisSeleccionado;
    }

    public List<Object[]> getPoblacionDistritos() {
        return poblacionDistritos;
    }

    public void setPoblacionDistritos(List<Object[]> poblacionDistritos) {
        this.poblacionDistritos = poblacionDistritos;
    }

    public List<PaisConDistritos> getPoblacionTodosLosPaises() {
        return poblacionTodosLosPaises;
    }

    public void setPoblacionTodosLosPaises(List<PaisConDistritos> poblacionTodosLosPaises) {
        this.poblacionTodosLosPaises = poblacionTodosLosPaises;
    }
}