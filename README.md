# Antykacraft

Custom plugin for "Antykacraft", a Minecraft server.

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
