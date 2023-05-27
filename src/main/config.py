line1  = '    @ConfigSection('
line2  = '            name = "{ucName}",'
line3  = '            description = "{ucName} Skill",'
line4  = '            position = 9,'
line5  = '            closedByDefault = true'
line6  = '    )'
line7  = '    String {lcName}Skill = "{lcName}Skill";'
line8  = ''
line9  = '    @ConfigItem('
line10 = '            keyName = "enable{ucName}",'
line11 = '            position = 0,'
line12 = '            name = "Enabled",'
line13 = '            description = "Configures whether or not {lcName} skill is enabled.",'
line14 = '            section = {lcName}Skill'
line15 = '    )'
line16 = '    default boolean enable{ucName}Skill() {{ return false; }}'
line17 = ''
line18 = '    @ConfigItem('
line19 = '            keyName = "{lcName}ProgressColor",'
line20 = '            position = 1,'
line21 = '            name = "Progress Color",'
line22 = '            description = "Configures the progress color.",'
line23 = '            section = {lcName}Skill'
line24 = '    )'
line25 = '    default Color {lcName}ProgressColor() {{ return Color.decode("#30FCAB"); }}'
line26 = ''
line27 = '    @ConfigItem('
line28 = '            keyName = "{lcName}XpGoal",'
line29 = '            position = 2,'
line30 = '            name = "Target Xp",'
line31 = '            description = "Configures the xp goal.",'
line32 = '            section = {lcName}Skill'
line33 = '    )'
line34 = '    default int {lcName}XpGoal() {{ return 0; }}'
line35 = ''
line36 = '    @ConfigItem('
line37 = '            keyName = "{lcName}ResetType",'
line38 = '            position = 3,'
line39 = '            name = "Reset Interval",'
line40 = '            description = "Configured how often skill progress resets.",'
line41 = '            section = {lcName}Skill'
line42 = '    )'
line43 = '    default ResetType {lcName}ResetType() {{ return ResetType.DAILY; }}'
line44 = ''
line45 = '    @ConfigItem('
line46 = '            keyName = "{lcName}Monday",'
line47 = '            position = 4,'
line48 = '            name = "Monday",'
line49 = '            description = "Configures whether or not progress is recorded on Mondays.",'
line50 = '            section = {lcName}Skill'
line51 = '    )'
line52 = '    default boolean {lcName}Monday() {{ return false; }}'
line53 = ''
line54 = '    @ConfigItem('
line55 = '            keyName = "{lcName}Tuesday",'
line56 = '            position = 5,'
line57 = '            name = "Tuesday",'
line58 = '            description = "Configures whether or not progress is recorded on Tuesdays.",'
line59 = '            section = {lcName}Skill'
line60 = '     )'
line61 = '     default boolean {lcName}Tuesday() {{ return false; }}'
line62 = ''
line63 = '     @ConfigItem('
line64 = '           keyName = "{lcName}Wednesday",'
line65 = '           position = 6,'
line66 = '           name = "Wednesday",'
line67 = '           description = "Configures whether or not progress is recorded on Wednesdays.",'
line68 = '           section = {lcName}Skill'
line69 = '     )'
line70 = '     default boolean {lcName}Wednesday() {{ return false; }}'
line71 = ''
line72 = '     @ConfigItem('
line73 = '           keyName = "{lcName}Thursday",'
line74 = '           position = 7,'
line75 = '           name = "Thursday",'
line76 = '           description = "Configures whether or not progress is recorded on Thursdays.",'
line77 = '           section = {lcName}Skill'
line78 = '     )'
line79 = '     default boolean {lcName}Thursday() {{ return false; }}'
line80 = ''
line81 = '     @ConfigItem('
line82 = '           keyName = "{lcName}Friday",'
line83 = '           position = 8,'
line84 = '           name = "Friday",'
line85 = '           description = "Configures whether or not progress is recorded on Fridays.",'
line86 = '           section = {lcName}Skill'
line87 = '     )'
line88 = '     default boolean {lcName}Friday() {{ return false; }}'
line89 = ''
line90 = '     @ConfigItem('
line91 = '           keyName = "{lcName}Saturday",'
line92 = '           position = 9,'
line93 = '           name = "Saturday",'
line94 = '           description = "Configures whether or not progress is recorded on Saturdays.",'
line95 = '           section = {lcName}Skill'
line96 = '     )'
line97 = '     default boolean {lcName}Saturday() {{ return false; }}'
line98 = ''
line99 = '     @ConfigItem('
line100 = '           keyName = "{lcName}Sunday",'
line101 = '           position = 10,'
line102 = '           name = "Sunday",'
line103 = '           description = "Configures whether or not progress is recorded on Sundays.",'
line104 = '           section = {lcName}Skill'
line105 = '     )'
line106 = '     default boolean {lcName}Sunday() {{ return false; }}'
line107 = ''
line108 = '     @ConfigItem('
line109 = '           keyName = "{lcName}StartTime",'
line110= '            position = 11,'
line111 = '           name = "Start Time",'
line112 = '           description = "Configures the start time for when progress is recorded each day.",'
line113 = '           section = {lcName}Skill'
line114 = '      )'
line115 = '      default Hour {lcName}StartTime() {{ return Hour.NONE; }}'
line116 = ''
line117 = '      @ConfigItem('
line118 = '            keyName = "{lcName}EndTime",'
line119 = '            position = 12,'
line120 = '            name = "End Time",'
line121 = '            description = "Configures the end time for when progress is recorded each day.",'
line122 = '            section = {lcName}Skill'
line123 = '      )'
line124 = '      default Hour {lcName}EndTime() {{ return Hour.NONE; }}'
line125 = ''
line126 = '      @ConfigItem('
line127 = '            keyName = "{lcName}DayCadence",'
line128 = '            position = 13,'
line129 = '            name = "Day Cadence",'
line130 = '            description = "Configures the day cadence for when progress is recorded. For example a 5 day cadence goes (1, 2, 3, 4, 5), then repeats.",'
line131 = '            section = {lcName}Skill'
line132 = '      )'
line133 = '      default DayCadence {lcName}DayCadence() {{ return DayCadence.NONE; }}'
line134 = ''
line135 = '      @ConfigItem('
line136 = '            keyName = "{lcName}CadenceDay",'
line137 = '            position = 14,'
line138 = '            name = "Day Cadence",'
line139 = '            description = "Configures on which day in the cadence progress is recorded.",'
line140 = '            section = {lcName}Skill'
line141 = '      )'
line142 = '      default DayCadence {lcName}DayCadenceDay() {{ return DayCadence.NONE; }}'
line143 = ''
line144 = '      @ConfigItem('
line145 = '      keyName = "{ucName}VisibilityPatterns",'
line146 = '            position = 15,'
line147 = '            name = "Custom Patterns",'
line148 = '            description = "Configures custom record patterns. See plugin page about making custom patterns.",'
line149 = '            section = {lcName}Skill'
line150 = '      )'
line151 = '      default String {lcName}Pattens() {{ return ""; }}'
line152 = ''

config = line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5 + "\n" + line6 + "\n" + line7 + "\n" + line8 + \
         "\n" + line9 + "\n" + line10 + "\n" + line11 + "\n" + line12 + "\n" + line13 + "\n" + line14 + "\n" + line15 + "\n" + \
         line16 + "\n" + line17 + "\n" + line18 + "\n" + line19 + "\n" + line20 + "\n" + line21 + "\n" + line22 + "\n" + line23 + \
         "\n" + line24 + "\n" + line25 + "\n" + line26 + "\n" + line27 + "\n" + line28 + "\n" + line29 + "\n" + line30 + "\n" + line31 + \
         "\n" + line32 + "\n" + line33 + "\n" + line34 + "\n" + line35 + "\n" + line36 + "\n" + line37 + "\n" + line38 + "\n" + line39 + \
         "\n" + line40 + "\n" + line41 + "\n" + line42 + "\n" + line43 + "\n" + line44 + "\n" + line45 + "\n" + line46 + "\n" + line47 + \
         "\n" + line48 + "\n" + line49 + "\n" + line50 + "\n" + line51 + "\n" + line52 + "\n" + line53 + "\n" + line54 + "\n" + line55 + \
         "\n" + line56 + "\n" + line57 + "\n" + line58 + "\n" + line59 + "\n" + line60 + "\n" + line61 + "\n" + line62 + "\n" + line63 + \
         "\n" + line64 + "\n" + line65 + "\n" + line66 + "\n" + line67 + "\n" + line68 + "\n" + line69 + "\n" + line70 + "\n" + line71 + \
         "\n" + line72 + "\n" + line73 + "\n" + line74 + "\n" + line75 + "\n" + line76 + "\n" + line77 + "\n" + line78 + "\n" + line79 + \
         "\n" + line80 + "\n" + line81 + "\n" + line82 + "\n" + line83 + "\n" + line84 + "\n" + line85 + "\n" + line86 + "\n" + line87 + \
         "\n" + line88 + "\n" + line89 + "\n" + line90 + "\n" + line91 + "\n" + line92 + "\n" + line93 + "\n" + line94 + "\n" + line95 + \
         "\n" + line96 + "\n" + line97 + "\n" + line98 + "\n" + line99 + "\n" + line100 + "\n" + line101 + "\n" + line102 + \
         "\n" + line103 + "\n" + line104 + "\n" + line105 + "\n" + line106 + "\n" + line107 + "\n" + line108 + "\n" + line109 + \
         "\n" + line110 + "\n" + line111 + "\n" + line112 + "\n" + line113 + "\n" + line114 + "\n" + line115 + "\n" + line116 + \
         "\n" + line117 + "\n" + line118 + "\n" + line119 + "\n" + line120 + "\n" + line121 + "\n" + line122 + "\n" + line123 + \
         "\n" + line124 + "\n" + line125 + "\n" + line126 + "\n" + line127 + "\n" + line128 + "\n" + line129 + "\n" + line130 + \
         "\n" + line131 + "\n" + line132 + "\n" + line133 + "\n" + line134 + "\n" + line135 + "\n" + line136 + "\n" + line137 + \
         "\n" + line138 + "\n" + line139 + "\n" + line140 + "\n" + line141 + "\n" + line142 + "\n" + line143 + "\n" + line144 + \
         "\n" + line145 + "\n" + line146 + "\n" + line147 + "\n" + line148 + "\n" + line149 + "\n" + line150 + "\n" + line151 + "\n"

skills = [
    'attack',
    'strength',
    'defense',
    'ranged',
    'prayer',
    'magic',
    'runecrafting',
    'construction',
    'hitpoints',
    'agility',
    'herblore',
    'thieving',
    'crafting',
    'fletching',
    'slayer',
    'hunter',
    'mining',
    'smithing',
    'fishing',
    'cooking',
    'firemaking',
    'woodcutting',
    'farming'
]

for skill in skills:
    print(config.format(ucName=skill.capitalize(), lcName=skill))