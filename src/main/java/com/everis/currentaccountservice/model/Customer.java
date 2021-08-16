package com.everis.currentaccountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//modelo del firmante autorizado  y titulares de las cuentas
@Getter
@Setter
@AllArgsConstructor
public class Customer {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Size(min = 8, max = 8, message = "El DNI debe contener 8 digitos")
    @Pattern(regexp = "^[0-9]*$", message = "El DNI solo acepta numeros")
    private String dni;

    @NotNull
    @Size(min = 11, max = 11, message = "El RUC debe contener 11 digitos")
    @Pattern(regexp = "^[0-9]*$", message = "El RUC solo acepta numeros")
    private String ruc;
}
