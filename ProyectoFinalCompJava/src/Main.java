import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException
    {
        solicitudAcceso();
    }

    public static void solicitudAcceso() throws IOException
    {
        File lista = new File("Usuarios.txt");    //Se crea el archivo
        ManejadorUsuarios us_handler = new ManejadorUsuarios();

        if(!lista.exists())   //Si el archivo no existe en el programa, se envia error y no se inicializa
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
                    us_handler.load();

                    if(us_handler.cursor.isEmpty())
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
                            usuarioExiste = us_handler.cursor.containsKey(nombreUsuario);

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
                        passwordReal = us_handler.cursor.get(nombreUsuario);
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