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
| 1. Modello concettuale e relazioni | Distinguere framework generico e istanza per film; fissare le associazioni, le cardinalità e i vincoli approvati; isolare le domande da sottoporre all'autore prima di vincolare lo schema. | **Finito** |
| 2. Nucleo del framework | Modellare i concetti generici e le regole indipendenti dalla persistenza e dalla UI; verificare le invarianti con test di dominio. | **Non iniziato** |
| 3. Servizi e associazioni | Implementare i casi d'uso e le relazioni tra profili, media, sezioni e autori; provarli con repository sostitutivi in memoria. | **Non iniziato** |
| 4. Persistenza relazionale con ORM | Scegliere l'ORM con l'autore, mappare i concetti nel database locale e verificare salvataggio, lettura, vincoli e relazioni con prove d'integrazione. | **Non iniziato** |
| 5. Profili e interfaccia essenziale | Allineare selezione, creazione, modifica e cancellazione confermata dei profili ai servizi del framework; aggiungere la scelta della foto e verificarne i flussi. | **Iniziato** |
| 6. Consultazione e ricerca del catalogo | Presentare preferiti e sezioni, applicare gli ordinamenti richiesti e integrare la ricerca dopo averne concordato i campi e il comportamento. | **Non iniziato** |
| 7. Inserimento e gestione di media, sezioni e autori | Aggiungere i form per film (media), generi (sezioni) e autori; applicare i limiti della creazione rapida e verificare le associazioni. | **Non iniziato** |

## Fase 1 — Primo modello concettuale

Questa fase è stata revisionata dall'autore. **Un film è un media nel framework**; un genere è una sezione e un autore di film è un autore/contributore. I profili condividono il catalogo globale; soltanto le loro preferenze sono personali.

### Relazioni approvate o direttamente espresse

| Relazione | Interpretazione per il dominio film |
|---|---|
| Profilo — media preferiti | Molti-a-molti: un profilo può preferire più media e lo stesso media può essere preferito da più profili. Le preferenze della sezione «Preferiti» appartengono al profilo attivo. |
| Profilo — autori preferiti | Molti-a-molti: un profilo può preferire più autori e lo stesso autore può essere preferito da più profili. |
| Sezione — media | Molti-a-molti e facoltativa da entrambi i lati: una sezione può essere vuota e un media può non avere sezioni. Nel caso film, il media è il film e la sezione è il genere. |
| Autore — media | Uno-a-molti: ogni media ha esattamente un autore; un autore può essere associato a zero o più media. Quando più persone hanno contribuito, l'utente sceglie quale autore associare. La stessa cardinalità vale nel framework generico e nell'istanza film. |
| Autore — sezioni | Relazione derivata attraverso i media dell'autore e le sezioni di ciascun media. Il conteggio per genere è calcolato; non è un'associazione autonoma da duplicare nello schema. |
| Profilo — catalogo | Nessuna relazione di proprietà: contenuti aggiunti da qualunque profilo sono condivisi con tutti. Non sono previsti contenuti privati. |
| Profilo — cancellazione | Dopo la conferma, si elimina il profilo e si cancellano le sue sole relazioni di preferenza. Media, sezioni e autori condivisi rimangono nel catalogo. |

### Campi obbligatori e fallback visivi

- Profilo: il solo nome è obbligatorio. La foto può mancare; mostrare un colore saturo casuale e l'iniziale del nome.
- Media (film): file e titolo sono gli unici campi obbligatori tra i dati propri del media. L'associazione con un autore è obbligatoria; descrizione, copertina caricata e sezioni possono mancare. Il catalogo fornisce comunque una copertina visiva; nell'istanza film il fallback è un'immagine verticale con colore saturo casuale e titolo centrato e adattato allo spazio.
- Autore: il solo nome è obbligatorio. Foto e descrizione possono mancare; il fallback per la foto usa colore saturo casuale e iniziale del nome. Il flusso di creazione rapida può quindi richiedere il solo nome.
- Sezione (genere): il solo nome è obbligatorio e può non avere media associati.
- L'applicazione mette a disposizione cartelle suggerite separate per video e locandine, ma non obbliga a usarle. Il database memorizza il percorso del file fornito dall'utente; l'implementazione può copiare file in un'area gestita, ma deve conservare il percorso fornito.
- La copertina caricata per un film è verticale, rapporto 3:4 (4:3 verticale), con avviso del ritaglio. Profili, autori e locandine mancanti hanno un segnaposto invece di un file immagine obbligatorio.
- Colore del segnaposto e percorso immagine sono concetti distinti: non trattare un valore colore come se fosse un percorso fisico. La rappresentazione del segnaposto (generazione a runtime, file generato o altro meccanismo esplicito) resta una scelta implementativa.

### Aspetti ancora da definire in fasi successive

- **Segnaposto** — Il comportamento visivo è deciso, ma la rappresentazione tecnica va progettata mantenendo separato il riferimento a un'immagine dal colore e dal testo generati. Il colore pseudo-casuale potrà essere persistente o stabile per identità; questa scelta non è ancora necessaria per il modello concettuale.
- **File** — Il riferimento al percorso scelto è richiesto; il meccanismo opzionale di copia e il comportamento se un file viene spostato, eliminato o diventa illeggibile andranno precisati con la persistenza e i flussi di errore.
- **Unicità e confronti** — Non sono ancora fissate l'unicità dei nomi, la normalizzazione di testo o le regole esatte per confrontare titoli nell'ordinamento alfabetico.
- **Interfaccia** — È ancora da definire come le preferenze di autori si rendono consultabili, oltre alla sezione dei media preferiti.

### Criterio di uscita della fase

La fase 1 è **Finita**: l'autore ha approvato condivisione, cardinalità, dati obbligatori, facoltatività delle sezioni e comportamento relazionale della cancellazione del profilo. Questa è la decisione concettuale di base, non ancora il diagramma UML o lo schema relazionale implementativo.

## Fasi successive: risultato e verifica prevista

### Fase 2 — Nucleo del framework

Definire i tipi e le regole di dominio riutilizzabili senza dipendere da film, Swing o database. Il test di dominio deve poter usare un media di prova generico e non richiedere che esistano già form, autori completi o file video reali. Non creare oggetti di catalogo incompleti se violano invarianti che l'autore avrà approvato.

### Fase 3 — Servizi e associazioni

Implementare operazioni applicative per i flussi approvati. Usare repository sostitutivi in memoria per verificare casi normali, alternative, errori, ordinamenti e associazioni senza database definitivo. Verificare in particolare che i contenuti siano globali, mentre le preferenze siano isolate per profilo, e che eliminare un profilo rimuova solo le sue preferenze. I sostituti implementano le stesse interfacce usate poi dalla persistenza, così i test e la UI attraversano i medesimi servizi.

### Fase 4 — Persistenza

Solo dopo la scelta dell'ORM e delle regole di integrità, implementare lo schema e le mappature. Verificare con dati temporanei la rilettura delle relazioni molti-a-molti, l'isolamento delle preferenze, i media senza sezioni, i film multi-genere e la cancellazione del profilo senza rimozione del catalogo. Non trattare i test in memoria come prova della correttezza del database.

### Fase 5 — Profili e interfaccia essenziale

Il prototipo attuale contiene la selezione e la creazione del profilo, ma va ancora ricompilato dopo una correzione e non è verificato. Usarlo come punto di partenza, riallineandolo ai servizi e alla persistenza scelti. Completare la UI dei profili con nome obbligatorio, foto facoltativa, modifica e cancellazione confermata; alla cancellazione eliminare solo profilo e preferenze. Verificare ogni flusso a livello di servizio/controller e manualmente nella schermata, includendo l'avatar segnaposto con iniziale.

### Fase 6 — Consultazione e ricerca

Preparare una vista di catalogo con un insieme piccolo e controllato di profili, media, generi e autori condivisi. Verificare prima l'ordinamento dei servizi/query; usare la schermata per controllare che le sezioni popolate precedano quelle vuote, che i titoli siano ordinati e che «Preferiti» dipenda dal profilo attivo. Implementare ricerca solo dopo aver definito i campi e le regole di corrispondenza.

### Fase 7 — Inserimento e gestione

Costruire verticalmente i flussi: creazione autonoma di genere usando zero o più film esistenti; creazione autonoma di autore usando zero o più film esistenti e soli nome obbligatorio; aggiunta di film (media) con file e titolo obbligatori, autore obbligatorio (creabile al volo con il solo nome), descrizione, locandina e generi facoltativi. Le scorciatoie di creazione di genere/autore sono solo nel form film. Offrire cartelle facoltative per video e locandine, lasciando scegliere qualunque percorso accessibile. Verificare i servizi senza UI, poi percorrere i form essenziali e le associazioni persistenti. Aggiornare relazione e istruzioni d'uso soltanto con comportamenti verificati.

## Regola d'aggiornamento

Ad ogni incremento aggiornare lo stato della fase qui, le feature corrispondenti in `FEATURES.md`, le decisioni didattiche in `REQUIREMENTS.md` solo quando pertinenti e i capitoli LaTeX toccati. Il README documenta installazione, avvio e uso effettivamente verificato; non è il registro dettagliato della roadmap.
