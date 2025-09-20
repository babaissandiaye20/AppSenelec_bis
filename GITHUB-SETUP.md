# 🔧 Configuration GitHub pour Déploiement Automatique

## 📋 **Étapes pour activer le déploiement automatique :**

### **1. Accéder aux Secrets GitHub :**
1. Allez sur votre repository : https://github.com/Team-Maxit-221/AppSenelec
2. Cliquez sur **Settings** (en haut à droite)
3. Dans le menu de gauche, cliquez sur **Secrets and variables** → **Actions**

### **2. Ajouter les Secrets requis :**
azeii
#### **DOCKER_USERNAME**
- **Valeur** : `zigfreak`
- **Description** : Nom d'utilisateur Docker Hub

#### **DOCKER_PASSWORD**
- **Valeur** : Votre token Docker Hub
- **Description** : Token d'accès Docker Hub

#### **RENDER_API_KEY**
- **Valeur** : Votre API key Render
- **Description** : Clé API pour déployer sur Render

#### **RENDER_SERVICE_ID**
- **Valeur** : ID de votre service Render
- **Description** : ID du service à déployer

## 🔑 **Comment obtenir les tokens :**

### **Docker Hub Token :**
1. Connectez-vous sur [Docker Hub](https://hub.docker.com)
2. Allez dans **Account Settings** → **Security**
3. Cliquez sur **New Access Token**
4. Donnez un nom et copiez le token

### **Render API Key :**
1. Connectez-vous sur [Render.com](https://render.com)
2. Allez dans **Account** → **API Keys**
3. Cliquez sur **Create API Key**
4. Copiez la clé générée

### **Render Service ID :**
1. Dans votre dashboard Render
2. Cliquez sur votre service
3. L'ID est dans l'URL : `https://dashboard.render.com/web/srv-XXXXXXXXXXXXXX`

## 🚀 **Workflow automatique :**

Une fois configuré, voici ce qui se passera automatiquement :

### **Quand vous poussez sur `main` :**
1. ✅ GitHub Actions se déclenche
2. ✅ Build de l'image Docker
3. ✅ Push vers Docker Hub (`zigfreak/appsenelec:latest`)
4. ✅ Déploiement automatique sur Render

### **Quand vous créez une Pull Request :**
1. ✅ Build de l'image Docker
2. ✅ Push vers Docker Hub (pas de déploiement)
3. ✅ Tests automatiques

## 📊 **Monitoring :**

### **Voir les Actions GitHub :**
- Allez dans l'onglet **Actions** de votre repository
- Vous verrez tous les déploiements en cours

### **Voir les logs Render :**
- Dans votre dashboard Render
- Section **Logs** de votre service

## 🔧 **Commandes utiles :**

### **Tester le workflow localement :**
```bash
# Pousser un changement pour déclencher le workflow
git add .
git commit -m "test: trigger deployment"
git push origin main
```

### **Vérifier le statut :**
```bash
# Voir les dernières actions
# Dans GitHub : Actions tab
```

## 🚨 **Troubleshooting :**

### **Action échoue :**
1. Vérifiez que tous les secrets sont configurés
2. Vérifiez les logs dans l'onglet Actions
3. Vérifiez que Docker Hub et Render sont accessibles

### **Déploiement échoue :**
1. Vérifiez les variables d'environnement Render
2. Vérifiez la connexion à la base de données
3. Vérifiez les logs Render

---

**🎉 Une fois configuré, chaque push sur `main` déclenchera automatiquement un déploiement !**
