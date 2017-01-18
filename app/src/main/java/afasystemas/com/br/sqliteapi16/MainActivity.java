package afasystemas.com.br.sqliteapi16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
