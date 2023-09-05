package com.example.sesion03_2023.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sesion03_2023.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.sesion03_2023.dao.AlumnoDataSource;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Alumnos;
import com.example.sesion03_2023.models.Carrera;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormAlumnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FormAlumnoFragment extends Fragment {

    private EditText editTextNombre, editTextApellidos, editTextCorreo;
    private Spinner spinnerCarreras;
    private Button btnAgregar;
    private AlumnoDataSource alumnoDataSource;
    private CarreraDataSource carreraDataSource;
    private ArrayAdapter<Carrera> carreraAdapter;
    private Carrera selectedCarrera;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormAlumnoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormAlumnoFragment newInstance(String param1, String param2) {
        FormAlumnoFragment fragment = new FormAlumnoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FormAlumnoFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_alumno, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellidos = view.findViewById(R.id.editTextApellidos);
        editTextCorreo = view.findViewById(R.id.editTextCorreo);
        spinnerCarreras = view.findViewById(R.id.spinnerCarreras);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        alumnoDataSource = new AlumnoDataSource(requireContext());
        carreraDataSource = new CarreraDataSource(requireContext());

        cargarCarreras();

        spinnerCarreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCarrera = carreraAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedCarrera = null;
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarAlumno();
            }
        });

        return view;
    }

    private void cargarCarreras() {
        carreraDataSource.open();
        List<Carrera> carreras = carreraDataSource.listarCarreras();
        carreraDataSource.close();

        // Configurar el adaptador para el Spinner
        carreraAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, carreras);
        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarreras.setAdapter(carreraAdapter);
    }

    private void agregarAlumno() {
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String correo = editTextCorreo.getText().toString();

        if (selectedCarrera != null && !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty()) {
            Alumnos alumno = new Alumnos(null, nombre, apellidos, correo, selectedCarrera.getIdCarrera());
            alumnoDataSource.open();
            alumnoDataSource.agregarAlumno(alumno);
            alumnoDataSource.close();

            // Limpiar los campos después de agregar
            editTextNombre.setText("");
            editTextApellidos.setText("");
            editTextCorreo.setText("");
            selectedCarrera = null;

            // Puedes mostrar un mensaje de éxito o realizar alguna otra acción aquí
        } else {
            // Puedes mostrar un mensaje de error o realizar alguna otra acción aquí
        }
    }
}