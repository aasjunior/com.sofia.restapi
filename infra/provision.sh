#!/bin/bash
echo "==> Atualizando..."
sudo apt update -y
sudo apt upgrade -y

echo "==> Instalando dependências..."
sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common

echo "==> Instalando o Docker..."
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io
# sudo apt-get install -y docker.io
sudo systemctl enable docker
sudo systemctl start docker
sudo usermod -aG docker vagrant

echo "==> Instalando o Docker Compose..."
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
sudo docker-compose --version

echo "==> Instalando o Git..."
sudo apt install git -y
git --version
git config --global user.name "username"
git config --global user.email "username@email.com"

echo "==> Configurando back-end..."
git clone https://github.com/aasjunior/com.sofia.restapi.git
cd com.sofia.restapi/infra
sudo docker-compose up


# echo "==> Instalando o Maven..."
# sudo apt install maven -y
# mvn -version
# # (Opcional) Adicionar MAVEN_HOME ao .bashrc 
# echo 'export MAVEN_HOME=/usr/share/maven' >> ~/.bashrc 
# echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> ~/.bashrc 
# # Recarregar .bashrc 
# source ~/.bashrc

# echo "==> Configurando back-end..."
# git clone https://github.com/aasjunior/com.sofia.restapi.git
# cd com.sofia.restapi
# git checkout docker
# git fetch
# git checkout docker
# git pull origin docker
# cd sofia-api
# sudo docker-compose up
