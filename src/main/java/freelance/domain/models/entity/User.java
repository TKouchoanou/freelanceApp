package freelance.domain.models.entity;

import freelance.domain.exception.DomainException;
import freelance.domain.models.objetValue.*;
import freelance.domain.security.Auth;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//TODO trop de logique dans cette seul classe , problème faudra soit créer une class Auth avec les données users et employee pour avoir accès aux roles ou alors créer des services de domaines pour récupérer ses informations pour dealer avec les règles domaines. Un service seraient nécessaire seulement pour les entités avec trop de logique.
// Pour les methodes  faire des methodes a effet de bords en privée et n'executer l'effet de bord que par une methode publique qui vérifie les règles métiers et ces si ces methodes public demande trop de logique qu'il faut les transformer en service de domaine
// dans le cas d'une transformation en service de domaine selon les possibilités du langage il faudrait que les modèles du domaine soit retourner aux uses cases en readonly
// les uses cases ne doivent pas faire directement des effets de bords, ils doivent passer par le domaine metier pour le faire.
// pour les langages ne supportant pas l'immutabilité on peut par exemple créer des interfaces avec seulement les accesseurs et ces derniers seront implémenter par les entités de domaines ainsi les services de domaines vont les retourner comme cet interface, les uses cases n'y auront accès donc qu'en lecture ou alors il faudra créer des modèles en lecture seul à retourner aux usesCases
// les usesCases ne doivent déclencher que des successions d'effets de bords, mais chaque effet de bords est en effet realised par la couche domaine.
// les usesCases sont des chefs d'orchestre qui valid les demandes de la couche de présentations et la cohérence des demandes et déclenchent les actions nécessaires pour satisfaire les demandes des couches de presentations
// les entités(agregats) dont qui contiennent  toutes leurs propres logiques métiers pour être cohérent (sans l'intervention d'un service de domaine ) peuvent être modifier du dehors et donc peuvent être persister dans depuis useCase
// par contre les entités dont a logique metier est dans un service de modèle et donc seulement accessible en lecture au UseCase ne peut pas être persister par un useCase car les useCase ne doivent pas disposer du modèle d'écriture. pour celà il faut casser les interfaces de repository en lectures et en écritures même s'ils sont implémenter ensemble les parties en lectures seront retourner en immutable (interface ou modèle immutable) et les parties en écriture doivent retourner des modèles (mutable)
// le principe est que seul des agrégats cohérents vise à vis des règles métiers doivent être persister et la couche metiers doit se débrouiller pour n'offrir que des possibilités garantissant cette coherence
// la partie authorisation de la logique metier peut être déplacer du domaine vers le use case pour décharger le domaine , mais là il faut avoir conscience que les règles d'authorisation peuvent être  disperser dans cette couche et des bugs d'autorisations peuvent se produire.
// cependant toutes les autres règles de cohérence doivent être dans la couche metiers C'est son rôle .
// j'estime que si l'autorisation ne se fait pas la couche de domaine, elle doit être fait dans une couche très proche du domaine
// de façon à ce qu'on ne puisse avoir une couche qui a accès au domaine et ignore les règles d'autorisation qui font partir des règles metiers.
// On peut aussi penser autorisation et commande et donc aucune commande ne doit être trop riche et de façon unitaire on doit pouvoir établir un tableau de command avec ces conditions d'autorisation et demander aux couche de presentation de verifier l'autorisation avant d'exécuter la commande d'un client.
// Ainsi on fait totalement confiance au controller et on lui délègue le role de garantir l'autorisation s'il échoue dans un role on sais que c'est lui qui doit être incriminé. On a donc conscience que les aspect autorisations du metier sont encapsuler dans dans les couches de présentation et que le metier et le useCase se décharge de cette tache.
// comme on peut ne pas faire confiance aux couche de présentation et decider de déléguer la vérification des autorisations aux usesCases ou alors au métiers
// implementer les règles d'autorisations dans le metiers ou les usesCases ne doit pas empêcher les couches de presentations et de useCase de de prendre des précautions pour éviter les exceptions d'autorisation du métiers
// mettre les règles d'autorisation dans le metiers garantit que peut importe la couche de usesCas qui se greffe au metiers les règles d'autorisations ne seront pas violer ais tout est question de choix et les choix sont justifiés selon les cas.
// comment le metiers peut verifier des authorisations sans s'encombrer de technique et de trop de responsabilité (authentification par exemple). Le metier ayant besoin de connaitre l'utilisateurs peut définir un modèle abstrait (interface à implementer) ou concret avec les methodes et les données dont il a besoin pour verifier la logique autorisations c'est ainsi qu'un type Auth et un type User peuvent exister dans un domaine les deux references un utilisateur mais existe pour des problèmes différents. l'agrégat User existe pour vérifier la cohérence de l'utilisateur a persisté et est donc orienter écriture cohérence alors que l'Auth peut contenir plus de données que l'utilisateur et répondre au besoin d'autorisation du métiers.
// Dans ce cas l'agrégats utilisateur ne doit pas être utiliser pour controller les autorisations mais plutôt Auth.
// L'agrégat utilisateur represent les données de nos utilisateurs  tandis que l'Auth représente l'utilisateur actuellement connecté. Ainsi on ne mélange plus les choses.
// il peut être interessant que l'Auth soit abstrait  afin de permettre par exemple à une seule implementation de répondre au besoin du domaine en terme d'autorisation et aussi de répondre au besoin d'un framework comme springSecurity en implementant l'interface UserDetails
// le domaine ne doit pas connaitre cette interface userDetails de springSecurity , mais elle peut s'en inspirer pour concevoir ses methodes car au fond cette interface décrit un minimum nécessaire générale pour gerer les authorisations, mais  ne s'adapte pas détails des metiers, on peut s'en inspirer pour construire nos types puisque la sécurité c'est générique ( voir orienté aspect) mais pour s'inviter dans le code metiers elle doit y être inviter par le metiers qui lui donne les habits (types, interfaces)  qu'elle doit porter pour y entrer
//
/*
Si authorization nécessite des requêtes api ou base de données, on peut distinguer deux cas selon les exigences.
premier cas : dans une même sessions ont doit faire appels aux apis ou DB plusieurs fois car le temps d'une session les réponses des api ou DB peuvent être périmés (besoin de refresh instantanée)  ou que cela nécessite trop de requête pour récupérer toutes les informations d'autorisation pouvant répondre à touts les cas d'autorisations or on en a besoin que d'un peut-être ( bref un load eager qui serait trop couteux et pas tout temps utile)
Deuxième cas :  les données  nécessaires ne périment pas pendant une session et il n'est pas très couteux de les récupérer
Dans le premier cas il faut plutôt utiliser un service d'autorisation définit de façon abstraite par le metier pour répondre à ses besoins. si ces services ne peuvent pas être implementer en étant agnostique des bases de données ou des api sous-jacent, il faudrait l'implémenter dans une couche infrastructure (données ou une autre couche bas niveau du genre)
Le metier doit être agnostique des technologies. Le metier peut lui même l'implémenter en déléguant les parties non agnostic à une couche d'infrastructure qui doit implementer l'interface mise à disposition par le metiers. Tout service qu'on peut rendre au metiers de l'extérieure doit se faire par interface.
Dans le deuxième cas il faut utiliser un type abstrait ou non avec etat (un modèle (classe par exemple) avec des données et methodes n'ayant pas besoin d'accéder au reseal).
*/

/*
Classe entité (ou model) garde des données cohérent et peut éventuellement avoir du comportement pour garder coherent des données qu'elles gardent pour répondre à un besoin. Les modèles aggregates des métiers sont les seules modèles mutables et persistable. Il a vocation à être serializable et ne doit jamais contenir un service (même abstrait, car au runtime l'abstrait devient un concret) et encore pire un service qui doit accede au reseal, on mélangerait donc metiers et technique
si la construction valide d'une telle classe nécessite l'accès au réseau pour des validations, sa construction doit être déléguée à un service du domaine et il faudrait s'arranger à ce qu'elle soit la seule à les construire. Si on a besoin d'une bibliothèque (non connu du domaine) mais dont la logique de validation est utile pour le domaine, on peut utiliser une couche anti-corruption pour que l'objet à construire l'utilise en tant que methode static. Mais il ne faut jamais rajouter un service (même sans accès réseau) ou une classe utilitaire, à une classe charger de garder des données comme les modèles de lecture et d'écriture.
En utilisant les classes avec des methodes statics des métiers, on résout deux problèmes. Premier problème, on ne couple pas directement et n'importe comment une bibliothèque avec notre code metier, la bibliothèque nous sert via un périmètre déterminer qu'on ouvre grâce à nos classes utilitaires static, le côté static nous garantis qu'on ne gardera pas un etat lié à cette classe utilitaire.
C'est la logique à appliquer pour une bibliothèque utilitaire de validation comme commons-validator pour valider par exemple un iban ou un mail dans un constructeur. Si on a besoin d'un type dans notre metiers par exemple Money et qu'il existe une bibliothèque éprouvée qui implement bien ce type et qu'on ne veut pas réinventer la roue, on a deux choix :
soit-on créer une surcouche à la bibliothèque et on utilise la surcouche dans notre code qui utilise implementation de la bibliothèque derrières. Notre surcouche serait comme un proxy personnaliser selon nos besoins de la bibliothèque.
Si on maitrise moins ce que fait la bibliothèque, mais qu'il le fait bien qu'on pourrait le faire et que ça peut necessiter des maintenances qu'on ne veut pas gérer et qu'il y a une communauté connu dynamique stable et sérieuse derrière cette bibliothèque,
on peut directement l'utiliser et accepter qu'elle soit coupler à notre code métier, car elle est fiable et nous fait gagner du temps. C'est des compromis qu'on peut faire. Dans le cadre du projet facture, on peut acccepter coupler Money à notre code metiers pour économiser le temps nécessaire à sa proxification.

Une Class service est sans états
*/
/*
Et si la couche de presentation veut utiliser la routine de validation de la couche metiers pour bien répondre aux attentes de la couche metiers ?
la couche de presentation n'est pas adjacent à la couche métier, elle ne doit donc pas la connaitre, le metier doit lui être masqué par la couche useCase
elle ne doit pas savoir comment la couche useCase se débrouille pour répondre à ses commands.
Ainsi si la couche de presentation a besoin de cette routine,
la solution est que les développeurs de la couche métiers externalise sous forme de module cette routine adapté à la couche metiers.
Ainsi la couche metiers utilisera cette routine (faite pour elle) comme dépendances et vu que c'est une dépendance maison,
on suppose que tant que l'équipe qui maintient le metier pourra la maintenir au service du metier.
La couche métier l'aura comme dépendance de même que la couche presentation, ainsi ce n'est pas via la couche de metiers que la couche de presentation l'utilisera.

*/

/*
La technique a des possibilités et c'est au buisness selon ces exigences de dealer avec les possibilités de la technique
Les meilleurs techniciens doit pouvoir proposer au buisness les meilleures options pour répondre à ces exigences.
Les options ont un cout et elle doivent être prise en compte pour voir comment combiner les moyens et exigences du buisness avec les possibilités techniques
Une analyse de projet doit pouvoir étudier la faisabilité d'un projet et voyant s'il peut avoir mariage entre les moyens, les exigences et les possibilités techniques. Montrer les options techniques qui permettant d'atteindre les objectifs avec leurs couts et inclure pour chaque, options les faiblesses et les forces vis à vis des exigences.
Ainsi le buisness peut annuler son projet ou revoir à la baisse ses objectifs pour s'adapter à une option
*/

public class User extends Auditable{
    UserId id;

    public User(UserId id, String firstName, String lastName, Email email, Password passWord, Set<Profile> profiles, boolean isActive,LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(createdDate, updatedDate);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.isActive = isActive;
        profiles.forEach(this::addProfile);
    }

    public User(UserId id, String firstName, String lastName, Email email, Password passWord, Set<Profile> profiles, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.isActive = isActive;
        profiles.forEach(this::addProfile);
    }
    public User(String firstName, String lastName, String email, String passWord, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
        this.passWord = new Password(passWord);
        this.isActive = isActive;
    }
    String firstName;
    String lastName;
    Email email;
    Password passWord;
    Set<Profile> profiles;
    boolean isActive;

    public void update(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new Email(email);
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPassWord(String passWord) {
        this.passWord = new Password(passWord);
    }

    private User addProfile(Profile profile) {
        if(profiles==null)
            profiles= new HashSet<>();
        if(!profiles.contains(profile)){
            this.profiles.add(profile);
        }
       return this;
    }
    public User addEmployeeProfile(Employee employee){
        //en effet, une fois qu'un employé est créé on peut déclencher cette action
        //tant qu'on nous envoie l'employé correspondant a cet utilisateur  on peut lui ajouter le profil Salarie, pareil pour un freelance
        //only employee can create employee profile
        //TODO salary=employee ubiquitous langage must be respect
        // TODO currentUser mus be auth
        if(this.hasProfile(Profile.SALARY)){
            return this;
        }

        if(!employee.isThisUser(this)){
            //to implement rule who tell that employeeProfile must be created before to add user a role
            throw new DomainException(" the employee is not this user ");
        }

       return this.addProfile(Profile.SALARY);
    }
    public User addFreeLanceProfile(Freelance freelance){
        if(this.hasProfile(Profile.FREELANCE)){
            return this;
        }
        if(!freelance.isThisUser(this)){
            throw new DomainException(" the employee is not this user ");
        }

        return this.addProfile(Profile.FREELANCE);
    }
    public User removeProfile(Profile profile) {
        if(profiles==null)
            profiles= new HashSet<>();
        if(profiles.contains(profile)){
            this.profiles.remove(profile);
        }
       return this;
    }

  public  boolean hasProfile(Profile profile){
        if(this.profiles!=null){
            return this.profiles.contains(profile);
        }
        return false;
  }
    public String getFirstName() {
        return firstName;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Profile> getProfiles() {
        return Collections.unmodifiableSet(profiles);
    }

    public String getLastName() {
        return lastName;
    }

    public Password getPassWord() {
        return passWord;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserId getId() {
        return id;
    }
}
