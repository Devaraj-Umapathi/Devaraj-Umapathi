# HashMap – Low Level Design (LLD)

A from-scratch generic `HashMap<K, V>` implemented in Java. Supports `put`, `get`, `remove`, `containsKey`, `size`, and dynamic resizing. Collisions are handled with **separate chaining** (a linked list per bucket), and the underlying bucket array grows automatically once the load factor threshold is crossed.

---

## 1. Problem Analysis & Requirements Breakdown

### What is a HashMap?

**Definition:** A HashMap is a key-value store that offers **average O(1)** lookup, insertion, and deletion. It works by hashing the key to a bucket index and storing the entry in that bucket. When multiple keys land on the same bucket, they collide and must be resolved.

**Real-world analogy:** Like a wall of numbered mailboxes:

| HashMap concept       | Mailbox analogy                        |
|-----------------------|----------------------------------------|
| Key                   | Recipient's name                       |
| Hash function         | Rule that maps a name to a box number  |
| Bucket / slot         | An individual mailbox                  |
| Collision             | Two people share the same mailbox      |
| Chaining              | Stack of letters inside one mailbox    |
| Resize / rehash       | Move to a bigger wall with more boxes  |

### What Are We Building?

- **Caller asks:** "Store this key-value pair" or "Give me the value for this key".
- **System answers:** stored/updated, the value (or `null`), removed value, `boolean` for `containsKey`.
- **Under the hood:** hash the key, find the bucket, walk the chain, decide insert/update/return/remove.

**Hidden complexities:**

- **Bad hashes** – `hashCode()` can be negative; naive `%` gives a negative index.
- **Collisions** – multiple keys hitting the same bucket must coexist without overwriting each other.
- **Load balance** – as the map fills up, chains grow and lookups degrade toward O(n).
- **Resize / rehash** – growing the bucket array requires re-inserting every entry at its new index.
- **Update vs insert** – putting an existing key must overwrite, not chain a duplicate.

### Core Requirements

| Requirement                | Description                                       | Why it matters              |
|----------------------------|---------------------------------------------------|-----------------------------|
| Generic key-value support  | `MyHashMap<K, V>` with any hashable key           | Real-world usage            |
| Constant-time average ops  | `put`, `get`, `remove` amortized O(1)             | Core value proposition      |
| Collision handling         | Separate chaining via singly-linked nodes         | Correctness under conflicts |
| Load-factor based resize   | Grow (2x) when `size / capacity > loadFactor`     | Keeps chains short          |
| Update semantics           | Re-`put` on same key replaces value               | Standard map behaviour      |
| Safe hash mapping          | `Math.floorMod(hash, capacity)` for non-negatives | Avoids negative indices     |

### High-Level Flow

```text
put(key, value)
  -> index = floorMod(key.hashCode(), buckets.length)
  -> walk chain at buckets[index]
       - if key present -> overwrite value, return
  -> insert new node at head of chain
  -> size++
  -> if size / capacity > loadFactor -> resize (2x, rehash all entries)

get(key)
  -> index = floorMod(key.hashCode(), buckets.length)
  -> walk chain -> return matching value or null

remove(key)
  -> index = floorMod(key.hashCode(), buckets.length)
  -> walk chain with prev/curr pointers
  -> unlink curr, size--, return old value
```

### Design Goals

- **Correctness first** – handles negative hashes, duplicates, empty buckets, resize during high fill.
- **Interview-friendly** – small enough to reason about, complete enough to demo real internals.
- **Clear separation** – hashing, chain traversal, and resize are separate concerns in the code.

---

## 2. Actors & Use Cases

### Actors

- **Client code** – any component that stores or looks up values by key.

### Use Cases

| Use case            | Actor  | Description                                | Success           | Failure        |
|---------------------|--------|--------------------------------------------|-------------------|----------------|
| Insert entry        | Client | `put(k, v)` adds or overwrites             | value stored      | (none thrown)  |
| Lookup by key       | Client | `get(k)` returns value or null             | value returned    | returns null   |
| Remove by key       | Client | `remove(k)` returns old value or null      | value returned    | returns null   |
| Check membership    | Client | `containsKey(k)` returns boolean           | true/false        | (none thrown)  |
| Trigger resize      | Client | Enough inserts to breach load factor       | rehashed silently | (none thrown)  |

### `put` – Step-by-step (this codebase)

1. Compute `index = Math.floorMod(key.hashCode(), buckets.length)`.
2. Walk the linked list at `buckets[index]`.
3. If a node's key equals the given key, overwrite its `value` and return.
4. Otherwise, allocate a new `Node`, insert it at the **head** of the chain, and increment `size`.
5. If `size / buckets.length > loadFactor`, call `resize()`.

### `resize` – Step-by-step

1. Save reference to the current bucket array as `oldBuckets`.
2. Allocate a new array of `oldBuckets.length * 2`.
3. Reset `size = 0`.
4. Walk every chain of `oldBuckets` and re-`put` each node (which re-hashes against the new capacity).

---

## 3. Core Entities & Responsibilities

### File layout

```text
src/
├── MyHashMap.java   Bucket array + chaining + resize
└── Main.java        Demo driver (put/get/remove/resize)
```

### 1. `MyHashMap<K, V>`

- **State:**
  - `Node<K, V>[] buckets` – array of bucket heads
  - `int size` – total entries across all chains
  - `float loadFactor` – e.g. `0.75f`
- **Behaviour:**
  - `put(K, V)`, `get(K)`, `remove(K)`, `containsKey(K)`, `size()`, `isEmpty()`, `printBuckets()`
  - Private: `hash(K)`, `resize()`
- **Responsibility:** own the bucket array, guarantee average O(1) operations, keep the map correct across resizes.

### 2. `MyHashMap.Node<K, V>` (static inner class)

- **State:** `final K key`, `V value`, `Node<K, V> next`.
- **Responsibility:** one entry inside a bucket chain.

### 3. `Main`

- **Responsibility:** demonstrate inserts, collisions, updates, removals, and a resize.

---

## 4. Relationships & Associations

| Relationship  | Example                              | Meaning                            |
|---------------|--------------------------------------|------------------------------------|
| Composition   | `MyHashMap` → `Node[] buckets`       | Map owns the bucket array          |
| Composition   | Bucket → chain of `Node`             | Each bucket owns its linked chain  |
| Dependency    | `Main` → `MyHashMap`                 | Demo driver uses the map           |

---

## 5. Design Choices

### 5.1 Separate chaining (vs open addressing)

- **Chosen:** singly-linked list per bucket.
- **Why:** simpler to reason about; deletion is straightforward; resize logic stays linear in `size`.
- **Trade-off:** extra memory per entry (the `next` pointer). Open addressing (linear/quadratic probing) is more cache-friendly but harder to implement correctly for LLD demos.

### 5.2 Head insertion for new nodes

- **Chosen:** new nodes go to the **head** of the chain.
- **Why:** O(1) insertion — no need to walk to the tail after a miss.
- **Trade-off:** iteration order within a bucket is LIFO. Fine for a hash map (which offers no order guarantee).

### 5.3 `Math.floorMod` for index

- **Chosen:** `Math.floorMod(key.hashCode(), buckets.length)`.
- **Why:** `hashCode()` can be negative; plain `%` in Java returns a negative remainder for negative operands. `floorMod` always returns a value in `[0, buckets.length)`.

### 5.4 Load factor 0.75

- **Chosen:** default `0.75f`, matching `java.util.HashMap`.
- **Why:** empirically balances space usage against chain length. Lower values waste memory; higher values grow chains and slow lookups.

### 5.5 Grow by 2x

- **Chosen:** double the bucket array on resize.
- **Why:** amortized O(1) inserts; power-of-two sizing works well with hash mixing (though this demo uses `floorMod`, not a bitmask).

---

## 6. Complexity

| Operation      | Average | Worst case (all keys collide) |
|----------------|---------|-------------------------------|
| `put`          | O(1)    | O(n)                          |
| `get`          | O(1)    | O(n)                          |
| `remove`       | O(1)    | O(n)                          |
| `containsKey`  | O(1)    | O(n)                          |
| `resize`       | O(n)    | O(n) (amortized O(1) per put) |

Worst case assumes a pathological hash function. In practice, `Object.hashCode()` distributes keys well enough that chains stay short.

---

## 7. API Design Choices

### `null` for missing keys

- `get` and `remove` return `null` when the key is absent. This mirrors `java.util.HashMap` and keeps the API exception-free.

### `containsKey` via `get`

- Implemented as `get(key) != null`. This is intentionally simple, but note it cannot distinguish "absent key" from "present key mapped to null". This map does not officially support null values in that ambiguous sense.

### Debug helper

- `printBuckets()` prints each bucket's chain, useful when demonstrating collisions on stage or in a walkthrough video.

---

## 8. How to Run

**Prerequisites:** Java 17+ (or compatible JDK).

**Compile:**

```bash
cd problems/java/HashMap
mkdir -p out
javac -d out src/MyHashMap.java src/Main.java
```

**Run:**

```bash
java -cp out Main
```

**Example client usage:**

```java
MyHashMap<String, Integer> map = new MyHashMap<>(8, 0.75f);

map.put("apple", 1);
map.put("banana", 2);
map.put("banana", 20);          // overwrite

Integer v = map.get("banana");  // 20
map.remove("apple");
boolean has = map.containsKey("apple"); // false
int n = map.size();

map.printBuckets();
```

---

## 9. End-to-End Flow (summary)

1. Construct `MyHashMap` with a capacity and load factor.
2. Every `put` computes an index, walks the chain, and either updates or inserts.
3. Once the load factor threshold is exceeded, `resize()` allocates a bigger array and rehashes every entry.
4. `get`, `remove`, and `containsKey` are all chain walks at a single bucket.

---

## 10. Extensibility

| Change                          | Effort | How                                                              |
|---------------------------------|--------|------------------------------------------------------------------|
| Iteration order (LinkedHashMap) | Medium | Add doubly-linked global list of entries; update on put/remove.  |
| Concurrent access               | Medium | Wrap operations with locks or move to segment/CAS-based design.  |
| Tree-ify long chains            | High   | Convert bucket chains above a threshold into red-black trees.    |
| Custom hash mixing              | Low    | Add a `spread(int h)` step similar to JDK's `(h ^ (h >>> 16))`.  |
| Shrink on remove                | Low    | Halve the array when `size / capacity` drops below a threshold.  |

---

## 11. Summary

- **Entities:** `MyHashMap<K, V>`, `MyHashMap.Node<K, V>`, `Main` (demo).
- **Techniques:** separate chaining, `floorMod` indexing, load-factor-triggered 2x resize.
- **Complexity:** average O(1) for `put`/`get`/`remove`; amortized O(1) with resize.
- **Scope:** in-process, single-threaded LLD demo. A great starting point for interviews or a design-walkthrough video.

This README reflects the current Java codebase in this repository. A Python port lives under [problems/python/HashMap](../../python/HashMap).
