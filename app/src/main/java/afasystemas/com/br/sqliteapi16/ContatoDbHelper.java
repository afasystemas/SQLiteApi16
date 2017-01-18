package afasystemas.com.br.sqliteapi16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AndreFA on 03/11/2016.
 */

public class ContatoDbHelper extends SQLiteOpenHelper {

    public static final String NOME_TABELA = "contato";
    public static final String COLUNA_ID_CONTATO = "id";
    public static final String COLUNA_NOME_CONTATO = "nome";
    public static final String COLUNA_TELEFONE_CONTATO = "telefone";

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";






    public static final String SQL_CREATE_CONTATO = "CREATE TABLE "+NOME_TABELA +" ("+
            COLUNA_ID_CONTATO + INTEGER_TYPE +" PRIMARY KEY,"+
            COLUNA_NOME_CONTATO+ TEXT_TYPE+" nome TEXT,"+
            COLUNA_TELEFONE_CONTATO+ TEXT_TYPE+");";

    private static final String SQL_DELETE_CONTATOS = "DROP TABLE IF EXISTS "+ NOME_TABELA;

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME= "Contatos.db";

    public ContatoDbHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("dentro do create");
        db.execSQL(SQL_CREATE_CONTATO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_CREATE_CONTATO);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void salvar(Contatos contato){
        System.out.print(" pessoa nome = "+contato.getNome()+"/ telefone = "+contato.getTelefone());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("telefone",contato.getTelefone());
        db.insert(NOME_TABELA,null,values);
        close();
    }



    public List<Contatos> getLista(){
        System.out.print(" antes d cursor ");

        String[] colunas = {"id","nome","telefone"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOME_TABELA,colunas,null,null,null,null,null);
        ArrayList<Contatos>lstContatos = new ArrayList<>();
        cursor.moveToPosition(1);

        System.out.print(" tamanho do cursor = "+cursor.getString(1));
        while(cursor.moveToNext()){

            Contatos pessoa= new Contatos();
            pessoa.setId(cursor.getInt(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setTelefone(cursor.getString(2));

            lstContatos.add(pessoa);
        }
        //System.out.print(" tamanho = "+lstContatos.size());
        return lstContatos;
    }

    public boolean deleteContato(int id_contato){
        boolean resp = false;
        Log.d(" deleteContato "," id = "+String.valueOf(id_contato));
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.delete(NOME_TABELA, COLUNA_ID_CONTATO+"= ?",new String[]{String.valueOf(id_contato)});
            db.close();
            resp = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;

    }

    public boolean updateContato(Contatos contato){
        boolean respo = false;
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME_CONTATO,contato.getNome());
        values.put(COLUNA_TELEFONE_CONTATO,contato.getTelefone());
        Log.d("contato id ",String.valueOf(contato.getId()));
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(NOME_TABELA,values,COLUNA_ID_CONTATO+" = ?" , new String[]{String.valueOf(contato.getId())});
            respo = true;
        }catch (Exception e){
            Log.d("erro:", e.toString());
        }

        Log.d(" respo ",String.valueOf(respo));
        return respo;

    }


}
