# 🚀 Déploiement Render avec Docker Hub

## 📋 **Options de déploiement automatique :**

### **Option 1 : Déploiement depuis Docker Hub (Recommandé)**

#### **1. Configuration Render :**
1. Allez sur [Render.com](https://render.com)
2. Créez un nouveau **Web Service**
3. Choisissez **"Deploy from Docker image"**
4. Entrez l'image : `zigfreak/appsenelec:latest`

#### **2. Variables d'environnement :**
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://ep-dawn-flower-a4jfwa7h-pooler.us-east-1.aws.neon.tech/senelec?sslmode=require&channel_binding=require
SPRING_DATASOURCE_USERNAME=Freak_owner
SPRING_DATASOURCE_PASSWORD=npg_HtBmg2zSwf9k
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
JAVA_OPTS=-Xmx1g -Xms512m -XX:+UseG1GC
```

#### **3. Configuration avancée :**
- **Health Check Path**: `/api-docs`
- **Auto-Deploy**: ✅ Activé
- **Branch**: `main` ou `master`

### **Option 2 : Déploiement depuis GitHub (Workflow complet)**

#### **1. Secrets GitHub requis :**
```bash
DOCKER_USERNAME=zigfreak
DOCKER_PASSWORD=votre_token_dockerhub
RENDER_API_KEY=votre_api_key_render
RENDER_SERVICE_ID=votre_service_id_render
```

#### **2. Workflow automatique :**
- Push sur `main` → Build Docker → Push Docker Hub → Déploy Render
- Pull Request → Build Docker → Push Docker Hub (pas de déploiement)

## 🔄 **Processus automatique :**

### **Quand vous poussez sur GitHub :**
1. ✅ GitHub Actions se déclenche
2. ✅ Build de l'image Docker
3. ✅ Push vers Docker Hub (`zigfreak/appsenelec:latest`)
4. ✅ Render détecte la nouvelle image
5. ✅ Déploiement automatique sur Render

### **Quand vous poussez directement sur Docker Hub :**
1. ✅ `./deploy-docker.sh` ou `docker push`
2. ✅ Render détecte la nouvelle image
3. ✅ Déploiement automatique sur Render

## ⚙️ **Configuration Render avancée :**

### **Health Check :**
```yaml
healthCheckPath: /api-docs
healthCheckTimeout: 300
```

### **Build Command (si build local) :**
```bash
docker build -t appsenelec .
```

### **Start Command :**
```bash
docker run -p 8080:8080 appsenelec
```

## 📊 **Monitoring Render :**

### **Logs en temps réel :**
```bash
# Dans le dashboard Render
# Ou via API
curl -H "Authorization: Bearer $RENDER_API_KEY" \
  https://api.render.com/v1/services/$SERVICE_ID/logs
```

### **Statut du service :**
```bash
curl -H "Authorization: Bearer $RENDER_API_KEY" \
  https://api.render.com/v1/services/$SERVICE_ID
```

## 🔧 **Variables d'environnement Render :**

### **Obligatoires :**
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://ep-dawn-flower-a4jfwa7h-pooler.us-east-1.aws.neon.tech/senelec?sslmode=require&channel_binding=require
SPRING_DATASOURCE_USERNAME=Freak_owner
SPRING_DATASOURCE_PASSWORD=npg_HtBmg2zSwf9k
```

### **Optionnelles :**
```bash
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
JAVA_OPTS=-Xmx1g -Xms512m -XX:+UseG1GC
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

## 🚨 **Troubleshooting :**

### **Problème de connexion DB :**
```bash
# Vérifier les logs Render
# Vérifier les variables d'environnement
# Tester la connexion depuis Render
```

### **Problème de mémoire :**
```bash
# Augmenter JAVA_OPTS
JAVA_OPTS=-Xmx2g -Xms1g -XX:+UseG1GC
```

### **Problème de port :**
```bash
# Render utilise le port 8080 par défaut
# Vérifier SERVER_PORT=8080
```

## 📈 **Optimisations Render :**

### **Performance :**
- **Instance Type**: Standard (2GB RAM)
- **Auto-Scaling**: Activé si nécessaire
- **Health Checks**: Configurés

### **Coût :**
- **Free Tier**: 750h/mois
- **Paid**: $7/mois pour instance standard

## 🔐 **Sécurité :**

### **Secrets :**
- Utilisez les variables d'environnement Render
- Ne committez jamais les mots de passe
- Utilisez les secrets GitHub pour les tokens

### **HTTPS :**
- Render fournit HTTPS automatiquement
- Certificats SSL gratuits

## 📋 **Commandes utiles :**

### **Déployer manuellement :**
```bash
# Via Render Dashboard
# Ou via API
curl -X POST \
  -H "Authorization: Bearer $RENDER_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{"clearCache": "do_not_clear"}' \
  https://api.render.com/v1/services/$SERVICE_ID/deploys
```

### **Vérifier le statut :**
```bash
curl -H "Authorization: Bearer $RENDER_API_KEY" \
  https://api.render.com/v1/services/$SERVICE_ID
```

---

**🎉 Avec cette configuration, chaque push sur Docker Hub déclenchera automatiquement un déploiement sur Render !** 