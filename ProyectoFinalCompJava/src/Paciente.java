public class Paciente
{
    private int ID;
    private String[] nombreCompleto;

    public Paciente(int ID, String[] nombreCompleto) {
        this.ID = ID;
        this.nombreCompleto = nombreCompleto;
    }

    public int getID() {
        return ID;
    }

    public String[] getNombreCompleto() {
        return nombreCompleto;
    }
}
