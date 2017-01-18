package afasystemas.com.br.sqliteapi16;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Cadastrar extends AppCompatActivity{

    private Button tel ;
    private EditText name ;
    private EditText telephone;
    ContatoDbHelper db ;
    Contatos pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        tel = (Button) findViewById(R.id.saveContact);
        name = (EditText)findViewById(R.id.name);
        telephone= (EditText)findViewById(R.id.telephone);

        pessoa = new Contatos();
        db = new ContatoDbHelper(Cadastrar.this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null && getIntent().getExtras().getBoolean("editar")){

            pessoa = (Contatos)getIntent().getSerializableExtra("contato");
            name.setText(pessoa.getNome());
            telephone.setText(pessoa.getTelefone());

        }
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validarDados(name,telephone)) {

                    if(pessoa.getId()==0){
                        pessoa = new Contatos();
                        pessoa.setNome(name.getText().toString());
                        pessoa.setTelefone(telephone.getText().toString());
                        db.salvar(pessoa);
                        Toast.makeText(Cadastrar.this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.d(" no click o id :",String.valueOf(pessoa.getTelefone()));
                        boolean resp = db.updateContato(pessoa);
                        if(resp){
                            Toast.makeText(Cadastrar.this, "Atualizado com Sucesso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Cadastrar.this, "Erro ao Atualizado", Toast.LENGTH_SHORT).show();

                        }
                    }

                    pessoa = new Contatos();
                    name.setText("");
                    telephone.setText("");
                }
                else{
                    Toast.makeText(Cadastrar.this, "O cadastro nao pode ser vazio!", Toast.LENGTH_SHORT).show();
                }
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
        return resp;
    }

    private boolean validarDados(EditText name, EditText celular){

        String nameStr = name.getText().toString().trim();
        String telephone = celular.getText().toString().trim();
        if(!nameStr.equals("") && !telephone.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }


}
