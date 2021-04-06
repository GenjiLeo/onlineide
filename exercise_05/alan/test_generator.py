from generator import generator


def test_generator():
    ast = {
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
    assert generator(ast) == "(2 - (1 + 3 + 4))"
    assert eval(generator(ast)) == -6


if __name__ == "__main__":
    test_generator()
    print("--- Tests passed ---")
