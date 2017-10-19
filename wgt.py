import itertools

import wget

if __name__ == '__main__':
    url_template = 'http://cliware.meteo.ru/webchart/timeser/27612/SYNOPRUS/' \
                   'WndSp/' \
                   '4800/2880' \
                   '?colors=255,255,255;255,255,255;255,255,255;255,255,255;255,255,255;0,0,0;255,255,255' \
                   '&dates='
    days = [('01', '10'), ('11', '20'), ('21', '31')]
    months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    urls = [url_template + '2017-' + x + '-' + str(y) + ',2017-' + x + '-' + str(y + 1) for x, y in
            itertools.product(months, [t for t in range(1, 17)])]
    for url in urls:
        filename = wget.download(url, out=(url[-21:] + '.jpeg'))
        print(filename)
