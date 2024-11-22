# 1. Lancement du jeux
Le jeu se lance simplement grâce à la variable **game** à la ligne 34 de
la classe **Play** *.../src/main/java/ch/epfl/cs107/play/Play/java*; et
ce en l'initialisant avec **new ICRogue()**, le tout suivi par l'excécution 
du programme.

# 2. Contrôles utilisables et effets
## Déplacements du personnage
Par l'intermédiaire des flèches du clavier.
## Interactions du personnage
**a) À distance:**\
La touche **W** permet les interactions à distance (ouverture des portes,
acquisition du bâton), ce qui est possible uniquement si l'objet avec 
lequel le personnage interagit est en face de lui dans la cellule à 
proximité.\
**b) Avec contact:**\
Les interactions avec contact se font automatiquement.
## Lancement des boules de feux
Possible si on a au préalable acquis le bâton et grâce à la touche **X**.
## Réinitialisation du jeu
Le *reset* du niveau est possible par l'utilisation de la touche **R**.
Ce qui va créer une nouvelle disposition des différentes salles de la 
carte(*map*), à condition que à la ligne 26 dans la classe **ICRogue**, 
on ait fourni la valeur *true* au paramètre du constructeur. Sinon, si 
la valeur *false* est fournie, il y aura création de la carte fixe et 
renvoi du personnage à la position initiale.

# 3. Comportement des différents composants :
La seule extension ajoutée est l'animation du mouvement du personnage. 
Celui-ci est maintenant dessiné avec un tableau de sprites au lieu d'un 
seul : on va itérer sur ce tableu si le personnage bouge pour donner la
sensation de mouvement.\
Tous les autres divers comportements sont directement inspirés de l'énoncé 
et rien de neuf n'a été introduit qui ne soit explicité tel quel dans l'énoncé.\
Une description essentielle du comportement des méthodes qui non triviales 
est fournie en commentaire dans le code.\
À noter que le personnage a dix points de vie décrémentés d'une unité à 
chaque fois qu'une flèche le touche. Finalament, on gagne le jeu si on tue
tous les ennemis de la salle du boss et on perd si le personnage meurt.
