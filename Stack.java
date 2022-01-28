class Stack {

    numbers head;

    public Stack(){
        head=null;
    }

    class numbers {
        int value;
        numbers nextNodeaddress;

        public numbers(int numbers) {
            this.value = numbers;
        }
    }

    public void push(int value){
        numbers node =new numbers(value);
        if(head==null){
            head=node;
        }
        else{
            node.nextNodeaddress=head;
            head=node;
        }
    }

    public int pop(){
        if(head==null){
            return -1;
        }
        else{
            int val= head.value;
            head=head.nextNodeaddress;
            return val;
        }

    }

    public int peek(){
        if(head==null){
            return -1;
        }
        else{
            return head.value;
        }
    }

}
class main{
    public  static void main(String args[]){
        Stack stack=new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        stack.push(4);
        System.out.println(stack.pop());
        stack.pop();
        System.out.println(stack.peek());
    }
}
