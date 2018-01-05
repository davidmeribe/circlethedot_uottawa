package circlethedot_uottawa.utility;


public class LinkedStack<E> implements Stack<E>{

    private static class Elem<T>{
        private T info;
        private Elem<T> next;
        private Elem( T info, Elem<T> next) {
            this.info = info;
            this.next = next;
        }
    }

    private Elem<E> top; // instance variable

    public LinkedStack() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push( E info ) {
      //cant push in null
      if (info == null){
      
        throw new NullPointerException("Can't push null");
      }else{
        top = new Elem<E>( info, top );
      }
    }

    public E peek() {
      //if not empty
      if (!isEmpty()){
         return top.info;
        
      }else{
        throw new IllegalStateException("the stack is empty");
    }
    }

    public E pop() {
//cant pop null
   if(!isEmpty()){
     
        E savedInfo = top.info;

        Elem<E> oldTop = top;
        Elem<E> newTop = top.next;

        top = newTop;

        oldTop.info = null; // scrubbing the memory
        oldTop.next = null; // scrubbing the memory
        
        return savedInfo;
      }
      else{
         throw new IllegalStateException("stack is empty"); 
      
      }
        
}
}
