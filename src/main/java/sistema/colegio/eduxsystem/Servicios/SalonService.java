package sistema.colegio.eduxsystem.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.colegio.eduxsystem.Clases.Salon;
import sistema.colegio.eduxsystem.Interfaces.ISalonService;
import sistema.colegio.eduxsystem.Repositorios.IClases;
import sistema.colegio.eduxsystem.Repositorios.ISalon;

import java.util.List;
import java.util.Optional;

@Service
public class SalonService implements ISalonService {
    @Autowired
    ISalon data;


    @Override
    public List<Salon> Listar() {
        return (List<Salon>) data.findAll();
    }

    @Override
    public Optional<Salon> ConsultarId(int id) {
        return data.findById(id);
    }

    @Override
    public void Guardar(Salon s) {
        data.save(s);

    }

    @Override
    public void Eliminar(int id) {

        data.deleteById(id);
    }

    @Override
    public List<Salon> Buscar(String desc) {
        return  data.findForAll(desc);
    }

    @Override
    public boolean existsByCodcorrelativo(String codcorrelativo) {
        return data.existsByCodcorrelativo(codcorrelativo);
    }

    @Override
    public boolean existsById(int id) {
        return data.existsById(id);
    }
}
