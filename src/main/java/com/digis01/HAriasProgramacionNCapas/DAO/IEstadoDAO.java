
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Result;

public interface IEstadoDAO {
    Result EstadoByIdPais(int IdPais);
    Result EstadoByIdPaisJPA(int IdPais);
}
