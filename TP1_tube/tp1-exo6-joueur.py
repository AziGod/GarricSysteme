
import os,errno,time,sys,random 

NB_JOUEURS=3

if len(sys.argv)!=2:
    print("il faut un paramètre")
    exit(1)
num = int(sys.argv[1])

nom_tube="tube"+str(num)

if os.path.exists(nom_tube): 
    os.unlink(nom_tube) 

os.mkfifo(nom_tube,0o600) 
fifo=os.open(nom_tube,os.O_RDONLY| os.O_NONBLOCK)
print("Tube du joueur "+str(num)+" créé et ouvert avec succès")  
fifo2 = [0] * (NB_JOUEURS)

# on ouvre le tube des autres joueur en écriture
for i in range(NB_JOUEURS):
    if i==num:
        continue
    autre_joueur_dispo=False
    while (autre_joueur_dispo==False):
        try:
            fifo2[i]=os.open("tube"+str(i),os.O_WRONLY |os.O_NONBLOCK)
            autre_joueur_dispo=True
        except OSError as e:
            if (e.errno==errno.ENXIO):
                print ("en attente du joueur "+str(i))
                time.sleep(1)
            else:
                print (e)
                print (e.errno)
    print (fifo2)
#on est prêt à recevoir et envoyer
print ("Prêt à jouer")
while True:
    try:
        carac = os.read(fifo,10)
        if (len(carac)!=0):
            print ("balle reçue")
            
            try:
                # on tire un nombre au hasard entre 1 et NB_JOUEURS num excepté
                dest=num
                while (dest==num):
                    dest=random.randint(0,NB_JOUEURS-1)

                print("envoi de la balle à "+str(dest))
                time.sleep(2)
                os.write(fifo2[dest],bytes("balle", 'utf-8')) 
            
            except OSError as e:
                if (e.errno==errno.ENXIO):
                    print ("tube non ouvert en lecture")
                elif (e.errno==errno.EAGAIN):
                    print ("tube plein")    
                elif (e.errno==errno.EPIPE):
                    print ("plus de lecteur")
                else:
                    print (e)

        
    except OSError as e:
        if (e.errno==errno.EAGAIN):
            a=0
        else:
            print (e) 
