import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ManejadorCitas implements ManejadorArchivos    //Manejador del archivo de citas
{
    public ManejadorCitas() throws IOException  //Constructor realizado para añadir IOException
    {

    }

    private ArrayList<Cita> cursor = new ArrayList<Cita>(); //Cursor que almacenará las citas que esten contenidas en el documento de texto

    @Override
    public void load() throws IOException   //Carga los registros del archivo de citas
    {
        cursor = new ArrayList<Cita>();
        FileReader textoCompleto = new FileReader("Databases\\Citas.txt");   //El file reader que permitirá leer el archivo
        BufferedReader bufred = new BufferedReader(textoCompleto);              //Se genera el buffered reader
        String linea;                                                           //ALmacena cada linea del documento
        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            String [] lineaSeparada = linea.split(",");                   //Separa la linea obtenida por las comas, teniendo por separaado el teléfono y el nombre al que le corresponde

           Cita cita = new Cita(Integer.parseInt(lineaSeparada[0]), lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], Integer.parseInt(lineaSeparada[4]), Integer.parseInt(lineaSeparada[5]));
           cursor.add(cita);
        }

        bufred.close();     //Se cierra el BufferedReader
    }

    @Override
    public void write() throws IOException  //Guarda los contenidos del cursor en el archivo de citas
    {
        FileWriter meterLista = new FileWriter("Databases\\Citas.txt");     //Se genera un filewriter paar el archivo

        for(int i = 0; i < cursor.size(); i++)    //Se inserta cada contacto en el archivo de texto
        {
            Cita cita = cursor.get(i);

            if(i == 0)  //Si es la primera insercion
            {
                meterLista.write(cita.getID() + "," + cita.getFecha()+ "," + cita.getHora()+ "," + cita.getMotivo() + "," + cita.getDoctor()  + "," + cita.getPaciente() + "\n");
            }

            else    //Todas las demas
            {
                meterLista.append(cita.getID() + "," + cita.getFecha()+ "," + cita.getHora()+ ","+ cita.getMotivo() + "," + cita.getDoctor()  + "," + cita.getPaciente() + "\n");
            }
        }
        meterLista.close();  //Se cierra el File Writer
    }

    public void ingresarNuevaCita() throws IOException  //Proceso para ingresar una nueva cita
    {
        File archivo = new File("Databases\\Citas.txt");    //Se crea el archivo

        if(archivo.exists())   //Si el archivo ya existe dentro del programa, se cargan los valores, de lo contrario no se hace
        {
            this.load();
        }

        LocalDateTime ahora = LocalDateTime.now();  //Guarda la fecha actual con hora

        //Se genera una lista de años para poder guardar citas durante los próximos 5 años
        Integer[] listaAnios = new Integer[]{ahora.getYear(), ahora.getYear() + 1, ahora.getYear() + 2, ahora.getYear() + 3, ahora.getYear() + 4, ahora.getYear() + 5};

        String motivoCita = "", doctorAsignado = "", pacienteAsignado = "", doctorSeleccionado = "", pacienteSeleccionado = "";     //Alamcena los valores en los que guardar los valores que construiran la cita
        Integer anioCita = 0, mesCita = 0, diaCita = 0, horaCita = 0;   //Valores que construiran la fecha a guadar
        boolean anioIngresado = false,  mesIngresado = false, diaIngresado = false, horaIngresada = false, motivoIngresado = false, doctorIngresado = false, pacienteIngresado  = false;    //Evalua si los valores respectivos ya fueron ingresados por el usuario
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida;       //Evalua que se repita el proceso completo

        do
        {
            accionValida = true;

            try
            {
                //Solicitud de la entrada del año de la cita
                if(!anioIngresado)
                {
                    do
                    {
                        anioCita = (Integer) JOptionPane.showInputDialog(null,"Ingrese el año de la cita", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, listaAnios, listaAnios[0]);
                        anioCita.equals(1);
                        anioIngresado = true;
                    }
                    while (!anioIngresado);
                }

                //Solicitud de la entrada del mes de la cita
                if(!mesIngresado)
                {
                    //Se genera una lista de meses para poder guardar citas
                    Integer[] listaMeses;
                    listaMeses = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

                    do
                    {
                        mesCita = (Integer) JOptionPane.showInputDialog(null,"Ingrese el mes de la cita", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, listaMeses, listaMeses[0]);
                        mesCita.equals(1);
                        mesIngresado = true;
                    }
                    while (!mesIngresado);
                }

                Integer diaLimite = 0;

                //En base al mes elegido, se determina el limite de dias que cada mes debe tener
                switch(mesCita)
                {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        diaLimite = 31;
                        break;

                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        diaLimite = 30;
                        break;

                    case 2:
                        if(anioCita % 4 == 0)
                        {
                            diaLimite = 29;
                        }

                        else
                        {
                            diaLimite = 28;
                        }
                        break;
                }

                //Solicitud de la entrada del dia de la cita
                if(!diaIngresado)
                {
                    //En base al limite de dias determinado por el mes, se genera la lista de los dias
                    Integer[] listaDias;
                    int j = 0;
                    listaDias = new Integer[diaLimite];

                    for (Integer i = 1; i < diaLimite+1; i++)
                    {
                        listaDias[j] = i;
                        j++;
                    }

                    do
                    {
                        diaCita = (Integer) JOptionPane.showInputDialog(null,"Ingrese el dia en el que desea realizar la cita", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, listaDias, listaDias[0]);
                        diaCita.equals(1);
                        diaIngresado = true;
                    }
                    while (!diaIngresado);
                }

                //Solicitud de la entrada de la hora de la cita
                if(!horaIngresada)
                {
                    //Se genera una lista de horas para poder guardar citas
                    Integer[] listaHoras;
                    listaHoras = new Integer[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

                    do
                    {
                        horaCita = (Integer) JOptionPane.showInputDialog(null,"Ingrese la hora de la cita", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE,null, listaHoras, listaHoras[0]);
                        horaCita.equals(1);
                        horaIngresada = true;
                    }
                    while (!horaIngresada);
                }

                //Se genera una fecha con los datos ingresados
                LocalDateTime diaHoraCita = LocalDateTime.of(anioCita,mesCita,diaCita,horaCita,0);

                //Se compara si la fecha ingresada es anterior o igual a la fecha actual del sistema, en caso de ser asi, se manda un mensaje de error y se pide ingresar una fecha que sea válida, es decir, que se encuentre en el futuro
                if(diaHoraCita.isBefore(ahora) || diaHoraCita.isEqual(ahora))
                {
                    JOptionPane.showMessageDialog(null, "La fecha ingresada es anterior o igual a la fecha actual, la fecha valida mas cercana es una hora despues a la fecha actual", "PROYECTO FINAL JAVA", JOptionPane.ERROR_MESSAGE);
                    anioIngresado = false;
                    mesIngresado = false;
                    diaIngresado = false;
                    horaIngresada = false;
                    accionValida = false;
                    continue;
                }

                //Solicitud de la entrada del motivo de la cita
                if(!motivoIngresado)
                {
                    do
                    {
                        motivoCita = (String) JOptionPane.showInputDialog(null,"Ingrese el motivo de la cita", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = motivoCita.charAt(0);
                        motivoIngresado = true;
                    }
                    while (!motivoIngresado);
                }

                //Lista que almacenará los doctores y los pacientes que pasen el filtro que se mencionará mas adelante
                ArrayList<Doctor> doctoresEncontrados = new ArrayList<>();
                ArrayList<Paciente> pacientesEncontrados = new ArrayList<>();

                //Almacena a los doctores y pacientes de las listas de arriba a modo de String
                String[] nombresDoctores;
                String[] nombresPacientes;

                //Almacenan los indices que tendrán  el paciente y el doctor seleccionado dentro del arraylist
                int indiceDoctor = 0;
                int indicePaciente = 0;

                //Solicitud de la entrada del doctor de la cita
                if(!doctorIngresado)
                {
                    do
                    {
                        //Se pide ingresar el apellido del doctor que tendrá la cita
                        doctorAsignado = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido paterno del doctor que el paciente requiere", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = doctorAsignado.charAt(0);
                        doctorIngresado = true;

                        //Se manda a llamar al manejador del archivo de doctores y se buscan aquellos que compartan el apellido paterno con el valor ingresado antes
                        for (Doctor doctor: Main.doctorHandler.getCursor())
                        {
                            if(doctorAsignado.equals(doctor.getNombreCompleto()[1]))
                            {
                                doctoresEncontrados.add(doctor);
                            }
                        }

                        //Si no se ha encontrado nada, se pide que se ingrese otro apellido paterno
                        if(doctoresEncontrados.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"No hay ningun doctor registrado con ese apellido, intente de nuevo.","PROYECTO FINAL JAVA",JOptionPane.ERROR_MESSAGE);
                            doctorIngresado = false;
                        }

                        //Se encontró por lo menos un doctor con un apellido igual al ingresado
                        else
                        {
                            nombresDoctores = new String[doctoresEncontrados.size()];

                            //Los doctores encontrados se pasan a String
                            for(int i = 0; i < nombresDoctores.length; i++)
                            {
                                nombresDoctores[i] = doctoresEncontrados.get(i).getNombreCompleto()[1] + " " + doctoresEncontrados.get(i).getNombreCompleto()[2] + " " + doctoresEncontrados.get(i).getNombreCompleto()[0] + ", " + doctoresEncontrados.get(i).getEspecialidad();
                            }

                            //Se pide que se elija de entre las opciones al doctor correcto
                            doctorSeleccionado = (String) JOptionPane.showInputDialog(null,"Seleccione el doctor especifico", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null,nombresDoctores, nombresDoctores[0]);
                            notEmpty = doctorSeleccionado.charAt(0);

                            //Se recupera el indice del doctor seleccionado para no perderlo
                            indiceDoctor = Arrays.asList(nombresDoctores).indexOf(doctorSeleccionado);
                        }

                    }
                    while (!doctorIngresado);
                }

                //Solicitud de la entrada del doctor de la cita, sigue el mismo proceso que a la hora de pedir el doctor
                if(!pacienteIngresado)
                {
                    do
                    {
                        pacienteAsignado = (String) JOptionPane.showInputDialog(null,"Ingrese el apellido paterno del paciente", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE);
                        char notEmpty = pacienteAsignado.charAt(0);
                        pacienteIngresado = true;

                        for (Paciente paciente: Main.pacientHandler.getCursor())
                        {
                            if(pacienteAsignado.equals(paciente.getNombreCompleto()[1]))
                            {
                                pacientesEncontrados.add(paciente);
                            }
                        }

                        if(pacientesEncontrados.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"No hay ningun paciente registrado con ese apellido, intente de nuevo.","PROYECTO FINAL JAVA",JOptionPane.ERROR_MESSAGE);
                            pacienteIngresado = false;
                        }

                        else
                        {
                            nombresPacientes = new String[pacientesEncontrados.size()];

                            for(int i = 0; i < nombresPacientes.length; i++)
                            {
                                nombresPacientes[i] = pacientesEncontrados.get(i).getNombreCompleto()[1] + " " + pacientesEncontrados.get(i).getNombreCompleto()[2] + " " + pacientesEncontrados.get(i).getNombreCompleto()[0];
                            }

                            pacienteSeleccionado = (String) JOptionPane.showInputDialog(null,"Seleccione el paciente especifico", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, nombresPacientes, nombresPacientes[0]);
                            notEmpty = pacienteSeleccionado.charAt(0);
                            indicePaciente = Arrays.asList(nombresPacientes).indexOf(pacienteSeleccionado);
                        }
                    }
                    while (!pacienteIngresado);
                }

                //Se construyen la fecha y la hora
                String diaCompleto = diaCita + "/" + mesCita + "/" + anioCita;
                String horaCompleta = horaCita + ":00";

                //Se contruye la cita, se guarda en el cursor, se actualiza el contador y finalmente se guarda en el archivo
                Cita nuevaCita = new Cita(recuperarCitasTotales()+1, diaCompleto, horaCompleta,motivoCita, doctoresEncontrados.get(indiceDoctor).getID(),pacientesEncontrados.get(indicePaciente).getID());
                cursor.add(nuevaCita);
                actualizarCitasTotales(nuevaCita.getID());
                this.write();

                //Mensaje de guardado exitoso
                JOptionPane.showMessageDialog(null, "Nueva cita creada exitosamente.","PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);

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

    public void mostrarCitas() throws IOException
    {
        boolean accionValida;

        do
        {
            accionValida = true;

            try
            {
                load();

                String[] citasString = new String[cursor.size()];

                int i = 0;
                for (Cita cita: cursor)
                {
                    citasString[i] = cita.getFecha() + ", " + cita.getHora();
                    i++;
                }

                String citaSeleccionada = (String) JOptionPane.showInputDialog(null,"Seleccione la cita especifica que desea revisar", "PROYECTO FINAL JAVA", JOptionPane.QUESTION_MESSAGE, null, citasString, citasString[0]);
                char notEmpty = citaSeleccionada.charAt(0);
                int indiceCita = Arrays.asList(citasString).indexOf(citaSeleccionada);

                JOptionPane.showMessageDialog(null, cursor.get(indiceCita).toString(), "PROYECTO FINAL JAVA", JOptionPane.INFORMATION_MESSAGE);
            }

            catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
            {
                //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                int salirProceso = JOptionPane.showConfirmDialog(null,"Quiere cancelar el proceso?", "PROYECTO FINAL JAVA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

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
        } while (!accionValida);
    }

    public int recuperarCitasTotales() throws IOException   //Se recupera el valor del contador total de citas para poder asignar un ID
    {
        FileReader archivo = new FileReader("Contadores\\ContadorTotalCitas.txt");

        BufferedReader bufred = new BufferedReader(archivo);

        int regresar = Integer.parseInt(bufred.readLine());

        bufred.close();

        return regresar;
    }

    public void actualizarCitasTotales(int nuevoValor) throws IOException   //Se actualiza el valor del contador total de citas para poder asignar un ID a la siguiente cita
    {
        FileWriter archivo = new FileWriter("Contadores\\ContadorTotalCitas.txt");

        archivo.write(String.valueOf(nuevoValor));

        archivo.close();
    }

    public ArrayList<Cita> getCursor() throws IOException       //Recupera el cursor de la clase
    {
        File archivo = new File("Databases\\Citas.txt");

        if (archivo.exists())
        {
            load();
        }

        return cursor;
    }


}
