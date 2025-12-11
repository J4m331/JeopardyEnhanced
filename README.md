# **Project Documentation**

## **1. Introduction**

### **Team Members**

* *Jameel Ali -816040972*
* *Matthew Moodoo - 816039942*
* *Nathan Baptiste - 816039236*

### **Project Scope**
This project involves designing and developing a Multi-Player Jeopardy Game in Java. The application supports 1–4 players in a turn-based trivia format. All game questions and categories are loaded from a CSV file.

Players select categories and point values, answer questions, and earn or lose points based on correctness. The game ends when all questions are exhausted or the session is manually stopped. At completion, the system generates two outputs:

* A summary report, in a TXT file, containing final scores and a full breakdown of every turn.

* A Process Mining event log capturing every user interaction in chronological, CSV-formatted form.

This project applies Object-Oriented Design, SOLID principles, and three design patterns (Observer, Builder and Composite), excluding Singleton, while implementing reliable file parsing, gameplay mechanics, scoring rules, report generation, and automated logging. It also includes JUnit testing and Maven build management.

---

## **Design**

### **Design Patterns Used**

* **Observer Pattern**

  ***Why it was chosen:***

  The Jeopardy Game contains multiple UI components and game systems that must react to events such as score updates, question selection, button locking, and logging. Using the Observer pattern prevents tight coupling by separating event producers (subjects) from event consumers (observers). This supports the project requirement for clean design, scalable UI updates, and real-time Process Mining logging.
   
  ***How it is applied:***

  The project defines generic Subject and Observer interfaces, along with specialized variants for different responsibilities (e.g., ScoreObserver, LogObserver, ButtonLockObserver, ScoreUIObserver).
  UI components and game objects receive updates without depending on concrete classes.


* **Builder Pattern**

  ***Why it was chosen:***

  Some objects in the system, such as event log entries, contain many fields. Creating these using long constructors is error-prone and difficult to read. The Builder pattern enables clean construction of structured objects, especially those written to the process mining CSV log.

   ***How it is applied:***

  The LogEvent class uses an internal Builder class to fluently assemble complete event records before they are passed to the logger. This ensures that each logged interaction conforms to the mandatory fields required by the assignment (Case_ID, Player_ID, Activity, Timestamp, etc.).

* **Composite Pattern**

   ***Why it was chosen:***
   
   Game data is naturally hierarchical:
   * A Category contains multiple Questions.
   * A Question contains multiple Options.
     
   This structure benefits from a composition-based model where larger components are built from smaller ones. It simplifies traversal, status checks, and UI representation of the Jeopardy board.
   
   ***How it is applied:***
   
   The Category class maintains a list of Question objects and provides operations such as addQuestion() and allAnswered(). Each Question stores a list of answer options.
   It models the problem domain with nested, composable objects, making it easy to manage question availability and UI rendering.

---

### **SOLID Principles**

* **Single Responsibility Principle**

  Each class handles exactly one core responsibility:
  * EventLogger → Manages event logging and writing to game_event_log.csv.
  * ReportGenerator → Creates the summary report file.
  * Player → Stores and manages individual player data and scoring.
  * QuestionWindow → Displays a single question and links its option buttons.
  * Category & Question → Store question structures only.

* **Open/Closed Principle**

  The Observer and Link interfaces allow new behaviors to be added without modifying existing classes.

  For example:
  * Adding a new ScoreObserver class requires no changes to OptionButton or GameManager.
  * Adding a new LogObserver implementation simply requires attaching it as an observer.

* **Liskov Substitution Principle**

  The system programs against interfaces (Subject, Observer, LogObserver, ScoreObserver). Any implementation of these interfaces can replace another without breaking functionality.

  For example:
  * EventLogger can act as any LogObserver.
  * A new UI class that implements ScoreUIObserver can be attached wherever the interface is expected.

* **Interface Segregation Principle**
  
  The project defines small, role-specific interfaces:
  * ScoreObserver
  * LogObserver
  * ButtonLockObserver
  * ScoreComponentLink
  * EventLogLink
  
  Classes implement only what they need.

  For example, a UI class that only updates the scoreboard implements ScoreObserver without being forced to implement unrelated methods.

* **Dependency Inversion Principle**

  High-level modules (UI, game logic) depend on abstractions rather than concrete classes.

  For example:
  * QuestionWindow takes observers via LinkObserver, which accepts the Observer interface, not a concrete GameManager.
  * OptionButton communicates through interfaces (ScoreObserver, LogObserver) rather than importing specific implementations.

---

## **Class Diagram**

### **UML Class Diagram**

![](/ClassDiagram.png)

---

## **Implementation**

### **How to Run the Application**

**Prerequisites**
  * Java JDK installed (latest version recommended).
  * An IDE (IntelliJ IDEA, Eclipse, VS Code).
  * Maven installed.

**Step-by-Step Instructions**
1) Clone the repository
2) Run Main.java
   * Open the project in your IDE.
   * Locate the Main.java file.
   * Run the file using the IDE run configuration.
3) When the app runs:
   * An application window will appear to prompt you to select the CSV file containing the questions. Use the file chooser to navigate to and select your CSV file.
   * Ensure your CSV has the columns required by the app. The column names and format are documented in the "File Format Details" section below.
   * After selecting the CSV, the app will ask you to select the number of players.
   * Input the names of each player when prompted; provide one name per player.
   * Confirm the setup; the game will start with the loaded questions and entered players.
     
---

### **File Format Details**

* **Input CSV file format:**

    Category | Value | Question | OptionA | OptionB | OptionC | OptionD | CorrectAnswer

    *Example:* Variables & Data Types | 100 | Which of the following declares an integer variable in C++? | int num; | float num; | num int; | integer num; | A

* **Output CSV file format (game_event_log.csv):**

    Case_ID | Player_ID | Activity | Timestamp | Category | Question_Value | Answer_Given | Result | Score_After_Play

* **Report Details (jeopardyGameReport.txt):**

  Contains:
    * Final scores
    * Full turn-by-turn breakdown
    * Player answers and correctness
    * Score after each turn
      
---

## **Test Summary**

## Test Suite
The test suite developed for this game contains the following:

### Unit Tests
- **PlayerTest**: Player creation, score management (positive/negative), cumulative scoring
- **OptionTest**: Answer option creation, formatting, header differentiation
- **QuestionTest**: Question creation, answered state tracking, score values, option retrieval
- **CategoryTest**: Category management, question addition, completion tracking
- **GameManagerTest**: Player rotation, score updates, turn management, current player tracking
- **LogEventTest**: Builder pattern implementation, event data structure, partial/full data handling
- **EventLoggerTest**: Singleton pattern, CSV logging, file creation, event persistence
- **ReportGeneratorTest**: Text report generation, player scores, session rundown, file output
- **CSVInputTest**: CSV parsing, category creation, question loading, data validation

### Integration Tests
- **IntegrationTest**: Complete game workflows including:
  - CSV loading → Category creation → Player setup → GameManager initialization
  - Multi-player scoring across multiple rounds
  - Turn rotation and score progression
  - Event logging throughout gameplay
  - End-to-end game simulation

### Edge Case Handling
- Single player games
- Negative scores (incorrect answers)
- Empty categories
- Tied player scores
- Missing or malformed CSV data
- File I/O errors

### UI Tests
Not applicable - UI components use Swing dialogs which require manual testing.

## Results

### Pass/Fail Statistics
- **Total Test Cases**: 79
- **Expected Pass Rate**: 100% (All of the tests are designed to pass with correct implementation)
- Run `mvn test` to generate current statistics.

### Known Issues
- Tests create temporary files (`test_questions.csv`, `integration_test.csv`, `test_jeopardyGameReport.txt`) that are cleaned up after execution.
- EventLogger singleton may persist state between test runs - restart test suite if needed.
- CSV tests require write permissions in the project directory.

---

## **Demo Video**
**Demo Video:**
*https://youtu.be/aVqUsg7Gx90*
