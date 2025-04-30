
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.Usuario;
import com.digis01.HAriasProgramacionNCapas.ML.UsuarioDireccion;

public interface IUsuarioDAO {
    Result GetAll();
    Result Add(UsuarioDireccion usuarioDireccion);
    Result DireccionesByIdUsuario(int IdUsuario);
    Result UsuarioById(int IdUsuario);
    Result UpdateUsuario(Usuario usuario);
    Result DelateUsuario(int IdUsuario);
    Result UpdateStatusByIdUsuario(int IdUsuario, int Status);
    Result UsuarioGetAllDinamico(Usuario usuario);
    
    Result GetAllJPA();
    Result AddJPA(UsuarioDireccion usuarioDireccion);
    Result DireccionesByIdUsuarioJPA(int IdUsuario);
    Result UsuarioByIdJPA(int IdUsuario);
    Result UpdateUsuarioJPA(Usuario usuario);
    Result DelateUsuarioJPA(int IdUsuario);
    Result UsuarioGetAllDinamicoJPA(Usuario usuario);
    
}
