import subprocess

def runTest(f):
    subprocess.call(["cp", f, "textgen/MyLinkedList.java"])
    p = subprocess.Popen(["javac -cp .:/usr/share/java/junit4.jar textgen/*.java"], shell=True, stderr=subprocess.PIPE)
    (out, err) = p.communicate()
    if err != "":
        return -2, err
    try:
        p = subprocess.Popen(["java", "-cp", ".:/usr/share/java/junit4.jar", "org.junit.runner.JUnitCore", "textgen.MyLinkedListTester"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        (out, err) = p.communicate()
        if err != "":
            return -1, err
        if "FAILURES" in out:
            return 1, ""
        else:
            return 0, ""
    except Exception as e:
        return -1, e

def main():
    successCount = 0
    feedback = ""
    for i in range(1, 13):
        s, err = runTest("MyLinkedList%s.java" % i)
        if s == -1:
            print "{\"fractionalScore\": 0.0, \"feedback\": \"Uncaught runtime exception:\n%s\"}" % err
            return
        elif s == -2:
            print "{\"fractionalScore\": 0.0, \"feedback\": \"Error during compilation:\n%s\"}" % err
            return

        if s == 0:
            if i == 1:
                feedback += "Make sure you can't get an element at too low of an index. "
            elif i == 2:
                feedback += "Make sure you can't get an element at too high of an index. "
            elif i == 3:
                feedback += "Check that you can't set an element at too low of an index. "
            elif i == 4:
                feedback += "Check that you can't set an element at too high of an index. "
            elif i == 5:
                feedback += "Ensure that you can't remove an element at too low of an index. "
            elif i == 6:
                feedback += "Ensure that you can't remove an element at too high of an index. "
            elif i == 7:
                feedback += "Make sure that you can't add an element at too low of an index. "
            elif i == 8:
                feedback += "How can you check to see if the 'next' pointer is updated on creation of a new node? "
            elif i == 9:
                feedback += "Make sure that you can't add an element at too high of an index. "
            elif i == 10:
                feedback += "How can you check to see if the 'next' pointer is updated on the removal of a node? "
            elif i == 11:
                feedback += "Check that you can't set an element to null. "
            elif i == 12:
                feedback += "Check that you can't add a null element. "
        successCount += s

    s, err = runTest("MyLinkedList.java")
    if s == 0:
        successCount += 1
    elif s == 1:
        feedback += "Your tester failed against the correct implementation."
    elif s == -1:
        print "{\"fractionalScore\": 0.0, \"feedback\": \"Uncaught runtime exception:\n%s.\"}" % err
        return


    if successCount == 13:
        print "{\"fractionalScore\": 1.0, \"feedback\": \"All test cases passed. Congrats!\"}"

    else:
        print "{\"fractionalScore\": %s, \"feedback\": \"%s\"}" % (successCount / 13.0, feedback)

if __name__ == "__main__":
    main()
