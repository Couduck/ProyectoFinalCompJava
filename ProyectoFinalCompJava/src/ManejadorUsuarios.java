import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ManejadorUsuarios implements ManejadorArchivos
{
    LinkedHashMap<String, String> cursor = new LinkedHashMap<>();

    @Override
    public void load() throws IOException {
        FileReader textoCompleto = new FileReader("Usuarios.txt");   //El file reader que permitirá leer el archivo
        BufferedReader bufred = new BufferedReader(textoCompleto);              //Se genera el buffered reader
        String linea;                                                           //ALmacena cada linea del documento
        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            String [] lineaSeparada = linea.split(",");                   //Separa la linea obtenida por las comas, teniendo por separaado el teléfono y el nombre al que le corresponde
            cursor.put(lineaSeparada[0], lineaSeparada[1]);             //Se coloca en la LinkedHashMap el telefono como llave y el nombre como valor
        }

        bufred.close();     //Se cierra el BufferedReader
    }

    @Override
    public void write() {

    }
}
