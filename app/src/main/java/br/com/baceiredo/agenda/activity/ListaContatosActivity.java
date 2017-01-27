package br.com.baceiredo.agenda.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.activity.asynctask.CalculaMediaNotasContatos;
import br.com.baceiredo.agenda.adapter.ContatosAdapter;
import br.com.baceiredo.agenda.model.dao.ContatoDAO;
import br.com.baceiredo.agenda.model.entity.Contato;

import static android.content.Intent.*;

public class ListaContatosActivity extends AppCompatActivity {

    private ListView listaContatosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        listaContatosView = (ListView) findViewById(R.id.lista_contatos);

        listaContatosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Contato contato = (Contato) listaContatosView.getItemAtPosition(position);
                Intent intentVaiFormulario = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                intentVaiFormulario.putExtra("contato", contato);
                startActivity(intentVaiFormulario);
            }
        });

        Button novoContato = (Button) findViewById(R.id.novo_contato);
        novoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiFormulario = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                startActivity(intentVaiFormulario);

            }
        });

        registerForContextMenu(listaContatosView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContatosAdapter adapter = new ContatosAdapter(this, R.layout.list_item, listarContatos());
        listaContatosView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        String urlPrefix = "http://";

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        /** para ser visualizado pela classe anônima precisa ser uma constante **/
        final Contato contato = (Contato) listaContatosView.getItemAtPosition(info.position);
        final String telefone = contato.getTelefone();
        final String celular = contato.getCelular();
        final String email = contato.getEmail();

        String site = contato.getSite();
        String endereco = contato.getEndereco();


        if (telefone != null && !telefone.isEmpty()) {
            MenuItem itemLigar = menu.add("Ligar Telefone");
            itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(ActivityCompat.checkSelfPermission(ListaContatosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ListaContatosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    }else {
                        Intent intentLigar = new Intent(Intent.ACTION_CALL);
                        intentLigar.setData(Uri.parse("tel:" + telefone));
                        startActivity(intentLigar);
                    }
                    return false;
                }
            });
        }

        if (celular != null && !celular.isEmpty()) {
            MenuItem itemLigar = menu.add("Ligar Celular");
            itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(ActivityCompat.checkSelfPermission(ListaContatosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ListaContatosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    }else {
                        Intent intentLigar = new Intent(Intent.ACTION_CALL);
                        intentLigar.setData(Uri.parse("tel:" + celular));
                        startActivity(intentLigar);
                    }
                    return false;
                }
            });

            MenuItem itemSMS = menu.add("Enviar SMS");
            Intent intentSMS = new Intent(Intent.ACTION_VIEW);
            intentSMS.setData(Uri.parse("sms:" + celular));
            itemSMS.setIntent(intentSMS);
        }

        if (email != null && !email.isEmpty()) {
            MenuItem itemEmail = menu.add("Enviar Email");
            itemEmail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intentEmail = new Intent(Intent.ACTION_SEND);
                    intentEmail.setType("text/plain");
                    intentEmail.putExtra(EXTRA_EMAIL, new String[]{email});
                    startActivity(intentEmail);
                    return false;
                }
            });
        }

        if (endereco != null && !endereco.isEmpty()) {
            MenuItem itemMapa = menu.add("Visualisar no mapa");
            Intent intentMapa = new Intent(Intent.ACTION_VIEW);
            intentMapa.setData(Uri.parse("geo:0.0?q=" + endereco));
            itemMapa.setIntent(intentMapa);
        }

        if (site != null && !site.isEmpty()) {
            if (!site.startsWith(urlPrefix)) {
                site = urlPrefix + site;
            }
            MenuItem itemSite = menu.add("Visitar Site");
            Intent intentSite = new Intent(Intent.ACTION_VIEW);
            intentSite.setData(Uri.parse(site));
            itemSite.setIntent(intentSite);
        }

        MenuItem itemExcluir = menu.add("Excluir");
        itemExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                excluirContato(contato);
                onResume();
                Toast.makeText(ListaContatosActivity.this, "Contato " + contato.getNome() + " Excluído!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_contatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                List<Contato> contatos = listarContatos();
                CalculaMediaNotasContatos calcula = new CalculaMediaNotasContatos(this);
                calcula.execute(contatos);
                break;

            case R.id.menu_baixar_provas:
                Intent vaiParaListaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaListaProvas);
                break;

            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Contato> listarContatos() {
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.list();
        dao.close();

        return contatos;
    }

    private void excluirContato(Contato contato) {
        ContatoDAO dao = new ContatoDAO(this);
        String caminhoFoto = contato.getCaminhoFoto();
        if(caminhoFoto != null && !caminhoFoto.isEmpty()){
            new File(caminhoFoto).delete();
        }
        dao.delete(contato);
        dao.close();
    }
}
