def main():
    f = open("errorfile", "r")
    msg = f.read()

    msg = msg.split("\n")[0]

    print "{\"fractionalScore\": 0.0, \"feedback\": \"Compile error: %s\"}" % msg

if __name__ == "__main__":
    main()
