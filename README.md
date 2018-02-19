# tika-server-wconfig

### install
    git clone https://github.com/asulikeit/tika-server-wconfig.git
    cd tika-server-wconfig
    git checkout develop
    mvn clean install

### start server
    cp target/tika-server-wconfig-0.0.1-SNAPSHOT.jar ./
    java -jar tika-server-wconfig-0.0.1-SNAPSHOT.jar
    
### usage
    curl -v http://localhost:8080/tika/pdf?file=/home/user/Documents/ORG.pdf
    curl -v http://localhost:8080/tika/scanpdf?file=/home/user/Documents/SCAN.pdf&lang=kor
 