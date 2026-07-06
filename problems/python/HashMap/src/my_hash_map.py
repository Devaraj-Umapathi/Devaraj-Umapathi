from typing import Generic, Iterator, List, Optional, TypeVar

K = TypeVar("K")
V = TypeVar("V")


class _Node(Generic[K, V]):
    __slots__ = ("key", "value", "next")

    def __init__(self, key: K, value: V) -> None:
        self.key: K = key
        self.value: V = value
        self.next: Optional["_Node[K, V]"] = None


class MyHashMap(Generic[K, V]):
    DEFAULT_CAPACITY = 16
    DEFAULT_LOAD_FACTOR = 0.75

    def __init__(
        self,
        capacity: int = DEFAULT_CAPACITY,
        load_factor: float = DEFAULT_LOAD_FACTOR,
    ) -> None:
        if capacity <= 0:
            raise ValueError("capacity must be positive")
        if not (0 < load_factor < 1):
            raise ValueError("load_factor must be in (0, 1)")

        self._buckets: List[Optional[_Node[K, V]]] = [None] * capacity
        self._load_factor: float = load_factor
        self._size: int = 0

    def _hash(self, key: K) -> int:
        return hash(key) % len(self._buckets)

    def put(self, key: K, value: V) -> None:
        index = self._hash(key)
        head = self._buckets[index]

        node = head
        while node is not None:
            if node.key == key:
                node.value = value
                return
            node = node.next

        new_node: _Node[K, V] = _Node(key, value)
        new_node.next = head
        self._buckets[index] = new_node
        self._size += 1

        if self._size / len(self._buckets) > self._load_factor:
            self._resize()

    def get(self, key: K) -> Optional[V]:
        index = self._hash(key)
        node = self._buckets[index]
        while node is not None:
            if node.key == key:
                return node.value
            node = node.next
        return None

    def remove(self, key: K) -> Optional[V]:
        index = self._hash(key)
        prev: Optional[_Node[K, V]] = None
        curr = self._buckets[index]

        while curr is not None:
            if curr.key == key:
                if prev is None:
                    self._buckets[index] = curr.next
                else:
                    prev.next = curr.next
                self._size -= 1
                return curr.value
            prev = curr
            curr = curr.next
        return None

    def contains_key(self, key: K) -> bool:
        return self.get(key) is not None

    def size(self) -> int:
        return self._size

    def is_empty(self) -> bool:
        return self._size == 0

    def _resize(self) -> None:
        old_buckets = self._buckets
        self._buckets = [None] * (len(old_buckets) * 2)
        self._size = 0

        for head in old_buckets:
            node = head
            while node is not None:
                self.put(node.key, node.value)
                node = node.next

    def print_buckets(self) -> None:
        for i, head in enumerate(self._buckets):
            entries: List[str] = []
            node = head
            while node is not None:
                entries.append(f"{node.key}={node.value}")
                node = node.next
            print(f"Bucket[{i}]: " + " -> ".join(entries))

    def __len__(self) -> int:
        return self._size

    def __contains__(self, key: object) -> bool:
        return self.contains_key(key)  # type: ignore[arg-type]

    def __getitem__(self, key: K) -> V:
        value = self.get(key)
        if value is None and not self.contains_key(key):
            raise KeyError(key)
        return value  # type: ignore[return-value]

    def __setitem__(self, key: K, value: V) -> None:
        self.put(key, value)

    def __delitem__(self, key: K) -> None:
        if self.remove(key) is None and not self.contains_key(key):
            raise KeyError(key)

    def __iter__(self) -> Iterator[K]:
        for head in self._buckets:
            node = head
            while node is not None:
                yield node.key
                node = node.next
