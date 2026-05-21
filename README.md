# Snake Game

A classic Snake game built in Java with Swing. The snake grows when it eats food and the game ends if it crashes into its own tail. Walls wrap around — exiting one edge teleports you to the opposite side.

## Features

- Main menu with **Play**, **Difficulty**, and **Quit**
- Three difficulty levels that adjust game speed:
  - **Easy** — 250 ms per tick
  - **Medium** — 150 ms per tick
  - **Hard** — 80 ms per tick
- Wrap-around walls (no instant death at edges)
- Live score display
- Game Over screen with **Play Again**, **Main Menu**, and **Quit**
- 15x15 grid, 40 px per cell

## Controls

| Key | Action |
| --- | --- |
| `↑` | Move up |
| `↓` | Move down |
| `←` | Move left |
| `→` | Move right |

The snake cannot reverse into itself — opposite-direction key presses are ignored.

## How to Run

Requires Java 17 or higher (uses pattern-matching `switch` expressions).

From the project root:

```bash
javac -d out src/Main.java src/model/*.java src/ui/*.java src/movement/*.java
java -cp out Main
```

## Project Structure

```
src/
├── Main.java              # Entry point
├── model/
│   ├── Snake.java         # Snake state and behavior (move, grow, collisions)
│   ├── Food.java          # Food position
│   └── Difficulty.java    # EASY / MEDIUM / HARD enum
├── ui/
│   ├── GameFrame.java     # Main window, CardLayout orchestration
│   ├── GamePanel.java     # Gameplay rendering and input
│   ├── MenuPanel.java     # Main menu screen
│   └── GameOverPanel.java # Game over screen
└── movement/
    └── Direction.java     # UP / DOWN / LEFT / RIGHT enum
```

The architecture follows a simple MVC-style separation:
- **model** — game entities and data
- **ui** — Swing panels and rendering
- **movement** — direction enum used by both layers

Panels communicate with the frame through callbacks (`Runnable`, `IntConsumer`, `Supplier`), so UI components are decoupled from each other.

## Built With

- **Java** (Swing for the UI)
- **CardLayout** for switching between menu, game, and game-over screens
- **javax.swing.Timer** for the game loop
