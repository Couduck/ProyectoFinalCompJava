import java.io.IOException;
import java.util.Collection;

public class Cita   //Clase que guarda una cita creada por el usuario, mayormente relevante a la hora de utilizar el cursor respectivo
{
    private int ID;     //ID de la cita
    private String fecha;   //Fecha de la cita
    private String hora;    //Hora de la cita
    private String motivo;  //Motivo de la cita
    private int doctor;     //Doctor asignado de la cita
    private int paciente;   //Paciente que ha solicitado la cita

    public Cita(int ID, String fecha, String hora, String motivo, int doctor, int paciente)     //Constructor
    {
        this.ID = ID;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    //Getters
    public int getID() {
        return ID;
    }

    public String getFecha()
    {
        return fecha;
    }

    public String getHora()
    {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getDoctor() {
        return doctor;
    }

    public int getPaciente() {
        return paciente;
    }

    @Override
    public String toString() {
        try {
            Doctor doctor = Main.doctorHandler.recuperarPorID(this.getDoctor());
            Paciente paciente = Main.pacientHandler.recuperarPorID(this.getPaciente());
            String nombreDoctor = doctor.getNombreCompleto()[0] + " " +doctor.getNombreCompleto()[1] + " " + doctor.getNombreCompleto()[2];
            String nombrePaciente = paciente.getNombreCompleto()[0] + " " + paciente.getNombreCompleto()[1] + " " + paciente.getNombreCompleto()[2];

            return "Cita #" + this.getID() + "\n\nFecha: " + this.getFecha() + "\nHora: " + this.getHora() + "\nMotivo: " + this.getMotivo() + "\nDoctor: " + nombreDoctor + "\nPaciente: " + nombrePaciente;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
