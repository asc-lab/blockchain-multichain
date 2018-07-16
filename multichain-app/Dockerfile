# Builder
FROM node:8.9 as builder
MAINTAINER ASC-LAB

COPY . /opt/app
WORKDIR /opt/app

RUN npm install

RUN sed -i "s|app_url: \".*\"|app_url: \"\"|" src/environments/environment.ts

RUN npm run build


# Runner
FROM nginx
MAINTAINER ASC-LAB

COPY --from=builder /opt/app/dist/* /usr/share/nginx/html

COPY ./nginx-app.conf /etc/nginx/conf.d/default.conf
