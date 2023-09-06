package com.example.sesion03_2023.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sesion03_2023.R;
import com.example.sesion03_2023.dao.AlumnoDataSource;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Alumnos;
import com.example.sesion03_2023.models.Carrera;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormAddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormAddStudentFragment extends Fragment {

    private EditText editTextNombre, editTextApellido, editTextCorreo;
    private Spinner spinnerCarrera;
    private Button btnAgregar;

    private CarreraDataSource carreraDataSource;
    private AlumnoDataSource alumnoDataSource;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormAddStudentFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static FormAddStudentFragment newInstance(Alumnos alumnos, boolean isEditing) {
        FormAddStudentFragment fragment = new FormAddStudentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, alumnos);
        args.putBoolean(ARG_PARAM2, isEditing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_add_student, container, false);

        // Asignar animación personalizada a la vista del formulario
        Animation slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
        view.startAnimation(slideUp);

        alumnoDataSource = new AlumnoDataSource(requireContext()); // Inicializa alumnoDataSource
        alumnoDataSource.open();

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido = view.findViewById(R.id.editTextApellido);
        editTextCorreo = view.findViewById(R.id.editTextCorreo);
        spinnerCarrera = view.findViewById(R.id.spinnerCarrera);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        carreraDataSource = new CarreraDataSource(requireContext()); // Inicializa carreraDataSource
        carreraDataSource.open();
        // Configurar el adaptador para el Spinner directamente aquí
        ArrayAdapter<Carrera> carreraAdapter = new ArrayAdapter<Carrera>(requireContext(), android.R.layout.simple_spinner_item, obtenerListaDeCarreras()) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                }

                // Obtiene la carrera en la posición actual
                Carrera carrera = getItem(position);

                // Configura el texto que se mostrará en el desplegable
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(carrera.getNombre()); // Muestra el nombre de la carrera

                return convertView;
            }
        };

        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(carreraAdapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la carrera seleccionada del Spinner
                Carrera carreraSeleccionada = (Carrera) spinnerCarrera.getSelectedItem();

                // Verificar si se ha seleccionado la opción ficticia
                if (carreraSeleccionada.getNombre().equals("Seleccione su carrera")) {
                    // Mostrar un mensaje de error al usuario
                    Toast.makeText(requireContext(), "Por favor, seleccione una carrera válida", Toast.LENGTH_SHORT).show();
                    return; // Salir de la función sin procesar los datos
                }

                // Obtener los datos del formulario
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String correo = editTextCorreo.getText().toString();
                // Obtener el ID de la carrera seleccionada
                int idCarreraSeleccionada = carreraSeleccionada.getIdCarrera();

                // Crear una instancia de Alumnos y configurar sus propiedades
                Alumnos alumnoNuevo = new Alumnos();
                alumnoNuevo.setNombre(nombre);
                alumnoNuevo.setApellidos(apellido);
                alumnoNuevo.setCorreo(correo);
                alumnoNuevo.setCarrera_id(idCarreraSeleccionada);

                //Agregar Alumno a la base de datos
                long idAlumno = alumnoDataSource.agregarAlumno(alumnoNuevo);

                if (idAlumno != -1){
                    Toast.makeText(requireContext(), "Alumno agregado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error al agregar el alumno", Toast.LENGTH_SHORT).show();
                }

            }


        });

        // Verifica si estamos editando un alumno
        if (getArguments() != null) {
            Alumnos alumno = (Alumnos) getArguments().getSerializable(ARG_PARAM1);
            boolean isEditing = getArguments().getBoolean(ARG_PARAM2);

            if (alumno != null && isEditing) {
                // Estamos editando un alumno, carga los datos
                editTextNombre.setText(alumno.getNombre());
                editTextApellido.setText(alumno.getApellidos());
                editTextCorreo.setText(alumno.getCorreo());

                // Cambia el título y el texto del botón
                btnAgregar.setText("Actualizar");

                // Configura el listener para la acción de edición
                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Obtener la carrera seleccionada del Spinner
                        Carrera carreraSeleccionada = (Carrera) spinnerCarrera.getSelectedItem();

                        // Verificar si se ha seleccionado la opción ficticia
                        if (carreraSeleccionada.getNombre().equals("Seleccione su carrera")) {
                            // Mostrar un mensaje de error al usuario
                            Toast.makeText(requireContext(), "Por favor, seleccione una carrera válida", Toast.LENGTH_SHORT).show();
                            return; // Salir de la función sin procesar los datos
                        }

                        // Obtener los datos editados y actualizar el alumno
                        String nuevoNombre = editTextNombre.getText().toString().trim();
                        String nuevosApellidos = editTextApellido.getText().toString().trim();
                        String nuevoCorreo = editTextCorreo.getText().toString().trim();
                        int idCarreraSeleccionada = carreraSeleccionada.getIdCarrera();

                        if (TextUtils.isEmpty(nuevoNombre) || TextUtils.isEmpty(nuevosApellidos) || TextUtils.isEmpty(nuevoCorreo)) {
                            // Validación de campos vacíos
                            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Actualizar el alumno existente
                        alumno.setNombre(nuevoNombre);
                        alumno.setApellidos(nuevosApellidos);
                        alumno.setCorreo(nuevoCorreo);
                        alumno.setCarrera_id(idCarreraSeleccionada);

                        // Llama a la función para actualizar el alumno en la base de datos
                        AlumnoDataSource alumnoDataSource = new AlumnoDataSource(requireContext());
                        alumnoDataSource.open();

                        if (alumnoDataSource.editarAlumno(alumno) > 0) {
                            Toast.makeText(requireContext(), "Alumno actualizado con éxito", Toast.LENGTH_SHORT).show();
                            // Cerrar el fragmento de edición y volver atrás
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Error al actualizar el alumno", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


        return view;
    }


    private List<Carrera> obtenerListaDeCarreras() {
        // Asumiendo que tienes acceso a carreraDataSource, puedes obtener la lista de carreras así:
        carreraDataSource.open();
        List<Carrera> listaCarreras = carreraDataSource.listarCarreras();
        carreraDataSource.close();

        // Crear una carrera ficticia para "Seleccione su carrera"
        Carrera carreraDefault = new Carrera();
        carreraDefault.setNombre(getString(R.string.seleccionar_carrera)); // Obtener la cadena desde strings.xml
        listaCarreras.add(0, carreraDefault);

        return listaCarreras;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Asegúrate de cerrar las bases de datos cuando se destruye la vista del fragmento
        if (alumnoDataSource != null) {
            alumnoDataSource.close();
        }
        if (carreraDataSource != null) {
            carreraDataSource.close();
        }
    }


}