import os
import sys
import time
import errno

def creer_tube(nom):
    """Crée un tube nommé (FIFO) s'il n'existe pas."""
    if os.path.exists(nom): 
        os.unlink(nom)  # Supprimer l'ancien tube s'il existe
    os.mkfifo(nom, 0o600)

def envoyer_balle(tube_sortie, joueur):
    """Envoie la balle à l'autre joueur avec son ID."""
    with open(tube_sortie, "w") as fifo_out:
        fifo_out.write(f"{joueur}|balle\n")  # Format : "1|balle"
    print(f"Joueur {joueur} : Balle envoyée à {tube_sortie}")

def recevoir_balle(tube_entree, tube_sortie, joueur):
    """Lit le tube en continu sans blocage et renvoie la balle."""
    print(f"Joueur {joueur} : En attente de la balle...")

    # Ouverture du tube en mode NON-BLOQUANT
    fifo_in = os.open(tube_entree, os.O_RDONLY | os.O_NONBLOCK)

    while True:
        try:
            carac = os.read(fifo_in, 100).decode("utf-8")  # Lire jusqu'à 100 caractères
            messages = carac.split("\n")  # Séparer les messages

            for msg in messages:
                if "|" in msg:
                    id_joueur, valeur = msg.split("|", 1)
                    if valeur == "balle":
                        print(f"Joueur {joueur} : Balle reçue de {id_joueur} !")
                        time.sleep(1)  # Pause avant renvoi
                        envoyer_balle(tube_sortie, joueur)

        except OSError as e:
            if e.errno == errno.EAGAIN:
                time.sleep(1)  # Tube vide, on attend un peu
            else:
                print(f"Erreur : {e}")
                break  # Sortir si autre erreur

# Vérifier le numéro du joueur (1 ou 2)
if len(sys.argv) != 2 or sys.argv[1] not in ["1", "2"]:
    print("Utilisation : python joueur.py <numéro_joueur>")
    sys.exit(1)

joueur = sys.argv[1]  # Numéro du joueur
tube_reception = f"tube{joueur}"  # Tube où le joueur reçoit
tube_envoi = f"tube{'2' if joueur == '1' else '1'}"  # Tube où le joueur envoie

# Création des tubes nommés (FIFO)
creer_tube(tube_reception)
creer_tube(tube_envoi)

# Le joueur 1 engage le jeu en envoyant la première balle
if joueur == "1":
    time.sleep(1)  # Petite pause pour s'assurer que l'autre joueur est prêt
    envoyer_balle(tube_envoi, joueur)

# Démarrer la boucle de réception
recevoir_balle(tube_reception, tube_envoi, joueur)
