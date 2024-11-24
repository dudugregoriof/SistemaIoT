// Padrão Singleton: Classe Controladora para gerenciar dispositivos IoT
import java.util.ArrayList;
import java.util.List;

// Classe ControladorIoT implementando o Padrão Singleton
class ControladorIoT {
    private static ControladorIoT instancia;  // Instância única do controlador
    private List<DispositivoIoT> dispositivos;  // Lista de dispositivos IoT

    // Construtor privado para impedir a criação de múltiplas instâncias
    private ControladorIoT() {
        dispositivos = new ArrayList<>();
    }

    // Método para obter a instância única do controlador
    public static ControladorIoT getInstancia() {
        if (instancia == null) {
            instancia = new ControladorIoT();
        }
        return instancia;
    }

    // Método para adicionar dispositivos à lista
    public void adicionarDispositivo(DispositivoIoT dispositivo) {
        dispositivos.add(dispositivo);
    }

    // Método para executar comandos em todos os dispositivos
    public void executarComandos() {
        for (DispositivoIoT dispositivo : dispositivos) {
            dispositivo.executarComando();
        }
    }
}

// Padrão Command: Interface Command e comandos concretos
interface Comando {
    void executar();
}

// Comando para ligar um dispositivo IoT
class ComandoLigar implements Comando {
    private DispositivoIoT dispositivo;

    public ComandoLigar(DispositivoIoT dispositivo) {
        this.dispositivo = dispositivo;
    }

    @Override
    public void executar() {
        dispositivo.ligar();
    }
}

// Comando para desligar um dispositivo IoT
class ComandoDesligar implements Comando {
    private DispositivoIoT dispositivo;

    public ComandoDesligar(DispositivoIoT dispositivo) {
        this.dispositivo = dispositivo;
    }

    @Override
    public void executar() {
        dispositivo.desligar();
    }
}

// Classe base para dispositivos IoT
class DispositivoIoT {
    protected String nome;
    protected String estado = "desligado";

    public DispositivoIoT(String nome) {
        this.nome = nome;
    }

    public void ligar() {
        estado = "ligado";
        System.out.println(nome + " agora está LIGADO.");
    }

    public void desligar() {
        estado = "desligado";
        System.out.println(nome + " agora está DESLIGADO.");
    }

    public void executarComando() {
        System.out.println(nome + ": Nenhum comando atribuído.");
    }
}

// Padrão Decorator: Adicionando funcionalidade extra aos dispositivos IoT
class MonitoramentoEnergiaDecorator extends DispositivoIoT {
    private DispositivoIoT dispositivo;

    public MonitoramentoEnergiaDecorator(DispositivoIoT dispositivo) {
        super(dispositivo.nome);
        this.dispositivo = dispositivo;
    }

    @Override
    public void ligar() {
        dispositivo.ligar();
        System.out.println(nome + ": Monitoramento de energia ativado.");
    }

    @Override
    public void desligar() {
        dispositivo.desligar();
        System.out.println(nome + ": Monitoramento de energia desativado.");
    }
}

// Classe Principal para testar o sistema
public class SistemaIoT {
    public static void main(String[] args) {
        // Criar uma instância do controlador (Singleton)
        ControladorIoT controlador = ControladorIoT.getInstancia();

        // Criar dispositivos IoT
        DispositivoIoT lampada = new DispositivoIoT("Lâmpada Inteligente");
        DispositivoIoT ventilador = new DispositivoIoT("Ventilador Inteligente");

        // Adicionar monitoramento de energia à lâmpada (Decorator)
        DispositivoIoT lampadaMonitorada = new MonitoramentoEnergiaDecorator(lampada);

        // Atribuir comandos aos dispositivos
        Comando comandoLigarLampada = new ComandoLigar(lampadaMonitorada);
        Comando comandoDesligarVentilador = new ComandoDesligar(ventilador);

        // Executar comandos nos dispositivos
        lampadaMonitorada.ligar();
        lampadaMonitorada.desligar();

        ventilador.ligar();
        ventilador.desligar();
    }
}
