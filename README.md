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
<p> Il package src/main/java/it.mariosantro.SemanticHarmonySocialNetwork/ fornisce 5 classi:</p>
 <ul>
	<li>MessageListener un'interfaccia per il listener di messaggi ricevuti da un peer.</li>
	<li>SemanticHarmonySocialNetwork un'interfaccia che definisce il social network.</li>
	<li>SemanticHarmonySocialNetworkImpl un'implementazione dell'interfaccia SemanticHarmonySocialNetwork che sfrutta la libreria TomP2P.</li>
	<li>User l'oggetto con le informazioni degli utenti del social network.</li>
	<li>Example la classe che usa l'implemetazione SemanticHarmonySocialNetworkImpl</li>
</ul>
 
 
 ## Soluzione
 
 ## Build your app in a Docker container
 <p>Viene fornita un'applicazione di esempio utilizzando il contenitore Docker, in esecuzione su un computer locale. Vedi il Dockerfile, per i dettagli della costruzione.</p>
 <p>Prima di tutto puoi costruire il tuo container docker:</p>
 
 ```
 docker build --no-cache -t shsn .
 ```
 <p><b>Start del master peer</b></p>
 
<p>Successivamente è possibile avviare il master peer, in modalità interattiva (-i) e con due (-e) variabili d'ambiente:</p>
 ```
docker run -i --name MASTER-PEER -e MASTERIP="127.0.0.1" -e ID=0 shsn
 ```
<p>la variabile d'ambiente MASTERIP è l'indirizzo IP del peer master e la variabile d'ambiente ID è l'id univoco del tuo peer. Ricorda che devi eseguire il peer master utilizzando l'ID = 0. </p> 

<b>Nota:</b> dopo il primo avvio, è possibile lanciare il masteer peer usando il comando seguente:   
```
docker start -i MASTER-PEER
```  
 ## Testing

 
  
