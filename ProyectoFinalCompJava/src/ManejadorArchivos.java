import java.io.IOException;
import java.text.ParseException;

public interface ManejadorArchivos  //Interfaz que los manejadores de archivos especificos utilizan para realizar las acciones de guardado y lectura
{
    void load() throws IOException, ParseException;     //Se usará para poder cargar los registros del archivo correspondiente al cursor
    void write() throws IOException;    //Se usará para escribir los contenidos del cursor en el archivo correspondiente
}
