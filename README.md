# Calculator (Android, Java)

A four-operation calculator for Android with correct operator precedence,
input guards and a dark Material 3 interface.

<img width="280" alt="Calculator screenshot" src="https://github.com/user-attachments/assets/387f9547-440d-49c4-b071-97fa4a9f6893" />

## Features

- `+ − × ÷` on decimal numbers, with precedence (`2+3*4 = 14`)
- A leading `+` or `−` is accepted (`-5+2`)
- Input guards: no two operators in a row, no leading `*` or `/`,
  one decimal point per number, no extra leading zeros,
  limits on the length of the expression and of a single number
- `c` removes the last character
- Edge-to-edge layout with system bar insets handled

## How it works

| Part | Role |
|---|---|
| `MainActivity` | button handlers and input guards |
| `logic/CalculatorLogic` | helpers that inspect the current display text |
| `model/CalculatorModel` | evaluates the expression in plain Java |

The model splits the expression into numbers and operators, then repeatedly
reduces the lists: first `*` and `/`, then `+` and `-`, always left to right.
A leading sign is handled by prefixing `0` (`-5` → `0-5`).

## Run

Open the project in Android Studio, sync Gradle and run on an emulator or a
device (minimum SDK is set in `app/build.gradle`).

## Origin

Based on an introductory Android exercise (a basic calculator layout).
I implemented the expression evaluation with precedence, the input guards,
separated the calculation from the activity and styled the app with a dark
Material 3 theme and shared dimens/colors/strings.

## Roadmap

- Unit tests for `CalculatorModel`
- `BigDecimal`/`double` and clean number formatting
- Parentheses and percent
- Landscape layout

## License

MIT
