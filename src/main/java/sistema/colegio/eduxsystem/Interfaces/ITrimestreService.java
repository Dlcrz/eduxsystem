package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Curso;
import sistema.colegio.eduxsystem.Clases.Trimestre;

import java.util.List;
import java.util.Optional;

public interface ITrimestreService {

    public List<Trimestre> Listar();
    public Optional<Trimestre> ConsultarId(int id);
    public void Guardar(Trimestre t);
    public void  Eliminar(int id);
    public boolean existsByTrimestreAndAnio(String trimestre, int anio);

    public  boolean existsById(int id);


   public List<Trimestre> findByAnio(int anio);
}
