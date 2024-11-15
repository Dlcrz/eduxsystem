package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Clases;
import sistema.colegio.eduxsystem.Clases.Salon;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface IClasesService {

    public List<Clases> Listar();
    public Optional<Clases> ConsultarId(int id);
    public void Guardar(Clases c);
    public void  Eliminar(int id);
    public List<Clases> findClasesAula(int id);
    public List<Clases> findClasesAulaPorAnio(int id, Year anio);
}
