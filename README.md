# 2023-05-otus-spring-borisov
Учебный курс OTUS "Разработчик на Spring Framework"


Домашнее задание 1

Задание:
Приложение по проведению тестирования студентов - только вывод вопросов и вариантов ответа (если имеются)

Реализация:
1. Модель данных 
     - Answer   - ответ (с оценкой ответа)
                  ToDo: добавить в файл оценку ответа и его заполнение при разборе файла
     - TestUnit - комбинация вопроса и вариантов ответа
                  ToDo: подразумеваются поля: - необходимость отображения ответов в интерфейсе
                                              - одиночный/множественный выбор ответов
     - TestBook - опросник (набор TestUnit)

2. Unit тесты
     Добавлен Unit тест класса Answer
