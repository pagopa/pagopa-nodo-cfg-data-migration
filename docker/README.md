# Docker Environment 🐳
 - Clone `https://github.com/oracle/docker-images` project
 - Download "OracleDB" zip v19.3.0, the downloaded file will be `LINUX.ARM64_1919000_db_home`. 
 - Move this zip in folder `/docker-images/OracleDatabase/SingleInstance/dockerfiles/19.3.0`.
 - As described [here](https://github.com/oracle/docker-images/blob/main/OracleDatabase/SingleInstance/README.md), run `./buildContainerImage.sh -e -v 19.3.0 -o '--build-arg SLIMMING=false'
 - From this repository in `docker` folder, execute the commands:
     ```
     docker run --name db-oracle \
       -p 1521:1521 -p 5500:5500 \
       -e ORACLE_SID=ORCLCDB \
       -e ORACLE_PDB=ORCLPDB1 \
       -e ORACLE_PWD=oracle \
       -e ORACLE_CHARACTERSET=AL32UTF8 \
       oracle/database:19.3.0-ee
     ```
 - Connect with your favourite client on local container for Oracle, using:
   - JDBC Connection: jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
   - Username: system
   - Password: oracle
 - Execute `init_oracle.sql` script in the client
 - Move in `db/liquibase/` folder
 - Run `sh runLiquibase.sh` script
 - From the repository `pagopa-infra`, in folder `/src/psql/local` execute the script `sh postgresql_local.sh 3.23.0 0`
 - Connect with your favourite client on local container for PostgreSQL, using:
    - JDBC Connection: jdbc:postgresql://localhost:5432/nodo
    - Username: cfg
    - Password: cfg
