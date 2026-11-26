# Requisiti agnostici per il progetto SWE – Modalità 2

## Scopo e criteri di lettura

Questo documento formalizza le indicazioni del corso per un progetto in Modalità 2 senza prescrivere uno specifico dominio applicativo o un insieme di funzionalità di prodotto. Deve guidare l'analisi, la progettazione, la realizzazione, la verifica e la relazione di un frammento applicativo scelto dallo studente.

Le etichette distinguono la forza delle indicazioni:

- **Richiesta esplicita**: formulata come richiesta o prescrizione nel disciplinare corrente.
- **Raccomandazione**: indicata come contenuto normale, consigliato o utile; va motivata e adattata al progetto.
- **Pratica didattica**: principio o tecnica insegnata nelle dispense, non automaticamente un vincolo valutativo.
- **Decisione da prendere**: informazione del progetto che le fonti non fissano e che deve essere decisa dall'autore o concordata col docente.

Le citazioni puntano ai documenti in `requirements/`, che sono fonti locali fornite dall'utente e non vengono riprodotti qui. I domini presenti negli esempi del corso sono solo illustrazioni e non definiscono funzionalità o vincoli per il progetto.

## 1. Inquadramento della Modalità 2

### Obiettivo

- **Richiesta esplicita** — Realizzare la progettazione e lo sviluppo di un frammento applicativo riferito a un dominio applicativo dichiarato, applicando le pratiche di ingegneria del software affrontate nel corso. La Modalità 2 è quella comune/raccomandata nel disciplinare. La descrizione deve delimitare il frammento e renderne comprensibili obiettivo e confini; non è necessario realizzare un sistema completo del dominio. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 5–8, 20)
- **Richiesta esplicita** — Il candidato deve comprendere e saper discutere il lavoro presentato, incluse le scelte e i contenuti del progetto. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 2–4)
- **Decisione da prendere** — L'ampiezza deve bastare a mostrare analisi, progettazione, implementazione e verifica, ma non è fissato un numero minimo di funzioni, classi o casi d'uso. Il docente ha indicato di ampliare il perimetro solo se, dopo averlo definito, risulta troppo povero.
- **Decisione da prendere** — Dominio, destinatari, confine del sistema, attori, obiettivi, funzioni incluse e non incluse, dati gestiti e contesto d'uso devono essere definiti per il singolo progetto. Non sono fissati dal disciplinare.

### Fonti del progetto e controllo delle decisioni

- Le funzionalità applicative devono derivare da decisioni dell'autore, non essere copiate automaticamente da esempi, diagrammi didattici o nomi di pattern.
- Distinguere nel materiale di progetto i fatti tratti dalle fonti, le decisioni approvate, le interpretazioni provvisorie e le questioni aperte. Contraddizioni tra fonti vanno segnalate e sottoposte al docente/autore, non risolte tacitamente.
- Non sono prescritti un processo di sviluppo specifico, una piattaforma, un database, uno strumento di build, un framework o una versione Java. Sceglierli solo dopo averne verificato l'adeguatezza al progetto e ai vincoli del corso, e documentare le scelte.

## 2. Analisi dei requisiti e progettazione funzionale

### Requisiti funzionali e casi d'uso

- **Richiesta esplicita** — Esplicitare quali servizi, compiti o funzioni il prodotto fornisce e quali attori interagiscono col sistema. Descrivere l'obiettivo dell'attore e il confine del sistema. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 20, 22; `UseCaseDiagsAndTemplates_26.pdf`, in particolare pp. 2–8)
- **Richiesta esplicita** — Includere diagrammi dei casi d'uso e template descrittivi dei casi d'uso. Diagramma e template devono corrispondere: identificativo e titolo coerenti; i template espandono le interazioni e i flussi rappresentati. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 22; `UseCaseDiagsAndTemplates_26.pdf`, sezioni su use case diagram e template)
- Per ogni caso d'uso significativo, specificare almeno: identificativo, titolo, livello/obiettivo, attori/ruoli, precondizioni, flusso normale, flussi alternativi o di errore e postcondizioni. Definire in modo comprensibile le condizioni che avviano o interrompono il flusso. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 22, 31–32; `UseCaseDiagsAndTemplates_26.pdf`, sezioni sui template e sui flussi)
- Distinguere casi d'uso orientati a un obiettivo dell'attore da passi interni o dettagli di interfaccia; usare relazioni UML quali `include` ed `extend` solo quando chiariscono una relazione reale. Diagrammi alternativi possono mostrare prospettive/ruoli diversi quando ciò rende più leggibile il sistema. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 22, 40–41, 52–53; `UseCaseDiagsAndTemplates_26.pdf`)
- Quando utile, associare ai passi del template i mockup disponibili e i test che li verificano, così che i flussi siano rintracciabili fino alla verifica. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 31–32, 40–41)

### Interfaccia e navigazione

- **Raccomandazione, non obbligo di GUI** — Mockup e diagramma di navigazione tra pagine sono raccomandati dal disciplinare; i mockup dovrebbero chiarire informazioni e controlli delle pagine, mentre la navigazione mostra come si passa tra esse. Possono essere sostituiti/aggiornati con schermate effettive quando l'interfaccia è realizzata. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 22, 42–43)
- Le dispense e il disciplinare ammettono che le prove funzionali siano condotte tramite metodi di controller che fungano da surrogato dell'interfaccia: la GUI completa non è quindi un prerequisito generale per il progetto. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 35; `NotesOnTheProjectWork_Jan2025.pdf`, sezione Testing)
- **Decisione da prendere** — Se realizzare una GUI, una CLI o altra forma d'interazione; quali schermate/viste e navigazione prevedere; i requisiti di accessibilità e usabilità pertinenti. L'eventuale interfaccia deve essere proporzionata agli obiettivi del progetto. Il docente, nella risposta all'autore sul tema proposto, consiglia di non enfatizzare il front-end perché non è il tema del corso e può essere difficile da difendere: è un'indicazione di impostazione, non un divieto di interfaccia.

## 3. Modello di dominio e progettazione

### Analisi OO

- **Pratica didattica** — Individuare concetti, oggetti/entità, responsabilità, stati e relazioni rilevanti nel dominio; separare il modello concettuale del dominio dalle classi, componenti e dipendenze introdotte per implementarlo. Mantenere il modello fedele ai requisiti e sufficientemente semplice da poter essere spiegato. (`ObjectOrientedAnalysis20.pdf`, sezioni su analisi e modelli di dominio; `AnalysisIdioms20.pdf`)
- Specificare le regole e i vincoli del dominio nei casi d'uso e/o nei modelli appropriati, senza lasciare che il solo diagramma sostituisca le regole testuali. Per processi lunghi, con alternative o responsabilità parallele, un diagramma di attività può integrare il template del caso d'uso. (`ActivityDiagsAndProcedureSpecification20.pdf`, sezioni su activity diagram e specifica procedurale)
- Usare associazioni per relazioni strutturali e dipendenze per uso non strutturale, con molteplicità e navigabilità chiare quando pertinenti. Stereotipi e idiomi analitici sono strumenti esplicativi, non obblighi da applicare indiscriminatamente. (`UmlClassDiagramsImplementationPerspective26.pdf`; `AnalysisIdioms20.pdf`)

### Architettura e package

- **Richiesta esplicita** — Presentare una vista d'insieme dell'architettura, un diagramma dei package e delle loro dipendenze, e spiegare responsabilità, contenuti e coesione di ciascun package. Indicare le tecnologie e gli strumenti effettivamente usati. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 21)
- L'architettura va motivata rispetto alle responsabilità e ai requisiti. La raccomandazione del docente per il tema proposto suggerisce di ragionare su modello di dominio, servizi, persistenza/ORM e controller verso l'interfaccia; sono responsabilità architetturali da valutare, non un obbligo a usare un ORM, un framework o una GUI in ogni progetto.
- Il docente ha anche suggerito, come possibile approfondimento, di separare un nucleo riutilizzabile che gestisca elementi di un catalogo astratto da una specializzazione applicativa concreta. È un'opzione di progettazione da adottare solo se utile e giustificata; non è una struttura obbligatoria né un requisito funzionale.
- **Richiesta esplicita** — Documentare la progettazione con diagrammi delle classi per package e una descrizione delle responsabilità e degli elementi di particolare significato. I diagrammi devono aiutare a comprendere il progetto e non essere un mero elenco di firme. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 24, 44, 48–49)
- Quando serve a spiegare un comportamento rilevante, integrare i diagrammi delle classi con diagrammi di sequenza/collaborazione, attività o altri modelli appropriati; non è richiesto produrre ogni tipo di diagramma. (`UmlClassDiagramsImplementationPerspective26.pdf`; `ActivityDiagsAndProcedureSpecification20.pdf`)

### Progettazione dei dati

- **Richiesta esplicita** — Documentare il modello di database. Il disciplinare mostra come riferimenti un diagramma Entità-Relazioni e il corrispondente modello relazionale. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 23, 39; `NotesOnTheProjectWork_Jan2025.pdf`, sezione Design)
- **Interpretazione operativa** — Per rendere la documentazione verificabile, includere concetti/entità e relazioni, cardinalità, attributi, chiavi primarie ed esterne, vincoli d'integrità e la mappatura nel modello relazionale. Specificare quali dati sono persistenti e quali operazioni li leggono o modificano. La forma esatta dipende dai requisiti del dominio.
- **Decisione da prendere** — Database, modalità di distribuzione e accesso, ORM o altra tecnologia di persistenza, migrazione/creazione dello schema e strategia di caricamento dei dati. Gli esempi del corso (tra cui JDBC/RDBMS) non fissano tali scelte per ogni progetto.
- Modellare relazioni many-to-many, entità associative, attributi e vincoli secondo il significato del dominio; non rappresentare collezioni o associazioni come campi non strutturati senza giustificazione. Verificare la coerenza fra modello concettuale, schema relazionale e accesso ai dati.

### Diagrammi UML

- Usare UML come notazione di comunicazione/progettazione e mantenere distinti i diagrammi concettuali da quelli che descrivono la prospettiva d'implementazione. Associazioni strutturali, dipendenze e chiamate temporanee non sono intercambiabili. (`UmlClassDiagramsImplementationPerspective26.pdf`, sezioni su prospettive e relazioni)
- **Vincolo del corso** — Non generare i diagrammi UML dal codice come sostituto della progettazione; il disciplinare considera normalmente inutile UML prodotto automaticamente dal codice. I diagrammi devono esprimere le decisioni progettuali discusse dall'autore. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 3–4)

## 4. Implementazione e qualità del codice

- **Linguaggio** — Il progetto riguarda software sviluppato nel contesto del corso di Software Engineering; il repository è predisposto come progetto Java. Le fonti qui esaminate non fissano una versione Java: va concordata/decisa e documentata prima di basare il lavoro su specifiche API.
- **Richiesta esplicita per la relazione** — Discutere dettagli d'implementazione significativi, responsabilità, estratti di codice selezionati e motivati, idiomi, pattern di progettazione/architetturali e dettagli sulle tecnologie effettivamente usate. Non serve trascrivere il codice sorgente nella relazione. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 18, 24, 33, 36–38)
- **Pratiche di manutenibilità** — Favorire responsabilità comprensibili, interfacce e visibilità non più ampie del necessario, incapsulamento, stato invariabile quando appropriato, gestione consapevole di oggetti mutabili e collezioni esposte. Preferire composizione a ereditarietà quando riduce accoppiamento e fragilità; prestare attenzione alle classi base fragili, alla duplicazione e alla complessità. Sono pratiche discusse nelle dispense, da applicare con giudizio e da motivare, non una checklist universale. (`JavaBasicsAndIdioms24.pdf`, sezioni su accessibilità, immutabilità e copie difensive; immagini `requirements/1. Java Basics and Idioms/sketches/Sketches_SWE-22-09-28_ FragileBaseClass_DynamicMethodLookup.png` e `Sketches_SWE-22-10-05_ HelperMethodToFixFragileBaseClass.png`)
- Documentare le scelte di pattern con il problema affrontato, il ruolo delle parti coinvolte e le conseguenze/alternative pertinenti. Un pattern è una soluzione ricorrente applicabile a un contesto; il solo uso nominale di un pattern non è un requisito e forzarlo senza motivazione può aumentare complessità. (`DesignPatternsJava22.pdf`, sezioni introduttive e discussione di pattern; `DesignPatternsJava20.pdf`)
- Non è prescritta una metrica di qualità o una soglia quantitativa universale. Le misure, se usate, devono rispondere a una domanda esplicita e ai requisiti del progetto, essere interpretabili e non diventare obiettivi isolati. (`SE_lecture_software_quality.pdf`, pp. 44–70)

## 5. Requisiti non funzionali e qualità

- **Decisione da prendere per ogni progetto** — Individuare gli attributi di qualità rilevanti dal contesto e dagli attori, formulandoli in modo osservabile o verificabile quando possibile. Il corso distingue ciò che il prodotto deve fare (requisiti funzionali) da come/quanto bene deve farlo (requisiti di qualità/non funzionali). (`SE_lecture_software_quality.pdf`, pp. 6–7)
- Gli attributi da considerare includono, a titolo di analisi e non come requisiti universali: adeguatezza e completezza funzionale; efficienza/prestazioni e capacità; compatibilità/interoperabilità; usabilità e accessibilità; affidabilità, disponibilità e tolleranza ai guasti; sicurezza; manutenibilità (modularità, riusabilità, analizzabilità, modificabilità, testabilità); flessibilità/adattabilità; sicurezza operativa. Definizioni e dimensioni sono trattate nel modello ISO/IEC 25010 illustrato nelle dispense. (`SE_lecture_software_quality.pdf`, pp. 10–43)
- Non trasferire dal materiale esemplificativo soglie, numeri, livelli di servizio o requisiti di sicurezza: per il singolo progetto vanno definiti in base a utenti, rischi, ambiente operativo e obiettivi. Se un attributo non è rilevante, non inventare un requisito artificiale.
- Valutare e discutere debito tecnico, manutenibilità e complessità quando pertinenti. Metriche statiche/dinamiche, copertura e complessità ciclomatica sono segnali parziali, non prove autonome della qualità o correttezza. (`SE_lecture_software_quality.pdf`, pp. 44–79; `Lifecycles20.pdf`, pp. 16–23)

## 6. Verifica e testing

- **Richiesta esplicita** — Distinguere test funzionali (black-box / integrazione) da test strutturali (white-box / unitari), motivando i test significativi e collegandoli alle funzioni e ai flussi documentati. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 18, 25, 35–38)
- **Test funzionali** — Derivare i casi di prova dai requisiti e dai template dei casi d'uso; verificare esiti attesi dei flussi normali, alternativi e di errore attraverso il confine applicativo appropriato. Il disciplinare indica che metodi del controller possono fungere da surrogato della GUI. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 25, 35–36)
- **Test strutturali/unitari** — Usare la conoscenza della struttura interna per verificare unità significative della logica, incluse condizioni/rami rilevanti e casi limite. Selezionare elementi critici o di particolare interesse e spiegare cosa copre il test e cosa resta fuori. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 25, 37; `Software Testing Principles and Java Testing Tools.pdf`, pp. 16–19, 26–32)
- **Persistenza** — Verificare operazioni e interrogazioni di accesso ai dati, se non già coperte dai test funzionali. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 38)
- Mantenere una traccia tra requisito/caso d'uso, identificativo di test, setup, input, azione e risultato atteso/osservato. Includere dati di prova e condizioni necessarie per riprodurre i risultati. Questa è l'interpretazione operativa necessaria a documentare prove verificabili; il template e il livello di dettaglio vanno adeguati al progetto.
- Le dispense descrivono granularità (unit, end-to-end, integration), piramide dei test, selezione/astrazione dei test e coverage; ciò informa la strategia, ma non prescrive una percentuale di copertura o uno specifico framework. JUnit è presentato come strumento Java didattico, non come unica scelta obbligatoria. (`Software Testing Principles and Java Testing Tools.pdf`, pp. 8–14, 16–32)
- **Decisione da prendere** — Strumenti, automazione, ambienti, dati e livelli di test da usare. Riportare con onestà quali verifiche sono state effettivamente eseguite; non dichiarare test passati se non eseguiti.

## 7. Processo di sviluppo, evoluzione e manutenzione

- Le dispense descrivono cicli di vita e processi sequenziali, iterativi/incrementali e adattivi, con artefatti e attività di analisi, progettazione, implementazione, test e rilascio. Non viene imposto un unico modello di ciclo di vita al singolo progetto; il processo scelto deve sostenere feedback, verifica e controllo dei cambiamenti. (`Lifecycles20.pdf`, pp. 3–14, 23–47)
- **Pratica raccomandata** — Sviluppare per passi coerenti e mantenere sincronizzati requisiti, modelli, codice e test; quando una decisione cambia, aggiornare gli artefatti coinvolti e verificare le ricadute. Le dispense trattano feedback, integrazione continua, piccoli rilasci e riduzione delle separazioni tra attività come pratiche di processo/adattamento, non come toolchain obbligatoria. (`Lifecycles20.pdf`, pp. 35–47)
- **Richiesta dell'autore per questo progetto** — Aggiornare progressivamente la relazione sorgente LaTeX già presente in `docs/` mentre si sviluppa il progetto, anziché compilarla soltanto alla fine. Dopo ogni cambiamento sostanziale aggiornare le sezioni effettivamente interessate (requisiti, modelli, architettura, implementazione o verifiche), mantenendo coerenza con il software e tracciabilità ai casi d'uso e ai test. Registrare solo scelte, artefatti e risultati osservati; lasciare segnaposto dove manca una decisione o una verifica. La compilazione del PDF e la procedura di consegna vanno definite in base agli strumenti effettivamente disponibili.
- **Vincolo esplicito del corso sull'uso dell'AI** — L'uso di AI generativa è ammesso e va documentato, compresi strumenti, modalità d'uso e parti del lavoro interessate; non generare UML dal codice. Il disciplinare avverte che testo generico prodotto dall'AI non è accettabile come relazione d'esame. L'autore deve quindi rielaborare e assumersi la responsabilità del testo finale; l'assistenza alla manutenzione LaTeX può tenere aggiornati struttura, fatti verificabili, riferimenti e appunti, che l'autore dovrà trasformare nella propria relazione. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 3–4)

## 8. Relazione e consegna d'esame

### Struttura di riferimento

- Il disciplinare dice che la relazione di Modalità 2 **normalmente** include: statement; architettura a colpo d'occhio; tecnologie e strumenti; analisi dei requisiti e disegno funzionale (diagrammi e template dei casi d'uso, mockup e navigazione); progettazione (architettura, package, diagrammi delle classi per package e dettagli significativi); dettagli d'implementazione, responsabilità, snippet motivati, idiomi/pattern/tecnologie; test funzionali black-box/integrazione e strutturali white-box/unitari. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 18)
- Il frontespizio deve identificare corso, anno accademico, autore/i e docente; fornire un indice esplicativo che aiuti a capire l'organizzazione del documento. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, p. 19)
- Lo statement dev'essere chiaro e sintetico e descrivere contesto e obiettivo dell'applicazione. Le sezioni di architettura, package, progetto e database devono descrivere ciò che è stato effettivamente progettato. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 20–24)
- Motivare gli estratti di codice scelti e discutere gli elementi di particolare rilievo, invece di riportare grandi quantità di codice senza spiegazione. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 24, 33, 36–38)

### Consegna e discussione

- **Vincoli espliciti nel disciplinare v1.2** — Inviare il PDF della relazione via email almeno tre giorni lavorativi prima dell'appello; il repository/GitHub è suggerito come riferimento; portare il laptop/ambiente necessario alla discussione. Saper comprendere e difendere il lavoro. Verificare eventuali aggiornamenti organizzativi del corso prima dell'appello. (`SWE25_disciplinareDiEsame_Vers1.2.pdf`, pp. 2–4)
- Il documento precedente (`NotesOnTheProjectWork_Jan2025.pdf`, versione 1.1, A.A. 2024/25) si dichiara incompleto. È utile per chiarire template, database, mockup e diagrammi, ma dove differisce prevale come riferimento corrente il disciplinare v1.2, A.A. 2025/26.

## 9. Decisioni e lacune ancora aperte

Le seguenti informazioni non sono fissate dalle fonti del corso e devono essere decise per il singolo progetto senza attribuirle al disciplinare:

1. Dominio applicativo definitivo, statement, utenti/attori e confine del frammento.
2. Elenco e priorità delle funzionalità approvate, regole di business, dati, pre/postcondizioni e flussi alternativi.
3. Requisiti non funzionali pertinenti e relativi criteri di accettazione misurabili, ove necessari.
4. Presenza e forma dell'interfaccia, strumenti per i mockup e livello di navigazione.
5. Modello dati, database, ORM/driver, strategia di persistenza e schema concreto.
6. Versione Java, ambiente di esecuzione, strumenti di build, librerie/framework e compatibilità richiesta.
7. Piano/strumenti di test, dati di test, criteri di copertura e verifiche da eseguire.
8. Convenzioni del docente non riportate nel disciplinare corrente e dettagli aggiornati di consegna dell'appello.

Questi punti non sono requisiti funzionali impliciti e non autorizzano ad aggiungere funzioni. Le decisioni devono essere concordate con l'autore e, quando necessario, verificate col docente.

## Fonti esaminate e limiti

Fonte normativa principale: `requirements/SWE25_disciplinareDiEsame_Vers1.2.pdf` (A.A. 2025/26, v1.2, 3 dicembre 2025). Riferimento precedente: `requirements/NotesOnTheProjectWork_Jan2025.pdf` (A.A. 2024/25, v1.1, dichiarato incompleto). Dispense tematiche consultate: `JavaBasicsAndIdioms22.pdf`, `JavaBasicsAndIdioms24.pdf`, `DefensiveCopies.pdf`, `guida-eclipse-javaSE.pdf`, `UmlClassDiagramsImplementationPerspective26.pdf`, `DesignPatternsJava20.pdf`, `DesignPatternsJava22.pdf`, `Design Patterns_ The Builder Pattern - DZone Java.pdf`, entrambe le dispense `iteratorPattern_javaCollectionFramework`, `telescopicConstructor.pdf`, `Software Testing Principles and Java Testing Tools.pdf`, `ObjectOrientedAnalysis20.pdf`, `AnalysisIdioms20.pdf`, `ActivityDiagsAndProcedureSpecification20.pdf`, `UseCaseDiagsAndTemplates_26.pdf`, `UseCaseDiagsAndTemplates_SW4ES_19.pdf`, `Lifecycles20.pdf` e `SE_lecture_software_quality.pdf`. I diagrammi e le figure presenti nei PDF sono stati inclusi nella lettura visiva; le figure chiariscono concetti ed esempi, ma non aggiungono requisiti applicativi al progetto.

Altri esempi esaminati, tra cui `An example of problem statement - Rete Biblioteche.pdf`, `ApplicazioneViola_ScuolaDiMedicina.pdf` e `BibliotecaDdd_4Nov2026-refined.pdf`, sono stati consultati esclusivamente per distinguere i principi generali dai dati specifici di un esempio; numeri, funzioni e modelli di quei domini non sono trasferiti al progetto. Gli altri materiali della cartella (esercizi, modelli UML, codice, presentazioni, documenti e immagini di lezione) non definiscono requisiti del prodotto. Le 22 immagini autonome e le pagine dei PDF contenenti immagini sono state renderizzate e sottoposte a controllo visivo; sono schemi, diagrammi e annotazioni didattiche. Non si sono riscontrati problemi di apertura dei PDF o delle immagini. Eventuali ambiguità nelle dispense sono gestite mantenendo come riferimento il disciplinare v1.2 e lasciando aperte le decisioni non definite.
