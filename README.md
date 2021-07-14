# SemanticsHarmonySocialNetwork
Progetto per l'esame Architetture Distribuite per il Cloud della magistrale in Informatica curriculum Cloud Computing dell'Università degli Studi di Salerno.<br>
Progetto è stato assegnato in maniera casuale prendendo il primo carattere generato da: <br> 
  - md5(mariosantoro-15)= <b>3c9c93a77cd8599600eb42c27591ca89</b>
## Prerequisiti
<ul>
  <li>Java 7 o superiore</li>
  <li><a href="https://maven.apache.org/">Apache Maven</a></li>
 </ul>

## Descrizione
Progettare e sviluppare un social network basato sugli interessi dell'utente che sfrutti una rete P2P. Il sistema raccoglie i profili degli utenti e crea automaticamente le amicizie secondo una strategia di matching. Gli utenti possono vedere i loro amici nel tempo e sono automaticamente informati quando un utente entra nel social network e diventa un nuovo potenziale amico. Il sistema definisce un insieme di domande, ad esempio, se all'utente piace o non piace un insieme di foto, un insieme di un hashtag, o più precisamente come <a href="https://it.wikipedia.org/wiki/Big_Five_(psicologia)">Big Five Personality Test</a>. A questo punto, il sistema può calcolare il punteggio dell'utente in base alle risposte. Questo punteggio è elaborato da una strategia di matching che scopre automaticamente gli amici. Si consideri, ad esempio, un vettore di risposte binarie; un processo di matching dovrebbe essere la differenza in 0 e 1, o la <a href="https://it.wikipedia.org/wiki/Distanza_di_Hamming">distanza di Hamming</a>, e così via. Il sistema permette agli utenti di vedere le domande del social network, di creare un punteggio di profilo in base alla risposta, di unirsi alla rete utilizzando un nickname, ed eventualmente di vedere tutti gli amici degli utenti. Come descritto nella API Java di <a href="https://github.com/spagnuolocarmine/distributedsystems_class_2020/blob/master/homework/SemanticHarmonySocialNetwork.java"> SemanticHarmonySocialNetwork</a>.

## Tecnologie
<img align="center" height="500" src="https://github.com/mario-santoro/SemanticsHarmonySocialNetwork/blob/main/tecnologie.png?raw=true" >

 <p>Documentazione TomP2P disponibile <a href="https://tomp2p.net/doc/">qui</a>. 
  <br> Per il testing è stato usato il framework JUnit, documentazione disponibile <a href="https://junit.org/junit4/javadoc/latest/index.html">qui</a>.</p>

 ## Project Structure
 
<p>Usando Maven puoi aggiungere le dipendenze a TomP2P nel file pom.xml.</p>

 ```
<repositories>
		<repository>
			<id>tomp2p.net</id>
			<url>http://tomp2p.net/dev/mvn/</url>
		</repository>
	</repositories>
	<dependencies>
		<dependency>
			<groupId>net.tomp2p</groupId>
			<artifactId>tomp2p-all</artifactId>
			<version>5.0-Beta8</version>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-engine</artifactId>
			<version>5.5.2</version>
		</dependency>
		<dependency>
			<groupId>args4j</groupId>
			<artifactId>args4j</artifactId>
			<version>2.33</version>
		</dependency>
	</dependencies>
```
<p> Il package <b>src/main/java/it.mariosantro.SemanticHarmonySocialNetwork/</b> fornisce 5 classi:</p>
 <ul>
	<li><b>MessageListener</b>: un'interfaccia per il listener di messaggi ricevuti da un peer.</li>
	<li><b>SemanticHarmonySocialNetwork</b>: un'interfaccia che definisce il social network.</li>
	<li><b>SemanticHarmonySocialNetworkImpl</b>: un'implementazione dell'interfaccia SemanticHarmonySocialNetwork che sfrutta la libreria TomP2P.</li>
	<li><b>User</b>: l'oggetto con le informazioni degli utenti del social network.</li>
	<li><b>Example</b>: la classe che usa l'implemetazione SemanticHarmonySocialNetworkImpl</li>
</ul>
 
 
 ## Soluzione
 <ul>
	<li><b>getUserProfileQuestions</b>: un utente appena accede deve rispondere ad una serie di domande restituite da questo metodo.</li>
	<li><b>createAuserProfileKey</b>: appena le domande vengono risposte dall'utente, viene generata una chiave del profilo in base alle risposte.</li>
	<li><b>join</b>: a questo punto l'utente può unirsi alla rete, con questo metodo inseriamo il nuovo utente nel social tramite il nickname (in caso è già presente dovrà sceglierne un altro) e il profileKey. Quest'ultima sarà usata per trovare gli amici (altri utenti con interessi comuni) calcolando la distanza di hamming. Le persone con cui è avvenuto il match riceveranno una notifica di una nuoca amicizia.</li>
	<li><b>getFriends</b>: dopo aver fatto la join alla rete l'utente può vedere la lista dei suoi amici tramite questo metodo.</li>
	<li><b>sendMessage</b>:  dopo aver fatto la join alla rete l'utente può mandare messaggi (solo) ai sui amici inserendo il loro nickname.</li>
	<li><b>leaveNetwork</b>: un utente può abbandonare la rete, così facendo verrà segnalato ai suoi amici che ha lasciato la rete e il suo nickname scomparirà dalla loro lista.</li> 
</ul>
 
 ## Build your app in a Docker container
 <p>Viene fornita un'applicazione di esempio utilizzando il contenitore Docker, in esecuzione su un computer locale. Vedi il Dockerfile, per i dettagli della costruzione.</p>
 <p>Prima di tutto puoi costruire il tuo container docker:</p>
 
 ```
 docker build --no-cache -t shsn .
 ```
### Avvio del master peer 
 
<p>Successivamente è possibile avviare il master peer, in modalità interattiva (-i) e con due (-e) variabili d'ambiente:</p>

 ```
docker run -i --name MASTER-PEER -e MASTERIP="127.0.0.1" -e ID=0 shsn
 ```
 
<p>la variabile d'ambiente MASTERIP è l'indirizzo IP del peer master e la variabile d'ambiente ID è l'id univoco del tuo peer. Ricorda che devi eseguire il peer master utilizzando l'ID = 0. </p> 

<b>Nota:</b> dopo il primo avvio, è possibile lanciare il masteer peer usando il comando seguente:   
```
docker start -i MASTER-PEER
```  

### Avvio di un generico peer
All'avvio del master devi controllare l'indirizzo IP del tuo container:
<ul>
	<li>Check del docker: docker ps</li>
	<li>Check del indirizzo IP: docker inspect <container ID></li>
</ul>
Ora puoi avviare i tuoi peer variando il peer ID univoco:
```
docker run -i --name PEER-1 -e MASTERIP="172.17.0.2" -e ID=1 shsn
```  

<b>Nota:</b> dopo il primo avvio, è possibile lanciare il peer usando il comando seguente:   
```
docker start -i PEER-1
```  
 ## Testing
 Sono stati creati casi di test per ogni funzionalità tramite <b>@Test</b> per eseguirlo con JUnit: 
 <ul>
	<li><b>TestCaseGetUserProfileQuestions</b>: utenti ricevono la lista di domande, che ci aspettiamo lunga 7, non vuota e tutte uguali per ogni utente.</li>
	<li><b>TestCaseCreateAuserProfileKey</b>: per ogni utente viene generata la ProfileKey che ci aspettiamo che sia per tutti lunga 7 caratteri, e non vuota. Inoltre ci aspettiamo che se un utente ha risposto a tutte le domande con 1, la ProfileKey sia '1111111'.</li>
	<li><b>TestCaseJoin</b>: Gli utenti con nickname diversi provano ad unirsi alla rete, ci attendiamo che la funzioni ritorni true per avvenuta unione.</li>
	<li><b>TestCaseJoin_ExistingNickName</b>: Un utente con nickname uguale ad un utente già in rete prova ad unirsi, ci attendiamo che la funzioni ritorni false poiché non possono esserci due utenti con lo stesso nickname.</li>
	<li><b>TestCaseGetFriends</b>: ci aspettiamo che ogni utente veda solo ed esclusivamente i suoi amici.</li>
	<li><b>TestCaseSendMessage_toFriend</b>: due utenti che sono amici inviano un messaggio l'uno all'altro, ci aspettiamo che il metodo restituisca true per avvenuto invio.</li>
	<li><b>TestCaseSendMessage_NoFriend</b>:  un utente tenta di inviare un messaggio ad un altro utente nella rete che non è un amico, ci aspettiamo che il metodo restituisca false poiché è possibile inviare messaggi soltanto agli amici.</li>
	<li><b>TestCaseLeaveNetwork</b>: ci sono due uenti che sono tra loro amici, uno di questi abbandona la rete, ci aspettiamo che non sia più visibile nella lista di amici dell'utente restante.</li> 
</ul>
 
Con <b>@AfterEach</b> viene chiamata, alla fine di ogni test fatto, la funzione <b>finish</b> per far lasciare la rete a tutti i peer.
  
