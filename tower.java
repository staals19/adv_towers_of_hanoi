import java.util.*;
public class tower{
    String position;
    ArrayList<disk> disks = new ArrayList<disk>();
    public tower(String p){
        this.position = p;
    }
    
    public void add(disk d){
        this.disks.add(0, d);
    }
    
    public void delete(int i){
        this.disks.remove(i);
    }
    
    public boolean contains(int i){
        for(disk d : this.disks){
            if(d.value == i){
                return true;
            }
        }
        return false;
    }
    
    public boolean check(){
        int val = disks.get(0).value;
        for(int i=1; i<disks.size(); i++){
            if(disks.get(i).value > val){
                val = disks.get(i).value;
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    public void print(){
        System.out.println(this.position + ": ");
        for(disk d : disks){
            System.out.print(d.value + " ");
        }
        System.out.println();
    }
}
