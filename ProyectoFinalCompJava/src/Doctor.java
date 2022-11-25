import java.util.Collection;

public class Doctor     //Clase que guarda una cita creada por el usuario, mayormente relevante a la hora de utilizar el cursor respectivo
{
    private int ID;     //ID del doctor
    private String[] nombreCompleto;    //Arreglo que guarda el nombre completo del doctor (Nombre y ambos apellidos)
    private String especialidad;    //Especialidad del doctor

    public Doctor(int ID, String[] nombreCompleto, String especialidad) {   //Constructor
        this.ID = ID;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    //Getters
    public int getID() {
        return ID;
    }

    public String[] getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }


}
