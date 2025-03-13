import os 
import time 
import errno 
nom_tube="tube1"

if os.path.exists(nom_tube): 
    os.unlink(nom_tube) 

os.mkfifo(nom_tube,0o600) 
print("Tube créé avec succès")  

fifo=os.open(nom_tube,os.O_RDONLY| os.O_NONBLOCK)
print("ouverture du tube :")

while True:
    try:
        carac = os.read(fifo, 100).decode('utf-8')
        messages = carac.split("\n")
        if (len(carac)!=0):
            for msg in messages:
                if "|" in msg:  # Vérifier que le message est bien formaté
                    id, valeur = msg.split("|", 1)  # Séparer ID et valeur
                    print(f"Message de {id} : {valeur}")  # Afficher correctement
        else:
            print("pas d'écrivain")
            time.sleep(1)
    except OSError as e:
        if (e.errno==errno.EAGAIN):
            print ("tube vide")
            time.sleep(1)
        else:
            print (e) 
        
