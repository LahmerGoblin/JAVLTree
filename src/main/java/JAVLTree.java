/**
 */
public class JAVLTree<T extends Comparable<T>> implements MySortedCollection<T> {
    private T value;
    private JAVLTree<T> parent;
    private int height=0;

    // lazy vals
    /** dynamically initialized Container for leftChild*/
    private JAVLTree<T> leftCache;
    public JAVLTree<T> leftChild() {
        if (leftCache == null) {
            leftCache = new JAVLTree<T>();
            return leftCache;
        } else {
            return leftCache;
        }
    }
    /** dynamically initialized Container for rightChild*/
    private JAVLTree<T> rightCache;
    public JAVLTree<T> rightChild() {
        if (rightCache == null) {
            rightCache = new JAVLTree<T>();
            return rightCache;
        } else {
            return rightCache;
        }
    }
    /** @see MySortedCollection#insert() */
    public void insert(T t){
        // some special to do for the first insert :-)
        if ((this.getValue() == null) && (this.isRoot())) {
            this.value = t;
        } else {
        // special thing for leaf 
        if (this.isLeaf()) {
            if (value.compareTo(t) < 0) {
                rightChild().setValue(t);
            } else if (value.compareTo(t) > 0) {
                leftChild().setValue(t);
            } else {
                // duplicates in tree are forbidden!
                System.err.println("Already existing");
                System.exit(1);
            }
        } else {
        // and the dirty case of just one child... :-@
            if (value.compareTo(t) < 0) {
                if (rightChild().getValue() != null) {
                    rightChild().insert(t);
                } else {
                    rightChild().setValue(t);
                }
            } else if (value.compareTo(t) > 0) {
                if (leftChild().getValue() != null) {
                    leftChild().insert(t);
                } else {
                    leftChild().setValue(t);
                }
            } else {
                System.err.println("Already existing");
                System.exit(1);
            }
            // AVL-features
            updateHeight();
            balance();
            updateHeight();
        }
        }
    }
    /** @see MySortedCollection#printSorted() */
    public void printSorted() {
        // left child is the smallest, than it's me and
        // after all my right child!
        if (!(leftChild().getValue() == null)) {
            leftChild().printSorted();
        }
            System.out.println(value.toString());
        if (!(rightChild().getValue() == null)) {
            rightChild().printSorted();
        }
    }
    /** @see MySortedCollection#isElement() */
    public boolean isElement(T t) {
        // remember why we invented a binary tree?
        if (value.compareTo(t) == 0) {
            return true;
        } else if (value.compareTo(t) > 0) {
            return rightChild().isElement(t); 
        } else {
            return leftChild().isElement(t);
        }
    }
    /** selfdesribing, isn't it? :-D */
    public boolean isLeaf() {
        if ((leftChild().getValue() == null) && (rightChild().getValue() == null)) {
            return true;
            // uhuu, got no children :'(
        } else {
            return false;
            // uhu, got children :'(
        }
    }
    /** is this tree the uppermost element? */
    public boolean isRoot() {
        // yeah, I'm root! :-)
        return this.parent != null ? false:true;
    }
    /** how tall is this tree now? */
    public void updateHeight() {
        int left = 0;
        int right = 0;
        if (this.isLeaf()) {
             height = 0;
        } else {

            if (leftChild().getValue() != null) {
                leftChild().updateHeight();
                left = leftChild().getHeight();
            }
            if (rightChild().getValue() != null) {
                rightChild().updateHeight();
                right = rightChild().getHeight();
            }
            int biggerChild = right > left ? right : left;
            height = biggerChild + 1;
        }
    }
    /** is the AVLTree balanced? */
    private boolean isBalanced() {
        if (Math.abs(leftChild().getHeight()- rightChild().getHeight()) >= 1) {
            return true;
        } else {
            return false;
        }

    }
    /** balance and rotate the tree according to the four cases */
    public void balance() {
            // case r r           case r l
            //  o ← this            o this
            //   \                   \
            //    o rightChild()      o rightChild()
            //     \                 /
            //      o               o
            //  cases lr and ll are analogous
        if (!this.isBalanced()) {
            if (rightChild().getValue() != null) {
                if (!rightChild().isBalanced()) {
                    System.out.println("rightChild not Balanced");
                    rightChild().balance();
                } else {
                   // case r {r,l}
                   if (rightChild().rightChild().getValue()!=null) {
                       // case rr
                       // change parental reference
                       if (!this.isRoot()) {
                           System.out.println("case rr not Root");
                        if (parent.rightChild().equals(this)) {
                            parent.setRightChild(rightChild());
                        } else {
                            parent.setLeftChild(rightChild());
                        }
                      } else {
                          System.out.println("case rr root");
                          rightChild().setParent(null);
                      }
                        rightChild().setLeftChild(this);
                        setRightChild(new JAVLTree<T>());
                   } else {
                       // case rl
                     if (!this.isRoot()) {
                       if (parent.rightChild().equals(this)) {
                           parent.setRightChild(rightChild().leftChild()); 
                        } else {
                           parent.setLeftChild(rightChild().leftChild());
                        }
                     } else {
                         rightChild().leftChild().setParent(null);
                    }
                        rightChild().leftChild().setLeftChild(this);
                        setRightChild(new JAVLTree<T>());
                   }
                }
            } else {
               if (leftChild().getValue()!= null) { 
                if (!leftChild().isBalanced()) {
                    leftChild().balance();
                } else {
                    // case l {r,l}
                    if (leftChild().rightChild().getValue() != null) {
                        // case lr
                      if(!this.isRoot()) {
                        if (parent.rightChild().equals(this)) {
                            parent.setRightChild(leftChild().rightChild());
                        } else {
                            parent.setLeftChild(leftChild().rightChild());
                        }
                       } else {
                           leftChild().rightChild().setParent(null);
                        }
                        leftChild().rightChild().setRightChild(this);
                        setLeftChild(new JAVLTree<T>());
                    } else {
                        // case ll
                    if (!this.isRoot()) {
                        if (parent.rightChild().equals(this)) {
                            parent.setRightChild(leftChild());
                        } else {
                            parent.setLeftChild(leftChild());
                        }
                    } else {
                        leftChild().setParent(null);
                    }
                        leftChild().setRightChild(this);
                        setLeftChild(new JAVLTree<T>());
                    }
                }
            }
            }
        }

    }
    // some uninteressting getter/setter-trash
    public void setValue(T t) {
        value = t;
    }
    public T getValue() {
        return value;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int h) {
        height = h;
    }
    public void setRightChild(JAVLTree<T> right) {
        rightCache = right;
    }
    public void setLeftChild(JAVLTree<T> left) {
        leftCache = left;
    }
    public void setParent(JAVLTree<T> t) {
        parent = t;
    }
    public static void main(String[] args) {
        JAVLTree<Integer> bar = new JAVLTree<Integer>();
        bar.setValue(new Integer(5));
         //   bar.insert(new Integer(5));
    bar.insert(new Integer(4));
 bar.insert(new Integer(6));
    bar.insert(new Integer(3));
    bar.insert(new Integer(7));
    bar.insert(new Integer(2));
    bar.insert(new Integer(1));
    bar.printSorted();
    System.out.println(bar.getHeight());
    }
}
