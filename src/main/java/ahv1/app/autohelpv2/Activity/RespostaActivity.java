package ahv1.app.autohelpv2.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import ahv1.app.autohelpv2.R;
import ahv1.app.autohelpv2.adapter.ComentarioAdapter;

public class RespostaActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    String txtPergunta;
    SQLiteDatabase bancoDados;
    private EditText txtComentario;
    private Button botaoLogar;
    ListView lista;
    ArrayAdapter itensAdaptados;
    ArrayList<Comentario> listaItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);
        bancoDados = openOrCreateDatabase("AutoHelp", MODE_PRIVATE , null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS Resposta(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " txtPost VARCHAR(50), autor VARCHAR(40) NOT NULL, dataPost VARCHAR(40) NOT NULL);");
        System.out.println("Criei o bd");

        Bundle extra = getIntent().getExtras();
        textView = (TextView) findViewById(R.id.textResposta);

        if(extra != null){
            txtPergunta = extra.getString("txtComentario");
        }

        textView.setText(txtPergunta);
        //inicializa toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarConversa);
        toolbar.setTitle("AutoHelp");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        try {
            System.out.println("To aqui");
            botaoLogar = (Button) findViewById(R.id.buttonR);
            txtComentario = (EditText) findViewById(R.id.EditResposta);
            lista = (ListView) findViewById(R.id.ListR);

            botaoLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comentario post = new Comentario();
                    post.setTxt_comentario(txtComentario.getText().toString());
                    post.setUsuario("Isabela Carolina");

                    Locale locale = new Locale("pt","BR");
                    GregorianCalendar calendar = new GregorianCalendar();
                    SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'", locale);

                    post.setDataPost(formatador.format(calendar.getTime()));
                    GuardaPost(post);
                }
            });

            recuperaPost(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GuardaPost(Comentario comentario) {

        try{
            if(comentario.getTxt_comentario().equals("")){
                Toast.makeText(RespostaActivity.this,"Digite sua Dúvida", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("entrei aqui");

                Cursor cursor = bancoDados.rawQuery("SELECT txtPost FROM Resposta", null);
                int postIndex = cursor.getColumnIndex("txtPost");
                cursor.moveToFirst();

                boolean verificaExistencia = false;

                while (!cursor.isAfterLast()) {
                    if(cursor.getString(postIndex).isEmpty()){
                        verificaExistencia = true;
                    }
                }
                if(verificaExistencia == false) {
                    bancoDados.execSQL("INSERT INTO Resposta( txtPost, autor, dataPost ) VALUES( '" + comentario.getTxt_comentario() +
                            "', '" + comentario.getUsuario() + "', '" + comentario.getDataPost() + "' ) ");
                    System.out.println("guardei");

                    Toast.makeText(RespostaActivity.this, "Dúvida Publicada", Toast.LENGTH_SHORT).show();
                    txtComentario.setText("");
                    recuperaPost(lista);
                } else {
                    Toast.makeText(RespostaActivity.this, "Dúvida já Publicada", Toast.LENGTH_SHORT).show();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void recuperaPost(ListView lista){
        try {
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM Resposta ORDER BY id DESC", null);
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
            itensAdaptados = new ComentarioAdapter(RespostaActivity.this, listaItens);

            lista.setAdapter(itensAdaptados);

            System.out.println("To aqui");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
