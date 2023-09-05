package com.example.sesion03_2023.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormAddCareerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormAddCareerFragment newInstance(String param1, String param2) {
        FormAddCareerFragment fragment = new FormAddCareerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        return view;
    }
}