# TypeSpeeder

## Important 
This project is a joint effort by `Depinder Kaur` & `Sofie Van Dingenen`.

------------------------------------------------------------------------------------------------------------------------

The warnings are written in english and make sure that you read the warnings carefully.

## Update 1 of 3
One file is now added to the project:

* MenuTest.java



## Update 2 of 3
Three files are now added to the project:

* ChallengePerformanceTest.java
* ChallengeTest.java
* MenuPerformanceTest.java

## Update 3 of 3
Two files are now added to the project:

* NewsLetterTest.java
* PatchTest.java

------------------------------------------------------------------------------------------------------------------------

## Set-up required before running this application

One *must* connect to the database. It can be done in the following manner:

Go to `Project` --> `src` --> `main` --> `resources` --> `application.properties`

Enter username and password to connect with the database before running this application either in IntelliJ or Command
Terminal.

## Instructions to run this application

In this application, one can check one's typing speed. To begin with, there exists an opportunity to choose a language- 
swedish or english. 
One needs to create a new account or log in into one's existing account to be able to play.

Right now, there exists 5 different games to choose from and 3 different levels (easy, medium and hard).

The user can update his/her information.
The user is allowed maximum 3 chances to log in with correct username and password, otherwise is sent back to the main
menu.

### Max points 
The user is rewarded max `10 points` for each user-input that exactly matches the expected-input.

### Min points 
The user is rewarded min `0 point` for each user-input that does not match expected-input at all.

### Great Performance --> Bonus 
If the user gets 2 correct answers in a row, he gets a notification that he is playing for bonus.
If he gets a third correct answer, he goes to the next level as a `bonus`. 

### Poor Performance --> Point deduction
However, if the user does not perform well and gets 0-0 two times in a row, he gets a warning to score `any` point to 
avoid point-deduction. 

If the user gets 0-0-0 points in a row, unfortunately one point gets deducted from his total 
points.

But the user never drops to the previous level because the total-points do not drop below the minimum points required 
for that level (even if the user continues to get 0 point).

### Ranking List

One can even see how well one performs when compared to other users/players in the ranking list.

It can be a sort of motivation to give one's best the next time.

-- You're all ready to play. Enjoy typing!