# HashMap – Python LLD

Python port of the Java HashMap under [problems/java/HashMap](../../java/HashMap). Same design: **separate chaining** via singly-linked nodes, **load-factor triggered 2x resize**, and `put` / `get` / `remove` / `contains_key` operations with average O(1) complexity.

For problem analysis, design choices, complexity discussion, and extensibility notes, see the [Java README](../../java/HashMap/README.md).

---

## Layout

```text
src/
├── my_hash_map.py   Bucket list + chaining + resize
└── main.py          Demo driver
```

---

## How to run

From this directory:

```bash
cd problems/python/HashMap
PYTHONPATH=src python3 src/main.py
```

---

## Notes vs Java

- **API:** methods use snake_case — `put`, `get`, `remove`, `contains_key`, `size`, `is_empty`, `print_buckets`. Behaviour matches the Java implementation.
- **Pythonic sugar (in addition to the core API):**
  - `map[key] = value` / `map[key]` / `del map[key]`
  - `key in map`
  - `len(map)`
  - `for key in map` (iterates keys)
- **Hash:** `hash(key) % capacity`. Python's `hash()` can return a negative int, but `%` in Python floors toward negative infinity so the result is always non-negative — the Java code uses `Math.floorMod` for the same reason.
- **Missing key semantics:** `get` returns `None` (like Java `null`). The `[]` indexer raises `KeyError`, matching Python `dict` conventions.
- **Concurrency:** single-threaded like the Java demo. Not thread-safe.

---

## Example

```python
from my_hash_map import MyHashMap

m: MyHashMap[str, int] = MyHashMap(capacity=8, load_factor=0.75)

m.put("apple", 1)
m["banana"] = 2            # dunder sugar
m.put("banana", 20)        # overwrite

value = m.get("banana")    # 20
"apple" in m               # True
del m["apple"]
n = len(m)

m.print_buckets()
```

This README reflects the Python codebase in this folder.
