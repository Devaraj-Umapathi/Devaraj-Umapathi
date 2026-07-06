from my_hash_map import MyHashMap


def main() -> None:
    hash_map: MyHashMap[str, int] = MyHashMap(capacity=8, load_factor=0.75)

    hash_map.put("apple", 1)
    hash_map.put("banana", 2)
    hash_map.put("cherry", 3)
    hash_map.put("date", 4)
    hash_map.put("elderberry", 5)

    print(f'get("banana") = {hash_map.get("banana")}')
    print(f'get("cherry") = {hash_map.get("cherry")}')
    print(f"size = {hash_map.size()}")

    print("\n--- Bucket layout ---")
    hash_map.print_buckets()

    hash_map.put("banana", 20)
    print(f'\nAfter update banana -> 20: {hash_map.get("banana")}')

    hash_map.remove("apple")
    print(f'After remove apple, contains? {hash_map.contains_key("apple")}')
    print(f"size = {hash_map.size()}")

    for i in range(20):
        hash_map.put(f"k{i}", i)
    print("\nAfter inserting 20 more keys (resize triggered):")
    print(f"size = {hash_map.size()}")
    print(f'get("k15") = {hash_map.get("k15")}')


if __name__ == "__main__":
    main()
