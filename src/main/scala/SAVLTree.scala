import java.lang.Integer

class SAVLTree[T <: Comparable[T]]
 (var value: Option[T],
  var parent: SAVLTree[T]=null)
  extends AnyRef with MySortedCollection[T]{

   lazy val leftChild: SAVLTree[T]= new SAVLTree(None,this)
   lazy val rightChild: SAVLTree[T]= new SAVLTree(None,this)

    def isBalanced(tree: SAVLTree[T]):Boolean = {
      def depths(tree: SAVLTree[T], depth: Int = 0): List[Int] = {
         List() ++
         (if (tree.leftChild.value.isDefined) depths(tree.leftChild,depth +1) else List()) ++
         (if (tree.isLeaf) List(depth) else List()) ++
         (if (tree.rightChild.value.isDefined) depths(tree.rightChild, depth +1) else List()) ++
         List()
      }

        val itsDepths = depths(tree)
        val max = itsDepths.max
        val diffs = itsDepths map (elem => max - elem)
          return !diffs.exists(elem=> max - elem >1)
    }
    def printSorted():Unit = {
        if (value.isDefined) {
          leftChild.printSorted() 
          println(value.get)
          rightChild.printSorted()
        }
    }
    
    def insert(t: T):Unit = if (value isDefined) {
      val child = if ((t compareTo value.get) > 0) rightChild else leftChild
      child.insert(t)
    } else value = Some(t)
    
    def isElement(t: T):Boolean= {
        ((leftChild == null)&&(rightChild == null))
    }
    def isLeaf :Boolean =  ((!leftChild.value.isDefined)&&(!rightChild.value.isDefined))

    def balance = {}
    
    override def toString = {
       value toString
    }
}
object Main extends App {
  val bar = new SAVLTree[Integer](None)
    bar insert 5
    bar insert 4
    bar insert 6
    bar insert 3
    bar insert 7
    bar insert 2
    bar insert 1
    bar printSorted
}
