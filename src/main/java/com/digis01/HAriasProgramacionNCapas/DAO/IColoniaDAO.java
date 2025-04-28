
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Result;

public interface IColoniaDAO {
    
    Result ColoniaByIdMunicipio(int IdMunicipio);
    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
}
