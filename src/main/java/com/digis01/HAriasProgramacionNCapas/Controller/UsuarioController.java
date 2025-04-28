package com.digis01.HAriasProgramacionNCapas.Controller;

import com.digis01.HAriasProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.DireccionDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.PaisDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.RolDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.HAriasProgramacionNCapas.ML.Colonia;
import com.digis01.HAriasProgramacionNCapas.ML.Direccion;
import com.digis01.HAriasProgramacionNCapas.ML.Result;
import com.digis01.HAriasProgramacionNCapas.ML.ResultFile;
import com.digis01.HAriasProgramacionNCapas.ML.Rol;
import com.digis01.HAriasProgramacionNCapas.ML.Usuario;
import com.digis01.HAriasProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDaOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    // Model y ModelAttribute
    @GetMapping("index")
    public String Index(Model model) {

        Result result = usuarioDAOImplementation.GetAll();
        Result resultJPA = usuarioDAOImplementation.GetAllJPA();
        Result resultRol = rolDAOImplementation.GetAllJPA();
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Rol = new Rol();
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        model.addAttribute("roles", resultRol.objects);
        model.addAttribute("listaUsuarios", resultJPA.objects);

        return "UsuarioIndex";
    }

    @PostMapping("/GetAllDinamico")
    public String BusquedaDinamica(@ModelAttribute Usuario usuario, Model model) {
        Result result = usuarioDAOImplementation.UsuarioGetAllDinamico(usuario);
        Result resultRol = rolDAOImplementation.GetAll();
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Rol = new Rol();

        model.addAttribute("roles", resultRol.object);
        model.addAttribute("listaUsuarios", result.objects);
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);

        return "UsuarioIndex";
    }

    @GetMapping("/CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {

        try {
            if (archivo != null && !archivo.isEmpty()) {

                String[] extension = archivo.getOriginalFilename().split("\\.");
                String tipoArchivo = extension[extension.length - 1].toLowerCase();
                if (!tipoArchivo.equals("txt") && !tipoArchivo.equals("xlsx")) {
                    //Manejo de error
                    return "cargaMasiva";
                }
                String root = System.getProperty("user.dir");//obtener deirccion del proyecto en el equipo
                String path = "src/main/resources/static/archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                archivo.transferTo(new File(absolutePath));
                //Leer el archivo
                List<UsuarioDireccion> listaUsuarios = new ArrayList();
                if (tipoArchivo.equals("txt")) {
                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath));
                } else {
                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
                }

                //Validar el archivo
                List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);
                if (listaErrores.isEmpty()) {
                    session.setAttribute("urlFile", absolutePath);
                    model.addAttribute("listaErrores", listaErrores);
                    model.addAttribute("archivoCorrecto", true);
                } else {
                    model.addAttribute("listaErrores", listaErrores);
                }
            }
        } catch (Exception ex) {
            return "redirect:/Usuario/CargaMasiva";
        }
        return "CargaMasiva";
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        //Logica para leer el archivo

        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();

        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNac = formato.parse(campos[3]);
                usuarioDireccion.Usuario.setFechaNacimiento(fechaNac);
                usuarioDireccion.Usuario.setTelefono(campos[4]);
                usuarioDireccion.Usuario.setUsername(campos[5]);
                usuarioDireccion.Usuario.setEmail(campos[6]);
                usuarioDireccion.Usuario.setPassword(campos[7]);
                usuarioDireccion.Usuario.setSexo(campos[8]);
                usuarioDireccion.Usuario.setCelular(campos[9]);
                usuarioDireccion.Usuario.setCurp(campos[10]);
                usuarioDireccion.Usuario.Rol = new Rol();
                usuarioDireccion.Usuario.Rol.setIdRol(Integer.parseInt(campos[11]));
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[12]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[13]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[14]);
                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[15]));
                usuarioDireccion.Usuario.setImagen(null);
                usuarioDireccion.Usuario.setStatus(Integer.parseInt(campos[16]));
                listaUsuarios.add(usuarioDireccion);
            }

        } catch (Exception ex) {
            listaUsuarios = null;
        }

        return listaUsuarios;
    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            DataFormatter dataFormatter = new DataFormatter();
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    Cell cell = row.getCell(3);
                    Date fechaNac;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        fechaNac = cell.getDateCellValue();
                    } else {
                        String fechastring = cell.toString();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        fechaNac = formato.parse(fechastring);
                    }
                    usuarioDireccion.Usuario.setFechaNacimiento(fechaNac);
                    usuarioDireccion.Usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(4)).toString());
                    usuarioDireccion.Usuario.setUsername(row.getCell(5).toString());
                    usuarioDireccion.Usuario.setEmail(row.getCell(6).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(7).toString());
                    usuarioDireccion.Usuario.setSexo(row.getCell(8).toString());
                    usuarioDireccion.Usuario.setCelular(dataFormatter.formatCellValue(row.getCell(9)).toString());
                    usuarioDireccion.Usuario.setCurp(row.getCell(10) != null ? row.getCell(10).toString() : "");
                    usuarioDireccion.Usuario.setImagen(null);
                    int status;
                    if (row.getCell(11).getCellType() == CellType.NUMERIC) {
                        status = (int) row.getCell(11).getNumericCellValue();
                    } else {
                        status = Integer.parseInt(row.getCell(11).toString().trim());
                    }
                    usuarioDireccion.Usuario.setStatus(status);
                    usuarioDireccion.Usuario.Rol = new Rol();
                    int rol;
                    if (row.getCell(12).getCellType() == CellType.NUMERIC) {
                        rol = (int) row.getCell(12).getNumericCellValue();
                    } else {
                        rol = Integer.parseInt(row.getCell(12).toString().trim());
                    }
                    usuarioDireccion.Usuario.Rol.setIdRol(rol);
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14) != null ? row.getCell(14).toString() : "");
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(15).toString());
                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    int colonia;
                    if (row.getCell(16).getCellType() == CellType.NUMERIC) {
                        colonia = (int) row.getCell(16).getNumericCellValue();
                    } else {
                        colonia = Integer.parseInt(row.getCell(16).toString().trim());
                    }
                    usuarioDireccion.Direccion.Colonia.setIdColonia(colonia);
                    listaUsuarios.add(usuarioDireccion);
                }
            }

        } catch (Exception ex) {
            listaUsuarios = null;

        }
        return listaUsuarios;
    }

    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();
        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "La lisat es nula", "La lista es nula"));

        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista está vacía", "La lista está vacía"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El nombre es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El apellido Paterno es un campo obligatorio"));
                }

                if (usuarioDireccion.Usuario.getFechaNacimiento() == null || usuarioDireccion.Usuario.getFechaNacimiento().equals("")) {
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    listaErrores.add(new ResultFile(fila, formato.format(usuarioDireccion.Usuario.getFechaNacimiento()), "La fecha de nacimiento es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getTelefono() == null || usuarioDireccion.Usuario.getTelefono().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getTelefono(), "El número de Telefono es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getUsername() == null || usuarioDireccion.Usuario.getUsername().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getUsername(), "El Username es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getEmail() == null || usuarioDireccion.Usuario.getEmail().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getEmail(), "La dirección de Email es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getPassword() == null || usuarioDireccion.Usuario.getPassword().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getPassword(), "El password es un campo obligatorio"));
                }
                if (usuarioDireccion.Usuario.getSexo() == null || usuarioDireccion.Usuario.getSexo().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getSexo(), "El sexo es un campo obligatorio"));
                }
                if (usuarioDireccion.Direccion.getCalle() == null || usuarioDireccion.Direccion.getCalle().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Direccion.getCalle(), "La calle es un campo obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

    @GetMapping("/CargaMasiva/procesar")
    public String CargaMasiva(HttpSession session) {
        String absolutePath = session.getAttribute("urlFile").toString();
        String tipoArchivo = session.getAttribute("urlFile").toString().split("\\.")[1];
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        if (tipoArchivo.equals("txt")){
            listaUsuarios = LecturaArchivoTXT(new File(absolutePath));
        }else{
            listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
        }
        for(UsuarioDireccion usuarioDireccion: listaUsuarios){
            usuarioDAOImplementation.AddJPA(usuarioDireccion);
        }
        
        session.removeAttribute("urlFile");;
        
        return "redirect:/Usuario/index";
    }

    @GetMapping("Form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {
        if (IdUsuario == 0) {//Agregar un usuario nuevo y dirección
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.Rol = new Rol();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.Colonia = new Colonia();

            model.addAttribute("roles", rolDAOImplementation.GetAllJPA().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAllJPA().correct ? paisDAOImplementation.GetAllJPA().objects : null);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "UsuarioForm";
        } else {
            Result result = usuarioDAOImplementation.DireccionesByIdUsuarioJPA(IdUsuario);
            model.addAttribute("usuarioDireccion", result.objects);
            return "UsuarioDetail";
        }

    }

    @GetMapping("/FormEditable")
    public String FormEditable(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
        if (IdDireccion == null) {
            //Editar usuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.UsuarioById(IdUsuario).object;
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("roles", rolDAOImplementation.GetAllJPA().objects);

        } else if (IdDireccion == 0) {
            //Agregar Direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(0);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAllJPA().correct ? paisDAOImplementation.GetAllJPA().objects : null);

        } else {
            // Editar dirección
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(IdDireccion);
            usuarioDireccion.Direccion = (Direccion) direccionDaOImplementation.GetByIdDireccion(IdDireccion).object;

            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAllJPA().correct ? paisDAOImplementation.GetAllJPA().objects : null);
            Result estado = estadoDAOImplementation.EstadoByIdPaisJPA(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais());
            model.addAttribute("estados", estado.objects);
            Result municipio = municipioDAOImplementation.MunicipioByIdEstadoJPA(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado());
            model.addAttribute("municipios", municipio.objects);
            model.addAttribute("colonias", coloniaDAOImplementation.ColoniaByIdMunicipioJPA(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);

        }
        return "UsuarioForm";
    }

    @PostMapping("Form")
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam(required = false) MultipartFile imagenFile, Model model) {

//        if (BindingResult.hasErrors()) {
//
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            return "UsuarioForm";
//
//        }
        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.Usuario.setImagen(imgBase64);
            }

        } catch (Exception ex) {
        }

        if (usuarioDireccion.Usuario.getIdUsuario() == 0) {
//            usuarioDAOImplementation.Add(usuarioDireccion);
            usuarioDAOImplementation.AddJPA(usuarioDireccion);
            return "redirect:/Usuario/index";
        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) {
                usuarioDAOImplementation.UpdateUsuarioJPA(usuarioDireccion.Usuario);
            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) {
                direccionDaOImplementation.AddDireccionJPA(usuarioDireccion);
                System.out.println("Agregar dirección");

            } else {
                direccionDaOImplementation.UpdatedireccionJPA(usuarioDireccion.Direccion);
                System.out.println("Actualiza direccion");
            }
            return "redirect:/Usuario/Form/" + usuarioDireccion.Usuario.getIdUsuario();
        }

    }

    @GetMapping("EstadoByIdPais/{IdPais}")
    @ResponseBody
    public Result EstadoByIdPais(@PathVariable int IdPais) {
        Result result = estadoDAOImplementation.EstadoByIdPaisJPA(IdPais);

        return result;
    }

    @GetMapping("MunicipioByIdEstado/{IdEstado}")
    @ResponseBody
    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
        Result result = municipioDAOImplementation.MunicipioByIdEstadoJPA(IdEstado);

        return result;
    }

    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipioJPA(IdMunicipio);

        return result;
    }

    @GetMapping("/DelateDireccion")
    public String DelateDireccion(@RequestParam int IdDireccion) {
        direccionDaOImplementation.DelateDireccion(IdDireccion);
        return "redirect:/Usuario/index";
    }

    @GetMapping("/DelateUsuario")
    public String DelateUsuario(@RequestParam int IdUsuario) {
        usuarioDAOImplementation.DelateUsuario(IdUsuario);
        return "redirect:/Usuario/index";
    }

    @PostMapping("/cambiarEstatus")
    @ResponseBody
    public Result UpdateStatusByIdUsuario(@RequestParam int IdUsuario, @RequestParam int Status) {

        return usuarioDAOImplementation.UpdateStatusByIdUsuario(IdUsuario, Status);

    }

}
