package com.example.sesion03_2023.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sesion03_2023.R;
import com.example.sesion03_2023.dao.CarreraDataSource;
import com.example.sesion03_2023.models.Carrera;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CareerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CareerFragment extends Fragment {

    private Button btnAgregar;
    private CarreraDataSource carreraDataSource;
    private TableLayout tableLayout;

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

            // Crear un LinearLayout horizontal para los iconos
            LinearLayout iconLayout = new LinearLayout(requireContext());
            iconLayout.setGravity(Gravity.CENTER);
            iconLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Agregar icono de lápiz (editar)
            ImageView editarIcon = new ImageView(requireContext());
            editarIcon.setImageResource(R.drawable.baseline_edit_24); // Aquí debes tener tus recursos de icono de lápiz
            editarIcon.setPadding(8, 8, 8, 8);
            editarIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Aquí creas un cuadro de diálogo para confirmar la edición
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setMessage("¿Desea editar esta carrera?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Si el usuario elige "Sí", abre el fragmento de edición
                                    editarCarrera(carrera); // Debes pasar la carrera que se va a editar
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Si el usuario elige "No", no haces nada
                                }
                            });
                    // Crea y muestra el cuadro de diálogo
                    builder.create().show();
                }
            });

            // Agregar icono de tachito de basura (eliminar)
            ImageView eliminarIcon = new ImageView(requireContext());
            eliminarIcon.setImageResource(R.drawable.baseline_delete_24); // Aquí debes tener tus recursos de icono de tachito de basura
            eliminarIcon.setPadding(8, 8, 8, 8);
            eliminarIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mostrar un cuadro de diálogo de confirmación antes de eliminar
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setMessage("¿Desea eliminar esta carrera?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Eliminar la carrera aquí
                                    boolean carreraEliminada = carreraDataSource.eliminarCarreraPorId(carrera.getIdCarrera());
                                    if (carreraEliminada) {
                                        Toast.makeText(requireContext(), "Carrera eliminada correctamente", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(requireContext(), "Error al eliminar la carrera", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // No hacer nada si el usuario selecciona "No"
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });

            iconLayout.addView(editarIcon);
            iconLayout.addView(eliminarIcon);

            // Agregar las vistas a la fila
            row.addView(nombreTextView);
            row.addView(estadoTextView);
            row.addView(iconLayout);

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
    private void editarCarrera(Carrera carrera) {
            // Crear una instancia del Fragment del formulario de edición
            FormAddCareerFragment editFragment = FormAddCareerFragment.newInstance(carrera, true);

            // Iniciar la transacción de Fragment
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Reemplazar el contenido actual con el Fragment de edición
            transaction.replace(R.id.fragment_container, editFragment);

            // Agregar la transacción a la pila de retroceso (opcional)
            transaction.addToBackStack(null);

            // Confirmar la transacción
            transaction.commit();
   }
}