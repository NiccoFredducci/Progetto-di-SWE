# Piano di implementazione progressiva

Questo piano descrive l'ordine di lavoro proposto per costruire e verificare il catalogo per incrementi. Ogni fase deve produrre un risultato osservabile e un percorso di verifica, senza richiedere che tutte le funzioni successive siano già disponibili.

## Stati di avanzamento

Usare esclusivamente questi valori nella colonna **Stato**:

- **Non iniziato** — il lavoro della fase non è cominciato.
- **Iniziato** — sono stati avviati alcuni elementi, ma manca ancora la parte principale.
- **A buon punto** — il risultato principale esiste, ma restano revisione, decisioni o verifiche.
- **Finito** — gli obiettivi della fase sono implementati e verificati; la documentazione riflette il risultato osservato.

Aggiornare questo piano insieme a `FEATURES.md` ogni volta che cambia lo stato di una fase o di una feature. Le feature restano in «Features da implementare» finché non sono completate e verificate. Una fase di progettazione non chiude questioni che l'autore deve ancora approvare.

## Roadmap

| Fase | Obiettivo | Stato |
|---|---|---|
| 1. Modello concettuale e relazioni | Distinguere framework generico e istanza per film; fissare le associazioni, le cardinalità e i vincoli approvati; isolare le domande da sottoporre all'autore prima di vincolare lo schema. | **A buon punto** |
| 2. Nucleo del framework | Modellare i concetti generici e le regole indipendenti dalla persistenza e dalla UI; verificare le invarianti con test di dominio. | **Non iniziato** |
| 3. Servizi e associazioni | Implementare i casi d'uso e le relazioni tra profili, media, sezioni e autori; provarli con repository sostitutivi in memoria. | **Non iniziato** |
| 4. Persistenza relazionale con ORM | Scegliere l'ORM con l'autore, mappare i concetti nel database locale e verificare salvataggio, lettura, vincoli e relazioni con prove d'integrazione. | **Non iniziato** |
| 5. Profili e interfaccia essenziale | Allineare selezione, creazione, modifica e cancellazione confermata dei profili ai servizi del framework; aggiungere la scelta della foto e verificarne i flussi. | **Iniziato** |
| 6. Consultazione e ricerca del catalogo | Presentare preferiti e sezioni, applicare gli ordinamenti richiesti e integrare la ricerca dopo averne concordato i campi e il comportamento. | **Non iniziato** |
| 7. Inserimento e gestione di media, sezioni e autori | Aggiungere i form per film (media), generi (sezioni) e autori; applicare i limiti della creazione rapida e verificare le associazioni. | **Non iniziato** |

## Fase 1 — Primo modello concettuale

Questa fase è stata svolta come prima analisi del dominio e delle sue relazioni. **Un film è l'istanza concreta di un media nel framework**; allo stesso modo, un genere è una sezione e un autore di film è un autore/contributore. Le decisioni qui sotto sono separate dalle questioni ancora da approvare.

### Relazioni approvate o direttamente espresse

| Relazione | Interpretazione per il dominio film |
|---|---|
| Profilo — media preferiti | Ogni profilo può riferirsi a media preferiti. Le preferenze della sezione «Preferiti» appartengono al profilo attivo. |
| Profilo — autori preferiti | Ogni profilo può riferirsi ad autori preferiti; la loro presentazione nell'interfaccia resta da definire. |
| Sezione — media | Una sezione contiene media. Nel caso film è un genere e un film può appartenere a più generi; una sezione vuota deve poter essere rappresentata e mostrata. |
| Autore — media | Gli autori sono associati ai media che hanno prodotto. Nel form film richiesto dall'autore si seleziona un autore per film; un autore può essere collegato a più film. |
| Autore — generi | Nel catalogo film l'elenco dei generi di un autore si ricava dai suoi film e dai generi di ciascun film; il numero mostrato è il conteggio dei film per genere. È una relazione derivata, non una nuova associazione autonoma richiesta. |

### Decisioni preliminari e domande aperte

- **Profilo–preferenze** — La rappresentazione molti-a-molti è la candidata naturale per utenti che scelgono media/autori e media/autori preferiti da profili diversi. La condivisione del catalogo tra profili e la cardinalità definitiva saranno confermate prima di fissare lo schema.
- **Autore–media nel framework** — La specializzazione film prevede il campo singolo richiesto dall'autore. Resta da stabilire se il framework generico consenta più autori/contributori per lo stesso media.
- **Sezione–media** — La relazione molti-a-molti è necessaria per i film multi-genere. Resta da chiarire se un film possa essere salvato senza genere; le sezioni vuote sono invece necessarie per il comportamento di navigazione richiesto.
- **Visibilità e proprietà del catalogo** — Non è ancora definito se film, sezioni e autori siano condivisi tra tutti i profili dell'installazione o se esista un concetto di proprietà per singolo profilo. Non introdurre una relazione di proprietà senza conferma.
- **Cancellazioni** — La conferma prima della cancellazione del profilo è richiesta; non è ancora deciso se le preferenze vengano rimosse, se altri dati vadano conservati o come trattare gli elementi non più associati.
- **File** — Non è ancora deciso se conservare percorsi originali o copiare i file, né come trattare immagini o video spostati, mancanti o non leggibili.
- **Unicità e campi obbligatori** — Unicità di nomi di profili, generi e autori, obbligatorietà di foto profilo/locandina e validazioni dei file vanno definite prima dei rispettivi casi d'uso.

### Criterio di uscita della fase

La fase 1 sarà **Finita** quando l'autore avrà revisionato e approvato le cardinalità e le regole necessarie a progettare le associazioni, incluse proprietà/condivisione del catalogo, autore per media, generi e cancellazioni. La presente mappa non è ancora un diagramma UML o uno schema relazionale definitivo; non codificare come fatti le interpretazioni in attesa di conferma.

## Fasi successive: risultato e verifica prevista

### Fase 2 — Nucleo del framework

Definire i tipi e le regole di dominio riutilizzabili senza dipendere da film, Swing o database. Il test di dominio deve poter usare un media di prova generico e non richiedere che esistano già form, autori completi o file video reali. Non creare oggetti di catalogo incompleti se violano invarianti che l'autore avrà approvato.

### Fase 3 — Servizi e associazioni

Implementare operazioni applicative per i flussi approvati. Usare repository sostitutivi in memoria per verificare casi normali, alternative, errori, ordinamenti e associazioni senza database definitivo. I sostituti implementano le stesse interfacce usate poi dalla persistenza, così i test e la UI attraversano i medesimi servizi.

### Fase 4 — Persistenza

Solo dopo la scelta dell'ORM e delle regole di integrità, implementare lo schema e le mappature. Verificare con dati temporanei la rilettura delle relazioni, l'isolamento delle preferenze, i film multi-genere e le regole di cancellazione approvate. Non trattare i test in memoria come prova della correttezza del database.

### Fase 5 — Profili e interfaccia essenziale

Il prototipo attuale contiene la selezione e la creazione del profilo, ma va ancora ricompilato dopo una correzione e non è verificato. Usarlo come punto di partenza, riallineandolo ai servizi e alla persistenza scelti. Realizzare la UI dei profili e verificare ogni flusso sia a livello di servizio/controller sia manualmente nella schermata; aggiungere modifica, cancellazione confermata e foto profilo quando i relativi comportamenti saranno definiti.

### Fase 6 — Consultazione e ricerca

Preparare una vista di catalogo con un insieme piccolo e controllato di profili, media, generi e autori. Verificare prima l'ordinamento dei servizi/query; usare la schermata per controllare che le sezioni popolate precedano quelle vuote, che i titoli siano ordinati e che «Preferiti» dipenda dal profilo attivo. Implementare ricerca solo dopo aver definito i campi e le regole di corrispondenza.

### Fase 7 — Inserimento e gestione

Costruire verticalmente i flussi: creazione autonoma di genere usando film esistenti; creazione autonoma di autore usando film esistenti e dati opzionali; aggiunta di film con selezione di file, generi e autore. Le scorciatoie di creazione sono solo nel form film. Verificare i servizi senza UI, poi percorrere i form essenziali e le associazioni persistenti. Aggiornare relazione e istruzioni d'uso soltanto con comportamenti verificati.

## Regola d'aggiornamento

Ad ogni incremento aggiornare lo stato della fase qui, le feature corrispondenti in `FEATURES.md`, le decisioni didattiche in `REQUIREMENTS.md` solo quando pertinenti e i capitoli LaTeX toccati. Il README documenta installazione, avvio e uso effettivamente verificato; non è il registro dettagliato della roadmap.
