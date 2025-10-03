#!/bin/bash

echo "Starting PostgreSQL container..."

# Запуск Docker Compose в фоновом режиме
docker-compose up -d

# Пауза для запуска контейнера (2 секунды, можно увеличить при необходимости)
sleep 2

# Проверка статуса контейнеров
echo "Checking container status..."
docker ps -a

# Подключение к PostgreSQL через docker exec
echo "Connecting to PostgreSQL..."
docker exec -it hwhibernate-postgres-1 psql -U postgres -d users_db

echo "Done!"
read -p "Press Enter to exit..."