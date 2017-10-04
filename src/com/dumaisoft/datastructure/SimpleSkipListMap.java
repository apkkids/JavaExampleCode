package com.dumaisoft.datastructure;

import java.util.*;

/**
 * SkipList的简单实现，参考了ConcurrentSkipListMap的JDK1.7版本，本类是多线程不安全的
 * 去掉了ConcurrentNavigableMap接口，因此减少了很多关于排序、子集的方法
 * 增加了printContent方法，用于调试，可打印出所有索引和节点信息
 */
public class SimpleSkipListMap<K, V> extends AbstractMap<K, V> implements Cloneable, java.io.Serializable {

    public static void main(String[] args) {
        SimpleSkipListMap<Integer, String> map = new SimpleSkipListMap<>();
        System.out.println("============== test put ================");
        for (int i = 0; i < 50; i++) {
            map.put(i, "Alex" + i);
        }
        map.printContent();

        System.out.println("============== test get(5) ================");
        System.out.println(map.get(5));


        System.out.println("=========== test remove 5 ============");
        map.remove(5);
        map.printContent();

        System.out.println("=========== test size and isEmpty ============");
        System.out.println(map.size());
        System.out.println(map.isEmpty());


        System.out.println("============== test keyset ================");
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        System.out.println("============== test values ================");
        Iterator<String> stringIterator = map.values().iterator();
        while (stringIterator.hasNext()) {
            System.out.print(stringIterator.next()+" ");
        }
        System.out.println();

        System.out.println("============== test entrySet ================");
        Iterator<Map.Entry<Integer, String>> entryIterator = map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            System.out.print(entryIterator.next()+" ");
        }
        System.out.println();
    }

    /**
     * 种子生成器
     */
    private static final Random seedGenerator = new Random();

    /**
     * Special value used to identify base-level header，节点头部
     */
    private static final Object BASE_HEADER = new Object();

    /**
     * The topmost head index of the skiplist，最高层的索引头（索引最多31层），插入节点时，每次最多增加一层
     */
    private transient volatile HeadIndex<K, V> head;

    /**
     * 用来排序的Comparator，若为空则使用自然排序
     */
    private final Comparator<? super K> comparator;

    /**
     * 随机种子
     */
    private transient int randomSeed;

    /**
     * Lazily initialized key set
     */
    private transient KeySet keySet;
    /**
     * Lazily initialized entry set
     */
    private transient EntrySet entrySet;
    /**
     * Lazily initialized values collection
     */
    private transient Values values;

    /**
     * 初始化Map
     */
    final void initialize() {
//        keySet = null;
//        entrySet = null;
//        values = null;
        randomSeed = seedGenerator.nextInt() | 0x0100; // ensure nonzero
        head = new HeadIndex<K, V>(new Node<K, V>(null, BASE_HEADER, null),
                null, null, 1);
    }

    public SimpleSkipListMap() {
        this.comparator = null;
        initialize();
    }

    public SimpleSkipListMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
        initialize();
    }

    public SimpleSkipListMap(Map<? extends K, ? extends V> m) {
        this.comparator = null;
        initialize();
        putAll(m);
    }

    /**
     * 节点类，注意两点：1.value为null时，表示此节点已经删除；2.value的类型为Object是因为一些特殊情况它不为V类型，例如BASE_HEADER
     */
    static final class Node<K, V> {
        final K key;
        Object value;
        Node<K, V> next;

        Node(K key, Object value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        V getValidValue() {
            Object v = value;
            if (v == null || v == BASE_HEADER)
                return null;
            return (V) v;
        }

        @Override
        public String toString() {
            return "{" + key + "," + value + "}";
        }
    }

    /**
     * 索引类
     */
    static class Index<K, V> {
        Node<K, V> node;  //指向被索引的节点
        Index<K, V> down; //指向下一层的索引，该索引都是node的索引
        Index<K, V> right; //指向同层右侧（后续）的索引

        Index(Node<K, V> node, Index<K, V> down, Index<K, V> right) {
            this.node = node;
            this.down = down;
            this.right = right;
        }
    }

    /**
     * 索引头类，增加了一个层次level
     */
    static final class HeadIndex<K, V> extends Index<K, V> {
        int level;

        HeadIndex(Node<K, V> node, Index<K, V> down, Index<K, V> right, int level) {
            super(node, down, right);
            this.level = level;
        }
    }

    /**
     * 用于调试的方法，按层打印出所有的索引和节点
     */
    public void printContent() {
        Index<K, V> h = head;
        for (int i = head.level; i > 0; i--) {
            System.out.println("Index level:" + i);
            for (Index<K, V> idx = h; idx != null; idx = idx.right) {
                if (idx.node.value != BASE_HEADER && idx.node.value != null) {
                    System.out.print(idx.node + " ");
                }
            }
            System.out.println();
            h = h.down;
        }
        System.out.println("Node List:");
        for (Node<K, V> node = head.node.next; node != null; node = node.next) {
            if (node.value != null) {
                System.out.print(node + " ");
            }
        }
        System.out.println();
    }
        /* ---------------- Comparison utilities -------------- */

    /**
     * 使用一个Comarator构造一个Comparable
     */
    static final class ComparableUsingComparator<K> implements Comparable<K> {
        final K actualKey;
        final Comparator<? super K> cmp;

        ComparableUsingComparator(K key, Comparator<? super K> cmp) {
            this.actualKey = key;
            this.cmp = cmp;
        }

        public int compareTo(K k2) {
            return cmp.compare(actualKey, k2);
        }
    }

    /**
     * 使用key来构造一个Comparable对象
     */
    private Comparable<? super K> comparable(Object key)
            throws ClassCastException {
        if (key == null)
            throw new NullPointerException();
        if (comparator != null)
            return new ComparableUsingComparator<K>((K) key, comparator);
        else
            return (Comparable<? super K>) key;
    }

    /**
     * 比较两个key，使用comparator或者自然排序
     */
    int compare(K k1, K k2) throws ClassCastException {
        Comparator<? super K> cmp = comparator;
        if (cmp != null)
            return cmp.compare(k1, k2);
        else
            return ((Comparable<? super K>) k1).compareTo(k2);
    }

    /* ---------------- put、remove、update、get utilities -------------- */

    /**
     * 新添加一个节点，在遍历的同时删除已经无效的节点（value==null）；在添加时如果key与链表中的某个节点的key相等，则直接替换
     *
     * @param kkey  插入的key值
     * @param value 插入的value值
     * @return 被替换节点的原value值，若无则返回null
     */
    public V put(K kkey, V value) {
        Comparable<? super K> key = comparable(kkey);
        Node<K, V> b = findPredecessor(key);   //一般用b来表示前序节点
        Node<K, V> n = b.next;                  //一般用n来表示当前节点
        Node<K, V> f = null;                    //一般用f来表示后续节点
        for (; ; ) {
            if (n != null) {
                f = n.next;
                if (n.value == null) {      //如果n已经被删除,从链表中删除
                    b.next = f;
                    n = f;
                    continue;
                }
                int c = key.compareTo(n.key);
                if (c > 0) {
                    b = n;
                    n = n.next;
                    continue;
                }
                if (c == 0) {  //如果key相等，直接替换value
                    V oldValue = (V) n.value;
                    n.value = value;
                    return oldValue;
                }
            } else
                break;
        }
        Node<K, V> z = new Node<>(kkey, value, n);
        b.next = z;
        int level = randomLevel();
        if (level > 0)
            insertIndex(z, level);
        return null;
    }

    /**
     * 为新加入的节点建立索引，注意，每次总索引最多增加一层
     *
     * @param z     节点
     * @param level 节点层级
     */
    private void insertIndex(Node<K, V> z, int level) {
        HeadIndex<K, V> h = head;
        int max = h.level;
        if (max >= level) {
            Index<K, V> idx = null;
            //从第1层到第level层建立索引，并设置好索引的down引用
            for (int i = 1; i <= level; i++) {
                idx = new Index<>(z, idx, null);
            }
            addIndex(idx, h, level);  //设置索引的right引用
        } else {
            //总索引层+1，重新设置head
            level = max + 1;
            HeadIndex<K, V> tmpHead = new HeadIndex<>(head.node, head, null, level);
            head = tmpHead;
            Index<K, V> idx = null;
            //从第1层到第max+1层建立索引，并设置好索引的down引用
            for (int i = 1; i <= level; i++) {
                idx = new Index<>(z, idx, null);
            }
            addIndex(idx, head, level);
        }
    }

    /**
     * 将插入的索引的right引用在索引结构的每一层中设置好，本方法仅在insertIndex方法中被调用，本方法还在遍历的同时删除无效节点的索引
     *
     * @param idx        插入的索引
     * @param h          当前的索引头
     * @param indexLevel 被插入索引的层级
     */
    private void addIndex(Index<K, V> idx, HeadIndex<K, V> h, int indexLevel) {
        int insertionLevel = indexLevel;  //当前要设置的索引的层次
        Comparable<? super K> key = comparable(idx.node.key);
        if (key == null) throw new NullPointerException();
        Index<K, V> q = h;
        Index<K, V> r = q.right;
        Index<K, V> t = idx;
        int j = h.level;      //索引遍历的层次
        for (; ; ) {
            if (r != null) {
                Node<K, V> n = r.node;
                int c = key.compareTo(n.key);
                if (n.value == null) {   //如果n已经被删除
                    q.right = r.right;
                    r = q.right;
                    continue;
                }
                if (c > 0) {  //c>0则同层向右搜索
                    q = r;
                    r = q.right;
                    continue;
                }
            }
            //此处开始设置每层的t的right，将t插入至q和r之间
            if (j == insertionLevel) {  //如果当前遍历的索引层次等于要设置索引的层次
                q.right = t;
                t.right = r;
                if (--insertionLevel == 0) {
                    return;
                }
            }
            if (--j >= insertionLevel && j < indexLevel) //如果j处于适当的层次，要设置的索引t下降一层
                t = t.down;
            q = q.down;   //要索引遍历的层次下降
            r = q.right;
        }
    }

    /**
     * 为新加入的节点计算索引层级，k=1, 每一层的概率p=0.5, max 31,照抄了JDK1.7中的实现
     *
     * @return 根据概率计算出的索引层级
     */
    private int randomLevel() {
        int x = randomSeed;
        x ^= x << 13;
        x ^= x >>> 17;
        randomSeed = x ^= x << 5;
        if ((x & 0x80000001) != 0) // test highest and lowest bits
            return 0;
        int level = 1;
        while (((x >>>= 1) & 1) != 0) ++level;
        return level;
    }

    /**
     * 找到前序节点，遍历索引的过程中删除作废的索引（已删除节点的索引）
     *
     * @param key
     * @return 前序节点，若没有找到则返回HEAD
     */
    private Node<K, V> findPredecessor(Comparable<? super K> key) {
        if (key == null)
            throw new NullPointerException(); // don't postpone errors
        Index<K, V> q = head;         //一般用q来表示前一个索引
        Index<K, V> r = q.right;      //一般用r来表示q的right索引
        for (; ; ) {
            if (r != null) {
                Node<K, V> n = r.node;
                K k = n.key;
                //处理作废的索引
                if (n.value == null) {
                    q.right = r.right;
                    r = q.right;
                    continue;
                }
                int c = key.compareTo(k);
                if (c > 0) {   //同层向后遍历
                    q = r;
                    r = r.right;
                    continue;
                }
            }
            //下降一层
            Index<K, V> d = q.down;
            if (d != null) {
                q = d;
                r = q.right;
            } else
                return q.node;
        }
    }

    /**
     * 根据key寻找匹配的节点,过程中删除无效的节点
     *
     * @param key
     * @return 找到则返回匹配的节点，否则返回null
     */
    private Node<K, V> findNode(Comparable<? super K> key) {
        Node<K, V> b = findPredecessor(key);
        Node<K, V> n = b.next;
        for (; ; ) {
            if (n != null) {
                if (n.value == null) {
                    b.next = n.next;
                    n = b.next;
                    continue;
                }
                int c = key.compareTo(n.key);
                if (c > 0) {
                    n = n.next;
                    continue;
                }
                if (c == 0) {
                    return n;
                }
            }
            return null;
        }
    }

    /**
     * 寻找第一个有效节点
     *
     * @return
     */
    Node<K, V> findFirst() {
        for (; ; ) {
            Node<K, V> b = head.node;
            Node<K, V> n = b.next;
            if (n == null)
                return null;
            if (n.value != null)
                return n;
        }
    }

    /**
     * 通过key寻找相应的节点，并返回节点值
     *
     * @param kkey
     * @return
     */
    public V get(Object kkey) {
        Comparable<? super K> key = comparable(kkey);
        Node<K, V> n = findNode(key);
        if (n.value != null) {
            return (V) n.value;
        }
        return null;
    }

    /**
     * 删除key指向的节点，删除时直接将找到的节点的value设置为null即可，后续遍历操作会逐步删除节点和它的各级索引
     *
     * @param kkey
     * @return 成功则返回原节点的值，否则返回null
     */
    public V remove(Object kkey) {
        Comparable<? super K> key = comparable(kkey);
        Node<K, V> b = findPredecessor(key);
        Node<K, V> n = b.next;
        for (; ; ) {
            if (n != null) {
                if (n.value == null) {
                    b.next = n.next;
                    n = b.next;
                    continue;
                }
                int c = key.compareTo(n.key);
                if (c > 0) {
                    b = n;
                    n = n.next;
                    continue;
                }
                if (c == 0) {
                    //通过将value设置为null使得该节点作废，在后续的各种遍历操作中会逐步删除节点以及它的索引
                    V v = (V) n.value;
                    n.value = null;
                    return v;
                }
            }
            return null;
        }
    }

    public int size() {
        int i = 0;
        for (Node<K, V> n = findFirst(); n != null; n = n.next) {
            if (n.value != null) {
                i++;
            }
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return findFirst() != null;
    }

   /* ---------------- Iterators -------------- */

    /**
     * 三种Iterator类的虚基类,没有实现next方法，留待子类实现
     */
    abstract class Iter<T> implements Iterator<T> {
        /**
         * the last node returned by next()
         */
        Node<K, V> lastReturned;
        /**
         * the next node to return from next();
         */
        Node<K, V> next;
        /**
         * Cache of next value field to maintain weak consistency
         */
        V nextValue;

        Iter() {
            next = findFirst();
            nextValue = (V) next.value;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        /**
         * 移动到下一个节点，在next()中使用
         */
        public void advance() {
            if (next == null)
                throw new NoSuchElementException();
            lastReturned = next;
            for (; ; ) {
                next = next.next;
                if (next == null) {
                    break;
                }
                Object x = next.value;
                if (x != null) {
                    nextValue = (V) x;
                    break;
                }
            }
        }

        @Override
        public void remove() {
            Node<K, V> l = lastReturned;
            if (l == null)
                throw new IllegalStateException();
            SimpleSkipListMap.this.remove(l.key);
            lastReturned = null;
        }
    }

    /**
     * 三种Iterator类的具体实现类
     */
    final class KeyIterator extends Iter<K> {
        @Override
        public K next() {
            Node<K, V> node = next;
            advance();
            return node.key;
        }
    }

    final class ValueIterator extends Iter<V> {
        @Override
        public V next() {
            Node<K, V> node = next;
            advance();
            return (V) node.value;
        }
    }

    final class EntryIterator extends Iter<Map.Entry<K, V>> {
        @Override
        public Entry<K, V> next() {
            Node<K, V> node = next;
            advance();
            return new SimpleImmutableEntry<K, V>(node.key, (V) node.value);
        }
    }

    private Iterator<K> keyIterator() {
        return new KeyIterator();
    }

    private Iterator<V> valueIterator() {
        return new ValueIterator();
    }

    private Iterator<Map.Entry<K, V>> entryIterator() {
        return new EntryIterator();
    }

    /* ----------------KeySet Values EntrySet utilities -------------- */
    //内部类，KeySet
    static final class KeySet<E> extends AbstractSet<E> {
        private final SimpleSkipListMap<E, Object> m;

        KeySet(SimpleSkipListMap<E, Object> m) {
            this.m = m;
        }

        @Override
        public Iterator<E> iterator() {
            return m.keyIterator();
        }

        @Override
        public int size() {
            return m.size();
        }
    }

    @Override
    public Set<K> keySet() {
        KeySet ks = keySet;
        return (ks != null) ? ks : (keySet = new KeySet(this));
    }

    //内部类，Values
    static final class Values<E> extends AbstractCollection<E> {
        private final SimpleSkipListMap<Object, E> m;

        Values(SimpleSkipListMap<Object, E> m) {
            this.m = m;
        }

        @Override
        public Iterator<E> iterator() {
            return m.valueIterator();
        }

        @Override
        public int size() {
            return m.size();
        }
    }

    @Override
    public Collection<V> values() {
        Values vs = values;
        return (vs != null) ? vs : (values = new Values(this));
    }

    //内部类，EntrySet
    static final class EntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {
        private final SimpleSkipListMap<K, V> m;

        EntrySet(SimpleSkipListMap<K, V> m) {
            this.m = m;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return m.entryIterator();
        }

        @Override
        public int size() {
            return m.size();
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        EntrySet<K, V> es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet(this));
    }
}
