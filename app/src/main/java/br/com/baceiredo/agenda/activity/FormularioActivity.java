package br.com.baceiredo.agenda.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.baceiredo.agenda.activity.helper.FormularioHelper;
import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.model.dao.ContatoDAO;
import br.com.baceiredo.agenda.model.entity.Contato;

public class FormularioActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private FormularioHelper formularioHelper;
    private String caminhoFoto;
    private String caminhoFotoAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new FormularioHelper(this);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if (contato != null) {
            caminhoFotoAnterior = contato.getCaminhoFoto();
            formularioHelper.preencheFormulario(contato);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            formularioHelper.carregaFoto(caminhoFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                Contato contato = formularioHelper.getContato();
                if (contato.getId() == null) {
                    if (insertAluno(contato)) {
                        Toast.makeText(this, "Contato " + contato.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Falha ao tentar salvar o Contato " + contato.getNome() + "!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (updateAluno(contato)) {
                        Toast.makeText(this, "Contato " + contato.getNome() + " atualizado!", Toast.LENGTH_SHORT).show();
                        if (caminhoFotoAnterior != null && !caminhoFotoAnterior.equals(contato.getCaminhoFoto())) {
                            File fotoAnterior = new File(caminhoFotoAnterior);
                            fotoAnterior.delete();
                        }
                    } else {
                        Toast.makeText(this, "Falha ao tentar atualizar o Contato " + contato.getNome() + "!", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean updateAluno(Contato contato) {
        ContatoDAO dao = new ContatoDAO(this);
        boolean alterou = dao.update(contato);
        dao.close();

        return alterou;
    }

    private boolean insertAluno(Contato contato) {
        ContatoDAO dao = new ContatoDAO(this);
        boolean criou = dao.insert(contato);
        dao.close();

        return criou;
    }
}
