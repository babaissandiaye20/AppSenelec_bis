# 🚀 Guide de Déploiement AppSenelec

## 📦 Images Docker Hub

Votre application AppSenelec est disponible sur Docker Hub :
- **Repository**: `zigfreak/appsenelec`
- **Tags disponibles**: `latest`, `v1.0.0`

## 🐳 Déploiement Rapide

### **Option 1 : Docker Run (Simple)**
```bash
# Lancer l'application avec la base de données Neon
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://ep-dawn-flower-a4jfwa7h-pooler.us-east-1.aws.neon.tech/senelec?sslmode=require&channel_binding=require" \
  -e SPRING_DATASOURCE_USERNAME="Freak_owner" \
  -e SPRING_DATASOURCE_PASSWORD="npg_HtBmg2zSwf9k" \
  zigfreak/appsenelec:latest
```

### **Option 2 : Docker Compose (Recommandé)**
```bash
# Développement avec PostgreSQL locale
docker-compose up -d

# Production avec base de données Neon
docker-compose -f docker-compose.prod.yml up -d
```

### **Option 3 : Script Automatisé**
```bash
# Déployer automatiquement (build + push)
./deploy-docker.sh
```

## 🔧 Configuration

### **Variables d'environnement importantes :**

| Variable | Description | Valeur par défaut |
|----------|-------------|-------------------|
| `SPRING_DATASOURCE_URL` | URL de la base de données | `jdbc:postgresql://postgres:5432/senelec` |
| `SPRING_DATASOURCE_USERNAME` | Nom d'utilisateur DB | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe DB | `password` |
| `SERVER_PORT` | Port de l'application | `8080` |
| `JAVA_OPTS` | Options JVM | `-Xmx512m -Xms256m` |

### **Ports exposés :**
- **8080** : Application AppSenelec
- **5432** : PostgreSQL (développement)
- **5050** : pgAdmin (développement)
aaz
## 🌐 Accès aux services

### **Application :**
- **URL**: `http://localhost:8080`
- az
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

### **pgAdmin (développement) :**
- **URL**: `http://localhost:5050`
- **Email**: `admin@senelec.com`
- **Mot de passe**: `admin123`

## 📋 Commandes utiles

### **Gestion des conteneurs :**
```bash
# Voir les conteneurs en cours
docker ps

# Voir les logs
docker logs app-senelec

# Arrêter les services
docker-compose down

# Redémarrer
docker-compose restart
```

### **Mise à jour de l'image :**
```bash
# Puller la dernière version
docker pull zigfreak/appsenelec:latest

# Redémarrer avec la nouvelle image
docker-compose pull && docker-compose up -d
```

### **Debug :**
```bash
# Accéder au conteneur
docker exec -it app-senelec /bin/sh

# Voir les variables d'environnement
docker exec app-senelec env
```

## 🔒 Sécurité

### **Production :**
- Utilisez des secrets pour les mots de passe
- Activez HTTPS avec un reverse proxy
- Configurez un firewall
- Utilisez des volumes pour les logs

### **Variables sensibles :**
```bash
# Créer un fichier .env
echo "DB_PASSWORD=votre_mot_de_passe_secret" > .env

# Utiliser dans docker-compose
docker-compose --env-file .env up -d
```

## 📊 Monitoring

### **Health Check :**
L'application inclut un health check automatique :
```bash
# Vérifier le statut
curl http://localhost:8080/actuator/health
```

### **Logs :**
```bash
# Suivre les logs en temps réel
docker-compose logs -f app-senelec

# Logs avec timestamps
docker-compose logs -f --timestamps app-senelec
```

## 🚨 Troubleshooting

### **Problème de connexion à la base de données :**
```bash
# Vérifier la connectivité
docker exec app-senelec ping ep-dawn-flower-a4jfwa7h-pooler.us-east-1.aws.neon.tech

# Tester la connexion JDBC
docker exec app-senelec java -cp /app/app.jar org.postgresql.Driver
```

### **Problème de mémoire :**
```bash
# Augmenter la mémoire JVM
docker run -e JAVA_OPTS="-Xmx1g -Xms512m" zigfreak/appsenelec:latest
```

### **Problème de port :**
```bash
# Vérifier les ports utilisés
netstat -tulpn | grep 8080

# Changer le port
docker run -p 9090:8080 zigfreak/appsenelec:latest
```

## 📈 Performance

### **Optimisations recommandées :**
- **Mémoire**: `-Xmx1g -Xms512m`
- **GC**: `-XX:+UseG1GC`
- **Container**: `-XX:+UseContainerSupport`

### **Monitoring :**
```bash
# Statistiques du conteneur
docker stats app-senelec

# Utilisation des ressources
docker exec app-senelec top
```

---

**🎉 Votre application AppSenelec est maintenant prête pour la production !**
