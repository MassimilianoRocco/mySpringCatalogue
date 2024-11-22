# INFO

Base per CRUD con Sring MVC(per la parte web) + JPA && Hibernate (per la parte di persistenza dati)


Si parte aggiungendo le dipendenze nel POM e sistemando il JRE a JAVASE-21 tramite build.

Creo l'AppConfig e l'AppInitializer 
-> l'AppInitializer sarà una classe che estenderà AbstractAnnotationConfigDispatcherServletInitializer e farà l'override dei metodi richiesti. Nel secondo andremo ad inserire la classe in cui si trova la configurazione dell'app. Nel terzo, invece, setteremo "/" così da far gestire tutte le richieste a questa servlet.
-> l'AppConfig invece andrà annotata come Configuration(per dire che contiene le configurazioni), come EnableWebMVC(per importare e configurare Spring MVC) e con @ComponentScan andremo ad indicare dove troverà i controller.
A questo punto andremo a creare i due Bean per l'utilizzo del template engine FreeMarker, specificando prefix, suffix(l'estensione dei file che andremo a creare) e, nel secondo bean, configurando il path in cui andranno i nostri file .ftl (ovviamente andremo a creare in src le cartelle che abbiamo indicato qui).

Con questo si conclude la configurazione dell'app per il Presentation Layer.

  

Adesso configureremo la parte di ORM/Connessione al database.


Sempre nell'AppConfig quindi andremo inizialmente ad aggiungere un ulteriore Bean per la creazione del Datasource.
Qui andremo a definire i dati di accesso per il nostro database, ma lo faremo tramite un file di properties esterno. In tal senso dovremo aggiungere un'ulteriore annotation alla classe AppConfig, ovvero @PropertySource, specificando il classpath. E siccome specifichiamo il classpath, il file andrà in main/resources.
A questo punto creiamo in AppConfig un oggetto di tipo Environment(org.springframework) e lo annotiamo con @Autowired. In questo modo potremo utilizzare il metodo getRequiredProperty dell'oggetto Environment nella configurazione del Datasource.

A questo punto, andremo a creare il DB.


A questo punto dobbiamo configurare i Bean per l'utilizzo di JPA e Hibernate(si andrà ad annotare la classe AppConfig con @EnableTransactionManagement)
-> metodo che ritorna un oggetto LocalContainerEntityManagerFactoryBean
-> metodo che ritorna un oggetto PlatformTransactionManager(con l'entityManagerFactory in ingresso, ovvero il metodo precedente).


A questo punto creeremo le entities, i model.
Avremo quindi un package model con le classi(annotate con @entity) Prodotto, 
Andremo ovviamente ad aggiungere le tabelle e colonne opportune nel DB.

A questo punto si andranno ad implementare i metodi per l'accesso in lettura e scrittura al DB.
Creaiamo quindi una nuova interfaccia DAO (data access object) con relativi metodi che andremo ad implementare nella classe DaoImpl (in cui ovviamente andremo ad aggiungere come prima cosa l'entityManager, annotato con @PersistenceContext, che verrà poi utilizzato nei vari metodi).
Andremo, in quest'ultima, quindi, ad implementare i metodi di CRUD e/o altri metodi definiti nel DAO ricordando che tutte le operazioni che modificano i dati nel database necessitano di una transazione (quindi quei metodi li annoteremo con @Transactional) per garantire che i cambiamenti siano applicati solo se l'intera operazione ha successo. Senza una transazione, queste operazioni fallirebbero o potrebbero lasciare il database in uno stato inconsistente. I metodi di sola lettura, invece, non necessitano di transazioni.


A questo punto potremo andare a definire il nostro ProdottoController, annotato con @Controller e @RequestMappin, e tutti i metodi per i path utili al suo interno. Andranno ovviamente create, di conseguenza, anche le relative views.
Nel Controller avremo bisogno dell'istanza del ProdottoDao. Per poterla instanziare dovremo per prima cosa creare in AppConfig la definizione del Bean ProdottoDao che ritornerà un oggetto ProdottoDaoImpl(così da poter utilizzare effettivamente i metodi relativi implementati in precedenza).