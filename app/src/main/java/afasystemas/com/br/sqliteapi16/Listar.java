package afasystemas.com.br.sqliteapi16;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Listar extends AppCompatActivity {


        private List<Contatos> lstContatos = null;
        private ListView lista;
        ContatoDbHelper dao;
        int opChoosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        lista = (ListView) findViewById(R.id.listaL);
        carregarLista();
        dao = new ContatoDbHelper(this);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int opcao, long l) {

                opChoosed = opcao;
//                Toast.makeText(Listar.this, " valor da opção = "+String.valueOf(opcao), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alerta =  new AlertDialog.Builder(Listar.this);
                alerta.setTitle("o que deseja fazer?");
                alerta
                        .setMessage("Escolha uma opção!")
                        .setCancelable(false)
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intenthome = new Intent(getApplication(),Cadastrar.class);
                                Contatos c = (Contatos)lista.getItemAtPosition(opChoosed);
                                intenthome.putExtra("contato",c);
                                intenthome.putExtra("editar",true);

                                startActivity(intenthome);
//                                Toast.makeText(Listar.this, "Editado com Sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Contatos c = (Contatos)lista.getItemAtPosition(opChoosed);

                               boolean resp = dao.deleteContato(c.getId());
                                dao.close();
                                if(resp) {
                                    Toast.makeText(Listar.this, "Excluido com Sucesso!", Toast.LENGTH_SHORT).show();
                                    carregarLista();
                                }
                            }
                        });
                AlertDialog alertDialog = alerta.create();
                alertDialog.show();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean resp = false;
        switch (item.getItemId()){
            case  R.id.home:

                Intent intenthome = new Intent(this.getApplication(),MainActivity.class);
                startActivity(intenthome);
                this.finish();
                resp = true;
                break;
            case  R.id.newContact:

                Intent intentCad = new Intent(this.getApplication(),Cadastrar.class);
                startActivity(intentCad);
                this.finish();
                resp = true;
                break;

            case R.id.listContact:
                Intent intentList = new Intent(this.getApplication(),Listar.class);
                startActivity(intentList);
                this.finish();
                resp = true;
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    private void carregarLista(){
        ContatoDbHelper db = new ContatoDbHelper(this);

        lstContatos = db.getLista();
        db.close();
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Contatos> adapter = new ArrayAdapter<Contatos>(this, layout,lstContatos);
        lista.setAdapter(adapter);

        //Toast.makeText(this, "Fim do carregamento", Toast.LENGTH_SHORT).show();

    }

}
