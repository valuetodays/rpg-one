echo %~dp0
cd /d %~dp0
mvn clean install -DskipTests && cd rpg-game && mvn clean package -DskipTests assembly:single


