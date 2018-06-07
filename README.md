# LogsParser
Моя реализация задания:
На входе имеется файл с логами. logs.zip.

Каждая строка из файла имеет вид:
2017-12-21 14:01:47.893 INFO   Module: 'XXX' Operation: 'OperationX' Execution time: 9999ms

Написать программу, которая будет анализировать этот файл и выводить статистику:
N самых длинных операций для каждого модуля, длительность операции и время, когда операция завершилась. Операции должны быть отсортированы в порядке убывания времени выполнения.

Требования:
1. Возможность сериализовать и десериализовать результаты анализа в/из файл(а). Загружать и показывать результаты из файла:
Например: java -jar homework.jar -load saved_results.dat

2. Указать параметром кол-во операций, стартовую дату и кол-во часов, начиная от стартовой даты. В этом временном диапазоне и проводить анализ.
Например:
java -jar homework.jar -n 5 -from "2017-12-21 10:56:59" -range 4

Программа должна выводить на экран результаты в данном формате:

Top 5 operations, starting from "2017-12-21 13:51:02.002" for 4 hours
XXX Module:
	OperationX 9999 ms, finished at 2017-12-21 13:51:46.120
OperationY 9998 ms, finished at 2017-12-21 15:51:46.121
OperationZ 9997 ms, finished at 2017-12-21 14:11:46.122
OperationN 9996 ms, finished at 2017-12-21 18:51:46.123
OperationK 9995 ms, finished at 2017-12-21 13:51:46.124
YYY Module:
	OperationX 9999 ms, finished at 2017-12-21 14:51:46.12
	...
...	 