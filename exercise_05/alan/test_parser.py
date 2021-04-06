from parser import parser


def test_parser():
    tokens = ["sub", "2", "sum", "1", "3", "4"]
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
    assert parser(tokens) == ast


if __name__ == "__main__":
    test_parser()
    print("--- Tests passed ---")
