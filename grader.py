import sys
def main(correct, student):
    answers, resp = map(lambda x: open(x).read().rstrip().split('\n'), [correct, student])

    penalty = 1.0 / len(answers)
    score = 1.0
    num = 0
    
    if len(answers) != len(resp):
        score = 0.0
        feedback = "Incorrect number of answers."
    else:
        for i in xrange(len(answers)):
            if answers[i] != resp[i]:
                score -= penalty
                num += 1
        if num > 0:
            feedback = "Your solution failed %s test case%s. Try again!" % (num, '' if num == 1 else 's')
        else:
            feedback = "Congrats! All test cases passed."

    jsonOutput = "{" + "\"fractionalScore\":" + str(score) + "," + "\"feedback\":" + "\"" + str(feedback) + "\"" + "}"
    print jsonOutput
    
if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2])
