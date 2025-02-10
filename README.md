# Birdy

**Birdy** is an AI project that learns to play a well known Bird game.
It is completely done in Java.

## What can you do ?

The main goal is to see the AI learn through games. You can also play the game yourself to challenge the AI.

## Options on launch

The executable can handle options which are:
- "-h" for displaying help message.
-  "-p" for playing the game. 

Without any option, sit back and enjoy the process !

## The AI 

The algorithm used for this AI is based on the the **genetic drift**.
First, I create 1000 Neural networks and make them play the game.
When all of the individuals died. I retrieve the 10 that performs the best and mutate themselves until I get 1000 new individuals. Mutating means in this context creating a new neural that comes from the merge of 2 ones.
This new generation plays the game and we will create a new generation based on the best of the previous one.
It goes on and this way the AI keeps getting better.

## Playing

The game is actually really easy if you don't know it. You are a bird and your goal is to go through an infinite amount of pipes by flapping.
> You only need one key to play. It is the spacebar. Every time you press it, you will flap.

Good luck !
