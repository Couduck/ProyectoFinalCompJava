
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
}
