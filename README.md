# `KanBash`

Access kanban board via CLI.

Board has 3 columns: `SHOULD DO`, `IN PROGRESS` and `DONE`.

# Supported commands

- new 'task' - creates new task in `SHOULD DO` column
- pick 'index' - picks i'th task from `SHOULD DO` and moves it to `IN PROGRESS`
- done 'index' - picks i'th task from `IN PROGRESS` and moves it to `DONE`
- clear 'something' - clears specified column or all columns
- remove 'something' 'index' - removes specified task from specified column
- numeration 'number' or 'hyphen' - changes numeration
- exit - for exit

Example of board:
```
----------------SHOULD-DO---------------+---------------IN-PROGRESS--------------+------------------DONE------------------
0. Do more                              |0. Doing something                      |0. Done something                       
1. I SAID DO MORE                       |                                        |                                        
2. IT IS NOT ENOUGH                     |                                        |                                        
```