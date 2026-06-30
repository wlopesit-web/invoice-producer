#!/bin/bash
echo "=== 🛑 STEP 1: CLEANING PREVIOUS CONTAINERS ==="
docker rm -f invoice-producer-container 2>/dev/null

echo "=== 🚀 STEP 2: DISPARANDO GO-LIVE COM COFRE DE CREDENCIAIS ==="

docker run -d \
  --name invoice-producer-container \
  --restart always \
  -p 8083:8083\
  -e SERVER_PORT=8083 \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=147.15.123.230:9092 \
  -e JAVA_OPTS="-Xmx256M -Xms128M" \
  invoice-producer-app

echo "====================================================="
echo "🟢 ECOSSISTEMA INICIADO COM ACESSO AO ORACLE AUTONOMOUS!"
echo "====================================================="

