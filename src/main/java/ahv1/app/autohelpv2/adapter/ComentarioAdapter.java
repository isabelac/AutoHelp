package ahv1.app.autohelpv2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ahv1.app.autohelpv2.Activity.Comentario;
import ahv1.app.autohelpv2.R;


/**
 * Created by bella on 28/07/2017.
 */

public class ComentarioAdapter extends ArrayAdapter<Comentario> {

    private ArrayList<Comentario> comentarios;
    private Context context;

    public ComentarioAdapter(Context c, ArrayList<Comentario> objects) {
        super(c, 0, objects);
        this.context = c;
        this.comentarios = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(comentarios != null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_post, parent, false);
            TextView comentario = (TextView) view.findViewById(R.id.texComent);
            TextView autor = (TextView) view.findViewById(R.id.textView);
            TextView data = (TextView) view.findViewById(R.id.textView2);

            Comentario post = comentarios.get(position);
            comentario.setText(post.getTxt_comentario());
            autor.setText(post.getUsuario());
            data.setText(post.getDataPost());
        }
        return  view;
    }
}
