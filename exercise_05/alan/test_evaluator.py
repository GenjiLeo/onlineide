from evaluator import evaluator


def test_evaluator():
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
    print(evaluator(ast))
    assert evaluator(ast) == -6


if __name__ == "__main__":
    test_evaluator()
    print("--- Tests passed ---")
