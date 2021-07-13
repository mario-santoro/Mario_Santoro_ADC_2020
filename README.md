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
<p> Il package src/main/java/it.mariosantro.SemanricHarmonySocialNetwork/ fornisce 5 classi:</p>
 
 ## Soluzione
 
 ## Build your app in a Docker container
 
 ## Testing

 
  
