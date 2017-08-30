package ahv1.app.autohelpv2.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import ahv1.app.autohelpv2.Activity.Comentario;
import ahv1.app.autohelpv2.Activity.RespostaActivity;
import ahv1.app.autohelpv2.R;
import ahv1.app.autohelpv2.adapter.ComentarioAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    private EditText txtComentario;
    private Button botaoLogar;
    protected SQLiteDatabase bancoDados;
    private ArrayAdapter itensAdaptados;
    private ArrayList<Comentario> listaItens;
    private ListView lista;

    public ForumFragment() {
    }

   @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            bancoDados = getActivity().openOrCreateDatabase("AutoHelp", Context.MODE_PRIVATE , null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS Coment(id INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL," +
                    " txtPost VARCHAR(50) NOT NULL, autor VARCHAR(40), dataPost VARCHAR(20));");
            System.out.println("Criei o bd");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        try {
            System.out.println("To aqui");
            botaoLogar = (Button) view.findViewById(R.id.button);
            txtComentario = (EditText) view.findViewById(R.id.txtpost);
            lista = (ListView) view.findViewById(R.id.List_id);

            botaoLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comentario post = new Comentario();

                    Locale locale = new Locale("pt","BR");
                    GregorianCalendar calendar = new GregorianCalendar();
                    SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'", locale);

                    post.setUsuario("Isabela Carolina");

                    post.setDataPost(formatador.format(calendar.getTime()));
                    post.setTxt_comentario(txtComentario.toString());

                    GuardaPost(post);
                }
            });

            recuperaPost(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comentario comentario = listaItens.get(position);
                Intent intent = new Intent(getActivity(), RespostaActivity.class);
                intent.putExtra("txtComentario", comentario.getTxt_comentario());
                startActivity(intent);
            }
        });
        return view;
    }


    public void GuardaPost(Comentario comentario) {

        try{
            if(comentario.getTxt_comentario().equals("")){
                Toast.makeText(getActivity(),"Digite sua Dúvida", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("entrei aqui");

                Cursor cursor = bancoDados.rawQuery("SELECT txtPost FROM Coment", null);
                int postIndex = cursor.getColumnIndex("txtPost");
                cursor.moveToFirst();

                boolean verificaExistencia = false;

                while (!cursor.isAfterLast()) {
                    if(cursor.getString(postIndex).isEmpty()){
                        verificaExistencia = true;
                    }
                }
                if(verificaExistencia == false) {
                    bancoDados.execSQL("INSERT INTO Coment( txtPost, autor, dataPost ) VALUES( '" + comentario.getTxt_comentario() +
                            "', '" + comentario.getUsuario() + "', '" + comentario.getDataPost() + "' ) ");
                    System.out.println("guardei");

                    Toast.makeText(getActivity(), "Dúvida Publicada", Toast.LENGTH_SHORT).show();
                    txtComentario.setText("");
                    recuperaPost(lista);

                } else {
                    Toast.makeText(getActivity(), "Dúvida já Publicada", Toast.LENGTH_SHORT).show();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void recuperaPost(ListView lista){
        try {
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM Coment ORDER BY id DESC", null);
            //int IdIndex = cursor.getColumnIndex("id");
            int postIndex = cursor.getColumnIndex("txtPost");
            int autorIndex = cursor.getColumnIndex("autor");
            int dataIndex = cursor.getColumnIndex("dataPost");

            listaItens = new ArrayList<>();
            cursor.moveToFirst();
            System.out.println("Cheguei pra lista");

            Comentario post;
            while (!cursor.isAfterLast()) {
                post = new Comentario();

                post.setTxt_comentario(cursor.getString(postIndex));
                post.setUsuario(cursor.getString(autorIndex));
                post.setDataPost(cursor.getString(dataIndex));

                //Log.i("Resultado: ", cursor.getString(postIndex));
                listaItens.add(post);
                cursor.moveToNext();
            }

            //itensAdaptados = new ArrayAdapter<>(RespostaActivity.this, R.layout.list, listaItens);
            itensAdaptados = new ComentarioAdapter(getActivity(), listaItens);

            lista.setAdapter(itensAdaptados);

            System.out.println("To aqui");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}