# Data Processor App

### Functional Requirement:

- Program to fetch `News` from external API for every `x` seconds configured in the properties

    API - https://newsapi.org/

- Create endpoints to view the latest records and delete records by name

## Getting Started

Instruction to set up application locally for running, compiling and testing

### Prerequisites

1. Java 11
2. Maven

To Download dependencies, Run below command from project root
```shell
mvn install
```

### Build

Build - Run below command from project root
```shell
mvn compile
```

## Run tests

```shell
mvn test
```

## Deployment

To run from the command line

```shell
mvn package
java -jar target/data-processor.jar
```

To run using Docker Container

1 - Build image
```shell
docker build -t data-processor .
```
2 - Run container
```shell
docker run -d -p 8081:8080 data-processor
```
3 - Run container with logs
```shell
docker run -it -p 8081:8080 data-processor
```
## Output

1 - Get last `x` record

Request

    API - news/articles?limit=3
```shell
curl --location --request GET 'http://localhost:8080/news/articles?limit=3'
```

Response

```json
[
  {
    "sourceId": "the-irish-times",
    "sourceName": "The Irish Times",
    "author": "Peter Doyle",
    "title": "Man jailed for killing three people while driving at 225km/h fails in sentence appeal - The Irish Times",
    "description": "Keith Lennon recorded vidoes and posted them on Snapchat while 105km/h over limit",
    "content": "A man who killed three people when he drove into the back of another car as he made Snapchat videos while travelling at 225km/h has failed in an attempt to have his nine-year jail sentence reduced. \r… [+3312 chars]",
    "createdDate": "2022-04-01"
  },
  {
    "sourceId": "cbc-news",
    "sourceName": "CBC News",
    "author": null,
    "title": "Pope Francis apologizes to Indigenous delegates for 'deplorable' abuses at residential schools - CBC News",
    "description": "Pope Francis has apologized for the conduct of some members of the Roman Catholic Church in Canada's residential school system, following a week of talks with First Nations, Inuit and Métis delegations.",
    "content": "Pope Francis has apologized for the conduct of some members of the Roman Catholic Church in Canada's residential school system, following a week of talks with First Nations, Inuit and Métis delegatio… [+2966 chars]",
    "createdDate": "2022-04-01"
  },
  {
    "sourceId": "cnn",
    "sourceName": "CNN",
    "author": "By Simone McCarthy, <a href=\"/profiles/travis-caldwell\">Travis Caldwell</a>, <a href=\"/profiles/helen-regan\">Helen Regan</a>, Sana Noor Haq, Sara Spary and <a href=\"/profiles/adrienne-vogt\">Adrienne Vogt</a>, CNN",
    "title": "Russia invades Ukraine: Live updates - CNN",
    "description": "Russian forces may be regrouping in Belarus, Ukrainian officials said. Heavy shelling has been reported in eastern Ukraine amid an apparent shift by Russia to redirect military efforts to the Donbas region. Follow here for live news updates.",
    "content": "Estonia has proposed that part of energy payments to Russia be put aside to pay for the costs of Ukraines recovery, the Estonian Minister of Foreign Affairs Eva-Maria Liimets told journalists on Frid… [+2059 chars]",
    "createdDate": "2022-04-01"
  }
]
```

1 - Delete records by name

Request

    API - news/articles?source_name=CNN

```shell
curl --location --request DELETE 'http://localhost:8081/news/articles?source_name=CNN'
```

Response

```text
Deleted 10 records
```