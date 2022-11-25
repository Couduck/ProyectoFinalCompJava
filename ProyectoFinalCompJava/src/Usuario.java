public class Usuario
{
    private final String nombre;
    private final String password;

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getPassword()
    {
        return password;
    }
}
