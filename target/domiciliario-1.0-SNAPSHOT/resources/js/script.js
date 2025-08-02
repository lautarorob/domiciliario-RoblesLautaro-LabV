/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function validarFormulario() {
    // Limpiar mensajes de error previos
    const errorName = document.getElementById("formularioEdicion:errorName");
    const errorDistrict = document.getElementById("formularioEdicion:errorDistrict");
    const errorPopulation = document.getElementById("formularioEdicion:errorPopulation");
    const errorCountry = document.getElementById("formularioEdicion:errorCountry");


    // Obtener valores de los campos
    const name = document.getElementById("formularioEdicion:name");
    const district = document.getElementById("formularioEdicion:district");
    const population = document.getElementById("formularioEdicion:population");
    const country = document.getElementById("formularioEdicion:countrycode");


    const nameValue = name.value.trim();
    const districtValue = district.value.trim();
    const populationValue = population.value.trim();
    const countryValue = country.value.trim();


    let isValid = true;

    // Validar nombre
    if (nameValue === "") {
        errorName.textContent = "El nombre es obligatorio";
        errorName.classList.add("error-ingreso");
        isValid = false;
    }

    // Validar distrito
    if (districtValue === "") {
        errorDistrict.textContent = "El distrito es obligatorio";
        errorDistrict.classList.add("error-ingreso");
        isValid = false;
    }

    // Validar población
    if (populationValue === "") {
        errorPopulation.textContent = "La población es obligatoria";
        errorPopulation.classList.add("error-ingreso");
        isValid = false;
    } else if (isNaN(populationValue) || parseInt(populationValue) <= 0) {
        errorPopulation.textContent = "Población inválida";
        errorPopulation.classList.add("error-ingreso");
        isValid = false;
    }

    // Validar país
    if (countryValue === "") {
        errorCountry.textContent = "El país es obligatorio";
        errorCountry.classList.add("error-ingreso");
        isValid = false;
    }

    return isValid;
}