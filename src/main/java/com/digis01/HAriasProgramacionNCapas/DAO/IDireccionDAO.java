
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Direccion;
import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.UsuarioDireccion;


public interface IDireccionDAO {
    Result GetByIdDireccion(int IdDireccion);
    Result UpdateDireccion(Direccion direccion);
    Result AddDireccion(UsuarioDireccion usuarioDireccion);
    Result DelateDireccion(int IdDireccion);
    
    Result GetByIdDireccionJPA(int IdDireccion);
    Result AddDireccionJPA(UsuarioDireccion usuarioDireccion);
    Result UpdatedireccionJPA(Direccion direccion);
    Result DelateDireccionJPA(int IdDireccion);
}
