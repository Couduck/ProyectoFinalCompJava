import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    //Todos estos objetos son manejadores de archivos, estos se encargan de administrar las escrituras y lecturas a sus respectivos archivos, asi como los métodos para ingresar nuevos registros
    static ManejadorUsuarios userHandler = new ManejadorUsuarios();
    static ManejadorDoctores doctorHandler = new ManejadorDoctores();
    static ManejadorPacientes pacientHandler = new ManejadorPacientes();

    static ManejadorCitas appointmentHandler;

    static
    {
        try
        {
            appointmentHandler = new ManejadorCitas();
        }

        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ParseException   //Metodo main: Se pide el ingreso por usuario y contraseña y luego se ejecuta el menu
    {
        //userHandler.solicitudIngreso();
        showMenu();
    }

    private static void showMenu() throws IOException, ParseException   //Metodo encargado de desplegar el menu del sistema
    {
        //Opciones del menu
        String[] opciones = {
                "A) Registrar nuevo Doctor",
                "B) Registrar nuevo Paciente",
                "C) Registrar nueva Cita",
                "D) Consultar Cita",
                "E) Salir del programa"};

        String eleccionCompleta;        //Captura el valor de la string elegida completa
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida = false;   //Boolean que permite que las opciones puedan repetirse indefinidamente hasta que el usuario desee salir del programa

        do
        {
            accionValida = true;

            //Almacena el primer caracter de la opcion elegida
            char eleccionSwit;

            try
            {
                //Panel que despliega el atributo a calcular
                eleccionCompleta = (String) JOptionPane.showInputDialog(null,"Seleccione la opcion que desea ejecutar", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                eleccionSwit = eleccionCompleta.charAt(0);

                switch(eleccionSwit) //Dependiendo de la opcion elegida, se ejecuta la acción especifica
                {
                    case 'A':   //Ingresar un nuevo doctor a la base de datos
                        doctorHandler.ingresarNuevoDoctor();
                        accionValida = false;
                        break;

                    case 'B':   //Ingresar un nuevo paciente a la base de datos
                        pacientHandler.ingresarNuevoPaciente();
                        accionValida = false;
                        break;

                    case 'C':   //Ingresar una nueva cita a la base de datos
                        if(doctorHandler.getCursor().isEmpty() || pacientHandler.getCursor().isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"No se pueden ingresar citas hasta que por lo menos haya un doctor y un paciente en la base de datos","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        }

                        else
                        {
                            appointmentHandler.ingresarNuevaCita();
                        }

                        accionValida = false;
                        break;

                    case 'D':   //Ingresar una nueva cita a la base de datos
                        if(appointmentHandler.getCursor().isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"No hay ninguna cita que revisar","PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                        }

                        else
                        {
                            appointmentHandler.mostrarCitas();
                        }

                        accionValida = false;
                        break;

                    case 'E':   //Salir del programa
                        JOptionPane.showMessageDialog(null,"Programa terminado", "PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                        break;
                }
            }

            catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
            {
                //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                salirProceso = JOptionPane.showConfirmDialog(null,"Quiere salir del programa?", "PROYECTO FINAL JAVA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

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

            catch(IndexOutOfBoundsException b) //El usuario no ingresó nada y dió aceptar de todas formas
            {
                JOptionPane.showMessageDialog(null,"Comando no reconocido, vuelva a intentarlo", "PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                accionValida = false;
            }
        }
        while(!accionValida);
    }
}