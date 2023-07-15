package com.example.preexamen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.preexamen.R;
import com.example.preexamen.Usuario;

import java.util.List;

public class MiAdaptador extends BaseAdapter {
    private Context context;
    private List<Usuario> listaUsuarios;

    public MiAdaptador(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_lista, null);
        } else {
            view = convertView;
        }

        TextView nombre = view.findViewById(R.id.editTextEmail);
        TextView correo = view.findViewById(R.id.editTextPassword);

        Usuario usuario = listaUsuarios.get(position);
        nombre.setText(usuario.getNombreUsuario());
        correo.setText(usuario.getCorreo());

        return view;
    }
}
