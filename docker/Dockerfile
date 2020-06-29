FROM mariadb
WORKDIR /docker-entrypoint-initdb.d
COPY . . 
RUN chmod +x /docker-entrypoint-initdb.d/entrypoint.sh
