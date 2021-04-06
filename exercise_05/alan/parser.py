import sys
from typing import Dict, Union


def parser(tokens: str) -> Dict[str, Union[str, int]]:
    """
    The parser turns the list of tokens into an Abstract Syntax Tree (AST).
    In the following, recursive descent parsing (https://en.wikipedia.org/wiki/Recursive_descent_parser)
    is used to produce the AST top-down.

    The process turns the tokens

    ["sub", "2", "sum", "1", "3", "4"]

    into the AST

     sub
     / \
    2  sum
       /|\
      1 3 4

    where each leaf in the tree is a (json) dictionary, this results in an AST as follows:

    {
        "type": "op",
        "value": "sub",
        "expr": [
            {"type": "num", "value": 2},
            {
                "type": "op",
                "value": "sum", "expr": [
                    {"type": "num", "value": 1},
                    {"type": "num", "value": 3},
                    {"type": "num", "value": 4}]
            }
        ]
    }

    The grammar used to parse the tokens is roughly:

    num := 0-9+ (only positive!)
    op := sum | sub | div | mul
    expr := num | op expr+

    :param tokens: (list)
    :return: ast (dict)
    """
    # if tokens are passed in as string, convert to python object
    if type(tokens) == str:
        tokens = eval(tokens)

    # if no tokens, exit 1 and return empty dict
    if len(tokens) == 0:
        sys.exit(1)

    # parse single token
    def _parse_token():
        # get counter variable from surrounding/outer scope
        nonlocal counter
        # get current token
        token = tokens[counter]
        counter += 1
        if token.isdigit():
            return {"type": "num", "value": int(token)}

        # create leaf for the op
        leaf = {"type" : "op",
                "value": token,
                "expr" : []}
        while len(tokens) > counter:
            leaf["expr"].append(_parse_token())
        return leaf

    # init counter in function scope, can be used in `_parse_token()` with keyword `nonlocal`
    counter = 0

    return _parse_token()


if __name__ == "__main__":
    # read from stdin (enables unix piping)
    for line in sys.stdin:
        # write to stdout (i.e. `print(...)`)
        print(parser(line))
