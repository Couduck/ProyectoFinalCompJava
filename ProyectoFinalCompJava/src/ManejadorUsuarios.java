import javax.swing.*;
import java.io.*;
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

    public void solicitudIngreso() throws IOException
    {
        File archivo = new File("Usuarios.txt");    //Se crea el archivo
        ManejadorUsuarios us_handler = new ManejadorUsuarios();

        if(!archivo.exists())   //Si el archivo no existe en el programa, se envia error y no se inicializa
        {
            JOptionPane.showMessageDialog(null, "Error, Archivo \"Usuarios.txt\" no se ha encontrado dentro de la carpeta del programa, Saliendo del programa.","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        else
        {
            String nombreUsuario = "";
            String passwordIngresada, passwordReal;
            boolean usuarioExiste, usuarioIngresado = false, passwordCorrecta;
            int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
            boolean accionValida;

            do
            {
                accionValida = true;

                try
                {
                    this.load();

                    if(this.cursor.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Error, Archivo \"Usuarios.txt\" vacio.","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                    //Solicitud entrada nombre de usuario
                    if (!usuarioIngresado)
                    {
                        do
                        {
                            nombreUsuario = (String) JOptionPane.showInputDialog(null,"Ingreses su nombre de usuario", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                            char notEmpty = nombreUsuario.charAt(0);
                            usuarioExiste = this.cursor.containsKey(nombreUsuario);

                            if(!usuarioExiste)
                            {
                                JOptionPane.showMessageDialog(null, "Error: Usuario inexistente en base de datos, intente de nuevo","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                            }

                            usuarioIngresado = true;

                        }
                        while (!usuarioExiste);
                    }

                    //Solicitud entrada contraseña
                    do
                    {
                        passwordIngresada = (String) JOptionPane.showInputDialog(null,"Ingrese su password", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = passwordIngresada.charAt(0);
                        passwordReal = this.cursor.get(nombreUsuario);
                        passwordCorrecta = passwordIngresada.equals(passwordReal);

                        if(!passwordCorrecta)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Password incorrecto para usuario ingresado, intente de nuevo","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    while (!passwordCorrecta);

                    JOptionPane.showMessageDialog(null, "Bienvenido: " + nombreUsuario,"PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);
                }

                catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
                {
                    //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                    salirProceso = JOptionPane.showConfirmDialog(null,"Quieres salir del programa?", "PROYECTO FINAL JAVA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    //Si presiona Si
                    if(salirProceso == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(null,"Programa terminado", "PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
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
    }

}
