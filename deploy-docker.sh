#!/bin/bash

# Configuration
DOCKER_USERNAME="zigfreak"
IMAGE_NAME="appsenelec"
VERSION="1.0.0"
FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME"

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}🚀 Déploiement Docker pour AppSenelec${NC}"
echo -e "${YELLOW}Repository: $FULL_IMAGE_NAME${NC}"
echo ""

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker n'est pas installé. Veuillez l'installer d'abord.${NC}"
    exit 1
fi

# Vérifier si l'utilisateur est connecté à Docker Hub
if ! docker info &> /dev/null; then
    echo -e "${RED}❌ Vous n'êtes pas connecté à Docker. Veuillez vous connecter avec 'docker login'${NC}"
    exit 1
fi

echo -e "${GREEN}📦 Build de l'image Docker...${NC}"
docker build -t $FULL_IMAGE_NAME:latest .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Build réussi !${NC}"
    
    echo -e "${GREEN}🏷️  Création des tags...${NC}"
    docker tag $FULL_IMAGE_NAME:latest $FULL_IMAGE_NAME:$VERSION
    
    echo -e "${GREEN}📤 Push des images vers Docker Hub...${NC}"
    echo -e "${YELLOW}   Pushing latest...${NC}"
    docker push $FULL_IMAGE_NAME:latest
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Push latest réussi !${NC}"
        
        echo -e "${YELLOW}   Pushing v$VERSION...${NC}"
        docker push $FULL_IMAGE_NAME:$VERSION
        
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✅ Push v$VERSION réussi !${NC}"
            
            echo ""
            echo -e "${GREEN}🎉 Déploiement terminé avec succès !${NC}"
            echo ""
            echo -e "${BLUE}📋 Images disponibles sur Docker Hub:${NC}"
            echo -e "${YELLOW}   - $FULL_IMAGE_NAME:latest${NC}"
            echo -e "${YELLOW}   - $FULL_IMAGE_NAME:$VERSION${NC}"
            echo ""
            echo -e "${BLUE}📋 Commandes utiles:${NC}"
            echo -e "${YELLOW}   # Lancer l'application${NC}"
            echo -e "${YELLOW}   docker run -p 8080:8080 $FULL_IMAGE_NAME:latest${NC}"
            echo ""
            echo -e "${YELLOW}   # Avec Docker Compose${NC}"
            echo -e "${YELLOW}   docker-compose up -d${NC}"
            echo ""
            echo -e "${YELLOW}   # Production avec base de données Neon${NC}"
            echo -e "${YELLOW}   docker-compose -f docker-compose.prod.yml up -d${NC}"
            echo ""
            echo -e "${BLUE}🌐 Documentation API:${NC}"
            echo -e "${YELLOW}   http://localhost:8080/swagger-ui.html${NC}"
            echo -e "${YELLOW}   http://localhost:8080/api-docs${NC}"
            
        else
            echo -e "${RED}❌ Erreur lors du push de la version v$VERSION${NC}"
            exit 1
        fi
    else
        echo -e "${RED}❌ Erreur lors du push de latest${NC}"
        exit 1
    fi
else
    echo -e "${RED}❌ Erreur lors du build${NC}"
    exit 1
fi 