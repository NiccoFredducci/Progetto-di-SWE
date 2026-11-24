# Requisiti e vincoli del progetto

## Stato e criteri dell'analisi

L'utente ha confermato che il progetto seguirà la **Modalità 2** del disciplinare `SWE25_disciplinareDiEsame_Vers1.2.pdf`. La scelta del dominio applicativo resta aperta. La traccia/esercitazione DDD della biblioteca si definisce esplicitamente uno sketch incompleto (PDF, p. 1) e non è stata scelta come progetto; i suoi contenuti restano una possibile fonte d'ispirazione, non funzionalità approvate. Le dispense, gli esempi e gli esercizi sono trattati come materiale didattico, non come requisiti del programma.

In questo documento:

- **Esplicito** indica un'indicazione formulata direttamente in una fonte.
- **Interpretazione** indica una lettura possibile, che non diventa un vincolo senza conferma.
- **Da confermare/mancante** indica informazione non definita o decisione ancora aperta.

## Descrizione e obiettivi

### Candidata traccia: biblioteca

Il documento `requirements/5. Object Oriented Analysis/BibliotecaDdd_4Nov2026-refined.pdf` presenta un'esercitazione di Domain Driven Design e model based development. La descrive come improvvisata, già trattata in precedenza e deliberatamente incompleta; il testo e i diagrammi sono stati poi ripuliti. Non specifica che questa sia la traccia scelta per il progetto dell'utente.

Il dominio abbozzato riguarda una biblioteca con volumi e collocazioni, copie di opere articolate in edizioni, commenti scritti da utenti, anagrafica utenti, catalogo, prestiti, solleciti e prenotazioni (PDF, p. 1). Il diagramma UML che accompagna la traccia mostra anche una possibile modellazione di questi elementi e delle interazioni, ma non ne completa le regole.

## Requisiti funzionali della candidata traccia biblioteca

Questi punti descrivono la traccia e il relativo modello UML, non una decisione funzionale già presa dall'utente.

### Indicazioni esplicite nel testo

Il testo abbozza le seguenti capacità (PDF, p. 1):

1. Gestire volumi dotati di collocazione; i volumi sono copie di opere, organizzate in edizioni.
2. Raccogliere commenti scritti da utenti su opere, edizioni e altri commenti.
3. Gestire l'anagrafica degli utenti.
4. Gestire un catalogo dei volumi e consentirne la consultazione.
5. Gestire prestiti di volumi, solleciti e prenotazioni riferite a un'opera o a un'edizione.
6. Prevedere registrazione, accesso e gestione/reimpostazione della password; il testo elenca “register, login, manage password”.

### Varianti e casi emersi nel testo

La stessa fonte segnala esplicitamente che durante l'analisi sono emersi casi “stated or implied” e possibili varianti (PDF, p. 1):

- consultare opere disponibili e ricevere una notifica quando un'opera diventa disponibile;
- modellare il prestito come un unico caso d'uso in cui l'utente lo compila e il bibliotecario conferma la consegna, oppure dividerlo in due casi d'uso;
- notificare all'utente l'avvicinarsi della scadenza e inviare solleciti;
- consentire una richiesta di estensione del prestito, collegata alle notifiche tramite relazione `<<extend>>` nel modello dei casi d'uso.

La fonte non decide fra le alternative né definisce politiche, tempi o condizioni.

### Contenuti del modello UML associato

Il file `requirements/5. Object Oriented Analysis/BibliotecaDdd_4Novembre26.mdj` contiene un modello StarUML con attori Utente, UtenteGenerico, Bibliotecario, Archivista e Sistema e i casi d'uso seguenti (nomi riportati dal diagramma; non sono specifiche testuali complete):

- pubblicare commento;
- compilare e registrare prestito, prenotazione e restituzione;
- ricevere notifiche di prossima scadenza, sollecito ed estensione;
- richiedere e approvare un'estensione;
- consultare il catalogo;
- registrarsi, accedere e recuperare la password;
- inviare notifiche, inclusa quella di disponibilità su prenotazione;
- trovare e gestire (CRUD) collocazioni;
- analizzare solleciti ed effettuare acquisizioni.

Il diagramma dei casi d'uso è un artefatto di esercitazione: non specifica in modo sufficiente precondizioni, flussi normali e alternativi, regole di business o criteri di accettazione. I nomi non vanno intesi automaticamente come funzionalità approvate.

## Requisiti non funzionali

Non risultano requisiti non funzionali specifici della candidata applicazione biblioteca (per esempio sicurezza, prestazioni, accessibilità, disponibilità o usabilità). Il materiale didattico definisce in generale la distinzione tra requisiti funzionali e requisiti di qualità, ma non assegna valori o soglie al progetto (`requirements/6. Sw Development Lifecycle/SoftwareQuality_RobertoVerdecchia_Nov24/SE_lecture_software_quality.pdf`, pp. 6-7).

### Vincoli presenti in un esempio distinto di rete bibliotecaria

`requirements/5. Object Oriented Analysis/An example of problem statement - Rete Biblioteche.pdf` è un esempio didattico di una rete cittadina di biblioteche e non è identificato come la traccia DDD del progetto. Nel suo statement (p. 1) compaiono vincoli e obiettivi diversi: partenza con 8 sedi e circa 16.000 accessi al giorno, scalabilità a 32 nodi/64.000 accessi giornalieri, risposta sotto 10 secondi per consultazioni web e sotto 2 secondi dalle sedi, accessibilità AA, autenticazione sicura per ruoli e log non revocabile. Lo stesso esempio ipotizza un sistema web-based centralizzato.

Questi sono requisiti espliciti **di quell'esempio**, non requisiti della candidata biblioteca DDD. Non sono trasferiti al progetto. Il PDF contiene 12 pagine, ma l'estrazione testuale ripete lo stesso statement; non emerge una specifica distinta nelle pagine successive dall'estrazione disponibile.

## Indicazioni e vincoli del corso

La fonte più recente e specifica è `requirements/SWE25_disciplinareDiEsame_Vers1.2.pdf`, intitolata Software Engineering A.A. 2025/26, versione 1.2 (dicembre 2025). La fonte precedente `requirements/NotesOnTheProjectWork_Jan2025.pdf` è versione 1.1, dichiarata incompleta, per A.A. 2024/25. Se i due documenti differiscono, in questa sintesi si evidenzia il testo più recente come riferimento corrente, senza cancellare le differenze.

### Modalità di progetto

Il disciplinare presenta tre modalità (disciplinare v1.2, pp. 5-8):

- **Modalità 1:** esercizio più circoscritto di progettazione OO, sviluppo e test, di norma centrato sulla combinazione di 2-3 design pattern in uno scenario; semplifica l'obiettivo.
- **Modalità 2:** progettazione e produzione di un frammento applicativo riferito a un dominio, per applicare le pratiche del corso; il documento la indica come modalità comune.
- **Modalità 3:** progetto più ambizioso, definito caso per caso per candidati con solide capacità di sviluppo.

L'utente ha scelto la **Modalità 2** per questo progetto. Le prescrizioni e le raccomandazioni della sezione seguente sono quindi il riferimento del corso applicabile, fermo restando che la specifica applicativa e il dominio devono ancora essere definiti.

### Contenuti del report per modalità 2

Per la modalità 2 il disciplinare dice che la relazione **normalmente** include (v1.2, pp. 18-25):

- frontespizio con corso, anno accademico, autori e docente e un indice esplicativo (p. 19);
- statement sintetico del contesto applicativo e del suo obiettivo (p. 20);
- architettura d'insieme, diagramma dei package/dipendenze e descrizione delle responsabilità e dei componenti per package, oltre a tecnologie e strumenti usati (p. 21);
- analisi dei requisiti e disegno funzionale;
- Use Case Diagram e template dei casi d'uso;
- progettazione con diagrammi delle classi per package, spiegazione delle responsabilità e dettagli significativi;
- documentazione del modello di database;
- dettagli implementativi, snippet motivati e discussione degli idiomi/pattern/tecnologie significativi;
- test funzionali e strutturali.

Il disciplinare distingue il grado di obbligatorietà in modo esplicito solo per alcuni artefatti: **Use Case Diagram e template sono obbligatori**; **mockup e diagramma di navigazione sono raccomandati** (p. 22). I template devono caratterizzare ciascun caso d'uso con numero, titolo, descrizione, livello, attori, pre/post-condizioni e flussi normale e alternativi; i flussi alternativi vanno numerati in relazione ai passi da cui si diramano e i casi d'uso devono restare coerenti tra diagrammi e template (p. 31). È suggerito l'uso di `<<include>>` e `<<extend>>` e un uso limitato di `<<precedes>>`/`<<invokes>>` (p. 22).

La progettazione include il modello del database (p. 23); le slide chiedono di documentarlo con diagramma Entity-Relationship e modello relazionale (p. 39). La parte di testing distingue: test funzionali black box/integrazione sui metodi dei Controller della business logic, collegati ai flussi dei template (p. 35); unit test white box su elementi critici o rilevanti (p. 37); test di DAO e, se non già coperti, delle query (p. 38). I test sono da motivare e ricondurre ai casi d'uso/template (p. 36).

L'architettura/package diagram e la documentazione di progetto sono chiaramente attesi nella relazione. Le slide dicono che la relazione “normalmente” comprende le sezioni elencate: al di fuori degli artefatti marcati obbligatori, non presentano una checklist formale con la stessa etichetta di obbligatorietà per ogni singola sottosezione.

### Interfaccia utente: cosa è richiesto

Il disciplinare **non impone di realizzare una GUI o un'interfaccia grafica**. Questa è un'interpretazione diretta delle indicazioni: mockup e page-navigation diagram sono espressamente raccomandati, non obbligatori (p. 22); i test possono fare da surrogato dell'interfaccia e della persistenza (p. 25); i test funzionali possono chiamare i metodi dei Controller, che “surrogano” l'interfaccia utente (p. 35). La slide sui mockup dice che, se si sviluppa un'interfaccia, i mockup sono sostituiti da screenshot (p. 42). Non c'è neppure un divieto di implementarla.

Quindi una UI reale è **opzionale secondo questo disciplinare**. Un programma Java con business logic accessibile tramite Controller e test funzionali può essere coerente con le indicazioni; una CLI è un'altra possibilità. Se si sceglie di costruire schermate, si documentano con screenshot e, se utile, mockup e navigazione. La possibile richiesta di un docente o di un incarico specifico non incluso nei file resta da verificare separatamente.

### Esempio illustrato nelle slide: serra

Le slide includono un esempio di statement per la gestione di una serra (p. 28): spazi suddivisi in settori e posizioni per le piante; aerazione, illuminazione, temperatura, luce e umidità monitorate; irrigazione e gestione in parte automatizzate; amministratore con monitoraggio in tempo reale; operatori per cura e posizionamento manuali; clienti che ordinano piante con consegna prevista. È un esempio didattico, non una traccia imposta.

La slide seguente mostra un'architettura con **CLI**, Business Logic, Domain Model, ORM, JDBC e RDBMS/PostgreSQL, e descrive i package e le loro responsabilità (p. 29). Le slide successive mostrano snippet di test sul modello (per esempio sensore e irrigatore, p. 37) e un modello ER della serra (p. 39). Questo esempio conferma visivamente che un progetto di Modalità 2 può essere presentato senza GUI grafica; non rende però CLI, PostgreSQL o JDBC obbligatori per il tuo progetto.

### Indicazioni per modalità 1

Per la modalità 1, il disciplinare illustra un elaborato basato su statement, diagramma delle classi UML, implementazione e test; altri artefatti UML dipendono dal tema (v1.2, pp. 9-16). JUnit è citato come strumento nei materiali/esempi per implementare test (p. 9 e p. 14), ma non è specificata una versione o una configurazione del progetto.

### Presentazione, AI e colloquio

Il disciplinare v1.2 stabilisce o raccomanda quanto segue:

- inviare la relazione in PDF via email almeno tre giorni lavorativi prima della discussione; il link a GitHub è suggerito, non obbligatorio;
- portare un portatile con l'ambiente di sviluppo alla discussione;
- includere `[SWE]` nell'oggetto delle email e mantenere il thread;
- il lavoro è autoassegnato e serve da argomento per la discussione; il candidato deve comprendere e saper discutere tutto ciò che presenta;
- l'AI generativa è benvenuta ma non obbligatoria; documentarne le parti e il processo d'integrazione è richiesto dal testo; il candidato deve saper discutere le scelte generate;
- non generare UML a partire dal codice;
- la versione 1.2 dice che non è accettabile testo generato nella relazione quando questa deve essere letta da persone (pp. 2-4).

Queste ultime indicazioni sono del disciplinare e riguardano il lavoro d'esame/la relazione; non sono requisiti funzionali della biblioteca.

## Linguaggi, tecnologie e strumenti

- **Linguaggio didattico esplicito:** Java è il linguaggio usato nei materiali del corso (per esempio `JavaBasicsAndIdioms24.pdf`, pp. 1-3). L'incarico dell'utente presenta inoltre il repository come progetto Java.
- **Modellazione:** UML è usato nelle indicazioni del corso; StarUML compare nei file `.mdj` e nei materiali.
- **Test:** il corso distingue test funzionali e strutturali; JUnit compare come strumento negli esempi.
- **Possibili architetture/tecnologie:** JDBC, JavaFX e JavaEE/Jakarta sono menzionati come esempi o possibilità; la traccia biblioteca dice che un'architettura layered in stile JavaEE/Jakarta è da continuare (PDF biblioteca, p. 3), non la impone.
- Una guida locale descrive l'avvio di Eclipse e la creazione di un progetto Java SE (`requirements/1. Java Basics and Idioms/guida-eclipse-javaSE.pdf`, pp. 1-2), ma non stabilisce che Eclipse sia obbligatorio.
- **Non specificati:** versione Java, sistema di build, framework, librerie, database, interfaccia, piattaforma di destinazione e ambiente di esecuzione.

## Consegna e valutazione

Le indicazioni disponibili per l'esame sono riportate sopra. Il disciplinare non fornisce una rubrica quantitativa o criteri/pesi di valutazione dettagliati nei passaggi esaminati. Richiede però padronanza dei contenuti presentati e descrive report e artefatti attesi secondo la modalità scelta. Non sono presenti una scadenza specifica di progetto, un formato di consegna del codice o un repository remoto obbligatorio.

## Contraddizioni, ambiguità e decisioni mancanti

1. **Scelta del dominio:** la modalità 2 è confermata; resta da scegliere il dominio. I file includono la traccia DDD della biblioteca, un esempio separato di rete bibliotecaria con vincoli molto diversi, un esempio serra e altre esercitazioni (per esempio gestione appelli).
2. **UI e persistenza concrete:** il disciplinare non impone una GUI e consente ai test di surrogarla; le slide mostrano una CLI. La persistenza effettivamente implementata e il database da usare non sono fissati come scelte tecnologiche per il progetto, anche se il modello del database è previsto nella documentazione della progettazione.
3. **Varianti della biblioteca:** il disciplinare dell'esercitazione lascia aperto se prestito/registrazione siano uno o due casi d'uso; le regole per prestiti, prenotazioni, notifiche, estensioni e commenti sono incomplete.
4. **Modello non equivalente a specifica:** il diagramma StarUML contiene casi d'uso ulteriori rispetto allo statement (per esempio acquisizioni e gestione delle collocazioni). La loro presenza nel diagramma non prova che siano requisiti approvati.
5. **Versioni dei materiali:** il disciplinare v1.1 di gennaio 2025 e v1.2 di dicembre 2025 si riferiscono ad anni accademici diversi e hanno formulazioni diverse. Il v1.2 è più recente; eventuali istruzioni specifiche del docente non sono incluse.
6. **Tecnologie e architettura:** Java è il contesto del corso, ma non sono fissati versione, build, database o framework. Le menzioni di Eclipse, JDBC, JavaFX e JavaEE/Jakarta sono indicazioni/esempi, non scelte confermate per il repository.
7. **Qualità e valutazione:** non sono stati trovati obiettivi misurabili di qualità né una griglia dettagliata di valutazione.

## Inventario dei materiali esaminati

Sono stati inventariati ricorsivamente i contenuti di `requirements/` dopo l'estrazione: 24 PDF, 22 PNG, 12 modelli StarUML `.mdj`, 66 sorgenti Java e 30 file `.class` di esempio, 6 DOCX, 2 HTML, 2 PPTX, i file di progetto/configurazione Eclipse, gli archivi originali ZIP/RAR, `README.md` e `.gitkeep`. Le cartelle estratte contengono esempi di codice e test, non specifiche di prodotto.

- I PDF sono stati esaminati per testo, pagine e contenuti pertinenti; è stato incluso anche `DefensiveCopies.pdf` estratto dal RAR, che specifica un esercizio didattico su copie difensive e relativi test, non il prodotto biblioteca. Le pagine grafiche sono state consultate con rendering visivo per i materiali centrali: nel disciplinare v1.2 sono state viste in particolare le pagine 5-8 e 18-56, inclusi esempi, mockup/screenshot, architetture, test, ER e diagrammi di navigazione. La traccia biblioteca è stata confrontata con il modello `.mdj` associato.
- Le 22 immagini PNG sono state passate in rassegna visivamente: sono schizzi didattici, prevalentemente del 2020, su Java/UML/pattern; non sono emerse schermate o specifiche grafiche attribuibili con certezza alla biblioteca. La scrittura a mano e la scala delle scansioni limitano la lettura puntuale di alcuni dettagli.
- I modelli `.mdj` sono stati letti come modelli, per identificarne attori, casi d'uso, classi e package; i file che rappresentano esempi diversi non sono stati trattati come requisiti della biblioteca.
- DOCX, PPTX, HTML, README e documentazione testuale estratti da ZIP/RAR sono stati letti per individuare requisiti o vincoli. I sorgenti Java e i test estratti sono stati inventariati e campionati, senza eseguirli. I materiali riguardano esempi di copie difensive, classi base fragili, Observer, Iterator, collezioni, Composite/Adapter/Strategy e Visitor; non aggiungono specifiche per la biblioteca.
- Gli archivi originali restano presenti accanto alle cartelle estratte. Nell'esempio `EvOct14_FragileBaseClass` sono ancora visibili due copie RAR annidate nelle cartelle `src` e `bin`; l'utente conferma che una è stata estratta e che quella in `src` era già rappresentata dai file estratti presenti. Le cartelle contengono i sorgenti e gli artefatti compilati corrispondenti; non resta una lacuna documentale rilevante per i requisiti del progetto.

Tra i materiali didattici/esempi figurano Java Basics e idioms (`1. Java Basics and Idioms/JavaBasicsAndIdioms22.pdf`, `JavaBasicsAndIdioms24.pdf`), UML di implementazione (`2. UML Diagrams - Implementation Perspective/UmlClassDiagramsImplementationPerspective26.pdf`), Design Patterns (`3. Design Patterns/DesignPatternsJava20.pdf`, `DesignPatternsJava22.pdf`, articolo Builder, esempi Iterator e costruttori), testing (`4. ElementsOfTesting/Software Testing Principles and Java Testing Tools.pdf`), analisi OO e use case (cartella `5. Object Oriented Analysis/`), lifecycle/qualità (cartella `6. Sw Development Lifecycle/`) ed esercizi pattern-based (cartella `7. Exercises/`). Questi materiali forniscono contesto metodologico; gli esempi applicativi in essi contenuti non sono stati trasferiti come requisiti del prodotto.

## Tracciabilità delle informazioni principali

| Informazione | Classificazione | Fonte e riferimento |
| --- | --- | --- |
| Volumi, opere, edizioni, collocazioni, utenti, catalogo, commenti, prestiti, solleciti e prenotazioni | Traccia esplicita ma incompleta; adozione da confermare | `5. Object Oriented Analysis/BibliotecaDdd_4Nov2026-refined.pdf`, p. 1 |
| Carico, tempi di risposta, accessibilità, autenticazione e log della rete bibliotecaria | Requisiti espliciti di un esempio distinto; non trasferiti alla candidata traccia DDD | `5. Object Oriented Analysis/An example of problem statement - Rete Biblioteche.pdf`, p. 1 |
| Varianti di consultazione disponibilità, conferma del prestito e notifiche/estensioni | Varianti esplicitamente suggerite, non decisioni definitive | Stesso PDF, p. 1 |
| Casi d'uso, attori e classi elencati nella sezione UML | Contenuto di un modello di esercitazione | `5. Object Oriented Analysis/BibliotecaDdd_4Novembre26.mdj`; testo di accompagnamento, PDF pp. 2-3 |
| Architettura layered stile JavaEE/Jakarta | Annotazione/esempio da continuare, non requisito vincolante | `BibliotecaDdd_4Nov2026-refined.pdf`, p. 3 |
| Tre modalità di progetto e loro ambito | Indicazione del corso; modalità scelta mancante | `SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 5-8 |
| Report e requisiti obbligatori/raccomandati in modalità 2 | Vincolo/indicazione del corso, applicabile se scelta modalità 2 | Stesso PDF, pp. 18-25 |
| Consegna e uso dell'AI | Indicazioni del corso per l'esame e la relazione | Stesso PDF, pp. 2-4 |
| Versione Java, build, framework, database e UI | Informazione non trovata | Fonti esaminate; nessuna scelta confermata |
