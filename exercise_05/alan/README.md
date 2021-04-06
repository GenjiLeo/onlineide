# Alan compiler in Python

Use programs as filters in Unix pipeline.

## Compile

```bash
$ echo "sub 2 sum 1 3 4" | python3 lexer.py | python3 parser.py | python3 generator.py
```

## Interpret

```bash
$ echo "sub 2 sum 1 3 4" | python3 lexer.py | python3 parser.py | python3 evaluator.py
```
