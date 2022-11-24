import javax.swing.*;
import java.io.*;
import java.util.LinkedHashMap;

public class ManejadorUsuarios implements ManejadorArchivos     //Manejador del archivo de usuario
{
    LinkedHashMap<String, String> cursor = new LinkedHashMap<>();   //Cursor intermedio entre el programa y el archivo

    @Override
    public void load() throws IOException   //Carga los valores del archivo al cursor
    {
        FileReader textoCompleto = new FileReader("Databases\\Usuarios.txt");   //El file reader que permitirá leer el archivo
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
    public void write()     //Guarda los valores del cursor al archivo
    {

    }

    public void solicitudIngreso() throws IOException   //Proceso para el ingreso de un usuario
    {
        File archivo = new File("Databases\\Usuarios.txt");    //Se crea el archivo
        ManejadorUsuarios us_handler = new ManejadorUsuarios();

        if(!archivo.exists())   //Si el archivo no existe en el programa, se envia error y no se inicializa
        {
            JOptionPane.showMessageDialog(null, "Error, Archivo \"Usuarios.txt\" no se ha encontrado dentro de la carpeta del programa, Saliendo del programa.","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        else
        {
            String nombreUsuario = "";  //Guarda nombre del usuario
            String passwordIngresada, passwordReal; //Guarda el password ingresado y en otra, el password correcto
            boolean usuarioExiste, usuarioIngresado = false, passwordCorrecta;  //evaluan dependiendo de la situación
            int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
            boolean accionValida;   //Evalua el ciclo completo

            do
            {
                accionValida = true;

                try
                {
                    this.load();    //Se cargan los usuarios existentes

                    if(this.cursor.isEmpty())   //Si no se obtuvo nada, significa que no hay usuarios, y no se puede usar el programa
                    {
                        JOptionPane.showMessageDialog(null, "Error, Archivo \"Usuarios.txt\" vacio.","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                    //Solicitud entrada nombre de usuario
                    if (!usuarioIngresado)
                    {
                        do
                        {
                            //Se ingresa el usuario y se verifica que exista en la base de datos
                            nombreUsuario = (String) JOptionPane.showInputDialog(null,"Ingreses su nombre de usuario", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                            char notEmpty = nombreUsuario.charAt(0);
                            usuarioExiste = this.cursor.containsKey(nombreUsuario);

                            //Si el usuario no existe, no se puede continuar
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
                        //Se ingresa la contraseña y se compara con la contraseña guardada del usuario respectivo
                        passwordIngresada = (String) JOptionPane.showInputDialog(null,"Ingrese su password", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = passwordIngresada.charAt(0);
                        passwordReal = this.cursor.get(nombreUsuario);
                        passwordCorrecta = passwordIngresada.equals(passwordReal);

                        //Si el password no es el correcto
                        if(!passwordCorrecta)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Password incorrecto para usuario ingresado, intente de nuevo","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    while (!passwordCorrecta);

                    //Mensaje de bienvenida
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
