# Xp Goalz

![goalz_8](https://github.com/erversteeg/Xp-Goals/assets/2341316/9200b49e-93b2-4fa3-89f8-66f8faa32f0f)

Track xp goals

## Custom Patterns

The format for custom patterns is one per line [Y]->[M]->[W]->[D]->[H] = XP

- Y = Year (2022, 2023, ...)
- M = Month (Apr, May, ..., Dec)
- W = Week (Week of Month or year) (1, 2, ..., ((28, 30, 31) (52))
- D = Day (Day of week, month, or year) (1, 2, ..., (7, (28, 30, 31), 365), or for day of week only (Mon, Tue, ..., Sun)
- H = Hour (Hour of day) (0, 1, ..., 23) 
- XP = Target Xp (250, 50,000)

Any of [Y], [M], [W], [D], or [H] can be omitted.

Omit XP for configured Target Xp

For cadences the format is [(M,W,D,H)|~offet|interval]

#### Example 1 - Different times for different days of the week
[Mon]->[3pm-5pm]=20000

[Wed]->[7pm-10pm]=30000

#### Example 2 - Every other day in 2023
[2023]->[D|2]

#### Example 3 - Every third day of each month starting on the 3rd
[M]->[D|~2|3]

#### Example 4 - April and May 5th 2pm - 6pm
[Apr-May]->[D^5]->[2pm-6pm]

#### Example 5 - 21st week of the year
[Y]->[W^21]

#### Example 6 - 50th & 149th day of the year
[Y]->[D^(50,149)]

#### Example 7 - 2nd and 4th Saturday of April
[Apr]->[W^(2,4)]->[Sat]

#### Example 8 - Monday, Wednesday, and Friday in July
[Jul]->[W]->[D^(1, 3, 5)]

#### Example 9 - October - December, 3rd week and the 5th day of December only
[Oct-Dec]->[W^3]

[Dec]->[D^5]
