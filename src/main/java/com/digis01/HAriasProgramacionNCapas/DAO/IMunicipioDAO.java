
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Result;

public interface IMunicipioDAO {
    Result MunicipioByIdEstado(int IdEstado);
    Result MunicipioByIdEstadoJPA(int IdEstado);
    
}
