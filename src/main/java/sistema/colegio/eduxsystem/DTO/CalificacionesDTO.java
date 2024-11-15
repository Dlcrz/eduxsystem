package sistema.colegio.eduxsystem.DTO;

import lombok.Data;

@Data
public class CalificacionesDTO {

    public int id;
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;
    private String nombreEstudiante;
    private int idClase;
    private int idTrimestre;

    public CalificacionesDTO( int id, double nota1,double nota2,double nota3, double promedio, String nombreEstudiante, int idClase, int idTrimestre) {
        this.id = id;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.promedio = promedio;
        this.nombreEstudiante = nombreEstudiante;
        this.idClase = idClase;
        this.idTrimestre = idTrimestre;
    }
}