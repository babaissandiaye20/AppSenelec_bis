# AppSenelec - API de Gestion Électrique

## 📋 Description

AppSenelec est une API REST développée avec Spring Boot pour la gestion d'un système de recharge électrique. Cette application permet de gérer les compteurs électriques, les achats de crédit électrique, les tarifs et les logs d'activité.

## 🚀 Fonctionnalités Principales

### 1. **Gestion des Compteurs**
- Création et gestion de compteurs électriques
- Suivi de la consommation en kWh
- Recharge de compteurs via codes de recharge
- Statut des compteurs (ACTIVE, INACTIVE)

### 2. **Système d'Achat de Crédit**
- Achat de crédit électrique avec différents tarifs
- Génération automatique de codes de recharge uniques (20 chiffres)
- Calcul automatique des kWh basé sur le montant et le tarif
- Suivi du statut des transactions

### 3. **Gestion des Tarifs**
- Configuration des tarifs électriques
- Calcul automatique des kWh selon la formule : `(montant achat × kWh tarif) ÷ montant tarif`

### 4. **Système de Logs**
- Enregistrement automatique de toutes les actions
- Traçabilité complète des opérations
- Historique des activités par utilisateur

## 🛠️ Technologies Utilisées

- **Backend**: Spring Boot 3.5.4
- **Base de données**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Documentation API**: Swagger/OpenAPI
- **Validation**: Bean Validation
- **Build Tool**: Maven

## 📦 Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- PostgreSQL
- Variables d'environnement configurées

## ⚙️ Installation et Configuration

### 1. **Cloner le projet**
```bash
git clone <url-du-repo>
cd AppSenelec
```

### 2. **Configurer la base de données**
- Créer une base de données PostgreSQL
- Configurer les variables d'environnement :
  ```bash
  export DB_PASSWORD=votre_mot_de_passe
  ```

### 3. **Configuration de la base de données**
Le fichier `application.yml` contient la configuration :
```yaml
spring:
  datasource:
    url: jdbc:postgresql://ep-dawn-flower-a4jfwa7h-pooler.us-east-1.aws.neon.tech/senelec?sslmode=require&channel_binding=require
    username: Freak_owner
    password: ${DB_PASSWORD}
```

### 4. **Lancer l'application**
```bash
mvn spring-boot:run
```

L'application sera accessible sur : `http://localhost:8080`

## 📚 Documentation API

### Accès à la documentation Swagger
- **URL**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

## 🔌 Endpoints Principaux

### Compteurs (`/api/compteurs`)
- `POST /api/compteurs` - Créer un compteur
- `GET /api/compteurs` - Lister tous les compteurs (avec pagination)
- `GET /api/compteurs/{id}` - Obtenir un compteur par ID
- `PUT /api/compteurs/{id}` - Mettre à jour un compteur
- `DELETE /api/compteurs/{id}` - Supprimer un compteur
- `POST /api/compteurs/recharge` - Recharger un compteur

### Achats (`/api/achats`)
- `POST /api/achats` - Créer un achat de crédit
- `GET /api/achats` - Lister tous les achats (avec pagination)
- `GET /api/achats/{id}` - Obtenir un achat par ID
- `PUT /api/achats/{id}` - Mettre à jour un achat
- `DELETE /api/achats/{id}` - Supprimer un achat

### Tarifs (`/api/tarifs`)
- `POST /api/tarifs` - Créer un tarif
- `GET /api/tarifs` - Lister tous les tarifs (avec pagination)
- `GET /api/tarifs/{id}` - Obtenir un tarif par ID
- `PUT /api/tarifs/{id}` - Mettre à jour un tarif
- `DELETE /api/tarifs/{id}` - Supprimer un tarif

### Logs (`/api/logs`)
- `POST /api/logs` - Créer un log
- `GET /api/logs` - Lister tous les logs (avec pagination)
- `GET /api/logs/{id}` - Obtenir un log par ID
- `PUT /api/logs/{id}` - Mettre à jour un log
- `DELETE /api/logs/{id}` - Supprimer un log

## 📝 Exemples d'Utilisation

### 1. Créer un compteur
```bash
curl -X POST http://localhost:8080/api/compteurs \
  -H "Content-Type: application/json" \
  -d '{
    "referenceNumber": "COMP001",
    "userId": 1
  }'
```

### 2. Créer un tarif
```bash
curl -X POST http://localhost:8080/api/tarifs \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1000.0,
    "kwh": 100.0
  }'
```

### 3. Effectuer un achat
```bash
curl -X POST http://localhost:8080/api/achats \
  -H "Content-Type: application/json" \
  -d '{
    "numero": "ACH001",
    "amount": 1500.0,
    "reference": "COMP001",
    "tarifId": 1
  }'
```

### 4. Recharger un compteur
```bash
curl -X POST http://localhost:8080/api/compteurs/recharge \
  -H "Content-Type: application/json" \
  -d '{
    "codeRecharge": "12345678901234567890",
    "referenceCompteur": "COMP001"
  }'
```

## 🔧 Paramètres de Pagination

Tous les endpoints de liste supportent la pagination avec les paramètres :
- `page` : Numéro de page (défaut: 0)
- `size` : Taille de la page (défaut: 10)
- `sortBy` : Champ de tri (défaut: "id")
- `sortDir` : Direction du tri "asc" ou "desc" (défaut: "asc")

Exemple :
```
GET /api/compteurs?page=0&size=20&sortBy=creationDate&sortDir=desc
```

## 🏗️ Architecture du Projet

```
src/main/java/com/AppSenelec/
├── controller/          # Contrôleurs REST
├── service/            # Services métier
├── repository/         # Accès aux données
├── model/             # Entités JPA
├── dto/               # Objets de transfert
├── mapper/            # Mappers DTO ↔ Entity
├── helper/            # Classes utilitaires
├── exception/         # Gestion d'erreurs
├── config/            # Configuration
└── util/              # Utilitaires
```

## 🔒 Sécurité et Validation

- Validation des données avec Bean Validation
- Gestion des erreurs centralisée
- Logs automatiques de toutes les actions
- Codes de recharge uniques et sécurisés

## 🐛 Gestion des Erreurs

L'API retourne des réponses standardisées avec :
- Code de statut HTTP approprié
- Message d'erreur descriptif
- Horodatage de l'erreur
- Détails techniques si nécessaire

## 📊 Monitoring

- Logs automatiques de toutes les requêtes
- Traçabilité complète des opérations
- Métriques de performance disponibles

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence [MIT](LICENSE).

## 📞 Support

Pour toute question ou problème :
- Ouvrir une issue sur GitHub
- Consulter la documentation Swagger
- Vérifier les logs de l'application

---

**AppSenelec** - Simplifiez la gestion électrique ! ⚡ # Senelec-clone-facture
