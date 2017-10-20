# /usr/bin/python3.6

import platform

import wget


def get_next_date(year, month, day):
    if day == 31:
        day = 1
        if month == 12:
            month = 1
            year += 1
    else:
        day += 1
    return year, month, day


# date1 > date2
def date_greater(year1, month1, day1, year2, month2, day2):
    if year1 > year2:
        return True
    if year1 < year2:
        return False
    if month1 > month2:
        return True
    if month1 < month1:
        return False
    if day1 > day2:
        return True
    return False


if __name__ == '__main__':
    img_path = 'Images/'
    if 'win' in platform.system().lower():
        img_path = 'Images\\'

    station_code, param, start_date, end_date = input().split()
    url_template = 'http://cliware.meteo.ru/webchart/timeser/' \
                   + station_code + '/SYNOPRUS/' + param + '4800/2880' \
                                                           '?colors=255,255,255;255,255,255;255,255,255;255,255,' \
                                                           '255;255,255,255;0,0,0;255,255,255' \
                                                           '&dates='
    start_year, start_month, start_day = list(map(int, (start_date.split('-'))))
    end_year, end_month, end_day = list(map(int, (end_date.split('-'))))
    print(end_day + 1)
    next_year, next_month, next_day = get_next_date(start_year, start_month, start_date)
    cur_year, cur_month, cur_day = start_year, start_month, start_date

    while not date_greater(next_year, next_month, next_day, end_year, end_month, end_day):
        days = '{}-{}-{},{}-{}-{}'.format(cur_year, cur_month, cur_day, next_year, next_month, next_day)
        filename = img_path + '{}-{}-{}_{}-{}-{}.jpg'.format(cur_year, cur_month, cur_day, next_year, next_month,
                                                             next_day)
        wget.download(url_template + days, out=filename)
        cur_year, cur_month, cur_day = next_year, next_month, next_day
        next_year, next_month, next_day = get_next_date(cur_year, cur_month, cur_day)
