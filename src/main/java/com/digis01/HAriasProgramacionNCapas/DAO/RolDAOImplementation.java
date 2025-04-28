package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.Rol;
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
public class RolDAOImplementation implements IRolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        
        try{
            result.object = jdbcTemplate.execute("{CALL RolGetAll(?)}",(CallableStatementCallback<List<Rol>>) callableStatement ->{
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                List<Rol> roles = new ArrayList<>();
                
                while(resultSet.next()){
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombre(resultSet.getString("Nombre"));
                    roles.add(rol);
                }
                result.correct = true;
                return roles;
            });
        }catch(Exception ex){
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
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Rol> queryRoles = entityManager.createQuery("FROM Rol", com.digis01.HAriasProgramacionNCapas.JPA.Rol.class);
            List<com.digis01.HAriasProgramacionNCapas.JPA.Rol> rolesJPA = queryRoles.getResultList();
            result.objects = new ArrayList<>();
            for (com.digis01.HAriasProgramacionNCapas.JPA.Rol rolJPA : rolesJPA) {
                Rol rol = new Rol();
                rol.setIdRol(rolJPA.getIdRol());
                rol.setNombre(rolJPA.getNombre());
                result.objects.add(rol);
                
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
