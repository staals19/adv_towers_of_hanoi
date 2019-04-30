import java.util.*;

public class main{
    static tower left = new tower("left");
    static tower middle = new tower("middle");
    static tower right = new tower("right");
    static int moves = 0;
    static boolean valid = true;
    static int num = 0;
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("How many disks?");
        num = Integer.parseInt(input.next());
        
        
        disperse(num); //putting disks on the towers randomly
        printT(num);
        int n = num;
        
        
        while(right.disks.size() != num){
            if(contains(n).position.equals("right")){ //if the biggest disk is already on the right, skip
                n--;
            }
            else{
                if(n == 1){ //if the biggest disk is the 1 disk
                    toTower(contains(1), right, 1);
                }
                else{
                    int secB = n-1; //second biggest
                    tower free = freeT(n); //tower that's not the right and doesn't contain the biggest disk
                    tower mid = midTower(contains(secB), free);
                    if(contains(secB).position.equals(free.position)){ //if second biggest is already on the free tower
                        for(int i=secB-1; i > 0; i--){ //move the rest of the disks to free
                            toTower(contains(i), free, i);
                        }
                    }
                    else{
                        for(int i=secB-1; i > 0; i--){ //move rest of disks to middle tower
                            toTower(contains(i), mid, i);
                        }
                        toTower(contains(secB), free, secB); //move second biggest to free tower
                        for(int i=secB-1; i > 0; i--){ //move rest to free tower
                            toTower(contains(i), free, i);
                        }
                    }
                    toTower(contains(n), right, n); //move biggest disk to the right
                    n--;
                }
            }
        }
        System.out.println("Moves: " + moves);
    }
    
    public static void disperse(int num){ //puts disks randomly on towers
        for(int i=num; i>0; i--){
            disk d = new disk(i);
            int t = (int)(Math.random() * 3) + 1;
            if(t == 1){
                left.add(d);
            }
            else if(t == 2){
                middle.add(d);
            }
            else{
                right.add(d);
            }
        }
    }
    
    public static void toTower(tower start, tower end, int big){ //moves disk from one tower to another
        if(big == 1){
            move(start, end);
        }
        else{
            int num = 1; //the number of the disk that we're moving
            while(num < big){
                int n = big;
                tower mid = midTower(start, end);
                while(n > num+1){
                    mid = midTower(contains(n-1), mid); //tower that the smaller disk has to go to
                    n -= 1;
                }
                move(contains(num), mid);
                if(num > 1){ //moving the smaller disks back on top
                    for(int i=num; i>1; i--){
                        toTower(contains(i-1), mid, i-1);
                    }
                }
                num++;
            }
            move(start, end);
        }
    }
   
    
    public static tower midTower(tower start, tower end){ //returns the middle tower
        if(start.position.equals("left") && end.position.equals("right") || start.position.equals("right") && end.position.equals("left")){
            return middle;
        }
        else if(start.position.equals("left") && end.position.equals("middle") || start.position.equals("middle") && end.position.equals("left")){
            return right;
        }
        else if(start.position.equals("right") && end.position.equals("middle") || start.position.equals("middle") && end.position.equals("right")){
            return left;
        }
        else{
            return start;
        }
    }
    
    public static tower freeT(int big){ //the tower that doesn't contain int big and isn't the right tower
        tower free = new tower("");
        for(disk d : left.disks){
            if(d.value == big){
                free = middle;
            }
        }
        for(disk d : middle.disks){
            if(d.value == big){
                free = left;
            }
        }
        return free;
    }
    
    public static tower contains(int i){ //returns the tower that contains the disk
        if(left.contains(i)){
            return left;
        }
        else if(middle.contains(i)){
            return middle;
        }
        else{
            return right;
        }
    }
    
    public static void move(tower o, tower n){
        n.add(o.disks.get(0));
        o.delete(0);
        moves++;
        if(n.check() == false){
            System.out.println("INVALID MOVE");
            valid = false;
        }
        if(!o.position.equals(n.position)){
            printT(num);
        }
    }
    
    public static void printTowers(){
        left.print();
        middle.print();
        right.print();
        System.out.println();
    }
    
    public static void printT(int n){
        String[] l = new String[n];
        String[] m = new String[n];
        String[] r = new String[n];
        int ls = 0;
        int ms = 0;
        int rs = 0; 
        
        for(int i=0; i<n-left.disks.size(); i++){
            l[i] = "|";
            ls = i+1;
        }
        for(int i=0; i<n-middle.disks.size(); i++){
            m[i] = "|";
            ms = i+1;
        }
        for(int i=0; i<n-right.disks.size(); i++){
            r[i] = "|";
            rs = i+1;
        }
        
        for(int i=0; i<left.disks.size(); i++){
            l[ls+i] = Integer.toString(left.disks.get(i).value);
        }
        for(int i=0; i<middle.disks.size(); i++){
            m[ms+i] = Integer.toString(middle.disks.get(i).value);
        }
        for(int i=0; i<right.disks.size(); i++){
            r[rs+i] = Integer.toString(right.disks.get(i).value);
        }
        
        
        for(int i=0; i<n; i++){
            System.out.println(l[i] + "            " + m[i] + "            " + r[i]);    
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
    
    
    public static void emptyTowers(){
        left.disks.clear();
        middle.disks.clear();
        right.disks.clear();
    }
}
