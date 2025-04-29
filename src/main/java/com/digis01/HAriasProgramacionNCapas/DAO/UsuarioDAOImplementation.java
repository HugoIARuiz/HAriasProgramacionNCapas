package com.digis01.HAriasProgramacionNCapas.DAO;

import com.digis01.HAriasProgramacionNCapas.ML.Colonia;
import com.digis01.HAriasProgramacionNCapas.ML.Direccion;
import com.digis01.HAriasProgramacionNCapas.ML.Estado;
import com.digis01.HAriasProgramacionNCapas.ML.Municipio;
import com.digis01.HAriasProgramacionNCapas.ML.Pais;
import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.Usuario;
import com.digis01.HAriasProgramacionNCapas.ML.Rol;
import com.digis01.HAriasProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired // conexión de JPA
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioGetAll(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {

                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {
                            int idUsuario = resultSet.getInt("Idusuario");
                            if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                                //Si la lista no está vacía y ya existe el usuario solo se agrega la dirección

                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                                ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                            } else {

                                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                                usuarioDireccion.Usuario = new Usuario();
                                usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuarioDireccion.Usuario.setUsername(resultSet.getString("UserName"));
                                usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                                usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                                usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                                usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                                usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                                usuarioDireccion.Usuario.setCurp(resultSet.getString("Curp"));
                                usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                                usuarioDireccion.Usuario.setStatus(resultSet.getInt("Status"));
                                //Recuperar información de  
                                usuarioDireccion.Usuario.Rol = new Rol();
                                usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                                usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                                //Recuperar información de direcciones
                                usuarioDireccion.Direcciones = new ArrayList<>();
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                                //Añadir la información en los arreglos
                                usuarioDireccion.Direcciones.add(direccion);
                                result.objects.add(usuarioDireccion);
                            }

                        }
                        result.correct = true;
                        return 1;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("Call UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, usuarioDireccion.Usuario.getNombre());
                callableStatement.setString(2, usuarioDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuarioDireccion.Usuario.getTelefono());
                callableStatement.setString(6, usuarioDireccion.Usuario.getUsername());
                callableStatement.setString(7, usuarioDireccion.Usuario.getEmail());
                callableStatement.setString(8, usuarioDireccion.Usuario.getPassword());
                callableStatement.setString(9, usuarioDireccion.Usuario.getSexo());
                callableStatement.setString(10, usuarioDireccion.Usuario.getCelular());
                callableStatement.setString(11, usuarioDireccion.Usuario.getCurp());
                callableStatement.setInt(12, usuarioDireccion.Usuario.Rol.getIdRol());
                callableStatement.setString(13, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(14, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(15, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(16, usuarioDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setString(17, usuarioDireccion.Usuario.getImagen());
                callableStatement.setInt(18, 1);

                int rowAffected = callableStatement.executeUpdate();
                result.correct = rowAffected > 0 ? true : false;

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
    public Result DireccionesByIdUsuario(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DireccionesByIdUsuario(?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);
                callableStatement.execute();

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);

                if (resultSetUsuario.next()) {
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setUsername(resultSetUsuario.getString("Username"));
                    usuarioDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.Usuario.setImagen(resultSetUsuario.getString("Imagen"));

                }
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);
                usuarioDireccion.Direcciones = new ArrayList();
                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSetDireccion.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultSetDireccion.getString("NombreMunicipio"));
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSetDireccion.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSetDireccion.getString("NombreEstado"));
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSetDireccion.getInt("IdPais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSetDireccion.getString("NombrePais"));

                    usuarioDireccion.Direcciones.add(direccion);
                }
                result.object = usuarioDireccion;
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
    public Result UsuarioById(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL UsuarioById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                if (resultSet.next()) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuarioDireccion.Usuario.setCurp(resultSet.getString("Curp"));
                    usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                    usuarioDireccion.Usuario.setUsername(resultSet.getString("Username"));
                    usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                    usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                    usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                    usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                    usuarioDireccion.Usuario.setStatus(resultSet.getInt("Status"));
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                    result.object = usuarioDireccion;
                }
                result.correct = true;
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
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setDate(5, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(6, usuario.getCurp());
                callableStatement.setString(7, usuario.getSexo());
                callableStatement.setString(8, usuario.getUsername());
                callableStatement.setInt(9, usuario.Rol.getIdRol());
                callableStatement.setString(10, usuario.getEmail());
                callableStatement.setString(11, usuario.getPassword());
                callableStatement.setString(12, usuario.getTelefono());
                callableStatement.setString(13, usuario.getCelular());
                callableStatement.setString(14, usuario.getImagen());
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
    public Result DelateUsuario(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DelateUsuario(?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdUsuario);
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

    @Override
    public Result UpdateStatusByIdUsuario(int IdUsuario, int Status) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UpdateStatusByIdUsuario(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdUsuario);
                callableStatement.setInt(2, Status);
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

    @Override
    public Result UsuarioGetAllDinamico(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioGetAllDinamico (?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("Idusuario");
                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        //Si la lista no está vacía y ya existe el usuario solo se agrega la dirección

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {

                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.Usuario = new Usuario();
                        usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.Usuario.setUsername(resultSet.getString("UserName"));
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                        usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.Usuario.setCurp(resultSet.getString("Curp"));
                        usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                        usuarioDireccion.Usuario.setStatus(resultSet.getInt("Status"));
                        //Recuperar información de  
                        usuarioDireccion.Usuario.Rol = new Rol();
                        usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                        //Recuperar información de direcciones
                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        //Añadir la información en los arreglos
                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }

                }

                return 1;
            });

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result GetAllJPA() {

        //Esto es lenguaje JPQL
        Result result = new Result();
        try {
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Usuario> queryusuarios = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario", com.digis01.HAriasProgramacionNCapas.JPA.Usuario.class);
            List<com.digis01.HAriasProgramacionNCapas.JPA.Usuario> usuarios = queryusuarios.getResultList();
            result.objects = new ArrayList<>();
            for (com.digis01.HAriasProgramacionNCapas.JPA.Usuario usuario : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.Usuario.setNombre(usuario.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.Usuario.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioDireccion.Usuario.setTelefono(usuario.getTelefono());
                usuarioDireccion.Usuario.setUsername(usuario.getUsername());
                usuarioDireccion.Usuario.setEmail(usuario.getEmail());
                usuarioDireccion.Usuario.setPassword(usuario.getPassword());
                usuarioDireccion.Usuario.setSexo(usuario.getSexo());
                usuarioDireccion.Usuario.setCelular(usuario.getCelular());
                usuarioDireccion.Usuario.setCurp(usuario.getCurp());
                usuarioDireccion.Usuario.setImagen(usuario.getImagen());
                usuarioDireccion.Usuario.setStatus(usuario.getStatus());
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(usuario.Rol.getIdRol());

                TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion  WHERE Usuario.IdUsuario = :idusuario", com.digis01.HAriasProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<com.digis01.HAriasProgramacionNCapas.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = new ArrayList<>();
                for (com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
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
                    usuarioDireccion.Direcciones.add(direccion);
                }
                result.objects.add(usuarioDireccion);

            }
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
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {

            //Llenar alumnoJPA con el alumnoML
            com.digis01.HAriasProgramacionNCapas.JPA.Usuario usuarioJPA = new com.digis01.HAriasProgramacionNCapas.JPA.Usuario();
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setUsername(usuarioDireccion.Usuario.getUsername());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setCurp(usuarioDireccion.Usuario.getCurp());
            usuarioJPA.Rol = new com.digis01.HAriasProgramacionNCapas.JPA.Rol();
            usuarioJPA.Rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());
            usuarioJPA.setStatus(1);

            entityManager.persist(usuarioJPA);
            //Inserción de la dirección
            com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA = new com.digis01.HAriasProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.Colonia = new com.digis01.HAriasProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Usuario = usuarioJPA;
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
    public Result UpdateUsuarioJPA(Usuario usuario) {
        Result result = new Result();
        try {
            com.digis01.HAriasProgramacionNCapas.JPA.Usuario usuarioJPA = new com.digis01.HAriasProgramacionNCapas.JPA.Usuario();
            usuarioJPA = entityManager.find(com.digis01.HAriasProgramacionNCapas.JPA.Usuario.class, usuario.getIdUsuario());
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setUsername(usuario.getUsername());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setPassword(usuario.getPassword());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setCelular(usuario.getCelular());
            usuarioJPA.setCurp(usuario.getCurp());
            usuarioJPA.Rol = new com.digis01.HAriasProgramacionNCapas.JPA.Rol();
            usuarioJPA.Rol.setIdRol(usuario.Rol.getIdRol());
            usuarioJPA.setImagen(usuario.getImagen());
            entityManager.merge(usuarioJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;
    }

    @Override
    public Result DireccionesByIdUsuarioJPA(int IdUsuario) {
        Result result = new Result();

        try {
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario WHERE IdUsuario = :idusuario", com.digis01.HAriasProgramacionNCapas.JPA.Usuario.class);
            queryUsuarios.setParameter("idusuario", IdUsuario);
            com.digis01.HAriasProgramacionNCapas.JPA.Usuario usuarioJPA = queryUsuarios.getSingleResult(); //Lista para un usuario?

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
            usuarioDireccion.Usuario.setNombre(usuarioJPA.getNombre());
            usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
            usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
            usuarioDireccion.Usuario.setUsername(usuarioJPA.getUsername());
            usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
            usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());

            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Direccion> queryDirecciones = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.digis01.HAriasProgramacionNCapas.JPA.Direccion.class);
            queryDirecciones.setParameter("idusuario", usuarioJPA.getIdUsuario());
            List<com.digis01.HAriasProgramacionNCapas.JPA.Direccion> direccionesJPA = queryDirecciones.getResultList();
            usuarioDireccion.Direcciones = new ArrayList<>();
            for (com.digis01.HAriasProgramacionNCapas.JPA.Direccion direccionJPA : direccionesJPA) {
                Direccion direccion = new Direccion();
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
                usuarioDireccion.Direcciones.add(direccion);
            }
            result.object = usuarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuarioByIdJPA(int IdUsuario) {
        Result result = new Result();
        try {
            TypedQuery<com.digis01.HAriasProgramacionNCapas.JPA.Usuario> queryUsusario = entityManager.createQuery("FROM Usuario WHERE IdUsuario = :idusuario",com.digis01.HAriasProgramacionNCapas.JPA.Usuario.class);
            queryUsusario.setParameter("idusuario", IdUsuario);
            com.digis01.HAriasProgramacionNCapas.JPA.Usuario usuarioJPA = queryUsusario.getSingleResult();
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
            usuarioDireccion.Usuario.setNombre(usuarioJPA.getNombre());
            usuarioDireccion.Usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
            usuarioDireccion.Usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
            usuarioDireccion.Usuario.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
            usuarioDireccion.Usuario.setCurp(usuarioJPA.getCurp());
            usuarioDireccion.Usuario.setSexo(usuarioJPA.getSexo());
            usuarioDireccion.Usuario.setUsername(usuarioJPA.getUsername());
            usuarioDireccion.Usuario.setEmail(usuarioJPA.getEmail());
            usuarioDireccion.Usuario.setPassword(usuarioJPA.getPassword());
            usuarioDireccion.Usuario.setCelular(usuarioJPA.getCelular());
            usuarioDireccion.Usuario.setTelefono(usuarioJPA.getTelefono());
            usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());
            usuarioDireccion.Usuario.setStatus(usuarioJPA.getStatus());
            usuarioDireccion.Usuario.Rol = new Rol();
            usuarioDireccion.Usuario.Rol.setIdRol(usuarioJPA.Rol.getIdRol());
            usuarioDireccion.Usuario.Rol.setNombre(usuarioJPA.Rol.getNombre());
            result.object = usuarioDireccion;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
