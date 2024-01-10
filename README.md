# Informações do Projeto

## Build no Sonar Local

mvn clean install sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${USUARIO} -Dsonar.password=${SENHA}