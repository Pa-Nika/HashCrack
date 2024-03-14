set DOCKERFILE_PATH=demo/Dockerfile


set MANAGER_PORT=8181
set MANAGER_JAR_FILE=manager/build/libs/*.jar

set WORKER_PORT=8182
set WORKER_JAR_FILE=worker/build/libs/*.jar

docker build -t ldfcz/dis-manager -f %DOCKERFILE_PATH% --build-arg EXPOSE_PORT=%MANAGER_PORT% --build-arg JAR_FILE=%MANAGER_JAR_FILE% .
docker build -t ldfcz/dis-worker -f %DOCKERFILE_PATH% --build-arg EXPOSE_PORT=%WORKER_PORT% --build-arg JAR_FILE=%WORKER_JAR_FILE% .

cd demo
docker-compose up -d
