from lexer import lexer


def test_lexer():
    code = "mul 3 sub 2 sum 1 3 4"
    assert lexer(code) == ["mul", "3", "sub", "2", "sum", "1", "3", "4"]


if __name__ == "__main__":
    test_lexer()
    print("--- Tests passed ---")
