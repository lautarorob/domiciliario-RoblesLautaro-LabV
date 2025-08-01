/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author roble
 */
@Embeddable
public class CountrylanguagePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String countryCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String language;

    public CountrylanguagePK() {
    }

    public CountrylanguagePK(String countryCode, String language) {
        this.countryCode = countryCode;
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        hash += (language != null ? language.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CountrylanguagePK)) {
            return false;
        }
        CountrylanguagePK other = (CountrylanguagePK) object;
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        if ((this.language == null && other.language != null) || (this.language != null && !this.language.equals(other.language))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CountrylanguagePK[ countryCode=" + countryCode + ", language=" + language + " ]";
    }
    
}
