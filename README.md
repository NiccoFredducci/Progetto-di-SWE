# Catalogo locale

Applicazione desktop Java per un catalogo multimediale locale. Il progetto è in sviluppo; le funzionalità approvate e il loro stato sono elencati in [`FEATURES.md`](FEATURES.md).

## Stato attuale

È preparata una schermata di selezione dei profili: legge gli utenti dal database locale, permette di selezionare un profilo e di crearne uno con il pulsante «+». La selezione mostra il profilo scelto nella schermata corrente; non apre ancora altre schermate.

La schermata e l'accesso al database sono stati scritti, ma non ancora compilati o provati nell'ambiente di sviluppo. La feature resta quindi in `Features da implementare` finché non sarà verificata.

## Cosa installare

- **JDK, non solo JRE.** Il progetto compila codice compatibile con Java 8 (`pom.xml`). Serve quindi un JDK 8 o successivo; per esempio è possibile scaricare Eclipse Temurin dalla [pagina ufficiale delle release](https://adoptium.net/temurin/releases/). `javac` deve essere disponibile nel terminale.
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

`javac` deve rispondere con una versione Java; se il comando non viene trovato, è installato solo un runtime o il percorso del JDK non è nel `PATH`. L'output di `mvn -version` mostra anche quale Java Maven sta usando. Dopo aver installato o modificato il `PATH`, chiudi e riapri VS Code e i terminali.

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

## Manutenzione delle istruzioni

Il README viene aggiornato insieme alle modifiche che cambiano installazione, avvio, configurazione o uso delle funzionalità. Le regole locali per gli agenti sono in `AGENTS.md` (file escluso dal repository).
