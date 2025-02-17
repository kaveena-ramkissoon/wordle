# wordle

Date Started: December 20, 2022
Date Completed: January 23, 2023

Description:
  This is a replica of the New York Times game Wordle programmed on Java as my 12th grade Computer Science final.
  As a result of this being a bit older and having beenn written by an inexperienced programmer, please take it with a grain of salt.
  It used to work perfectly, except one bug, and then I loaded it up again and suddenly it imploded.
  One day, I may revisit this and make it better, who knows. I would probably use a stack next time...

Known Issues:
  - If there is only one specific letter in a word and the user guesses a word with two of those letters, where one of the letters is in the correct spot, the other would appear as yellow, despite there not being a second letter.
    For example, if the word is SELLS and the user guesses SEVER, the second E would appear yellow, which is wrong.

  - The leaderboard initializes with a score of 1, but fixes itself once a game is won. I'm not sure why, and I don't want to find out.

Execution:
  If you would like to try out the code, download the zip file, import it into your IDE and run the main file Gr12Wordle.
