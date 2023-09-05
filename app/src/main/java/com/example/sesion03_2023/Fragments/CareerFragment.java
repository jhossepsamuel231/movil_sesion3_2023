package com.example.sesion03_2023.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sesion03_2023.R;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Carrera;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CareerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CareerFragment extends Fragment {

    private Button btnAgregar;
    private CarreraDataSource carreraDataSource;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CareerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CareerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CareerFragment newInstance(String param1, String param2) {
        CareerFragment fragment = new CareerFragment();
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
        View view = inflater.inflate(R.layout.fragment_career, container, false);

        btnAgregar = view.findViewById(R.id.btnAddCareer);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFormulario();
            }
        });

        // Inicializar y abrir la base de datos
        carreraDataSource = new CarreraDataSource(requireContext());
        carreraDataSource.open();

        // Obtener el TableLayout desde el diseño
        TableLayout tableLayout = view.findViewById(R.id.tableLayout);

        // Obtener la lista de carreras desde CarreraDataSource
        List<Carrera> carreras = carreraDataSource.listarCarreras();

        // Agregar las filas de carrera a la tabla
        for (Carrera carrera : carreras) {
            TableRow row = new TableRow(requireContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(layoutParams);

            // Agregar los datos de la carrera a las celdas
            TextView nombreTextView = new TextView(requireContext());
            nombreTextView.setText(carrera.getNombre());
            nombreTextView.setGravity(Gravity.CENTER);
            nombreTextView.setPadding(8, 8, 8, 8);

            TextView estadoTextView = new TextView(requireContext());
            estadoTextView.setText(carrera.getEstado() ? "Activa" : "Inactiva");
            estadoTextView.setGravity(Gravity.CENTER);
            estadoTextView.setPadding(8, 8, 8, 8);

            // Puedes agregar botones u otras acciones aquí si es necesario

            // Agregar las vistas a la fila
            row.addView(nombreTextView);
            row.addView(estadoTextView);

            // Agregar la fila a la tabla
            tableLayout.addView(row);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Asegúrate de cerrar la base de datos cuando se destruye la vista
        if (carreraDataSource != null) {
            carreraDataSource.close();
        }
    }


    private void mostrarFormulario() {
        // Crear una instancia del Fragment del formulario
        Fragment formFragment = new FormAddCareerFragment();

        // Iniciar la transacción de Fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplazar el contenido actual con el Fragment del formulario
        transaction.replace(R.id.fragment_container, formFragment);

        // Agregar la transacción a la pila de retroceso (opcional)
        transaction.addToBackStack(null);

        // Confirmar la transacción
        transaction.commit();
    }


}