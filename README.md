# Elasticsearch RU-EN Phonetic Translit Plugin

![CI](https://github.com/f1monkey/elasticsearch-ru-en-translit-plugin/workflows/CI%20—%20Test%20&%20Build/badge.svg)

**Phonetic transliteration filter** for Russian and English text.
Enables **sound-based search** across languages.

In example, this words will match
* **"extension"** / **"экстеншн"** / **"экстеншен"**
* **"daily"** / **"дейли"** / **"дэйли"**
* **"chlorophyll"** / **"хлорофилл"**

Perfect for:
- E-commerce product search
- Brand name matching
- Multilingual catalogs
- Fuzzy phonetic search

---

## Installation

* Run command
```bash
sudo /usr/share/elasticsearch/bin/elasticsearch-plugin install https://github.com/f1monkey/elasticsearch-ru-en-translit-plugin/releases/download/v1.0.0/elasticsearch-ru-en-translit-plugin-1.0.0.zip
```
* Restart elasticsearch

## Usage

```json
PUT /my_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_analyzer": {
          "tokenizer": "standard",
          "filter": ["ru_en_translit"]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": { "type": "text", "analyzer": "my_analyzer" }
    }
  }
}

POST /my_index/_analyze
{
  "analyzer": "my_analyzer",
  "text": "extension экстеншен экстеншон экстеншн"
}
```

Result:
```json
{
  "tokens": [
    {
      "token": "ekstenshn",
      "start_offset": 0,
      "end_offset": 9,
      "type": "<ALPHANUM>",
      "position": 0
    },
    {
      "token": "ekstenshn",
      "start_offset": 10,
      "end_offset": 19,
      "type": "<ALPHANUM>",
      "position": 1
    },
    {
      "token": "ekstenshn",
      "start_offset": 20,
      "end_offset": 29,
      "type": "<ALPHANUM>",
      "position": 2
    },
    {
      "token": "ekstenshn",
      "start_offset": 30,
      "end_offset": 38,
      "type": "<ALPHANUM>",
      "position": 3
    }
  ]
}
```

## Build from Source

```bash
git clone https://github.com/f1moneky/elasticsearch-ru-en-translit-plugin.git
cd elasticsearch-ru-en-translit-plugin
./gradlew clean build
```

## Run tests

```bash
./gradlew test
```

## Licence

MIT