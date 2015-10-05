while True:
    try:
        word = raw_input()
        print word * 2
    except EOFError:
        break
