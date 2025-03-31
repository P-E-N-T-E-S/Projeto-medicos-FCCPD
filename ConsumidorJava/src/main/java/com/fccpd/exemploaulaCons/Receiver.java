package Projeto_medicos.ConsumidorJava.src.main.java.com.fccpd.exemploaulaCons;


import org.springframework.stereotype.Component;

@Component
public class Receiver {

    public void receiveMessage(byte[] message) {
        String mensagem = new String(message);
        System.out.println("Received <" + mensagem + ">");
    }


}
