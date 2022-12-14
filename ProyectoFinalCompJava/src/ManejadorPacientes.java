import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ManejadorPacientes implements ManejadorArchivos    //Manejador de archivo de pacientes
{

    static ArrayList<Paciente> cursor = new ArrayList<Paciente>();     //Cursor intermediario entre archivo de pacientes y programa

    @Override
    public void load() throws IOException       //Cargar loss valores del archivo al cursor
    {
        cursor = new ArrayList<Paciente>();
        FileReader textoCompleto = new FileReader("Databases\\Pacientes.txt");   //El file reader que permitirá leer el archivo
        BufferedReader bufred = new BufferedReader(textoCompleto);              //Se genera el buffered reader
        String linea;                                                           //ALmacena cada linea del documento
        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            String [] lineaSeparada = linea.split(",");                   //Separa la linea obtenida por las comas, teniendo por separaado el teléfono y el nombre al que le corresponde
            String[] nombreCompleto = {lineaSeparada[1], lineaSeparada[2], lineaSeparada[3]};            //Se coloca en la LinkedHashMap el telefono como llave y el nombre como valor

            Paciente paciente = new Paciente(Integer.parseInt(lineaSeparada[0]),nombreCompleto);
            cursor.add(paciente);
        }

        bufred.close();     //Se cierra el BufferedReader
    }

    @Override
    public void write() throws IOException      //Guardar los valores del cursor en el archivo
    {
        FileWriter meterLista = new FileWriter("Databases\\Pacientes.txt");     //Se genera un filewriter paar el archivo

        for(int i = 0; i < cursor.size(); i++)    //Se inserta cada contacto en el archivo de texto
        {
            Paciente paciente = cursor.get(i);

            if(i == 0)  //Si es la primera insercion
            {
                meterLista.write(paciente.getID() + "," + paciente.getNombreCompleto()[0] + "," + paciente.getNombreCompleto()[1] + "," + paciente.getNombreCompleto()[2] + "\n");
            }

            else    //Todas las demas
            {
                meterLista.append(paciente.getID() + "," + paciente.getNombreCompleto()[0] + "," + paciente.getNombreCompleto()[1] + "," + paciente.getNombreCompleto()[2] + "\n");
            }
        }
        meterLista.close();  //Se cierra el File Writer
    }

    public void ingresarNuevoPaciente() throws IOException      //Metodo para ingresar un nuevo paciente
    {
        File archivo = new File("Databases\\Pacientes.txt");    //Se crea el archivo

        if(archivo.exists())   //Si el archivo ya existe dentro del programa, se cargan los valores, de lo contrario no sehace
        {
            this.load();
        }

        String nombrePaciente = "", apellidoPatPaciente = "", apellidoMatPaciente = "";     //Valores para crear al paciente
        boolean nombreIngresado = false,  apellidoPatIngresado = false, apellidoMatIngresado = false;      //Verifican que los valores ya fueron ingresados
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida;

        do
        {
            accionValida = true;

            try
            {
                //Solicitud entrada nombre de paciente
                if(!nombreIngresado)
                {
                    do
                    {
                        nombrePaciente = (String) JOptionPane.showInputDialog(null,"Ingrese el nombre del paciente", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = nombrePaciente.charAt(0);
                        nombreIngresado = true;
                    }
                    while (!nombreIngresado);
                }

                //Solicitud apellido paterno de paciente
                if(!apellidoPatIngresado)
                {
                    do
                    {
                        apellidoPatPaciente = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido paterno del paciente", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = apellidoPatPaciente.charAt(0);
                        apellidoPatIngresado = true;
                    }
                    while (!apellidoPatIngresado);
                }

                //Solicitud apellido materno de paciente
                if(!apellidoMatIngresado)
                {
                    do
                    {
                        apellidoMatPaciente = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido materno del paxiente", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = apellidoMatPaciente.charAt(0);
                        apellidoMatIngresado = true;
                    }
                    while (!apellidoMatIngresado);
                }

                //Creación de objeto paciente, actualización al contador y guardado en cursor y archivo
                String[] nombreCompleto = {nombrePaciente, apellidoPatPaciente, apellidoMatPaciente};
                Paciente nuevoPaciente = new Paciente(recuperarPacientesTotales()+1, nombreCompleto);
                cursor.add(nuevoPaciente);
                actualizarPacientesTotales(nuevoPaciente.getID());
                this.write();

                JOptionPane.showMessageDialog(null, "Nuevo paciente creado exitosamente.","PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);

            }

            catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
            {
                //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                salirProceso = JOptionPane.showConfirmDialog(null,"Quiere cancelar el proceso?", "PROYECTO FINAL JAVA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //Si presiona Si
                if(salirProceso == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null,"Regresando al menu principal", "PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                //Si presiona No
                else
                {
                    accionValida = false;
                }
            }

            catch(IndexOutOfBoundsException b) //El usuario no ingreso nada y dió aceptar de todas formas
            {
                JOptionPane.showMessageDialog(null,"No se ha ingresado nada", "PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                accionValida = false;
            }

        }
        while (!accionValida);
    }

    public int recuperarPacientesTotales() throws IOException       //Recupera el contador total de pacientes
    {
        FileReader archivo = new FileReader("Contadores\\ContadorTotalPacientes.txt");

        BufferedReader bufred = new BufferedReader(archivo);

        int regresar = Integer.parseInt(bufred.readLine());

        bufred.close();

        return regresar;
    }

    public void actualizarPacientesTotales(int nuevoValor) throws IOException       //Actualiza el contador total de pacientes
    {
        FileWriter archivo = new FileWriter("Contadores\\ContadorTotalPacientes.txt");

        archivo.write(String.valueOf(nuevoValor));

        archivo.close();
    }

    public ArrayList<Paciente> getCursor() throws IOException       //Recupera el cursor de la clase
    {
        File archivo = new File("Databases\\Pacientes.txt");

        if (archivo.exists())
        {
            load();
        }
        return cursor;
    }

    public Paciente recuperarPorID(int IDRecuperar) throws IOException      //Recupera del cursor un paciente utilizando como referencia su ID
    {
        load();

        Paciente regresar = null;

        for (Paciente paciente : cursor)
        {
            if(paciente.getID() == IDRecuperar)
            {
                regresar = paciente;
                break;
            }
        }

        return regresar;
    }
}
