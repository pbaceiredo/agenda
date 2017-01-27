package br.com.baceiredo.agenda.activity.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.baceiredo.agenda.gps.Localizador;
import br.com.baceiredo.agenda.model.dao.ContatoDAO;
import br.com.baceiredo.agenda.model.entity.Contato;

/**
 * Created by ur5l on 25/01/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String endereco = "Praça Quinze de Novembro, Centro, Rio de Janeiro";
        LatLng posicaoInicial = getCoordenada(endereco);
        if(posicaoInicial != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 11);
            googleMap.moveCamera(update);
        }

        ContatoDAO contatoDAO = new ContatoDAO(getContext());
        for(Contato contato : contatoDAO.list()){
            endereco = contato.getEndereco();
            if(!endereco.isEmpty()) {
                LatLng coodenada = getCoordenada(endereco);
                if(coodenada != null){
                    MarkerOptions marcador = new MarkerOptions();
                    marcador.position(coodenada);
                    marcador.title(contato.getNome());
                    marcador.snippet(endereco);
                    googleMap.addMarker(marcador);
                }
            }
        }
        contatoDAO.close();

        new Localizador(getContext(), googleMap);

    }

    private LatLng getCoordenada(String endereco) {
        LatLng coordenada = null;
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);
            if(!enderecos.isEmpty()){
               coordenada = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordenada;
    }
}
