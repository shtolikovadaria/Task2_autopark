# Автопарк

Веб-система «Автопарк»

## Возможности
- добавление, редактирование и удаление машин
- поиск по марке, году выпуска и ФИО владельца
- сортировка по году выпуска (машины с пустой маркой игнорируются)
- счётчик записей в таблице
- гистограмма количества машин по дате постановки на учёт за 60 дней

## Стек
Java 21, Spring Boot, Spring Data JPA, MySQL 8, Thymeleaf, Bootstrap 5, Chart.js, Maven

## Запуск
1. Создать базу в MySQL:
   ```sql
   CREATE DATABASE autopark CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'autopark_user'@'localhost' IDENTIFIED BY 'ваш_пароль';
   GRANT ALL PRIVILEGES ON autopark.* TO 'autopark_user'@'localhost';
   ```
2. Создать файл `src/main/resources/application-local.properties`:
   ```properties
   spring.datasource.password=ваш_пароль
   ```
3. Запустить `AutoparkApplication` и открыть http://localhost:8080
