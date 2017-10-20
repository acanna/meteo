#!/usr/bin/python3.6

import platform
import os
import wget


def get_next_date(date):
    year, month, day = date
    if day == 31:
        day = 1
        if month == 12:
            month = 1
            year += 1
    else:
        day += 1
    return (year, month, day)


def get_next_date_2(date):
    return get_next_date(get_next_date(date))


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
    station_code, param, start_date, end_date = input().split()
    img_path = 'GraphImages/{}/'.format(param)
    if 'win' in platform.system().lower():
        img_path = 'GraphImages\\{}\\'.format(param)

    if not os.path.exists(img_path):
        os.makedirs(img_path)


    url_template = 'http://cliware.meteo.ru/webchart/timeser/' \
                   + station_code + '/SYNOPRUS/' + param + '/4800/2880' \
                                                           '?colors=255,255,255;255,255,255;255,255,255;255,255,' \
                                                           '255;255,255,255;0,0,0;255,255,255' \
                                                           '&dates='
    start_year, start_month, start_day = [int(s) for s in start_date.split('-')]
    end_year, end_month, end_day = [int(s) for s in end_date.split('-')]

    next_year, next_month, next_day = start_year, start_month, start_day
    cur_year, cur_month, cur_day = start_year, start_month, start_day

    while date_greater(end_year, end_month, end_day, next_year, next_month, next_day):
        cur_year, cur_month, cur_day = next_year, next_month, next_day
        next_year, next_month, next_day = get_next_date_2((cur_year, cur_month, cur_day))
        days = '{}-{}-{},{}-{}-{}'.format(cur_year, cur_month, cur_day, next_year, next_month, next_day)
        filename = img_path + '{}-{}-{}_{}-{}-{}.jpg'.format(cur_year, cur_month, cur_day, next_year, next_month,
                                                             next_day)
        wget.download(url_template + days, out=filename)

