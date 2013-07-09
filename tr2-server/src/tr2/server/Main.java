package tr2.server;

public class Main {

	public static void main(String[] args) {

		// Inicialização
		Multicast multiChannel = new Multicast(Multicast.serverServerDiscoveryIP, Multicast.serverServerDiscoveryPort);
		Thread multiListener = new Thread(multiChannel);
		multiListener.start();
		
		PeriodicMulticastMessage periodic = new PeriodicMulticastMessage(multiChannel, Messages.Hello, 30000);
		Thread periodicHello = new Thread(periodic);
		periodicHello.start();
		
		// Entrada do servidor na rede - Entra no canal multicast
//		Multicast serverDiscoveryChannel = new Multicast(Multicast.serverServerDiscoveryIP, Multicast.serverServerDiscoveryPort);
//		Thread multicastSDC = new Thread(serverDiscoveryChannel);
//		
//		// Inicia a thread do serverDiscoveryChannel (listener)
//		multicastSDC.start();
//		
//		// Manda um hello
//		serverDiscoveryChannel.speak(Messages.Hello);
		
		
	}

}
