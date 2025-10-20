# 🏫 UndoSchool - Course Search & Suggest API 
### *(Spring Boot + Elasticsearch + Docker)*

This project is a Spring Boot application that provides **course search and auto-suggestion** functionality using **Elasticsearch**.  
It indexes sample course data and allows users to search, filter, and get suggestions with fuzzy and autocomplete capabilities.

## Before You Begin
Make sure your folder looks something like this:
```css
UndoSchool/
 ┣ src/
 ┃ ┣ main/java/com/Hayat/UndoSchool/
 ┃ ┗ main/resources/sample-courses.json
 ┣ docker-compose.yml
 ┣ pom.xml
 ┣ README.md

```

#### (😊 Personally I had used the Browser URL Method for the checking API + Postman API)

---

### 1️⃣ Launch Elasticsearch using Docker

This project uses Elasticsearch for indexing and searching course data.  
Make sure you have **Docker** and **Docker Compose** installed.

Start Elasticsearch:

```bash
docker-compose up -d
```

Check if it's running:
```html
BROWSER URL: http://localhost:9200

OR TERMINAL: curl http://localhost:9200
```

Expected Output:
```json
{
  "name": "elasticsearch",
  "cluster_name": "docker-cluster",
  "tagline": "You Know, for Search"
}
```

📘 **Explanation:**  
The `docker-compose.yml` file contains configuration to spin up Elasticsearch at `localhost:9200`.  
This is required before running the Spring Boot application.

---


### 2️⃣ Build and Run the Spring Boot Application

Make sure Maven is installed.

```bash
mvn clean install
mvn spring-boot:run
```

You Should See logs like:
```bash
 Indexed 50 courses into Elasticsearch!
Tomcat started on port 8080
```

📘 **Explanation:**  
This step builds your Spring Boot app and starts it.  
It will automatically connect to Elasticsearch using the configuration in `application.properties`.
---

### 3️⃣ Populate the Index with Sample Data

When the application starts, it automatically loads data from `src/main/resources/sample-courses.json` and indexes them into Elasticsearch.

You can verify using:
```bash
BROWSER URL: http://localhost:9200/courses/_count?pretty

OR TERMINAL: curl http://localhost:9200/courses/_count?pretty
```

Expected Output:
```json
{
  "count": 50,
  "_shards": { "total": 1, "successful": 1, "failed": 0 }
}
```

📘 **Explanation:**  
Your `CourseIndexService` runs automatically (`CommandLineRunner`) and loads all JSON data into Elasticsearch.  
This confirms your setup is correct.

---
### 4️⃣ Test the REST Endpoints

Base URL: `http://localhost:8080/api/search?q=science`

#### 🔍 Search by Keyword
```bash
curl "http://localhost:8080/api/search?q=science"
```

#### 🎯 Filter by Category and Price
```bash
curl "http://localhost:8080/api/search?q=science&category=Science&minPrice=50&sort=priceAsc"
```

#### 👶 Filter by Age
```bash
curl "http://localhost:8080/api/search?minAge=8&maxAge=12"
```

#### 💰 Price Range Search
```bash
curl "http://localhost:8080/api/search?minPrice=100&maxPrice=500"
```

#### 📅 Filter by Upcoming Session Date
```bash
curl "http://localhost:8080/api/search?startDate=2025-06-15"
```

📘 **Explanation:**  
Each of these endpoints triggers queries in your `CourseSearchService`, applying filters like price, category, and date.

---

### 5️⃣ (Bonus) Autocomplete and Fuzzy Search

#### 🔠 Autocomplete (Suggestions)
```bash
curl "http://localhost:8080/api/search/suggest?q=phy"
```

Expected Output
```json
["Physics & Engineering Principles","Physics in Action"]
```

#### 🔡 Fuzzy Search (Handles Typos)
```bash
curl "http://localhost:8080/api/search?q=chemstry"
```

Expected Output:
Returns result related to **Chemistry** courses even if mispelled

📘 **Explanation:**  
This section demonstrates your advanced features like **fuzzy matching** and **autocomplete** using the Elasticsearch Completion Suggester.

---

---

## 🙋‍♂️ Personal Note

This project was built as part of the UndoSchool Internship assignment.  
I learned a lot about integrating **Spring Boot** with **Elasticsearch**, handling data indexing, and implementing **fuzzy search** and **autocomplete**.

Thank you for reviewing my work! 😊  
– *Umar Hayat*

