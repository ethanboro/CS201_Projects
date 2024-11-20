public class LinkStrand implements IDnaStrand {



    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    private class Node {
        String info;
        Node next;
        public Node (String s, Node n) {
            info = s;
            next = n;
        }
    }



    
    
    public LinkStrand(String s) {
        initialize(s);
    }

    public LinkStrand() {
        this("");
    }


    @Override
    public long size() {
        return mySize;

        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(String source) {

        
        myAppends = 0;

        myFirst = new Node(source, null);
        myLast = myFirst;
        mySize = source.length();
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;

        
        // TODO Auto-generated method stub
    }

    

    

    @Override
    public IDnaStrand getInstance(String source) {
        // TODO Auto-generated method stub
        return new LinkStrand(source);

        //throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }

    @Override
    public IDnaStrand append(String dna) {
        Node newNode = new Node(dna, null);
        myLast.next = newNode;
        myLast = newNode;
        mySize += dna.length();
        myAppends++;
        return this;
    }
    @Override
    

    public IDnaStrand reverse() {
        LinkStrand rev = new LinkStrand();
        Node current = myFirst;
        while (current != null) {
            StringBuilder temp = new StringBuilder(current.info);
            rev.addFirst(temp.reverse().toString());
            current = current.next;
        }
        return rev;
    }


    private void addFirst(String s) {
        Node newFirst = new Node(s, myFirst);
        myFirst = newFirst;
        mySize += s.length();
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override

public char charAt(int index) { 
    if(index < 0 || index >= mySize) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mySize);
    }

    int count = 0;
    int i = 0;
    Node current = myFirst;

    if(myIndex < index) {
        count = myIndex;

        i = myLocalIndex;
        current = myCurrent;
    }

    while (count != index) {
        count++;
        i++;

        if (i >= current.info.length()) {
            i = 0;
            current = current.next;
        }

    }

    myIndex = count;
    myLocalIndex = i;
    myCurrent = current;

    return current.info.charAt(i); 
}


   

    public String toString() {
        StringBuilder ret = new StringBuilder();
        Node current = myFirst;
        while (current != null) {
            ret.append(current.info);
            current = current.next;
        }

        return ret.toString();
    }

}
