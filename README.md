# HabitApp

A habit tracker built with Spring Boot, Vaadin and PostgreSQL. Contains external API calls to NASA and ZenQuotes, with demo/free version keys.

WIP; made for my personal use basically but feel free to try :D Check HabitController.java for api calls if you're curious
## Run it

You only need [Docker Desktop](https://www.docker.com/products/docker-desktop/) and Git. No Java, Maven or database install required.

```
git clone <your-repo-url>
cd HabitApp
docker compose up --build
```

Wait a few minutes on the first run, then open **http://localhost:8080**.

## Stop it

Press `Ctrl+C`, then:

```
docker compose down
```

## Uninstall

```
docker compose down -v --rmi all
```

Then delete the folder. This removes the database, images and containers.
