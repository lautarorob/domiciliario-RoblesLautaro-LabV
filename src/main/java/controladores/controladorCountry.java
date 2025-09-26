/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.Country;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import repositorios.repositorioCountry;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    // Variables para el informe
    private List<String> listaContinentes;
    private String continenteSeleccionado;
    private List<Object[]> datosPorContinente;
    private List<ContinenteConRegiones> datosTodosLosContinentes;

    public controladorCountry() {
    }

    // Clase interna para representar un continente con sus regiones
    public static class ContinenteConRegiones {

        private String nombreContinente;
        private String codigoContinente;
        private List<Object[]> regiones;

        public ContinenteConRegiones(String nombreContinente, String codigoContinente, List<Object[]> regiones) {
            this.nombreContinente = nombreContinente;
            this.codigoContinente = codigoContinente;
            this.regiones = regiones;
        }

        public String getNombreContinente() {
            return nombreContinente;
        }

        public void setNombreContinente(String nombreContinente) {
            this.nombreContinente = nombreContinente;
        }

        public String getCodigoContinente() {
            return codigoContinente;
        }

        public void setCodigoContinente(String codigoContinente) {
            this.codigoContinente = codigoContinente;
        }

        public List<Object[]> getRegiones() {
            return regiones;
        }

        public void setRegiones(List<Object[]> regiones) {
            this.regiones = regiones;
        }
    }

    @Model
    @Produces
    public Country country() {
        //capturara CODE de la URL
        if (code == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String codeParam = params.get("code");

            if (codeParam != null && !codeParam.isEmpty()) {
                try {
                    this.code = codeParam;
                    System.out.println("CODE capturado de URL: " + this.code);
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear CODE: " + e.getMessage());
                }
            }
        }

        System.out.println("code " + code);
        if (code != null && !code.trim().isEmpty()) {
            repositorio.PorCode(code).ifPresent(cy -> {
                country = cy;
            });
        } else if (country == null) {
            country = new Country();
            // Inicializar con valores válidos por defecto

            country.setRegion(" ");
            country.setSurfaceArea(new BigDecimal("1.00"));
            country.setIndepYear(null);
            country.setPopulation(1);
            country.setLifeExpectancy(null);
            country.setGnp(null);
            country.setGNPOld(null);
            country.setLocalName(" ");
            country.setGovernmentForm(" ");
            country.setHeadOfState(null);
            country.setCapital(null);
            country.setCode2("XX");
        }
        return country;
    }

    public String guardar() {
        repositorio.Guardar(country);
        return "/countries/index.xhtml?faces-redirect=true";
    }

    public String eliminar(String code) {
        repositorio.Eliminar(code);
        return "/countries/index.xhtml?faces-redirect=true";
    }

    public Country countryPorNombre(String nombre) {
        return repositorio.PorNombre(nombre);
    }

    public Country countryPorCode(String code) {
        return repositorio.PorCode(code).orElse(null);
    }

    public List<Country> listadoCountries() {
        return repositorio.Listar();
    }

    public List<Object[]> poblacionYEsperanzaVidaPorRegion() {
        return repositorio.poblacionYEsperanzaVidaPorRegion();
    }

    @PostConstruct
    public void init() {
        cargarListaContinentes();
        datosPorContinente = Collections.emptyList();
        cargarDatosTodosLosContinentes();
    }

    public void cargarListaContinentes() {
        listaContinentes = repositorio.Listar().stream()
                .map(Country::getContinent)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void cargarDatosTodosLosContinentes() {
        datosTodosLosContinentes = new ArrayList<>();
        List<Object[]> todosLosDatos = repositorio.poblacionYEsperanzaVidaPorRegion();
        
        Map<String, List<Object[]>> datosPorContinente = todosLosDatos.stream()
                .collect(Collectors.groupingBy(dato -> (String) dato[0]));
        
        for (Map.Entry<String, List<Object[]>> entry : datosPorContinente.entrySet()) {
            String continente = entry.getKey();
            List<Object[]> regiones = entry.getValue();
            datosTodosLosContinentes.add(new ContinenteConRegiones(continente, continente, regiones));
        }
    }

    public void mostrarDatosPorContinente() {
        if (continenteSeleccionado != null && !continenteSeleccionado.isEmpty()) {
            datosPorContinente = repositorio.poblacionYEsperanzaVidaPorRegion().stream()
                    .filter(dato -> continenteSeleccionado.equals(dato[0]))
                    .collect(Collectors.toList());
        } else {
            datosPorContinente = Collections.emptyList();
        }
    }

    public void limpiarSeleccion() {
        this.continenteSeleccionado = "";
        this.datosPorContinente = null;
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

    // Getters y Setters para el informe
    public List<String> getListaContinentes() {
        return listaContinentes;
    }

    public void setListaContinentes(List<String> listaContinentes) {
        this.listaContinentes = listaContinentes;
    }

    public String getContinenteSeleccionado() {
        return continenteSeleccionado;
    }

    public void setContinenteSeleccionado(String continenteSeleccionado) {
        this.continenteSeleccionado = continenteSeleccionado;
    }

    public List<Object[]> getDatosPorContinente() {
        return datosPorContinente;
    }

    public void setDatosPorContinente(List<Object[]> datosPorContinente) {
        this.datosPorContinente = datosPorContinente;
    }

    public List<ContinenteConRegiones> getDatosTodosLosContinentes() {
        return datosTodosLosContinentes;
    }

    public void setDatosTodosLosContinentes(List<ContinenteConRegiones> datosTodosLosContinentes) {
        this.datosTodosLosContinentes = datosTodosLosContinentes;
    }

}
