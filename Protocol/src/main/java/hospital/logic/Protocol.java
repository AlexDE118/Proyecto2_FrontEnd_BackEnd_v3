package hospital.logic;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1234;

    //Doctores
    public static final int DOCTOR_CREATE=101;
    public static final int DOCTOR_READ=102;
    public static final int DOCTOR_UPDATE=103;
    public static final int DOCTOR_DELETE=104;
    public static final int DOCTOR_SEARCH_NAME=105;
    public static final int DOCTOR_SEARCH_ID=106;
    public static final int DOCTOR_LOADALL=107;

    //FARMACEUTAS
    public static final int FARMACEUTA_CREATE=201;
    public static final int FARMACEUTA_READ=202;
    public static final int FARMACEUTA_UPDATE=203;
    public static final int FARMACEUTA_DELETE=204;
    public static final int FARMACEUTA_SEARCH_NAME=205;
    public static final int FARMACEUTA_SEARCH_ID=206;
    public static final int FARMACEUTA_LOADALL=207;
    //MEDICAMENTOS
    public static final int MEDICAMENTO_CREATE=301;
    public static final int MEDICAMENTO_READ=302;
    public static final int MEDICAMENTO_UPDATE=303;
    public static final int MEDICAMENTO_DELETE=304;
    public static final int MEDICAMENTO_SEARCH_NAME=305;
    public static final int MEDICAMENTO_SEARCH_ID=306;
    public static final int MEDICAMENTO_LOADALL=307;
    //PACIENTES
    public static final int PACIENTE_CREATE=401;
    public static final int PACIENTE_READ=402;
    public static final int PACIENTE_UPDATE=403;
    public static final int PACIENTE_DELETE=404;
    public static final int PACIENTE_SEARCH_NAME=405;
    public static final int PACIENTE_SEARCH_ID=406;
    public static final int PACIENTE_LOADALL=407;

    //RECETA
    public static final int RECETA_CREATE=501;
    public static final int RECETA_READ=502;
    public static final int RECETA_UPDATE=503;
    public static final int RECETA_DELETE=504;
    public static final int RECETA_SEARCH_ID=505;
    public static final int RECETA_LOADALL=506;
    //PRESCRIPCION
    public static final int PRESCRIPCION_CREATE=601;
    public static final int PRESCRIPCION_READ=602;
    public static final int PRESCRIPCION_UPDATE=603;
    public static final int PRESCRIPCION_DELETE=604;
    public static final int PRESCRIPCION_SEARCH_ID=605;
    public static final int PRESCRIPCION_LOADALL=606;
    public static final int PRESCRIPCION_REMOVE_RECETA=607;
    //DASHBOARD
    public static final int DASHBOARD_GETPRESCRIPCIONES = 701;

    //USUARIO

    public static final int USER_ADD = 801;
    public static final int USER_READ = 802;
    public static final int USER_UPDATE = 803;
    public static final int USER_DELETE = 804;
    public static final int USER_SEARCH_ID = 805;
    public static final int USER_LOADALL = 806;
    public static final int USER_UPDATE_PASSWORD = 807;

    //

    public static final int ERROR_NO_ERROR=0;
    public static final int ERROR_ERROR=1;

    public static final int SYNC = 10;
    public static final int ASYNC=11;
    public static final int DELIVER_MESSAGE=20;

    public static final int DISCONNECT=99;
}
