import sys

from typing import List


def lexer(code: str) -> List[str]:
    """
    The lexer (responsible for lexical analysis) creates a list of tokens from the source code, e.g.:

    "sub 2 sum 1 3 4"

    to

    ["sub", "2", "sum", "1", "3", "4"]

    :param code: (str)
    :return: tokens (list)
    """
    # if no string passed, exit 1 and return empty list
    if type(code) != str:
        sys.exit(1)

    # split code into tokens
    code = code.split(" ")

    # strip whitespaces
    code = map(lambda s: s.strip(), code)

    # filter out empty tokens
    code = filter(lambda s: len(s) > 0, code)

    # return list of tokens
    return list(code)


if __name__ == "__main__":
    # read from stdin (enables unix piping)
    for line in sys.stdin:
        # write to stdout (i.e. `print(...)`)
        print(lexer(line))
