import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ManejadorDoctores implements ManejadorArchivos
{

    ArrayList<Doctor> cursor = new ArrayList<Doctor>();

    @Override
    public void load() throws IOException {
        cursor = new ArrayList<Doctor>();
        FileReader textoCompleto = new FileReader("Doctores.txt");   //El file reader que permitirá leer el archivo
        BufferedReader bufred = new BufferedReader(textoCompleto);              //Se genera el buffered reader
        String linea;                                                           //ALmacena cada linea del documento
        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            String [] lineaSeparada = linea.split(",");                   //Separa la linea obtenida por las comas, teniendo por separaado el teléfono y el nombre al que le corresponde
            String[] nombreCompleto = {lineaSeparada[1], lineaSeparada[2], lineaSeparada[3]};            //Se coloca en la LinkedHashMap el telefono como llave y el nombre como valor

            Doctor doctor = new Doctor(Integer.parseInt(lineaSeparada[0]),nombreCompleto,lineaSeparada[4]);
            cursor.add(doctor);
        }

        bufred.close();     //Se cierra el BufferedReader
    }

    @Override
    public void write() throws IOException
    {

        FileWriter meterLista = new FileWriter("Doctores.txt");     //Se genera un filewriter paar el archivo

        for(int i = 0; i < cursor.size(); i++)    //Se inserta cada contacto en el archivo de texto
        {
            Doctor doctor = cursor.get(i);

            if(i == 0)  //Si es la primera insercion
            {
                meterLista.write(doctor.getID() + "," + doctor.getNombreCompleto()[0] + "," + doctor.getNombreCompleto()[1] + "," + doctor.getNombreCompleto()[2]  + "," + doctor.getEspecialidad() + "\n");
            }

            else    //Todas las demas
            {
                meterLista.append(doctor.getID() + "," + doctor.getNombreCompleto()[0] + "," + doctor.getNombreCompleto()[1] + "," + doctor.getNombreCompleto()[2]  + "," + doctor.getEspecialidad() + "\n");
            }
        }
        meterLista.close();  //Se cierra el File Writer
    }

    public void ingresarNuevoDoctor() throws IOException
    {
        File archivo = new File("Doctores.txt");    //Se crea el archivo

        if(archivo.exists())   //Si el archivo ya existe dentro del programa, se cargan los valores, de lo contrario no sehace
        {
            this.load();
        }

        String nombreDoctor = "", apellidoPatDoctor = "", apellidoMatDoctor = "", especialidadDoctor = "";
        boolean nombreIngresado = false,  apellidoPatIngresado = false, apellidoMatIngresado = false, especialidadIngresada = false;
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida;

        do
        {
            accionValida = true;

            try
            {
                //Solicitud entrada nombre de doctor
                if(!nombreIngresado)
                {
                    do
                    {
                        nombreDoctor = (String) JOptionPane.showInputDialog(null,"Ingreses el nombre del doctor", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = nombreDoctor.charAt(0);
                        nombreIngresado = true;
                    }
                    while (!nombreIngresado);
                }

                //Solicitud apellido paterno
                if(!apellidoPatIngresado)
                {
                    do
                    {
                        apellidoPatDoctor = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido paterno del doctor", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = apellidoPatDoctor.charAt(0);
                        apellidoPatIngresado = true;
                    }
                    while (!apellidoPatIngresado);
                }

                if(!apellidoMatIngresado)
                {
                    do
                    {
                        apellidoMatDoctor = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido materno del doctor", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = apellidoMatDoctor.charAt(0);
                        apellidoMatIngresado = true;
                    }
                    while (!apellidoMatIngresado);
                }

                if(!especialidadIngresada)
                {
                    do
                    {
                        especialidadDoctor = (String) JOptionPane.showInputDialog(null,"Ingrese la especialidad del doctor", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = especialidadDoctor.charAt(0);
                        especialidadIngresada = true;
                    }
                    while (!especialidadIngresada);
                }

                String[] nombreCompleto = {nombreDoctor, apellidoPatDoctor, apellidoMatDoctor};
                Doctor nuevoDoctor = new Doctor(recuperarDoctoresTotales()+1, nombreCompleto, especialidadDoctor);
                cursor.add(nuevoDoctor);
                actualizarDoctoresTotales(nuevoDoctor.getID());
                this.write();

                JOptionPane.showMessageDialog(null, "Nuevo doctor creado exitosamente.","PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);

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

    public int recuperarDoctoresTotales() throws IOException
    {
        FileReader archivo = new FileReader("ContadorTotalDoctores.txt");

        BufferedReader bufred = new BufferedReader(archivo);

        int regresar = Integer.parseInt(bufred.readLine());

        bufred.close();

        return regresar;
    }

    public void actualizarDoctoresTotales(int nuevoValor) throws IOException
    {
        FileWriter archivo = new FileWriter("ContadorTotalDoctores.txt");

        archivo.write(String.valueOf(nuevoValor));

        archivo.close();
    }
}
