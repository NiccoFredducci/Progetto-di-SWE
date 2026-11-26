# Catalogo multimediale locale

Applicazione desktop Java per esplorare e organizzare un catalogo multimediale locale. Il progetto è concepito con un nucleo riutilizzabile per cataloghi generici e una specializzazione concreta per i film: nel nucleo gli elementi sono media collegati a file, le categorie sono sezioni e le persone associate sono autori/contributori; nell'applicazione specifica i media sono film e le sezioni sono generi.

Il lavoro privilegia modello di dominio, servizi, relazioni e persistenza. L'interfaccia serve a esplorare e verificare il sistema e non ne costituisce l'obiettivo principale.

La roadmap incrementale con lo stato delle fasi è in [`IMPLEMENTATION.md`](IMPLEMENTATION.md).

## Stato attuale

Il prototipo contiene una schermata iniziale di selezione profilo che legge i profili dal database locale e consente di selezionarne o crearne uno. Non apre ancora le schermate del catalogo.

L'ultima compilazione effettuata ha rilevato un conflitto di nome con un metodo Swing; il metodo è stato rinominato, ma la build deve ancora essere rilanciata per verificare la correzione. La schermata e il salvataggio dei profili non sono quindi ancora dichiarati verificati.

## Cosa installare

- **JDK 27, non solo JRE.** Il progetto usa Java 27 (`pom.xml`); serve quindi un JDK 27 per compilare ed eseguire. È possibile scaricare Eclipse Temurin dalla [pagina ufficiale delle release](https://adoptium.net/temurin/releases/). `javac` deve essere disponibile nel terminale. Java 27 è la versione stabile più recente disponibile al momento della scelta, ma non è una release LTS: riceve aggiornamenti fino a marzo 2027, quindi in seguito andrà rivalutato l'aggiornamento a una versione successiva.
- **Apache Maven 3.9.0 o successivo.** Il comando di avvio usa Exec Maven Plugin 3.7.0, che richiede Maven 3.9.0 e JDK 8 o successivo. Installazione e archivio sono disponibili nella [guida ufficiale Maven](https://maven.apache.org/install.html).
- **Connessione Internet al primo avvio della build.** Maven deve scaricare il driver SQLite e i plugin dichiarati nel progetto; dopo il download, questi componenti restano nella cache locale di Maven.

Non serve installare SQLite separatamente: il driver JDBC dichiarato nel `pom.xml` crea e usa il file database locale.

### Controllare Java e Maven

Apri un nuovo terminale PowerShell nella cartella del progetto ed esegui:

```powershell
java -version
javac -version
mvn -version
```

`javac` deve rispondere con la versione 27; se il comando non viene trovato, è installato solo un runtime o il percorso del JDK non è nel `PATH`. L'output di `mvn -version` mostra anche quale Java Maven sta usando: deve essere il JDK 27. Dopo aver installato o modificato il `PATH`, chiudi e riapri VS Code e i terminali.

## Compilare e avviare

Dalla cartella principale della repository, esegui:

```powershell
mvn clean compile exec:java
```

La prima esecuzione può richiedere qualche minuto per scaricare le dipendenze. La finestra dell'applicazione rimane aperta finché non viene chiusa.

## Uso attualmente previsto

1. All'avvio, la schermata mostra i profili già salvati.
2. Seleziona un profilo con un clic: la schermata ne mostra il nome come profilo selezionato.
3. Premi «+» per inserire il nome di un nuovo profilo e conferma la creazione. Il profilo viene salvato e compare nell'elenco.

Il nome non può essere vuoto. Non sono ancora previste modifica o cancellazione dei profili, password o schermate successive.

Il database SQLite viene creato automaticamente in `data/catalogo.db`, relativo alla cartella da cui si avvia il comando. I dati creati rimangono locali. Il database e i suoi file ausiliari sono esclusi da Git.

## Generare e visualizzare la relazione LaTeX

La relazione principale è [`docs/report.tex`](docs/report.tex), che include i capitoli nella sottocartella `docs/chapters/`.

Hai già installato MiKTeX, Perl e l'estensione LaTeX Workshop. Verifica anche che il pacchetto MiKTeX `latexmk` sia installato (in **MiKTeX Console → Packages**). La ricetta predefinita di LaTeX Workshop usa `latexmk`, che con MiKTeX richiede Perl disponibile nel `PATH`.

### Da Visual Studio Code

1. Apri la cartella principale della repository in VS Code.
2. Apri `docs/report.tex`.
3. Esegui **LaTeX Workshop: Build LaTeX project** dalla Command Palette, oppure premi `Ctrl+Alt+B`.
4. Apri il PDF generato con **LaTeX Workshop: View LaTeX PDF** dalla Command Palette, con `Ctrl+Alt+V` o dall'icona dell'estensione nell'editor.

### Da PowerShell

```powershell
Set-Location docs
latexmk -pdf report.tex
```

Il PDF risultante è `docs/report.pdf` (salvo diversa cartella di output configurata nell'estensione). Il documento è ancora una struttura iniziale con segnaposto: va aggiornato progressivamente con i contenuti e le verifiche effettive del progetto.

Per controllare che gli strumenti siano raggiungibili:

```powershell
latexmk -v
pdflatex --version
perl -v
```

Se VS Code segnala che `latexmk` o Perl non è disponibile, controlla che MiKTeX e Perl siano nel `PATH`, installa il pacchetto `latexmk` da MiKTeX Console e riavvia VS Code. MiKTeX può inoltre chiedere di scaricare pacchetti LaTeX mancanti durante la prima compilazione.
