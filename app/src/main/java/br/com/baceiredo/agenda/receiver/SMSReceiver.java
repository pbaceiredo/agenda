package br.com.baceiredo.agenda.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.model.dao.ContatoDAO;

/**
 * Created by ur5l on 26/09/2016.
 */

public class SMSReceiver  extends BroadcastReceiver{
    @Override
    @TargetApi(23)
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage smsMessage = SmsMessage.createFromPdu(pdu, formato);

        String celular = smsMessage.getDisplayOriginatingAddress();
        ContatoDAO dao = new ContatoDAO(context);
        String nome = dao.retornaNomeContatoDeCelular(celular);
        dao.close();

        if(nome != null) {
            Toast.makeText(context, "Recebido SMS de " + nome, Toast.LENGTH_SHORT).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
            mediaPlayer.start();
        }
    }
}
