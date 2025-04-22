FROM node:23.10.0-alpine3.21
LABEL authors="Kazi Al Araf Kabir"

COPY package.json package-lock.json

RUN npm install

COPY src .

RUN npm run build