
package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Colonia;
import com.digis01.HAriasProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result ColoniaByIdMunicipio(int IdMunicipio) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL ColoniaByIdMunicipio(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdMunicipio);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();
                while (resultSet.next()) {

                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    result.objects.add(colonia);
                }
                return 1;

            });
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result ColoniaByIdMunicipioJPA(int IdMunicipio) {
        Result result = new Result();
        
        try {
            
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Colonia> queryColonia = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio", com.digis01.HAriasProgramacionNCapas.JPA.Colonia.class);
            queryColonia.setParameter("idmunicipio", IdMunicipio);
            List<com.digis01.HAriasProgramacionNCapas.JPA.Colonia> coloniasJPA = queryColonia.getResultList();
            result.objects = new ArrayList<>();
            for (com.digis01.HAriasProgramacionNCapas.JPA.Colonia coloniaJPA : coloniasJPA) {
                Colonia colonia = new Colonia();
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                result.objects.add(colonia);
                
                
            }
            
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
