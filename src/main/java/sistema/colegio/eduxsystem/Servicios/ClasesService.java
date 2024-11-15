package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Interfaces.IClasesService;
import sistema.colegio.eduxsystem.Interfaces.INotasService;
import sistema.colegio.eduxsystem.Repositorios.IClases;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class ClasesService implements IClasesService {

    @Autowired
   IClases data;

    @Override
    public List<Clases> Listar() {
        return (List<Clases>) data.findAll();
    }

    @Override
    public Optional<Clases> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Clases c) {
        data.save(c);
    }

    @Override
    public void Eliminar(int id) {
        data.deleteById(id);
    }

    @Override
    public List<Clases> findClasesAula(int id) {
      return  data.findClasesAula(id);
    }

    @Override
    public List<Clases> findClasesAulaPorAnio(int id, Year anio) {
        return data.findClasesAulaPorAnio(id,anio);
    }


}
