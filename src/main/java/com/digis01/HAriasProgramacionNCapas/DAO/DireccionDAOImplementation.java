package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Colonia;
import com.digis01.HAriasProgramacionNCapas.ML.Direccion;
import com.digis01.HAriasProgramacionNCapas.ML.Estado;
import com.digis01.HAriasProgramacionNCapas.ML.Municipio;
import com.digis01.HAriasProgramacionNCapas.ML.Pais;
import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired // conexión de JPA
    private EntityManager entityManager;

    @Override
    public Result GetByIdDireccion(int IdDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL DireccionGetByIdDireccion(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));

                    result.object = direccion;

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
    public Result UpdateDireccion(Direccion direccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL DireccionUpdate(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.Colonia.getIdColonia());
                callableStatement.executeUpdate();
                int rowsAffected = callableStatement.getUpdateCount();
                if (rowsAffected != 0) {
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "No fue posible actualizar";
                }

                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result AddDireccion(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DireccionAdd(?, ?, ?, ?, ?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, usuarioDireccion.Usuario.getIdUsuario());
                callableStatement.setString(2, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, usuarioDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.executeUpdate();

                int rowsAffected = callableStatement.getUpdateCount();
                if (rowsAffected != 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "No fue posible agregar la dirección";
                }
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DelateDireccion(int IdDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL DelateDireccion(?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdDireccion);
                callableStatement.executeUpdate();

                int rowsAffected = callableStatement.getUpdateCount();
                if (rowsAffected != 0) {
                    result.correct = true;

                } else {
                    result.correct = false;

                }

                return 1;
            });

        } catch (Exception ex) {

            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result AddDireccionJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA = new com.digis01.HAriasProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.Colonia = new com.digis01.HAriasProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Usuario = new com.digis01.HAriasProgramacionNCapas.JPA.Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
            entityManager.persist(direccionJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result UpdatedireccionJPA(Direccion direccion) {
        Result result = new Result();
        try {
            com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA = new com.digis01.HAriasProgramacionNCapas.JPA.Direccion();
            direccionJPA = entityManager.find(com.digis01.HAriasProgramacionNCapas.JPA.Direccion.class, direccion.getIdDireccion());
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            direccionJPA.Colonia = new com.digis01.HAriasProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
            entityManager.merge(direccionJPA);
            
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdDireccionJPA(int IdDireccion) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE IdDireccion = :iddireccion",com.digis01.HAriasProgramacionNCapas.JPA.Direccion.class);
            queryDireccion.setParameter("iddireccion", IdDireccion);
            com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA = queryDireccion.getSingleResult();
            Direccion direccion = new Direccion();
            
            direccion.setIdDireccion(direccionJPA.getIdDireccion());
            direccion.setCalle(direccionJPA.getCalle());
            direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
            direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
            direccion.Colonia = new Colonia();
            direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
            direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
            direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());
            direccion.Colonia.Municipio = new Municipio();
            direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
            direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());
            direccion.Colonia.Municipio.Estado = new Estado();
            direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
            direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());
            direccion.Colonia.Municipio.Estado.Pais = new Pais();
            direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
            direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());
            result.object = direccion;
            
            
            result.correct = true;
        } catch (Exception ex) {
            
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result DelateDireccionJPA(int IdDireccion) {
        Result result = new Result();
        try {
            com.digis01.HAriasProgramacionNCapas.JPA.Direccion  direccion = new com.digis01.HAriasProgramacionNCapas.JPA.Direccion();
            direccion = entityManager.find(com.digis01.HAriasProgramacionNCapas.JPA.Direccion.class, IdDireccion);
            entityManager.remove(direccion);
            
            
            result.correct = true;
        } catch (Exception ex) {
            
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

}
