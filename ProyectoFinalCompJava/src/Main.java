import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    static ManejadorUsuarios userHandler = new ManejadorUsuarios();
    static ManejadorDoctores doctorHandler = new ManejadorDoctores();

    static ManejadorPacientes pacientHandler = new ManejadorPacientes();

    public static void main(String[] args) throws IOException
    {
        userHandler.solicitudIngreso();
        showMenu();
    }

    private static void showMenu() throws IOException
    {
        String[] opciones = {
                "A) Registrar nuevo Doctor",
                "B) Registrar nuevo Paciente",
                "C) Registrar nueva Cita",
                "D) Salir del programa"};

        String eleccionCompleta;        //Captura el valor de la string elegida completa
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida = false;   //Boolean que permite que las opciones puedan repetirse indefinidamente hasta que el usuario desee salir del programa

        do
        {
            accionValida = true;

            //Almacena las opciones elegidas por el usuario, tanto para el atributo que desea calcular como la forma que desea usar
            char eleccionSwit;

            try
            {
                //Panel que despliega el atributo a calcular
                eleccionCompleta = (String) JOptionPane.showInputDialog(null,"Seleccione la opcion que desea ejecutar", "ACTIVIDAD 13: Anonimos, Lambda y referencias", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                eleccionSwit = eleccionCompleta.charAt(0);

                switch(eleccionSwit) //Dependiendo de la figura elegida, se ejecuta la acción especifica
                {
                    case 'A':
                        doctorHandler.ingresarNuevoDoctor();
                        accionValida = false;
                        break;

                    case 'B':
                        pacientHandler.ingresarNuevoPaciente();
                        accionValida = false;
                        break;

                    case 'C':
                        //Citas
                        accionValida = false;
                        break;

                    case 'D':
                        //Salir del programa
                        JOptionPane.showMessageDialog(null,"Programa terminado", "ACTIVIDAD 13: Anonimos, Lambda y referencias", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                        break;
                }
            }

            catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
            {
                //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                salirProceso = JOptionPane.showConfirmDialog(null,"Quiere salir del programa?", "ACTIVIDAD 13: Anonimos, Lambda y referencias", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //Si presiona Si
                if(salirProceso == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null,"Programa terminado", "ACTIVIDAD 13: Anonimos, Lambda y referencias", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(null,"Comando no reconocido, vuelva a intentarlo", "ACTIVIDAD 13: Anonimos, Lambda y referencias", JOptionPane.ERROR_MESSAGE);
                accionValida = false;
            }
        }
        while(!accionValida);
    }
}