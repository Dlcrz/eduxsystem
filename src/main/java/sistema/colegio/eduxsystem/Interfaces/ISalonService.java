package sistema.colegio.eduxsystem.Interfaces;

import sistema.colegio.eduxsystem.Clases.Salon;

import java.util.List;
import java.util.Optional;

public interface ISalonService {
    public List<Salon> Listar();
    public Optional<Salon> ConsultarId(int id);
    public void Guardar(Salon s);
    public void  Eliminar(int id);
    public List<Salon> Buscar(String desc);
    public boolean existsByCodcorrelativo(String codcorrelativo);

    public  boolean existsById(int id);
}
