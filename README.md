# Instalación
```
 git clone https://github.com/Naraku86/ed-ava-api-testing.git
 ```
 
 # Compilación
 
 ```
 cd ed-ava-api-testing/
 mvn clean dependency:copy-dependencies package
 ```
 
 # Ejecución
 ```
 java -Dava.node.url=http://127.0.0.1:9650 -Dava.test.number=200 -Dava.verbose=true -classpath target/dependency/json-20200518.jar:target/AvalancheFlashTest-1.0-SNAPSHOT.jar com.ed.criptos.avalanche.testing.Main
 ```
 