package it.mariosantoro.SemanticHarmonySocialNetwork;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
/**
 * docker build --no-cache -t test  .
 * docker run -i -e MASTERIP="127.0.0.1" -e ID=0 test
 * use -i for interactive mode
 * use -e to set the environment variables
 * @author carminespagnuolo
 *
 */
public class Example {

	@Option(name="-m", aliases="--masterip", usage="the master peer ip address", required=true)
	private static String master;

	@Option(name="-id", aliases="--identifierpeer", usage="the unique identifier for this peer", required=true)
	private static int id;

	public static void main(String[] args) throws Exception {

		class MessageListenerImpl implements MessageListener{
			int peerid;
		
			public MessageListenerImpl(int peerid)
			{
				this.peerid=peerid;

			}
			public Object parseMessage(Object obj) {
				
				TextIO textIO = TextIoFactory.getTextIO();
				TextTerminal terminal = textIO.getTextTerminal();
				terminal.printf("\n"+peerid+"] (Direct Message Received) "+obj+"\n\n");
				return "success";
			}

		}
		Example example = new Example();
		final CmdLineParser parser = new CmdLineParser(example);  
		try  
		{  
			parser.parseArgument(args);  
			TextIO textIO = TextIoFactory.getTextIO();
			TextTerminal terminal = textIO.getTextTerminal();
			SemanticHarmonySocialNetworkImpl peer = 
					new SemanticHarmonySocialNetworkImpl(id, master, new MessageListenerImpl(id));
			
			terminal.printf("\nStaring peer id: %d on master node: %s\n",
					id, master);
			String _nickname = "";
			
			terminal.printf("Ciao ti faremo una serie di domande per trovare amici con interessi comuni, rispondi con un numero: 1 (mi piace) oppure 0 (non mi piace)\n");
			 
			 List<String> questions= peer.getUserProfileQuestions();
			 List<Integer> answer= new ArrayList<Integer>();
			 List<String> friends;
			 for(int i=0; i<questions.size();i++) {
				terminal.printf( questions.get(i));
				answer.add( textIO.newIntInputReader()
						.withMaxVal(1)
						.withMinVal(0)
						.read("Scelta"));
			 }
			 
			terminal.printf("\nInserisci un nickname\n");
			 _nickname = textIO.newStringInputReader().read("Nickname:");
			String profile_key= peer.createAuserProfileKey(answer);			 
			while(!peer.join(profile_key, _nickname)) {
				terminal.printf("Nickname esistente. Per favore riprova.\n");
				 _nickname = textIO.newStringInputReader()
					        .read("Nickname:");
			}
			terminal.printf("\nAcesso eseguito! Benvenuto!!\n");	
			while(true) {
				printMenu(terminal);
				
				int option = textIO.newIntInputReader()
						.withMaxVal(3)
						.withMinVal(1)
						.read("Option");
				switch (option) {
				case 1:
					terminal.printf("\nEcco i tuoi amici:\n");
				    friends= peer.getFriends();
					if(friends!=null && friends.size()>0) {
						for(int i=0;i<friends.size();i++) {
							terminal.printf("%s\n", friends.get(i));							
						}						
					}else {
						terminal.printf("Non ci sono amici con interessi comuni nella rete per ora\n");	
					}
					break;
				case 2:
					terminal.printf("\nA quale amico vuoi mandare il messaggio:\n");
					friends= peer.getFriends();
					if(friends!=null && friends.size()>0) {
						for(int i=0;i<friends.size();i++) {
							terminal.printf("%s\n", friends.get(i));							
						}
				 
						String choice= textIO.newStringInputReader()
						        .read("Nickname:");
						terminal.printf("\nScrivi messaggio\n");
						String message = textIO.newStringInputReader()
						        .withDefaultValue("default-message")
						        .read(" Message:");
						if(peer.sendMessage(choice, message)) {
							terminal.printf("Messaggio inviato!\n");
						}else {
							terminal.printf("Nickname inesitente o non tra gli amici\n");
						}
					}else {
						terminal.printf("Non ci sono amici con interessi comuni nella rete per ora\n");	
					}
					 
					break;
				case 3:
					terminal.printf("\nSei sicuro di uscire dalla rete?\n");
					boolean exit = textIO.newBooleanInputReader().withDefaultValue(false).read("exit?");
					if(exit) {
						peer.leaveNetwork(_nickname);
						System.exit(0);
					}
					break;

				default:
					break;
				}
			}


		

		}  
		catch (CmdLineException clEx)  
		{  
			System.err.println("ERROR: Unable to parse command-line options: " + clEx);  
		}  


	}
	public static void printMenu(TextTerminal terminal) {
	
		terminal.printf("\n1 - LISTA DI AMICI\n"); 
		terminal.printf("\n2 - INVIO MESSAGGIO\n"); 
		terminal.printf("\n3 - EXIT\n");

	}



}