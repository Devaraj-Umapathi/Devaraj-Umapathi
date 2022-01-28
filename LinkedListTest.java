public class LinkedListTest {
    public static void main(String[] args){

        LinkedListImpl obj=new LinkedListImpl();

        obj.insert(1);
        obj.insert(2);
        obj.insert(3);

        obj.print();


    }
}

final class LinkedListImpl {

    numbers head;
    numbers tail;

    public LinkedListImpl(){
        head=null;
        tail=null;

    }

    class numbers {
        int value;
        numbers nextNodeaddress;

        public numbers(int numbers) {
            this.value = numbers;
        }
    }

    public void insert(int value) {
        numbers node=new numbers(value);
        if(head==null){
            head= node;
            tail= node;
        }
        else{
            tail.nextNodeaddress=node;
            tail= tail.nextNodeaddress;
        }
    }

    public void print() {
        numbers curr=head;
        while(curr!=null){
            System.out.println(curr.value);
            curr=curr.nextNodeaddress;
        }
    }

}

