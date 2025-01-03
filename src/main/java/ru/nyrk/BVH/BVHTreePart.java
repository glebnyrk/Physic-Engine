package ru.nyrk.BVH;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nyrk.hitboxes.AABB;
import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;


public class BVHTreePart implements BVHChild {
    private BVHChild leftChild = null;
    private BVHChild rightChild = null;
    private int cache_deep = 0;
    private int cache_count = 0;
    private final AABB aabb;
    private Vector3 velocity;
    private BVHTreePart parent;
    private boolean deleteOrder = false;

    public BVHTreePart() {
        this(null, null);
    }

    public BVHTreePart(BVHChild left, BVHChild right) {
        setRightChild(right);
        setLeftChild(left);
        aabb = new AABB(Vector3.ZERO, Vector3.ZERO);
        fullParentUpdate();
    }

    public BVHTreePart(List<? extends BVHChild> children) {
        aabb = new AABB(Vector3.ZERO, Vector3.ZERO);
        for (BVHChild child : children) {
            insert(child);
        }
    }

    public void rebuild() {
        if (getDeep() > log2(getCount()) * 2) {
            serviceDeleteOrder();
            BVHTreePart part = new BVHTreePart(getList());
            setLeftChild(part.getLeftChild());
            setRightChild(part.getRightChild());
            fullTreeUpdate();
        }
    }

    void turnLeft(BVHTreePart a) {
        BVHChild b = a.getRightChild();
        if (b instanceof BVHTreePart bPart) {
            a.setRightChild(bPart.getLeftChild());
            bPart.setLeftChild(a);
        }
    }

    void turnRight(BVHTreePart a) {
        BVHChild b = a.getLeftChild();
        if (b instanceof BVHTreePart bPart) {
            a.setLeftChild(bPart.getRightChild());
            bPart.setRightChild(a);
        }
    }

    void bigTurnLeft(BVHTreePart a) {
        if (a.getRightChild() instanceof BVHTreePart aPart) {
            turnRight(aPart);
            turnLeft(a);
        }
    }

    void bigTurnRight(BVHTreePart a) {
        if (a.getLeftChild() instanceof BVHTreePart aPart) {
            turnLeft(aPart);
            turnRight(a);
        }
    }

    public static float log2(int N) {
        return (float) (Math.log(N) / Math.log(2));
    }

    int balanceFactor() {
        int leftHeight = (leftChild != null ? leftChild.getDeep() : 0);
        int rightHeight = (rightChild != null ? rightChild.getDeep() : 0);
        return (leftHeight - rightHeight);
    }

    @Override
    public float getAABBVolume() {
        return getAABB().getVolume();
    }

    @Override
    public AABB getAABB() {
        return aabb;
    }

    @Override
    public int getDeep() {
        return cache_deep;
    }

    public void updateDeep() {
        cache_deep = Math.max((leftChild != null ? leftChild.getDeep() : -1), (rightChild != null ? rightChild.getDeep() : -1)) + 1;
    }

    @Override
    public List<PhysicsBody> rawCollides(@NotNull PhysicsBody body) {
        List<PhysicsBody> collidedBodies = new ArrayList<>();
        rawCollidesRecursion(body, collidedBodies);
        return collidedBodies;
    }

    public void rawCollidesRecursion(@NotNull PhysicsBody body, List<PhysicsBody> alreadyAdded) {
        if (this.getAABB().collide(body.getAABB())) {
            if (leftChild != null) {
                if (leftChild instanceof BVHTreePart leftPart) {
                    leftPart.rawCollidesRecursion(body, alreadyAdded);
                } else {
                    alreadyAdded.addAll(leftChild.rawCollides(body));
                }
            }
            if (rightChild != null) {
                if (rightChild instanceof BVHTreePart rightPart) {
                    rightPart.rawCollidesRecursion(body, alreadyAdded);
                } else {
                    alreadyAdded.addAll(rightChild.rawCollides(body));
                }
            }
        }
    }

    @Nullable
    public BVHChild getLeftChild() {
        return leftChild;
    }

    @Nullable
    public BVHChild getRightChild() {
        return rightChild;
    }

    public void setLeftChild(@Nullable BVHChild newLeftChild) {
        if (newLeftChild != null) {
            newLeftChild.setParent(this);
        }
        this.leftChild = newLeftChild;
        if (newLeftChild == null) {
            if (parent != null) {
                parent.flipNull();
            }
        }
    }

    public void setRightChild(@Nullable BVHChild newRightChild) {
        if (newRightChild != null) {
            newRightChild.setParent(this);
        }
        this.rightChild = newRightChild;
        if (newRightChild == null) {
            if (parent != null) {
                parent.flipNull();
            }
        }
    }

    public void insert(BVHChild child) {
        if (leftChild == null) {
            //ставим влево если слева пусто
            setLeftChild(child);
        } else if (rightChild == null) {
            //ставим вправо если справа пусто
            setRightChild(child);
        } else {
            //определяем где оптимальнее втиснуть
            if (isLeftOptimal(child)) {
                if (leftChild instanceof BVHTreePart treePart) {
                    treePart.insert(child);
                } else {
                    setLeftChild(new BVHTreePart(child, leftChild));
                }
            } else {
                //справа лучше
                if (rightChild instanceof BVHTreePart treePart) {
                    treePart.insert(child);
                } else {
                    setRightChild(new BVHTreePart(child, rightChild));
                }
            }
        }
        fullParentUpdate();
    }

    public void fullParentUpdate() {
        updateAABB();
        updateDeep();
        updateCount();
        if (parent != null) {
            parent.fullParentUpdate();
            empty();
        }

    }

    public void fullTreeUpdate() {
        if (leftChild != null) {
            if (leftChild instanceof BVHTreePart treePart) {
                treePart.fullTreeUpdate();
            }
        }
        if (rightChild != null) {
            if (rightChild instanceof BVHTreePart treePart) {
                treePart.fullTreeUpdate();
            }
        }
        updateAABB();
        updateDeep();
        updateCount();
        if (parent != null) {
            empty();
        }
    }

    public void updateAABB() {
        aabb.reorganize(leftChild, rightChild);
    }

    private boolean isLeftOptimal(BVHChild toInsert) {
        float indexL = index(leftChild, toInsert);
        if (indexL == 0) {
            return true;
        }
        float indexR = index(rightChild, toInsert);
        if (indexR == 0) {
            return false;
        }
        return indexL < indexR;
    }

    private float index(BVHChild child, BVHChild toInsert) {

        return new AABB(child, toInsert).getVolume() * (float) child.getCount();
    }

    public void serviceDeleteOrder() {
        if (leftChild != null) {
            if (leftChild.isInDeleteOrder()) {
                setLeftChild(null);
                if (rightChild == null) {
                    yieldDelete();
                }
            } else {
                if (leftChild instanceof BVHTreePart leftPart) {
                    leftPart.serviceDeleteOrder();
                }
            }
        }
        if (rightChild != null) {
            if (rightChild.isInDeleteOrder()) {
                setRightChild(null);
                if (leftChild == null) {
                    yieldDelete();
                }
            } else {
                if (rightChild instanceof BVHTreePart rightPart) {
                    rightPart.serviceDeleteOrder();
                }
            }
        }
        fullParentUpdate();
    }

    public boolean deleteFromChildren(BVHChild child) {
        if (!empty()) {
            if (leftChild == child || leftChild.isInDeleteOrder()) {
                setLeftChild(null);
            } else if (rightChild == child || rightChild.isInDeleteOrder()) {
                setRightChild(null);
            } else {
                return false;
            }
        }
        fullParentUpdate();
        return true;
    }
    public void flipNull(){
        if (leftChild != null && leftChild instanceof BVHTreePart treePart) {
            if (rightChild == null){
                setRightChild(treePart.getRightChild());
                setLeftChild(treePart.getLeftChild());
                if (parent != null) {
                    parent.flipNull();
                }
            }
        }
        if (rightChild != null && rightChild instanceof BVHTreePart treePart) {
            if (leftChild == null){
                setLeftChild(treePart.getLeftChild());
                setRightChild(treePart.getRightChild());
                if (parent != null) {
                    parent.flipNull();
                }
            }
        }
    }
    public boolean parentalDelete(BVHChild child) {
        if (child != null) {
            BVHTreePart parent1 = child.getParent();
            if (parent1 != null) {
                boolean b = parent1.deleteFromChildren(child);
                return b;
            }
        }
        return false;
    }

    public List<PhysicsBody> getList() {
        List<PhysicsBody> list = new ArrayList<PhysicsBody>();
        getRawList(list);
        return list;
    }

    private void getRawList(List<PhysicsBody> toAdd) {
        if (empty()) {
            return;
        }
        if (leftChild != null) {
            if (leftChild instanceof PhysicsBody leftBody) {
                toAdd.add(leftBody);
            }
            if (leftChild instanceof BVHTreePart leftPart) {
                leftPart.getRawList(toAdd);
            }
        }
        if (rightChild != null) {
            if (rightChild instanceof PhysicsBody rightBody) {
                toAdd.add(rightBody);
            }
            if (rightChild instanceof BVHTreePart rightPart) {
                rightPart.getRawList(toAdd);
            }
        }
    }

    private boolean empty() {
        boolean b = getCount() == 0;
        if (b) {
            yieldDelete();
        }
        return b;
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        String spaces = spaces(indent);
        String spaces1 = spaces(indent + 1);
        return spaces + "BVH Part(" + (getDeep() + ", " + getCount() + ", " + getAABBVolume() + ", " + isInDeleteOrder()) + "){\n" +
                (leftChild != null ? leftChild.toString(indent + 1) : (spaces1 + "null")) + "\n" +
                (rightChild != null ? rightChild.toString(indent + 1) : (spaces1 + "null")) + "\n" + spaces + "};";
    }

    public void updateCount() {
        cache_count = (leftChild != null ? leftChild.getCount() : 0) +
                (rightChild != null ? rightChild.getCount() : 0);
    }

    @Override
    public int getCount() {
        return cache_count;
    }

    @Override
    public Vector3 getVelocity() {
        return velocity;
    }

    public void updateVelocity() {
        velocity = (leftChild != null ? leftChild.getVelocity() : Vector3.ZERO)
                .add(rightChild != null ? rightChild.getVelocity() : Vector3.ZERO)
                .mul(0.5f);
    }

    public String spaces(int i) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < i; j++) {
            builder.append("|   ");
        }
        return builder.toString();
    }

    @Override
    public Vector3 getCenter() {
        return getAABB().getCenter();
    }

    @Override
    public Vector3 getSize() {
        return getAABB().getSize();
    }

    @Override
    public BVHTreePart getParent() {
        return parent;
    }

    @Override
    public void setParent(BVHTreePart parent) {
        this.parent = parent;
    }

    @Override
    public void yieldDelete() {
        deleteOrder = true;
    }

    @Override
    public boolean isInDeleteOrder() {
        return deleteOrder;
    }
}
