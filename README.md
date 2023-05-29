# Xp Goalz

Set XP goals. For example:

- 50,000 Firemaking xp / hour only on Saturday and Sunday between noon and 3pm
- 25,000 Strength xp / day on Mondays, Wednesdays, and Fridays
- Rotate between Runecrafting, Smithing, and Herblore each day starting at 8pm

Optionally configure the follwing for each skill:
- Target Xp
- Reset Interval
  - Hourly
  - Daily
  - Weekly
  - Monthly
  - Yearly
- Days of the Week
- Start Time
- End Time
- Day Cadence

More advanced configurations with custom patterns.

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
