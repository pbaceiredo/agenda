package br.com.baceiredo.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.model.entity.Contato;

/**
 * Created by ur5l on 23/09/2016.
 */

public class ContatosAdapter extends BaseAdapter{


    private final Context context;
    private final int layout;
    private final List<Contato> contatos;

    public ContatosAdapter(Context context, int layout, List<Contato> contatos) {
        this.context = context;
        this.layout = layout;
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Contato contato = contatos.get(position);
        String nome = contato.getNome();
        String celular = contato.getCelular();
        String telefone = contato.getTelefone();
        String caminhoFoto = contato.getCaminhoFoto();
        String endereco = contato.getEndereco();
        String site = contato.getSite();

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout, parent, false);
        }

        if(nome != null && !nome.isEmpty()) {
            TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
            campoNome.setText(nome);
        }

        if((celular != null && !celular.isEmpty()) || (telefone != null  && !telefone.isEmpty())) {
            TextView campoCelular = (TextView) view.findViewById(R.id.item_celular);
            if(celular != null && !celular.isEmpty()) {
                campoCelular.setText(celular);
            } else{
                campoCelular.setText(telefone);
            }
        }

        /*if(caminhoFoto != null && !caminhoFoto.isEmpty()){
            ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 160, 90, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }*/

        if(endereco != null && !endereco.isEmpty()) {
            TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);
            if(campoEndereco != null) {
                campoEndereco.setText(endereco);
            }
        }

        if(site != null && !site.isEmpty()) {
            TextView campoSite = (TextView) view.findViewById(R.id.item_site);
            if(campoSite != null) {
                campoSite.setText(site);
            }
        }

        return view;
    }
}
