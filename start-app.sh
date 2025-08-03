#!/bin/bash

echo "Starting E-Commerce Shopping Cart Application..."
echo

echo "Starting Backend (Spring Boot)..."
cd backend
gnome-terminal -- bash -c "mvn spring-boot:run; exec bash" &

echo "Waiting for backend to start..."
sleep 10

echo "Starting Frontend (React)..."
cd ../frontend
gnome-terminal -- bash -c "npm start; exec bash" &

echo
echo "Both servers are starting..."
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:3000"
echo

read -p "Press any key to continue..."
