# Procédure pas à pas pour installer et lancer le projet

### 1- Cloner le projet sur GitHub avec la commande :  
**`git clone https://github.com/WassimZerouta/ChaTop.git`**  

### 2- Ouvrir le projet dans un éditeur de texte tel que **Visual Studio Code**.

### 3- Assurez-vous d’avoir **MySQL** et **MySQL Workbench** installés sur votre PC.

### 4- Connectez-vous à **MySQL Workbench** et créez la base de données à partir du script fourni.

### 5- Retournez sur l’éditeur de texte.

### 6- Créez un fichier `.env` à la racine et remplissez :

```
DB_URL= ****
DB_USERNAME= ***
DB_PASSWORD= ***
```

Avec vos informations de connexion à la base de données.

### 7- Exécutez la commande suivante pour installer toutes les dépendances nécessaires :  
**`mvn clean install`**  

Puis démarrez le serveur avec la commande :  
**`mvn spring-boot:run`**  

### 8- Vous pouvez à présent suivre les instructions du **README du frontend** pour le lancer.

