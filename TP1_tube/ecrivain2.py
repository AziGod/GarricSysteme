
import os 
import errno 
import time 

nom_tube="tube1"

try:
    fifo=os.open(nom_tube,os.O_WRONLY |os.O_NONBLOCK)
    i=0
    x=0
    id="toi"
    while True:
        try:
            if x >= 10:
                x = 0
            message = f"{id}|{x}\n".encode('utf-8')
            line = os.write(fifo,message)
            print(i)
            time.sleep(1)    
            i+=1
            x+=1
        except OSError as e:
            if (e.errno==errno.ENXIO):
                print ("tube non ouvert en lecture")
            elif (e.errno==errno.EAGAIN):
                print(i)
                print ("tube plein")
                time.sleep(1)    
            elif (e.errno==errno.EPIPE):
                print ("plus de lecteur")
                break;   
            else:
                print (e)
except OSError as e:
    if (e.errno==errno.ENXIO):
        print ("tube non ouvert en lecture")
    else:
        print (e)
    
