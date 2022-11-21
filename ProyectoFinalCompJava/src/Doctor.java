public class Doctor
{
    private int ID;
    private String[] nombreCompleto;
    private String especialidad;

    public Doctor(int ID, String[] nombreCompleto, String especialidad) {
        this.ID = ID;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

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
