GoldRush is a basic 2D platformer game realized with the libGdx engine. It builds upon their tutorial and expands it with a few functionalities. 
Moreover, the game offers the opportunity to play together with any number of players on the local network.
It features basic player movement and very rudimentary collision detection.

Collision Handling:
I attempted to implement collision handling using the Physics Library box2d. However, due to significant issues in multiplayer caused by client-side computation, I reverted to a very simple collision handling implementation using squares and the built-in libGdx functionalities. Thus, collisions are still calculated client-side and are therefore unsuitable. In the future, I may consider writing server-side collision handling.
