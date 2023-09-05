package com.example.sesion03_2023.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sesion03_2023.R;
import com.example.sesion03_2023.dao.AlumnoDataSource;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Alumnos;
import com.example.sesion03_2023.models.Carrera;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumnsFragment extends Fragment {
    private AlumnoDataSource alumnoDataSource;
    private CarreraDataSource carreraDataSource;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlumnsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlumnsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlumnsFragment newInstance(String param1, String param2) {
        AlumnsFragment fragment = new AlumnsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumns, container, false);

        //Inicializar y abrir la base de datos
        alumnoDataSource = new AlumnoDataSource(requireContext());
        alumnoDataSource.open();

        // Inicializar carreraDataSource
        carreraDataSource = new CarreraDataSource(requireContext());
        carreraDataSource.open();

        // Obtener el TableLayout desde el diseño
        TableLayout tableLayout = view.findViewById(R.id.tablayoutAlumns);

        //Obtener la lista de alumnos desde alumnoDataSource
        List<Alumnos> alumnosList = alumnoDataSource.listarAlumnos();

        //Agregar las filas de alumnos a la tabla
        for (Alumnos alumno: alumnosList){
            TableRow row = new TableRow(requireContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(layoutParams);

            //Agregar los datos de los alumnos a las celdas
            TextView textViewNombreAlumno = new TextView(requireContext());
            textViewNombreAlumno.setText(alumno.getNombre());
            textViewNombreAlumno.setGravity(Gravity.CENTER);
            textViewNombreAlumno.setPadding(8,8,8,8);

            TextView textViewApellidosAlumno = new TextView(requireContext());
            textViewApellidosAlumno.setText(alumno.getApellidos());
            textViewApellidosAlumno.setGravity(Gravity.CENTER);
            textViewApellidosAlumno.setPadding(8, 8, 8, 8);


            TextView textViewCorreo = new TextView(requireContext());
            textViewCorreo.setText(alumno.getCorreo());
            textViewCorreo.setGravity(Gravity.CENTER);
            textViewCorreo.setPadding(8,8,8,8);

            String nombreCarrera = obtenerNombreCarrera(alumno.getCarrera_id());

            TextView textViewCarrera = new TextView(requireContext());
            textViewCarrera.setText(nombreCarrera);
            textViewCarrera.setGravity(Gravity.CENTER);
            textViewCarrera.setPadding(8,8,8,8);


            //Agregar las vistas a la fila
            row.addView(textViewNombreAlumno);
            row.addView(textViewApellidosAlumno);
            row.addView(textViewCorreo);
            row.addView(textViewCarrera);

            //Agregar la fila a la tabla
            tableLayout.addView(row);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Asegúrate de cerrar la base de datos cuando se destruye la vista
        if (alumnoDataSource != null) {
            alumnoDataSource.close();
        }
    }

    // Método para obtener el nombre de la carrera en función del ID de carrera
    private String obtenerNombreCarrera(int idCarrera) {
        Carrera carrera = carreraDataSource.buscarCarreraPorId(idCarrera);
        if (carrera != null) {
            return carrera.getNombre();
        } else {
            return "Carrera no encontrada";
        }
    }
}