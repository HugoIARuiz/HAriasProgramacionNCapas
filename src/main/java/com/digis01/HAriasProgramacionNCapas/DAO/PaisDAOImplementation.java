package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Pais;
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
public class PaisDAOImplementation implements IPaisDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
     @Autowired // conexión de JPA
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        
        try {
            result.object = jdbcTemplate.execute("{CALL PaisGetAll(?)}", (CallableStatementCallback<List<Pais>>) callableStatement ->{
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                List<Pais> paises = new ArrayList<>();
                while(resultSet.next()){
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    paises.add(pais);
                }
                result.correct = true;
                return paises;
                
            });
                    
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
    }

    @Override
    public Result GetAllJPA() {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Pais> queryPaises = entityManager.createQuery("FROM Pais", com.digis01.HAriasProgramacionNCapas.JPA.Pais.class);
            List<com.digis01.HAriasProgramacionNCapas.JPA.Pais> paisesJPA = queryPaises.getResultList();
            result.objects = new ArrayList<>();
            for (com.digis01.HAriasProgramacionNCapas.JPA.Pais paisJPA : paisesJPA){
                Pais pais = new Pais();
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombre(paisJPA.getNombre());
                result.objects.add(pais);
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
