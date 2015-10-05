import subprocess

def runTest(f):
    subprocess.call(["cp", f, "textgen/MyLinkedList.java"])
    try:
        subprocess.check_call(["javac -cp .:/usr/share/java/junit4.jar textgen/*.java"], shell=True)
    except:
        return -2
    try:
        p = subprocess.Popen(["java", "-cp", ".:/usr/share/java/junit4.jar", "org.junit.runner.JUnitCore", "textgen.MyLinkedListTester"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        (out, err) = p.communicate()
        if "FAILURES" in out:
            return 1
        else:
            return 0
    except:
        return -1

def main():
    successCount = 0
    for i in range(1, 12):
        s = runTest("MyLinkedList%s.java" % i)
        if s == -1:
            print "{\"fractionalScore\": \"0.0\", \"feedback\": \"Uncaught runtime exception.\"}"
            return
        elif s == -2:
            print "{\"fractionalScore\": \"0.0\", \"feedback\": \"Error during compilation.\"}"
            return
        successCount += s

    s = runTest("MyLinkedList.java")
    if s == 0:
        successCount += 1
    elif s == -1:
        print "{\"fractionalScore\": \"0.0\", \"feedback\": \"Uncaught runtime exception.\"}"
        return


    if successCount == 12:
        print "{\"fractionalScore\": \"1.0\", \"feedback\": \"All test cases passed. Congrats!\"}"

    else:
        print "{\"fractionalScore\": \"%s\", \"feedback\": \"Some cases have not been covered. You may want to try iterating forwards and backwards through the list or accessing an out-of-bounds element.\"}" % (successCount / 12.0)

if __name__ == "__main__":
    main()
