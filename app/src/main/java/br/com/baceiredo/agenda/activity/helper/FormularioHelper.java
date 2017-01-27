package br.com.baceiredo.agenda.activity.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.activity.FormularioActivity;
import br.com.baceiredo.agenda.model.entity.Contato;

/**
 * Created by ur5l on 28/07/2016.
 */
public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoCelular;
    private EditText campoEmail;
    private EditText campoSite;
    private EditText campoNotas;
    private RatingBar campoNota;
    private ImageView campoFoto;
    private Contato contato;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoCelular = (EditText) activity.findViewById(R.id.formulario_celular);
        campoEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNotas = (EditText) activity.findViewById(R.id.formulario_notas);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        contato = new Contato();
    }

    public Contato getContato() {
        contato.setNome(campoNome.getText().toString());
        contato.setEndereco(campoEndereco.getText().toString());
        contato.setTelefone(campoTelefone.getText().toString());
        contato.setCelular(campoCelular.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
        contato.setSite(campoSite.getText().toString());
        contato.setNotas(campoNotas.getText().toString());
        contato.setNota(Double.valueOf(campoNota.getRating()));
        contato.setCaminhoFoto((String) campoFoto.getTag());

        return contato;
    }

    public void preencheFormulario(Contato contato) {
        campoNome.setText(contato.getNome());
        campoEndereco.setText(contato.getEndereco());
        campoTelefone.setText(contato.getTelefone());
        campoCelular.setText(contato.getCelular());
        campoEmail.setText(contato.getEmail());
        campoSite.setText(contato.getSite());
        campoNotas.setText(contato.getNotas());
        campoNota.setRating(contato.getNota().floatValue());
        carregaFoto(contato.getCaminhoFoto());
        this.contato = contato;
    }

    public void carregaFoto(String caminhoFoto) {
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            /* ultimo paremetro true informa que deve aplicar filtro */
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
