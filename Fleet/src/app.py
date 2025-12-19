import csv
import datetime
cleared = []

with open("Input.csv", newline='', encoding='utf-8') as infile, \
         open("Output.csv", 'w', newline='', encoding='utf-8') as outfile:
    reader= csv.DictReader(infile)
    writer= csv.DictWriter(outfile, fieldnames=reader.fieldnames)
    writer.writeheader()
    for row in reader:
        if('CORRUPT_DATA' in row.values()):
            continue
        try:
            order_id = int(row['Date'])
            order_date = datetime.datetime.strptime(row['OrderItemDate'], '%Y-%m-%d').date()
            writer.writerow(row)
        except Exception as e:
                continue