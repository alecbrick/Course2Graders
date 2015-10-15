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
            print "{\"fractionalScore\": 0.0, \"feedback\": \"Uncaught runtime exception: %s\"}" % err
            return
        elif s == -2:
            print "{\"fractionalScore\": 0.0, \"feedback\": \"Error during compilation: %s\"}" % err
            return

        if i == 1:
            feedback += "** Test #1: Lower bounds on get..."
            if s == 0:
                feedback += "FAILED. Make sure you can't get an element at too low of an index. "
            else:
                feedback += "PASSED. "
        elif i == 2:    
            feedback += "** Test #2: Upper bounds on get..."
            if s == 0:
                feedback += "Make sure you can't get an element at too high of an index. "
            else:
                feedback += "PASSED. "
        elif i == 3:
            feedback += "** Test #3: Lower bounds on set..."
            if s == 0:
                feedback += "FAILED. Check that you can't set an element at too low of an index. "
            else:
                feedback += "PASSED. "

        elif i == 4:
            feedback += "** Test #4: Upper bounds on set..."
            if s == 0:
                feedback += "FAILED. Check that you can't set an element at too high of an index. "
            else:
                feedback += "PASSED. "
        elif i == 5:
            feedback += "** Test #5: Lower bounds on remove..."
            if s == 0:
                feedback += "FAILED. Ensure that you can't remove an element at too low of an index. "
            else:
                feedback += "PASSED. "
        elif i == 6:
            feedback += "** Test #6: Upper bounds on remove..."
            if s == 0:
                feedback += "FAILED. Ensure that you can't remove an element at too high of an index. "
            else:
                feedback += "PASSED. "
        elif i == 7:
            feedback += "** Test #7: Lower bounds on add..."
            if s == 0:
                feedback += "FAILED. Make sure that you can't add an element at too low of an index. "
            else:
                feedback += "PASSED. "
        elif i == 8:
            feedback += "** Test #8: Correctly updating 'next' pointer on add..."
            if s == 0:
                feedback += "FAILED. No checks to see if 'next' pointers are updated correctly on add. "
            else:
                feedback += "PASSED. "
        elif i == 9:
            feedback += "** Test #9: Upper bounds on add..."
            if s == 0:
                feedback += "FAILED. Make sure that you can't add an element at too high of an index. "
            else:
                feedback += "PASSED. "
        elif i == 10:
            feedback += "** Test #10: Correctly updating 'next' pointer on remove..."
            if s == 0:
                feedback += "FAILED. No checks to see if 'next' pointers are updated correctly on remove. "
            else:
                feedback += "PASSED. "
        elif i == 11:
            feedback += "** Test #11: Setting null element..."
            if s == 0:
                feedback += "FAILED. Check that you can't set an element to null. "
            else:
                feedback += "PASSED. "
        elif i == 12:
            feedback += "** Test #12: Adding null element..."
            if s == 0:
                feedback += "FAILED. Check that you can't add a null element. "
            else:
                feedback += "PASSED. "
        successCount += s

    feedback += "** Test #13: Running on correct implementation..."
    s, err = runTest("MyLinkedList.java")
    if s == 0:
        successCount += 1
        feedback += "PASSED. "
    elif s == 1:
        feedback += "FAILED. Your tester failed against the correct implementation."
    elif s == -1:
        print "{\"fractionalScore\": 0.0, \"feedback\": \"Uncaught runtime exception: %s.\"}" % err
        return


    if successCount == 13:
        feedback += "All tests passed. Congrats!"
    else:
        feedback = "Some tests failed. Check the following: %s" % feedback

    print "{\"fractionalScore\": %s, \"feedback\": \"%s\"}" % (successCount / 13.0, feedback)

if __name__ == "__main__":
    main()
