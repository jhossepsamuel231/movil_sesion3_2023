package com.example.sesion03_2023.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sesion03_2023.R;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Carrera;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormAddCareerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormAddCareerFragment extends Fragment {

    private EditText editTextCareer;
    private Button btnAgregar;
    private TextView textViewTitle;
    private CarreraDataSource carreraDataSource;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormAddCareerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FormAddCareerFragment newInstance(Carrera carrera, boolean isEditing) {
        FormAddCareerFragment fragment = new FormAddCareerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, carrera);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_add_career, container, false);

        // Asignar animación personalizada a la vista del formulario
        Animation slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
        view.startAnimation(slideUp);

        editTextCareer = view.findViewById(R.id.editTextCareer);
        btnAgregar = view.findViewById(R.id.btnAgregar);
        textViewTitle = view.findViewById(R.id.tituloAddEdit);

        // Inicializar carreraDataSource
        carreraDataSource = new CarreraDataSource(requireContext());
        carreraDataSource.open(); // Asegúrate de abrir la base de datos aquí

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos del formulario
                String nombreCareer = editTextCareer.getText().toString();

                // Crear una instancia de Carrera y configurar sus propiedades
                Carrera carrera = new Carrera();
                carrera.setNombre(nombreCareer);
                carrera.setEstado(true); // Opcional, configura el estado como desees

                // Agregar la carrera a la base de datos
                long idCarrera = carreraDataSource.agregarCarrera(carrera);

                if (idCarrera != -1) {
                    Toast.makeText(requireContext(), "Carrera agregada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error al agregar la carrera", Toast.LENGTH_SHORT).show();
                }

                // Limpiar el campo de texto después de agregar
                editTextCareer.setText("");
            }
        });

        // Verifica si estamos editando una carrera
        if (getArguments() != null) {
            Carrera carrera = (Carrera) getArguments().getSerializable(ARG_PARAM1);
            boolean isEditing = getArguments().getBoolean(ARG_PARAM2);

            if (carrera != null && isEditing) {
                // Estamos editando una carrera, carga los datos
                editTextCareer.setText(carrera.getNombre());

                // Obtén la referencia al RadioGroup y RadioButtons
                RadioGroup radioGroupEstado = view.findViewById(R.id.radioGroupEstado);
                RadioButton radioButtonActiva = view.findViewById(R.id.radioButtonActiva);
                RadioButton radioButtonInactiva = view.findViewById(R.id.radioButtonInactiva);

                // Configura la visibilidad del RadioGroup
                radioGroupEstado.setVisibility(View.VISIBLE);

                // Establece el estado actual de la carrera en los RadioButtons
                if (carrera.getEstado()) {
                    radioButtonActiva.setChecked(true);
                } else {
                    radioButtonInactiva.setChecked(true);
                }

                // Cambia el título y el texto del botón

                textViewTitle.setText("Editando Carrera");
                btnAgregar.setText("Editar");

                // Configura el listener para la acción de edición
                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Obtener los datos editados y actualizar la carrera
                        String nuevoNombre = editTextCareer.getText().toString().trim();
                        if (TextUtils.isEmpty(nuevoNombre)) {
                            editTextCareer.setError("El nombre de la carrera no puede estar vacío");
                            return;
                        }


                        // Obtener el estado seleccionado de los RadioButtons
                        boolean nuevoEstado = radioButtonActiva.isChecked(); // true si está activa, false si está inactiva


                        // Actualizar la carrera existente
                        carrera.setNombre(nuevoNombre);
                        carrera.setEstado(nuevoEstado);

                        // Llama a la función para actualizar la carrera en la base de datos
                        if (carreraDataSource.actualizarCarrera(carrera)) {
                            Toast.makeText(requireContext(), "Carrera actualizada con éxito", Toast.LENGTH_SHORT).show();
                            // Cerrar el fragmento de edición y volver atrás
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Error al actualizar la carrera", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


        return view;
    }
}