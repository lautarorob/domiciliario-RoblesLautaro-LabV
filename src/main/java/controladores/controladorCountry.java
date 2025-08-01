/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.Country;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.repositorioCountry;

/**
 *
 * @author roble
 */
@Named(value = "controladorCountry")
@RequestScoped
public class controladorCountry {

    /**
     * Creates a new instance of controladorCountry
     */
    @Inject
    repositorioCountry repositorio;

    private Country country;

    private String code;

    public controladorCountry() {
    }
    
    @Model
    @Produces
    public Country country() {
        System.out.println("code " + code);
        if (code != null && !code.trim().isEmpty()) {
            repositorio.PorCode(code).ifPresent(cy -> {
                country = cy;
            });
        } else {
            country = new Country();
        }
        return country;
    }

    /*
    public String guardar(){
        repositorio.Guardar(country);
        return "index.xhtml?faces-redirect=true";
    }
    
    public String eliminar(){
        repositorio.Eliminar(code);
        return "index.xhtml?faces-redirect=true";
    }*/

    public Country countryPorNombre(String nombre) {
        return repositorio.PorNombre(nombre);
    }

    public Country countryPorCode(String code) {
        return repositorio.PorCode(code).orElse(null);
    }

    public List<Country> listadoCountries() {
        return repositorio.Listar();
    }

    public Country getCountry() {
        if (country == null && code != null) {
            country = repositorio.PorCode(code).orElse(null);
        }
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
