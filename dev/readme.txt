docker-compose up -d mongo rabbitmq

user rabbit

rabbitmq.uri.serial.bus=amqp://public:public@192.168.0.105:5672/%2F

docker build . -t my-web-app -f ../dev/Dockerfile
