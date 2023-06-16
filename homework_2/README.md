# 2023-05-otus-spring-borisov
Учебный курс OTUS "Разработчик на Spring Framework"

Домашнее задание 2

Задание:
Приложение по проведению тестирования студентов (с самим тестированием)

Реализация:
1. Модель данных 
     - Рефакторинг названия классов в сторону упрощения
	 - Класс Answer содержит теперь признак правильности ответа
	 - Добавлены классы Person и TestRun
	 
2. Реализация слоев dao и service
     - QuestionDao: чтение и парсинг строки разделены на два шага
	 - QuestionService: 
          - getQuestions  - читает данные из dao
		  - showQuestions - отображает полученные данные 
		  
		  Добавлены методы
		  - runQuestions       - формирует на выходе класс TestRun
		  - showTestRunResults - отображает результаты по данным объекта TestRun
		  
	 - IOService - новый сервис ввода-вывода

3. Вся логика убрана в контекст. Вызывается инициализация контекста и запус метода одного bean.
     - Первое задание оставлено в классе Main в закомментированном состоянии

2. Unit тесты
     Добавлено два Unit теста класса QuestionsServiceImpl с использованием Mock QuestionsDao и ioService:
	 - тестирование метода getQuestions
	 - тестирование метода runQuestions