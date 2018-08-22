# Antykacraft

Plugin sur mesure pour le serveur Minecraft "Antykacraft".

# Commands

### /faction
- Description: Permet de communiquer avec sa faction
- Utilisation: 
  * /faction: Active / Désactive le canal de faction automatique.
  * /faction [message]: Envoie un message aux membres de sa faction.
- Permissions:
  * /faction [message]: antykacraft.faction
- Aliases: /f

### /ping
- Description: Permet de mesurer son temps de latence avec le serveur.
- Utilisation:
  * /ping: Mesure son propre temps de latence.
  * /ping [joueur]: Mesure le temps de latence d'un autre joueur connecté sur le serveur.
- Permissions:
  * /ping: antykacraft.ping
  * /ping [joueur]: antykacraft.ping.others
# PvPBox

Mini-jeu PvP avec des kits de combats.

# Commands

### /pvpbox
  - Description: Permet de gérer le PvPBox.
  - Utilisation:
    * /pvpbox [args]
    
#### /pvpbox list
  - Description: Affiche la liste des arènes PvPBox.
  - Utilisation: /pvpbox list
  - Permission: antykacraft.pvpbox.list
  
#### /pvpbox reload
  - Description: Rafraichit dynamiquement la configuration du plugin.
  - Utilisation: /pvpbox reload
  - Pemission: antykacraft.pvpbox.reload

#### /pvpbox stats [joueur]
  - Description: Affiche les stats PvPBox de joueurs.
  - Utilisation:
    * /pvpbox stats: Affiche ses stats personnelles.
    * /pvpbox stats [joueur]: Affiche les stats d'un autre joueur.
  - Permissions:
    * /pvpbox stats: antykacraft.pvpbox.stats
    * /pvpbox stats [joueur]: antykacraft.pvpbox.stats.others
   
#### /pvpbox rank [limit]
  - Description: Affiche un classement des joueurs PvPBox (par nombre de kills).
  - Utilisation:
    * /pvpbox rank: Affiche le classement avec une limite de 5 joueurs.
    * /pvpbox rank [limite]: Affiche le classement avec une limite de joueurs spécifiée (>= 5).
  - Permissions:
    * /pvpbox rank: antykacraft.pvpbox.rank
    * /pvpbox rank [limite]: antykacraft.pvpbox.rank.limit

#### /pvpbox trank [limit]
  - Description: Affiche un classement des joueurs PvPBox (par ratio kills / morts).
  - Utilisation
    * /pvpbox trank: Affiche le classement avec une limite de 5 joueurs.
    * /pvpbox trank [limite]: Affiche le classement avec une limite de joueurs spécifiée (>= 5).
  - Permissions:
    * /pvpbox trank: antykacraft.pvpbox.trank
    * /pvpbox trank [limite]: antykacraft.pvpbox.trank.limit
    
#### /pvpbox debug
  - Description: Active / Désactive le mode debug.
  - Utilisation: /pvpbox debug
  - Permission: antykacraft.pvpbox.debug

#### /pvpbox leave
  - Description: Quitte une partie de PvPBox en cours.
  - Utilisation: /pvpbox leave
  - Permission: antykacraft.pvpbox.leave
  
#### /pvpbox lobby [args]
  - Description: Permet de gérer le lobby PvPBox.
  - Utilisation:
    * /pvpbox lobby: Téléporte l'opérateur au lobby PvPBox (si défini).
    * /pvpbox lobby set: Définit le lobby PvPBox (à la position de l'opérateur).
  - Permissions:
    * /pvpbox lobby: antykacraft.pvpbox.lobby
    * /pvpbox lobby set: antykacraft.pvpbox.lobby.set
    
#### /pvpbox points [args]
  - Description: Permet de gérer les points de victoire.
  - Utilisation:
    * /pvpbox points: Affiche les points de victoire de l'opérateur.
    * /pvpbox points give [joueur] [somme]: Donne un montant de points de victoire à un joueur.
  - Pemissions:
    * /pvpbox: antykacraft.pvpbox.points
    * /pvpbox points give [joueur] [somme]: antykacraft.pvpbox.points.give

#### /pvpbox multiplier [valeur]
  - Description: Permet de gérer le multiplicateur de points de victoire.
  - Utilisation:
    * /pvpbox multiplier: Affiche la valeur du multiplicateur de points de victoire.
    * /pvpbox multiplier [valeur]: Modifie la valeur du multiplicateur de points de victoire.
  - Permissions:
    * /pvpbox multiplier: antykacraft.pvpbox.multiplier
    * /pvpbox multiplier [valeur]: antykacraft.pvpbox.multiplier.set
    
#### /pvpbox killreward [valeur]
   - Description: Définit le nombre de points de victoire gagnés par kills.
   - Utilisation: /pvpbox killreward [valeur]
   - Permission: antykacraft.pvpbox.killreward.set
 
#### /pvpbox mostplayed
  - Description: Affiche le kit le plus joué.
  - Utilisation: /pvpbox mostplayed
  - Permission: antykacraft.pvpbox.kit.mostplayed

#### /pvpbox lessplayed
  - Description: Affiche le kit le moins joué.
  - Utilisation: /pvpbox lessplayed
  - Permission: antykacraft.pvpbox.kit.lessplayed

#### /pvpbox create [arène]
  - Description: Crée une arène PvPBox (à la position de l'opérateur).
  - Utilisation: /pvpbox create [arène]
  - Permission: antykacraft.pvpbox.create
  
#### /pvpbox setdefault [arène]
  - Description: Définit l'arène PvPBox par défaut.
  - Utilisation: /pvpbox setdefault [arène]
  - Permission: antykacraft.pvpbox.setdefault

#### /pvpbox remove [arène]
  - Description: Supprime une arène PvPBox.
  - Utilisation: /pvpbox remove [arène]
  - Permission: antykacraft.pvpbox.remove
  
#### /pvpbox kits [args]
  - Description: Permet de gérer les kits PvPBox.
  - Utilisation:
    * /pvpbox kits buyable: Rend gratuit tous les kits spéciaux.
    * /pvpbox kits buy: Ouvre le magasin de kits spéciaux.
    * /pvpbox kits list: Affiche la liste des kits PvPbox.
    * /pvpbox kits enable [kit]: Active un kit PvPBox.
    * /pvpbox kits disable [kit]: Désactive un kit PvPBox.
    * /pvpbox kits price [kit] [prix]: Définit le prix d'un kit.
  - Permissions:
    * /pvpbox kits buyable: antykacraft.pvpbox.kit.buyable
    * /pvpbox kits buy: antykacraft.pvpbox.kit.buy
    * /pvpbox kits list: antykacraft.pvpbox.kit.list
    * /pvpbox kits enable [kit]: antykacraft.pvpbox.kit.enable
    * /pvpbox kits disable [kit]: antykacraft.pvpbox.kit.disable
    * /pvpbox kits price [kit] [prix]: antykacraft.pvpbox.kit.price
 
> ###### (!) Les commande suivantes ne sont pas executables par les joueurs

###### /pvpbox [joueur]
  - Description: Ouvre la liste des kits au joueur spécifié.
  - Utilisation: /pvpbox [joueur] (@p pour les command blocks)

###### /pvpbox lobby [joueur]
  - Description: Téléporte le joueur spécifié au lobby PvPBox.
  - Utilisation: /pvpbox lobby [joueur]
 
## Kits

#### Guerrier
  - Description: Incarnez l'âme d'un guerrier.
  - Equipement:
    * Epée en fer
    * Plastron en fer
    * Jambières en fer
    * Bottes en  or

### Archer
  - Description: Mettez votre précision à l'épreuve.
  - Equipement:
    * Epée en bois
    * Arc [Infinity]
    * Salve
    * Plastron en or
    * Jambières en or
    * Bottes en or
  - Compétences:
    * Salve: Lance une salve de flèches enflammées sur le champ de bataille.
    
### Reaper
  - Description: Maudissez vos adversaires.
  - Equipement:
    * Faux [Sharpness IV]
    * Potion de force II
    * Potion splash de poison II
    * Plastron en diamant
    * Jambières en cuir
    * Bottes en cuir

### Wizard
  - Description: Jouez avec la magie noire.
  - Equipement:
    * Pelle en fer [Sharpness IV]
    * Potion de force I
    * Potion de vitesse allongée
    * Potion de soins II
    * Potion de régéneration II
    * Plastron en cuir
    * Jambières en cuir
    * Bottes en cuir
    
### Trolleur
  - Description: Faites ragequit vos adversaires.
  - Equipement:
    * Stick of truth [Knockback III, Sharpness II]
    * 64x2 Boule de neiges
    * 2x Potion splash de faiblesse
    * Plastron en cuir
    * Jambières en cuir
    * Bottes en cuir

### Ninja
  - Description: Combattez furtivement.
  - Equipement:
    * Katana (Epée en pierre) [Sharpness I, Knockback I]
    * Shuriken
    * Casque en cuir
    * Plastron en cuir
    * Jambières en cuir
    * Bottes en cuir
  - Effets:
    * Speed I (infini)
  - Compétences:
    * Shuriken: Lance un shuriken (3.5 dégats)

### Tank
  - Description: Encaissez tous les dégâts.
  - Equipement:
    * Hâche de bourrin
    * Casque en cuir [Protection I]
    * Plastron en diamant [Protection I]
    * Jambières en diamant [Protection I]
    * Bottes en diamant [Protection I]
  - Effets:
    * 16 coeurs
    * Slow I
    
## Rabbit
  - Description: Follow the white rabbit.
  - Equipement:
    * Fire Carrot [Sharpness 2, Fire Aspect II]
    * Casque en cuir [Protection I]
    * Plastron en cuir [Protection I]
    * Jambières en cuir [Protection I]
    * Bottes en cuir [Protection I]
  - Effets:
    * Jump II
    * Speed II

### Ghost
  - Description: Faufilez vous dans les rangs ennemis.
  - Equipement:
    * Os du spectre [Sharpness III]
    * 3x Potion splash Instants Damage
  - Effets:
    * Invibility
