#실습7
import sys
wcount = 0
f = open(sys.argv[1],'r')
linelist = f.readlines()
for line in linelist:
    buf = line.split()
    wcount+=len(buf)
print("# of lines : ",len(linelist))
print("#of words : ",wcount)

