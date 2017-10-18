# Meteo

This program gets data from [http://cliware.meteo.ru/](http://cliware.meteo.ru/) as a chart.
The purpose is to get this data in CSV format (and some other formats, e. g. TXT).

[http://cliware.meteo.ru/webchart/timeser/](http://cliware.meteo.ru/webchart/timeser/) сервер с данными
 
27612/ № станции (ВДНХ)

SYNOPRUS/ синоптический архив

TempDb/ температура в срок

1200/1000? размер 1200 на 1000 (лучше использовать 2000 на 1200)

[но все же лучше использовать 4800 на 2880]

colors=255,255,255;225,240,255;50,50,50;50,50,50;50,160,130;35,0,255;240,255,185& - цвета графиков

[используем colors=255,255,255;255,255,255;255,255,255;255,255,255;255,255,255;0,0,0;255,255,255]

dates=2013-01-01,2013-01-10 - начальная и конечная дата периода (Я использую 1 -10, 11-20, 21-31)

Если в месяце меньше 31 дня - не страшно писать 31 - система выдаст что есть фактически.
 
TempDb/ -темп в срок (24 в сутки)

TempDbMax/ темп. наиб. в сут. (1-2 в сутки)

TempDbMin/ темп. наим. в сут. (1-2 в сутки)

TempDb24/ темп. сред. в сут. (1 в сутки)

TempDP/ точка росы

PresSt/ давл. на станции

PresSl/ давл. прив. к уровню моря

WWterm/ код текущ. погоды

Precip/ код наличия осадков

CldTot/ полная облачность

CldLow/ нижняя облачность

WndDir/ напр. ветра

WndDir/ скор. ветра

winds/ напр. и скор. ветра



## Authors

* **Dmitry Kuklin** - *some work* - [Diamindes](https://github.com/Diamindes)
* **Anna Tsalapova** - *some work* - [acanna](https://github.com/acanna)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

No license.

## Acknowledgments

* Acknowlegee
