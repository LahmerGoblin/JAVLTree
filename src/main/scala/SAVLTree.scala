import java.lang.Integer

class SAVLTree[T <: Comparable[T]]
 (var value: Option[T],
  var parent: SAVLTree[T]=null)
  extends AnyRef with MySortedCollection[T]{

    var height:Int=_

    // Lazy-Values
    private var leftCache: Option[SAVLTree[T]] = None
    def leftChild: SAVLTree[T]={
      if (!leftCache.isDefined) leftCache = Some(new SAVLTree(None,this))
      return leftCache.get
    }

    private var rightCache: Option[SAVLTree[T]] = None
    def rightChild: SAVLTree[T]={
      if(!rightCache.isDefined) rightCache = Some(new SAVLTree(None,this))
        return rightCache.get
    }

    def isBalanced(tree: SAVLTree[T]):Boolean = {
      def heights(tree: SAVLTree[T], height: Int = 0): List[Int] = {
         List() ++
         (if (tree.leftChild.value.isDefined) heights(tree.leftChild,height +1) else List()) ++
         (if (tree.isLeaf) List(height) else List()) ++
         (if (tree.rightChild.value.isDefined) heights(tree.rightChild, height +1) else List()) ++
         List()
      }

        val itsHeights = heights(tree)
        val max = itsHeights.max
        val diffs = itsHeights map (elem => max - elem)
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
    } else {
      value = Some(t)
        updateHeight
    }

    def updateHeight = {
       // if(
          // rekursiv Höhe ändern
    }
    /*
    // height initial auf -1! oder doch nicht mehr?
    def updateHeight(newHeight: Int = 0):Unit = if(height < newHeight) {
      height = newHeight
      if (aParent.isDefined) {
        val parent = aParent.get
        parent.updateHeight(newHeight + 1)
      }
    } else rebalance()
    */
    
    def isElement(t: T):Boolean= {
        ((leftChild == null)&&(rightChild == null))
    }
    def isLeaf :Boolean =  ((!leftChild.value.isDefined)&&(!rightChild.value.isDefined))

    def balance :Unit = {
    
    }
    
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
