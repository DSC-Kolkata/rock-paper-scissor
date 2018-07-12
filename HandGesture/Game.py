import cv2
import Gesture



cap1 = cv2.VideoCapture(0)
cap2 = cv2.VideoCapture(1)


while 1:
    try:
        #Frame 1
        dict1 = Gesture.gestureRegognition(cap1)
        l1 = dict1['l']
        areacnt1 = dict1['areacnt']
        arearatio1 = dict1['arearatio']
        frame1 = dict1['frame']

        Gesture.display(l1, areacnt1, arearatio1, frame1)
        cv2.imshow('frame1', frame1)

        #Frame 2
        dict2 = Gesture.gestureRegognition(cap2)


        l2 = dict2['l']
        areacnt2 = dict2['areacnt']
        arearatio2 = dict2['arearatio']
        frame2 = dict2['frame']


        Gesture.display(l2, areacnt2, arearatio2, frame2)
        cv2.imshow('frame2', frame2)
    except:
        pass




    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break

cv2.destroyAllWindows()
cap1.release()
cap2.release()


# show the windows
#cv2.imshow('mask', mask)
#cv2.imshow('frame', frame)
