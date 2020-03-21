import sys, os

def usage():
    print("python3 cjb.py 应用名称 [应用参数]")
    exit(1)

if len(sys.argv) < 2:
    usage()

main = f'cn.yhsb.cjb.application.{sys.argv[1]}'
args = ' '.join(sys.argv[2:])
cmd = f'mvn exec:java -Dexec.mainClass={main} -q -Dexec.args="{args}"'
# print(cmd)
os.system(cmd)
