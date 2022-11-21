import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;

public interface ManejadorArchivos
{
    void load() throws IOException;
    void write() throws IOException;
}
