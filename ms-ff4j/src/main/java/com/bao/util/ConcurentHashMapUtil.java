package com.bao.util;

import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

/**
 * 哈希表支持完全并发检索高期望并发更新。这个类遵从了Hashtable的相同功能特性。
 * 并且包含了Hashtable每个方法的各个版本的关联性。但是，即使所有操作都是线程安全的，
 * 检索操作也没有继承锁，而且没有对整个表进行锁定的任何支持以防止所有访问的方式。
 * 这个类与Hashtable在程序中是完全可共同使用，依靠其线程安全但不同步细节。
 *
 * 检索操作（包括get）一般不锁，因此可能与更新（包含put和remove）操作重叠。检索反映大多
 * 最近完成的更新操作holding upon their onset(更正式地，对于给定键的更新操作需要一个
 * happens-before关系在与任何（非空）检索之前用于该键报告更新值)。对于聚合操作例如putAll
 * 和clear，并发检索应该反映插入与删除仅仅一些实体。同样的，遍历、Spliterators、枚举返回元素
 * 反映哈希表在某些位置的状态或自创建的iterator/enumeration。它们不抛出ConcurrentModificationException。
 * 无论如何，遍历设计为一次仅被一个线程使用。记住聚合状态方法的结果包括size，isEmpty，containsValue
 * 都是典型使用仅当一个map没有在其他线程中经历并发更新。否则，这些方法的结果反映瞬态状态
 * 这可能是足够的监测或估计的目的，但不是用于程序控制。
 *
 * table动态扩展当有有太多碰撞（即，有不同的哈希码，但落入相同的插槽，取表size的模时），
 * 与预期的平均每次映射保持大致两个箱的效果（对应到0.75负载因子阈值调整大小）。可能有很多
 * 在这个平均值的变量作为映射被添加和删除，但总体而言，这维护了一个普遍接受的时间/空间权衡哈希表。
 * 但是，调整这个或任何其他类型的哈希表可能是一个相对缓慢的操作。可能的话，
 * 它是好主意，提供一个大小估计作为一个可选的initialCapacity构造函数的参数。
 * 另外一个可选的loadFactor构造函数的参数提供了一个进一步的手段，
 * 通过指定表密度自定义初始表容量用于计算分配的空间数量给定的元素数，
 * 也与以前的兼容性这个类的版本，构造函数可以选择性地指定预计concurrencyLevel作为
 * 一个额外的提示内部尺寸。注意使用许多键拥有完全相同hashcode()是一个肯定的方式来
 * 降低任何哈希表的性能。为了改善影响，当键是Comparable，这个类可以使用键之间的比较顺序，
 * 以帮助打破关系。
 *
 * ConcurrentHashMap的Set设计可已被创建通过newKeySet()或者newKeySet(int)，或者使用
 * keySet(Object)查看仅当键是有趣的，映射的值（可能临时的）没有被使用或者取得相同的映射值。
 *
 * ConcurrentHashMap可用来作为可频繁扩展的map（一种histogram或者multiset）通过使用LongAdder
 * 的值和通过computeIfAbsent初始化。例如，添加计数到ConcurrentHashMap<String,LongAdder> freqs，
 * 你可以使用freqs.computeIfAbsent(k -> new LongAdder()).increment();
 *
 * 这个类和它的视图与迭代器实现所有Map与Iterator的optional方法
 *
 * 与Hashtable类似，不像HashMap，这个类不允许null作为key或者value。
 *
 * ConcurrentHashMaps支持一组串行和并行块操作，不像大多数Stream方法，
 * 被设计为安全的，可感知的，即使maps被其他线程并发更新；
 * 例如，当计算共享注册表中快照摘要的值时，有三种操作，每种有四种形式，
 * 接受具有键、值、实体和（键、值）参数的函数和/或返回值。
 * 因为ConcurrentHashMap的元素不以任何特定方式排序。
 * 并可在不同顺序在不同并行执行中处理，提供的功能正确性不依赖与任何任何顺序，或
 * 或其他对象或值被临时改变当在计算过程中；期望forEach动作，理想是无副作用。
 * Entry块操作不支持setValue方法
 *
 * forEach：执行一个给定的操作在每个元素上。
 * 一个变化的形式是在执行操作之前应用一个给定转换在每个元素上。
 *
 * search：返回第一个可用非空的值在对每个元素执行一个给定函数；当有结果时跳过
 * 深入的搜索。
 *
 * reduce：累计每个元素。提供的reduction函数不能依赖与排序（更正式的是，
 * 它应该联合和交换），有五种变化：
 *
 * Plain reductions。（这里不是一种(key, value)函数参数方法，因此这里没有相应的返回类型）
 *
 * Mapped reductions累计在每个元素上给定函数的结果
 *
 * Reductions扩展doubles, longs, and ints,使用一个给定值。
 *
 * 这些块操作接受一个parallelismThreshold参数。
 * 如果当前map大小估算小于给定阈值，则方法继续进行。
 * 使用值Long.MAX_VALUE抑制所有并行。
 * 使用一个值为1的结果最大并行度划分子任务的足够充分利用ForkJoinPool#commonPool()是用于所有的并行计算。
 * 通常，您会首先选择这些极端值中的一个，然后测量在权衡开销与吞吐量之间使用的值的性能。
 *
 * 块操作的并发性能遵循那些ConcurrentHashMap：任何非空返回的结果，
 * get(key和相关的访问方法期望happens-before与插入或更新。
 * 任何块操作的结果反映了每个元素关系的组成（但不一定是原子相对于map的整体而言，除非它被认为是静止的）。
 * 相反，因为map中的键和值从不为空，NULL作为当前任何结果缺乏的可靠的原子指示器。
 * 若要维护此属性，空作为所有非标量reduction操作的隐式基。
 * 对于double, long和int版本，基础应该是一个，当与任何其他值相结合，
 * 返回其他值（更正式地，它应该是reduction的身份元素）。
 * 最常见的reductions有这些属性；例如，计算总和与基础或基础max_value最低0。
 *
 * 作为参数提供的搜索和转换函数同样应返回NULL，以指示缺少任何结果（在这种情况下，它不使用）。
 * 在映射reductions的情况下，这也使转换作为过滤器，返回null（或在原始专业，认同基础的情况下），
 * 如果不应该结合的元素。您可以创建复合变换和滤波的组成他们自己在这种“
 * 零意味着在使用它们之前在搜索或减少操作也没有现在的“规则。
 *
 * 方法接受和/或返回输入参数维护键值关联。他们可能是有用的，
 * 例如，当找到最大的价值的关键。请注意，“平原”的输入参数可以提供使用新的抽象。SimpleEntry（K，V）。
 *
 * 批量操作可能会突然完成，引发在提供的应用程序中遇到的异常。
 * 记住在处理这些异常时，其他并发执行的函数也可能抛出异常，
 * 或者如果没有发生第一个异常，则会这样做。
 *
 * 加速并行比较序列的形式是常见的但不保证。
 * 并行操作涉及小地图简单函数可以执行速度比顺序形式如果基础工作并行计算比计算本身更贵。
 * 同样，并行化可能不会导致太多的实际并行，如果所有的处理器都忙于执行无关的任务。
 *
 * 所有任务方法的所有参数必须是非空的。
 *
 * 这个类是Collections的一员。
 *
 */
public class ConcurentHashMapUtil<K,V> {
//        extends AbstractMap<K,V>
//        implements ConcurrentMap<K,V>, Serializable {
//    private static final long serialVersionUID;

    /*
     * 概述:
     *
     * 这个哈希表的首要设计目标是去维护并发可读性（典型方法get()，还有迭代器和相关方法）
     * 当减少更新竞争。第二目标是比HashMap保持空间消费一致或更佳，并支持
     * 通过多线程在一个空table上提高初始化插入速率。
     *
     * 这个map通常扮演一个分段binned（桶装bucketed）哈希表。每个key-value对存储在Node中。
     * 多数nodes是基础Node类（hash, key, value, and next fields）实例。
     * 无论如何，不同的子类存在：TreeNodes被安排为为平衡树为非list。
     * TreeBins持有TreeNodes集合的根。在resizing时ForwardingNodes被放置在bins的头部。
     * ReservationNodes用来作为占位符当建立值在使用computeIfAbsent和相关方法时。
     * TreeBin，ForwardingNode，ReservationNode不存储正常的用户keys，values或hashes，
     * 并且很容易区分在搜索时，因为它们有负哈希字段和空键值字段。
     * （这些特殊节点是不寻常的或短暂的，所以携带一些未使用的字段的影响是微不足道的）
     *
     * 该table在第一次插入时被初始化为2的幂大小。
     * 表中的每个bin通常包含一个节点列表（通常情况下，列表只有零或一个节点）。
     * 表访问需要volatile/atomic 读、写和CASes。因为没有其他的方式来安排它，不加入更多的间接的话，
     * 我们使用内联函数（sun.misc.Unsafe）操作
     *
     * 我们使用顶级（符号）位的节点散列字段进行控制目的--反正它是可用的，因为寻址约束。
     * 具有负散列字段的节点在map中是特别处理或忽略。
     *
     * 插入（通过放置或其变种）的第一个节点在一个空的bin进行，仅仅CASing它到bin。
     * 这是最常见的情况下，在大多数key/has分布的put操作。
     * 其他更新操作（insert,delete, and replace）需要锁。我们不想浪费将一个唯一的锁对象与每个bin关联的空间，
     * 因此使用bin列表本身的第一个节点作为锁。锁定这些锁支持依赖于内置的“同步”显示器。
     *
     * 使用list的第一个节点作为锁而不是它本身已经足够了：当一个节点被锁，
     * 任何更新在锁之后必须先校验它仍是第一个节点，
     * 如果不是则重试。因为新当节点一直是附加到list中，一旦节点在一个bin中是第一个，
     * 它将保持第一个位置直到删除或者bin变为不可用（在resizing时）
     *
     * 主要到缺点是每个bin锁是其他更新操作在其他节点在一个bin list中被相同锁保护可以延缓，
     * 例如当使用equals()或者mapping函数时间较长。
     * 无论如何，统计上来说，在随机hash code，这不是常见当问题。
     * 理想状态，在bin中node的频率服从柏松分布，平均参数为0.5，阀值0.75
     *
     * The main disadvantage of per-bin locks is that other update
     * operations on other nodes in a bin list protected by the same
     * lock can stall, for example when user equals() or mapping
     * functions take a long time.  However, statistically, under
     * random hash codes, this is not a common problem.  Ideally, the
     * frequency of nodes in bins follows a Poisson distribution
     * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
     * parameter of about 0.5 on average, given the resizing threshold
     * of 0.75, although with a large variance because of resizing
     * granularity. Ignoring variance, the expected occurrences of
     * list size k are (exp(-0.5) * pow(0.5, k) / factorial(k)). The
     * first values are:
     *
     * 0:    0.60653066
     * 1:    0.30326533
     * 2:    0.07581633
     * 3:    0.01263606
     * 4:    0.00157952
     * 5:    0.00015795
     * 6:    0.00001316
     * 7:    0.00000094
     * 8:    0.00000006
     * more: less than 1 in ten million
     *
     * Lock contention probability for two threads accessing distinct
     * elements is roughly 1 / (8 * #elements) under random hashes.
     *
     * Actual hash code distributions encountered in practice
     * sometimes deviate significantly from uniform randomness.  This
     * includes the case when N > (1<<30), so some keys MUST collide.
     * Similarly for dumb or hostile usages in which multiple keys are
     * designed to have identical hash codes or ones that differs only
     * in masked-out high bits. So we use a secondary strategy that
     * applies when the number of nodes in a bin exceeds a
     * threshold. These TreeBins use a balanced tree to hold nodes (a
     * specialized form of red-black trees), bounding search time to
     * O(log N).  Each search step in a TreeBin is at least twice as
     * slow as in a regular list, but given that N cannot exceed
     * (1<<64) (before running out of addresses) this bounds search
     * steps, lock hold times, etc, to reasonable constants (roughly
     * 100 nodes inspected per operation worst case) so long as keys
     * are Comparable (which is very common -- String, Long, etc).
     * TreeBin nodes (TreeNodes) also maintain the same "next"
     * traversal pointers as regular nodes, so can be traversed in
     * iterators in the same way.
     *
     * The table is resized when occupancy exceeds a percentage
     * threshold (nominally, 0.75, but see below).  Any thread
     * noticing an overfull bin may assist in resizing after the
     * initiating thread allocates and sets up the replacement array.
     * However, rather than stalling, these other threads may proceed
     * with insertions etc.  The use of TreeBins shields us from the
     * worst case effects of overfilling while resizes are in
     * progress.  Resizing proceeds by transferring bins, one by one,
     * from the table to the next table. However, threads claim small
     * blocks of indices to transfer (via field transferIndex) before
     * doing so, reducing contention.  A generation stamp in field
     * sizeCtl ensures that resizings do not overlap. Because we are
     * using power-of-two expansion, the elements from each bin must
     * either stay at same index, or move with a power of two
     * offset. We eliminate unnecessary node creation by catching
     * cases where old nodes can be reused because their next fields
     * won't change.  On average, only about one-sixth of them need
     * cloning when a table doubles. The nodes they replace will be
     * garbage collectable as soon as they are no longer referenced by
     * any reader thread that may be in the midst of concurrently
     * traversing table.  Upon transfer, the old table bin contains
     * only a special forwarding node (with hash field "MOVED") that
     * contains the next table as its key. On encountering a
     * forwarding node, access and update operations restart, using
     * the new table.
     *
     * Each bin transfer requires its bin lock, which can stall
     * waiting for locks while resizing. However, because other
     * threads can join in and help resize rather than contend for
     * locks, average aggregate waits become shorter as resizing
     * progresses.  The transfer operation must also ensure that all
     * accessible bins in both the old and new table are usable by any
     * traversal.  This is arranged in part by proceeding from the
     * last bin (table.length - 1) up towards the first.  Upon seeing
     * a forwarding node, traversals (see class Traverser) arrange to
     * move to the new table without revisiting nodes.  To ensure that
     * no intervening nodes are skipped even when moved out of order,
     * a stack (see class TableStack) is created on first encounter of
     * a forwarding node during a traversal, to maintain its place if
     * later processing the current table. The need for these
     * save/restore mechanics is relatively rare, but when one
     * forwarding node is encountered, typically many more will be.
     * So Traversers use a simple caching scheme to avoid creating so
     * many new TableStack nodes. (Thanks to Peter Levart for
     * suggesting use of a stack here.)
     *
     * The traversal scheme also applies to partial traversals of
     * ranges of bins (via an alternate Traverser constructor)
     * to support partitioned aggregate operations.  Also, read-only
     * operations give up if ever forwarded to a null table, which
     * provides support for shutdown-style clearing, which is also not
     * currently implemented.
     *
     * Lazy table initialization minimizes footprint until first use,
     * and also avoids resizings when the first operation is from a
     * putAll, constructor with map argument, or deserialization.
     * These cases attempt to override the initial capacity settings,
     * but harmlessly fail to take effect in cases of races.
     *
     * The element count is maintained using a specialization of
     * LongAdder. We need to incorporate a specialization rather than
     * just use a LongAdder in order to access implicit
     * contention-sensing that leads to creation of multiple
     * CounterCells.  The counter mechanics avoid contention on
     * updates but can encounter cache thrashing if read too
     * frequently during concurrent access. To avoid reading so often,
     * resizing under contention is attempted only upon adding to a
     * bin already holding two or more nodes. Under uniform hash
     * distributions, the probability of this occurring at threshold
     * is around 13%, meaning that only about 1 in 8 puts check
     * threshold (and after resizing, many fewer do so).
     *
     * TreeBins use a special form of comparison for search and
     * related operations (which is the main reason we cannot use
     * existing collections such as TreeMaps). TreeBins contain
     * Comparable elements, but may contain others, as well as
     * elements that are Comparable but not necessarily Comparable for
     * the same T, so we cannot invoke compareTo among them. To handle
     * this, the tree is ordered primarily by hash value, then by
     * Comparable.compareTo order if applicable.  On lookup at a node,
     * if elements are not comparable or compare as 0 then both left
     * and right children may need to be searched in the case of tied
     * hash values. (This corresponds to the full list search that
     * would be necessary if all elements were non-Comparable and had
     * tied hashes.) On insertion, to keep a total ordering (or as
     * close as is required here) across rebalancings, we compare
     * classes and identityHashCodes as tie-breakers. The red-black
     * balancing code is updated from pre-jdk-collections
     * (http://gee.cs.oswego.edu/dl/classes/collections/RBCell.java)
     * based in turn on Cormen, Leiserson, and Rivest "Introduction to
     * Algorithms" (CLR).
     *
     * TreeBins also require an additional locking mechanism.  While
     * list traversal is always possible by readers even during
     * updates, tree traversal is not, mainly because of tree-rotations
     * that may change the root node and/or its linkages.  TreeBins
     * include a simple read-write lock mechanism parasitic on the
     * main bin-synchronization strategy: Structural adjustments
     * associated with an insertion or removal are already bin-locked
     * (and so cannot conflict with other writers) but must wait for
     * ongoing readers to finish. Since there can be only one such
     * waiter, we use a simple scheme using a single "waiter" field to
     * block writers.  However, readers need never block.  If the root
     * lock is held, they proceed along the slow traversal path (via
     * next-pointers) until the lock becomes available or the list is
     * exhausted, whichever comes first. These cases are not fast, but
     * maximize aggregate expected throughput.
     *
     * Maintaining API and serialization compatibility with previous
     * versions of this class introduces several oddities. Mainly: We
     * leave untouched but unused constructor arguments refering to
     * concurrencyLevel. We accept a loadFactor constructor argument,
     * but apply it only to initial table capacity (which is the only
     * time that we can guarantee to honor it.) We also declare an
     * unused "Segment" class that is instantiated in minimal form
     * only when serializing.
     *
     * Also, solely for compatibility with previous versions of this
     * class, it extends AbstractMap, even though all of its methods
     * are overridden, so it is just useless baggage.
     *
     * This file is organized to make things a little easier to follow
     * while reading than they might otherwise: First the main static
     * declarations and utilities, then fields, then main public
     * methods (with a few factorings of multiple public methods into
     * internal ones), then sizing methods, trees, traversers, and
     * bulk operations.
     */


     /* ---------------- Constants -------------- */

    /**
     * The largest possible table capacity.  This value must be
     * exactly 1<<30 to stay within Java array allocation and indexing
     * bounds for power of two table sizes, and is further required
     * because the top two bits of 32bit hash fields are used for
     * control purposes.
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The default initial table capacity.  Must be a power of 2
     * (i.e., at least 1) and at most MAXIMUM_CAPACITY.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * The largest possible (non-power of two) array size.
     * Needed by toArray and related methods.
     */
    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * The default concurrency level for this table. Unused but
     * defined for compatibility with previous versions of this class.
     */
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

    /**
     * The load factor for this table. Overrides of this value in
     * constructors affect only the initial table capacity.  The
     * actual floating point value isn't normally used -- it is
     * simpler to use expressions such as {@code n - (n >>> 2)} for
     * the associated resizing threshold.
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2, and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * The value should be at least 4 * TREEIFY_THRESHOLD to avoid
     * conflicts between resizing and treeification thresholds.
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * Minimum number of rebinnings per transfer step. Ranges are
     * subdivided to allow multiple resizer threads.  This value
     * serves as a lower bound to avoid resizers encountering
     * excessive memory contention.  The value should be at least
     * DEFAULT_CAPACITY.
     */
    private static final int MIN_TRANSFER_STRIDE = 16;

    /**
     * The number of bits used for generation stamp in sizeCtl.
     * Must be at least 6 for 32bit arrays.
     */
    private static int RESIZE_STAMP_BITS = 16;

    /**
     * The maximum number of threads that can help resize.
     * Must fit in 32 - RESIZE_STAMP_BITS bits.
     */
    private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;

    /**
     * The bit shift for recording size stamp in sizeCtl.
     */
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

    /*
     * Encodings for Node hash fields. See above for explanation.
     */
    static final int MOVED     = -1; // hash for forwarding nodes
    static final int TREEBIN   = -2; // hash for roots of trees
    static final int RESERVED  = -3; // hash for transient reservations
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    /** Number of CPUS, to place bounds on some sizings */
    static final int NCPU = Runtime.getRuntime().availableProcessors();


}
