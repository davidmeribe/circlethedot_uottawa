package circlethedot_uottawa.utility;

public class LinkedQueue<E> implements Queue<E> {

    private static class Elem<T> {

        private T value;
        private Elem<T> next;

        private Elem( T value, Elem<T> next ) {
            this.value = value;
            this.next = next;
        }
    }

    private Elem<E> front;
    private Elem<E> rear;

    public E peek() {
      // cant peek null
      if (isEmpty()){
      
        throw new IllegalStateException("the stack is empty");
      }else{
      
        return front.value;
    }
    }

    public void enqueue( E o ) {
     
      if ( o != null){
      Elem<E> newElem;
        newElem = new Elem<E>( o, null );

        if ( rear == null ) {
            front = rear = newElem;
        } else {
            rear.next = newElem;
            rear = newElem;
        }
      } else{
          throw new NullPointerException("Can't enqueue null");
      }
    }

    public E dequeue() {
      
      if (isEmpty()){
         throw new IllegalStateException("empty queue");
      }else{   
        E result = front.value;
        if ( front != null & front.next == null ) {
          
            front = rear = null;
        } else {
            front = front.next;
        }
        return result;
      }
  
      }

    public boolean isEmpty() {
        return front == null;
    }

}
