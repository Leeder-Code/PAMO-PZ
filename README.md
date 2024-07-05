# PAMO - Projekt zaliczeniowy - POPRAWA




## New Functionality: Savings Goals

### New Savings Goals Page
A new section has been added to the application to help users set and manage their savings goals. This includes:

1. **Adding a New Savings Goal**
   - **Goal Name**: The name of the savings goal.
   - **Description**: A description of what the funds are being saved for.
   - **Monthly Savings Amount**: The amount the user wants to save each month towards the goal.
   - **Target Amount**: The total amount the user aims to save.

2. **Progress Tracking**
   - A progress bar that shows the percentage of the target amount that has been saved.
   - Display of the estimated time required to reach the savings goal based on the current saving rate.

3. **Goal Details**
   - Detailed view of each savings goal.
   - Recent contributions to the savings goal with dates.

4. **Goal Management**
   - Ability to delete a savings goal.

## Features
- **Savings Goals**: Set, track, and manage your savings goals with detailed progress indicators and contribution tracking.

## Requirements
- Android 14 or higher

## Task Distribution and Planning
- **Task Assignment**: Tasks are assigned based on team members' expertise and availability.
- **Equal Distribution**: Tasks are distributed as evenly as possible to ensure fair workload.
- **Task List**: A comprehensive task list in Trello is maintained, with clear deadlines and responsibilities.
- **Sufficient Tasks**: Adequate tasks are planned to cover all aspects of the new functionality and ongoing maintenance.


## Day 1 - 01.07.2024

- Creating a new board on Trello
- Determining the requirements for the new functionality
- Planning to-do list
- Selecting tasks for the next day
- Scheduling daily meetings

![todo-poprawa-v1](./screens/todo-poprawa-v1.png 'todo-poprawa-v2')


## Day 2 - 02.07.2024

- Creating KDocs for Activities
- Selecting tasks for the next day
- Creating KDocs for AddExpenseActivity, SetBudgetActivity
- Creating layout for savings goals and savings goals details
- Adding information about new functionality on Readme.md
- Code review

![todo-poprawa-v2](./screens/todo-poprawa-v2.png 'todo-poprawa-v2')
![todo-poprawa-v2_2](./screens/todo-poprawa-v2_2.png 'todo-poprawa-v2_2')

## Day 3 - 03.07.2024

- Creating GoalsActivity
- Creating Goal entity in database
- Creating goals table in database
- Changing AppDatabase to singleton
- Creating GoalsAdapter
- Creating KDocs for GoalsActivity, Goal, GoalsDao, GoalsAdapter
- Selecting tasks for the next day
- Code review
- Crating AddGoalActivity xml layout
- Creating AddGoalActivity with KDocs (form to add a goal: goal name, description, monthly amount set aside, amount to be collected).
- Creating testMonkey AddGoalActivity

![todo-poprawa-v3](./screens/todo-poprawa-v3.png 'todo-poprawa-v3')
![todo-poprawa-v3_2](./screens/todo-poprawa-v3_2.png 'todo-poprawa-v3_2')

## Day 4 - 04.07.2024

- Creating GoalDetailsActivity
- Changing Goal schema in database
- Loading Goals from database
- Connecting Goals and Transactions logic
- Creating Espresso AddExpenseActivityTest
- Creating function to delete savings goals

![todo-poprawa-v4](./screens/todo-poprawa-v4.png 'todo-poprawa-v4')



# PAMO - Projekt zaliczeniowy

Aplikacja "Finansowy Menedżer" to intuicyjne narzędzie do zarządzania finansami osobistymi, które umożliwia dodawanie i kategoryzację wydatków, ustawianie miesięcznych budżetów, generowanie szczegółowych raportów finansowych oraz zarządzanie przypomnieniami o terminach płatności rachunków. Dzięki tym funkcjom użytkownicy mogą lepiej monitorować swoje wydatki, planować oszczędności i unikać opóźnień w płatnościach.

Lista funkcjonalności Aplikacji do Zarządzania Finansami:

**MVP (Minimum Viable Product) w kolejności wg priorytetów:**

1. Śledzenie wydatków - Użytkownicy mogą dodawać i kategoryzować swoje codzienne wydatki.
   Budżetowanie
1. Ustawianie miesięcznego budżetu dla wybranych kategorii wydatków.
   Analizy i raporty
1. Proste raporty pokazujące wydatki w podziale na kategorie.
   Przypomnienia o rachunkach
1. Podstawowe przypomnienia o nadchodzących płatnościach.

**Wdrożenie funkcjonalności:**

- Faza 1: Rozwój MVP - Implementacja funkcjonalności 1-4. _Planowane zakończenie: 6 tygodni._

- Pierwsze 3 tygodnie poświęcone na projektowanie i wdrożenie funkcjonalności do śledzenia wydatków i budżetowania.
- Następne 3 tygodnie na dodanie funkcjonalności analiz, raportów i przypomnień.

#### Ostatni tydzień: Faza testów i optymalizacji

## Lista zadań z systemu do śledzenia postępów (Trello)
![todo-v1](./screens/todo-v1.png 'todo-v1')
