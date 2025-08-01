/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author roble
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countrylanguage.findAll", query = "SELECT c FROM Countrylanguage c"),
    @NamedQuery(name = "Countrylanguage.findByCountryCode", query = "SELECT c FROM Countrylanguage c WHERE c.countrylanguagePK.countryCode = :countryCode"),
    @NamedQuery(name = "Countrylanguage.findByLanguage", query = "SELECT c FROM Countrylanguage c WHERE c.countrylanguagePK.language = :language"),
    @NamedQuery(name = "Countrylanguage.findByIsOfficial", query = "SELECT c FROM Countrylanguage c WHERE c.isOfficial = :isOfficial"),
    @NamedQuery(name = "Countrylanguage.findByPercentage", query = "SELECT c FROM Countrylanguage c WHERE c.percentage = :percentage")})
public class Countrylanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CountrylanguagePK countrylanguagePK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Character isOfficial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal percentage;
    @JoinColumn(name = "CountryCode", referencedColumnName = "Code", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Country country;

    public Countrylanguage() {
    }

    public Countrylanguage(CountrylanguagePK countrylanguagePK) {
        this.countrylanguagePK = countrylanguagePK;
    }

    public Countrylanguage(CountrylanguagePK countrylanguagePK, Character isOfficial, BigDecimal percentage) {
        this.countrylanguagePK = countrylanguagePK;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    public Countrylanguage(String countryCode, String language) {
        this.countrylanguagePK = new CountrylanguagePK(countryCode, language);
    }

    public CountrylanguagePK getCountrylanguagePK() {
        return countrylanguagePK;
    }

    public void setCountrylanguagePK(CountrylanguagePK countrylanguagePK) {
        this.countrylanguagePK = countrylanguagePK;
    }

    public Character getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Character isOfficial) {
        this.isOfficial = isOfficial;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countrylanguagePK != null ? countrylanguagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Countrylanguage)) {
            return false;
        }
        Countrylanguage other = (Countrylanguage) object;
        if ((this.countrylanguagePK == null && other.countrylanguagePK != null) || (this.countrylanguagePK != null && !this.countrylanguagePK.equals(other.countrylanguagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Countrylanguage[ countrylanguagePK=" + countrylanguagePK + " ]";
    }
    
}
