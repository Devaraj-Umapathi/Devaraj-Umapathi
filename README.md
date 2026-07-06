# Low-Level Design (LLD)

A hands-on repository for **Low-Level Design** fundamentals: UML class diagrams, SOLID principles, design patterns, and classic LLD problems. Each topic includes **Java** and **Python** implementations where applicable, with READMEs and examples you can run locally.

---

## What this repo covers

- **UML & class diagrams** — How to model structure and relationships before coding.
- **SOLID principles** — Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
- **Design patterns** — Creational, structural, and behavioral patterns with code (with/without pattern comparisons where relevant).
- **Problems** — End-to-end designs (e.g. Parking Lot, Vending Machine, Rate Limiter) that tie together patterns and principles.

---

## Repository structure

| Section           | Description                                      |
|-------------------|--------------------------------------------------|
| [UML Diagrams](#uml-diagrams) | Notes and tutorials on UML, with focus on class diagrams. |
| [SOLID Principles](#solid-principles) | Single Responsibility, Open/Closed, Liskov, Interface Segregation, Dependency Inversion. |
| [Design Patterns](#design-patterns) | Pattern explanations and code (Java + Python).  |
| [Problems](#problems)        | Full LLD problems with Java and Python solutions. |

---

## UML Diagrams

Intro to UML and class diagrams (elements, relationships, visibility, multiplicity).

| Topic           | Link |
|-----------------|------|
| Class Diagram   | [uml-diagrams/class-diagram](uml-diagrams/class-diagram) |

---

## SOLID Principles

Java and Python examples (with and without violations) under `SOLID-Principles/java/` and `SOLID-Principles/python/`.

| Java | Python |
|------|--------|
| [SOLID-Principles/java](SOLID-Principles/java) | [SOLID-Principles/python](SOLID-Principles/python) |

---

## Design Patterns

Java and Python code live under `design-patterns/java/<pattern>/` and `design-patterns/python/<pattern>/`. Each pattern folder has a README and runnable examples.

| Pattern              | Java | Python |
|----------------------|------|--------|
| Abstract Factory     | [design-patterns/java/AbstractFactory-DesignPattern](design-patterns/java/AbstractFactory-DesignPattern) | [design-patterns/python/AbstractFactory-DesignPattern](design-patterns/python/AbstractFactory-DesignPattern) |
| Adapter              | [design-patterns/java/Adapter-DesignPattern](design-patterns/java/Adapter-DesignPattern) | [design-patterns/python/Adapter-DesignPattern](design-patterns/python/Adapter-DesignPattern) |
| Builder              | [design-patterns/java/Builder-DesignPattern](design-patterns/java/Builder-DesignPattern) | [design-patterns/python/Builder-DesignPattern](design-patterns/python/Builder-DesignPattern) |
| Decorator            | [design-patterns/java/Decorator-DesignPattern](design-patterns/java/Decorator-DesignPattern) | [design-patterns/python/Decorator-DesignPattern](design-patterns/python/Decorator-DesignPattern) |
| Factory              | [design-patterns/java/Factory-DesignPattern](design-patterns/java/Factory-DesignPattern) | [design-patterns/python/Factory-DesignPattern](design-patterns/python/Factory-DesignPattern) |
| Observer             | [design-patterns/java/Observer-DesignPattern](design-patterns/java/Observer-DesignPattern) | [design-patterns/python/Observer-DesignPattern](design-patterns/python/Observer-DesignPattern) |
| Singleton            | [design-patterns/java/Singleton-DesignPattern](design-patterns/java/Singleton-DesignPattern) | [design-patterns/python/Singleton-DesignPattern](design-patterns/python/Singleton-DesignPattern) |
| State                | [design-patterns/java/State-DesignPattern](design-patterns/java/State-DesignPattern) | [design-patterns/python/State-DesignPattern](design-patterns/python/State-DesignPattern) |
| Strategy             | [design-patterns/java/Strategy-DesignPattern](design-patterns/java/Strategy-DesignPattern) | [design-patterns/python/Strategy-DesignPattern](design-patterns/python/Strategy-DesignPattern) |

---

## Problems

Java and Python implementations live under `problems/java/<problem>/` and `problems/python/<problem>/`.

| Problem            | Java | Python |
|--------------------|------|--------|
| Parking Lot System | [problems/java/ParkingLotSystem](problems/java/ParkingLotSystem) | [problems/python/ParkingLotSystem](problems/python/ParkingLotSystem) |
| Vending Machine    | [problems/java/VendingMachineDemo](problems/java/VendingMachineDemo) | [problems/python/VendingMachineDemo](problems/python/VendingMachineDemo) |
| Rate Limiter       | [problems/java/RateLimiter](problems/java/RateLimiter) | [problems/python/RateLimiter](problems/python/RateLimiter) |
| Meeting Scheduler  | [problems/java/MeetingScheduler](problems/java/MeetingScheduler) | [problems/python/MeetingScheduler](problems/python/MeetingScheduler) |
| HashMap            | [problems/java/HashMap](problems/java/HashMap) | [problems/python/HashMap](problems/python/HashMap) |

---

## Running the code

- **Java:** Open the project in an IDE or use `javac`/`java` from the relevant `java/` (or `java/src/...`) directory.
- **Python:** From the pattern or problem folder (e.g. `design-patterns/python/Adapter-DesignPattern`), set `PYTHONPATH=.` so the `src` package is found, then run the module. Example:
  ```bash
  cd design-patterns/python/Adapter-DesignPattern && PYTHONPATH=. python3 -m src.demo
  ```
  For problems (e.g. Parking Lot, Rate Limiter), run from the problem folder with `PYTHONPATH=src`:
  ```bash
  cd problems/python/ParkingLotSystem && PYTHONPATH=src python3 src/parking_lot_demo.py
  cd problems/python/RateLimiter && PYTHONPATH=src python3 src/main.py
  cd problems/python/MeetingScheduler && PYTHONPATH=src python3 src/main.py
  cd problems/python/HashMap && PYTHONPATH=src python3 src/main.py
  ```

---

## Contributing

Contributions are welcome! 

1. **Fork the repository**
2. **Create a feature branch:** `git checkout -b feature/your-feature-name`
3. **Commit your changes:** `git commit -m 'Add some feature'`
4. **Push to the branch:** `git push origin feature/your-feature-name`
5. **Submit a pull request**

Please make sure to update README files and documentation as appropriate.
