#실습6
import sys
fname = sys.argv[1]
newfname = sys.argv[2]
f1 = open(fname,'r')
f2 = open(newfname,'w')

for line in f1:
    f2.write(line)
f1.close()
f2.close()