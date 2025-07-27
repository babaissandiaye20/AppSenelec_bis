#!/bin/bash

# Configuration
DOCKER_USERNAME="votre_username_dockerhub"
IMAGE_NAME="app-senelec"
VERSION="1.0.0"
FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:$VERSION"
LATEST_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:latest"

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🚀 Démarrage du build et push Docker pour AppSenelec${NC}"

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

# Demander le nom d'utilisateur Docker Hub si pas défini
if [ "$DOCKER_USERNAME" = "votre_username_dockerhub" ]; then
    echo -e "${YELLOW}⚠️  Veuillez modifier le script pour définir votre nom d'utilisateur Docker Hub${NC}"
    read -p "Entrez votre nom d'utilisateur Docker Hub: " DOCKER_USERNAME
    FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:$VERSION"
    LATEST_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:latest"
fi

echo -e "${GREEN}📦 Build de l'image Docker...${NC}"
docker build -t $FULL_IMAGE_NAME -t $LATEST_IMAGE_NAME .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Build réussi !${NC}"
    
    echo -e "${GREEN}📤 Push de l'image vers Docker Hub...${NC}"
    docker push $FULL_IMAGE_NAME
    docker push $LATEST_IMAGE_NAME
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Push réussi !${NC}"
        echo -e "${GREEN}🎉 Image disponible sur Docker Hub:${NC}"
        echo -e "${YELLOW}   - $FULL_IMAGE_NAME${NC}"
        echo -e "${YELLOW}   - $LATEST_IMAGE_NAME${NC}"
        
        echo -e "${GREEN}📋 Commandes utiles:${NC}"
        echo -e "${YELLOW}   docker run -p 8080:8080 $LATEST_IMAGE_NAME${NC}"
        echo -e "${YELLOW}   docker-compose up -d${NC}"
        echo -e "${YELLOW}   docker-compose -f docker-compose.prod.yml up -d${NC}"
    else
        echo -e "${RED}❌ Erreur lors du push vers Docker Hub${NC}"
        exit 1
    fi
else
    echo -e "${RED}❌ Erreur lors du build${NC}"
    exit 1
fi 