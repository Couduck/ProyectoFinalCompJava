public class Paciente       //Clase del paciente
{
    private int ID;     //Id del paciente
    private String[] nombreCompleto;    //Nombre completo del paciente: nombre, apellidos materno y paterno

    public Paciente(int ID, String[] nombreCompleto) //Constructor
    {
        this.ID = ID;
        this.nombreCompleto = nombreCompleto;
    }

    //Getter
    public int getID() {
        return ID;
    }

    public String[] getNombreCompleto() {
        return nombreCompleto;
    }
}
