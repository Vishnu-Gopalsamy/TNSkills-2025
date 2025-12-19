import csv
import datetime
cleared = []

with open("INPUT.csv",'r',newline='') as f:
    a= csv.DictReader(f)
    for i in a:
        if('CORRUPT_DATA' in i.values()):
            continue
        cleared.append(i)

for i in cleared:
    
